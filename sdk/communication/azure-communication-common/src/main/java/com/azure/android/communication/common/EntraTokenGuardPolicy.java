// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.communication.common;

import com.azure.android.core.http.HttpPipelinePolicy;
import com.azure.android.core.http.HttpPipelinePolicyChain;
import com.azure.android.core.http.HttpRequest;
import com.azure.android.core.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Pipeline policy that caches and validates Entra and ACS tokens in HTTP responses.
 */
public final class EntraTokenGuardPolicy implements HttpPipelinePolicy {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private String entraTokenCache;
    private HttpResponse responseCache;

    @Override
    public void process(HttpPipelinePolicyChain chain) {
        HttpRequest request = chain.getRequest();
        String currentEntraToken = request.getHeaders().get(AUTHORIZATION_HEADER).getValue();
        boolean entraTokenCacheValid = isEntraTokenCacheValid(currentEntraToken);

        if (entraTokenCacheValid && isAcsTokenCacheValid()) {
            chain.processNextPolicy(request);
        } else {
            entraTokenCache = currentEntraToken;
            chain.processNextPolicy(request);
        }
    }

    private boolean isEntraTokenCacheValid(String currentEntraToken) {
        return entraTokenCache != null && !entraTokenCache.trim().isEmpty()
            && entraTokenCache.equals(currentEntraToken);
    }

    private boolean isAcsTokenCacheValid() {
        return responseCache != null && responseCache.getStatusCode() == 200 && isAcsTokenValid();
    }

    private boolean isAcsTokenValid() {
        try {
            String body = responseCache.getBodyAsString(Charset.forName("UTF-8"));
            JsonNode root = new ObjectMapper().readTree(body);
            JsonNode accessTokenNode = root.get("accessToken");
            CommunicationAccessToken accessToken = new TokenParser()
                .createAccessToken(accessTokenNode.get("token").asText());
            return accessToken.getExpiresAt().isBefore(org.threeten.bp.OffsetDateTime.now());
        } catch (RuntimeException | IOException e) {
            return false;
        }
    }
}
