// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.android.communication.common;

/**
 * The Entra Communication Token scopes.
 */
class EntraCommunicationTokenScopes {
    public static final String TEAMS_EXTENSION_SCOPE_PREFIX = "https://auth.msft.communication.azure.com/";
    public static final String COMMUNICATION_CLIENTS_SCOPE_PREFIX = "https://communication.azure.com/clients/";
    public static final String DEFAULT_SCOPES = COMMUNICATION_CLIENTS_SCOPE_PREFIX + ".default";
}

