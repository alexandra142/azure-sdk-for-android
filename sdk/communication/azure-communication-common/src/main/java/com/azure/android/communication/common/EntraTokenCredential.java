// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.communication.common;

import com.azure.android.core.http.HttpClient;
import com.azure.android.core.http.HttpPipeline;
import com.azure.android.core.http.HttpPipelineBuilder;
import com.azure.android.core.http.HttpPipelinePolicy;
import com.azure.android.core.http.policy.BearerTokenAuthenticationPolicy;
import com.azure.android.core.http.policy.RetryPolicy;

import java9.util.concurrent.CompletableFuture;

/**
 * The Azure Communication Services User token credential.
 * <p>
 * This class is used to cache/refresh the access token required by Azure Communication Services.
 */
public final class EntraTokenCredential  extends UserCredential {
    public static final String TEAMS_EXTENSION_SCOPE_PREFIX = "https://auth.msft.communication.azure.com/";
    public static final String COMMUNICATION_CLIENTS_SCOPE_PREFIX = "https://communication.azure.com/clients/";

    public static final String TEAMS_EXTENSION_ENDPOINT = "/access/teamsPhone/:exchangeAccessToken";
    public static final String TEAMS_EXTENSION_API_VERSION = "2025-03-02-preview";

    public static final String COMMUNICATION_CLIENTS_ENDPOINT = "/access/entra/:exchangeAccessToken";
    public static final String COMMUNICATION_CLIENTS = "2024-04-01-preview";

    private final String resourceEndpoint;
    private final String[] scopes;
    private final HttpPipeline pipeline;
    /**
     *
     * @return Get scopes.
     */
    public String[] getScopes() {
        return scopes;
    }

    /**
     * Creates an EntraTokenCredential using the provided EntraCommunicationTokenCredentialOptions.
     * This constructor is intended for scenarios where
     * an Entra user token is required for Azure Communication Services.
     *
     * @param entraTokenOptions The options for configuring the Entra token credential.
     */
    EntraTokenCredential(EntraCommunicationTokenCredentialOptions entraTokenOptions) {
        this(entraTokenOptions, null);
    }

    /**
     * For testing purposes: Creates an EntraTokenCredential
     * using the provided EntraCommunicationTokenCredentialOptions and HttpClient.
     * This constructor is intended for scenarios
     * where an Entra user token is required for Azure Communication Services.
     *
     * @param entraTokenOptions The options for configuring the Entra token credential.
     * @param httpClient The HTTP client to use for making requests.
     */
    EntraTokenCredential(EntraCommunicationTokenCredentialOptions entraTokenOptions, HttpClient httpClient) {
        this.resourceEndpoint = entraTokenOptions.getResourceEndpoint();
        this.scopes = entraTokenOptions.getScopes();
        this.pipeline = createPipelineFromOptions(entraTokenOptions, httpClient);

        this.exchangeEntraToken().subscribe();
    }

    private HttpPipeline createPipelineFromOptions(EntraCommunicationTokenCredentialOptions entraTokenOptions,
                                                   HttpClient httpClient) {
        BearerTokenAuthenticationPolicy authPolicy
                = new BearerTokenAuthenticationPolicy(entraTokenOptions.getTokenCredential(), scopes);
        HttpPipelinePolicy guardPolicy = new EntraTokenGuardPolicy();
        RetryPolicy retryPolicy = new RetryPolicy();
        HttpClient clientToUse = (httpClient != null) ? httpClient : HttpClient.createDefault();

        return new HttpPipelineBuilder().httpClient(clientToUse).policies(authPolicy, guardPolicy, retryPolicy).build();
    }

    @Override
    CompletableFuture<CommunicationAccessToken> getToken() {
        return null;
    }
}
