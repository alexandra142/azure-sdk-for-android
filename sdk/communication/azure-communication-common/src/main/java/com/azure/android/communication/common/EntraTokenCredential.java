// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.communication.common;

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

    private String resourceEndpoint;
    private String[] scopes;

    /**
     *
     * @return Get scopes.
     */
    public String[] getScopes() {
        return scopes;
    }

    @Override
    CompletableFuture<CommunicationAccessToken> getToken() {
        return null;
    }
}
