// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.android.communication.common;

import com.azure.android.core.credential.TokenCredential;


/**
 * Options for refreshing CommunicationTokenCredential
 * <p>
 * This class is used to define how CommunicationTokenCredential should be refreshed
 * </p>
 */
public final class EntraCommunicationTokenCredentialOptions {
    private final String[] defaultScopes = { EntraCommunicationTokenScopes.DEFAULT_SCOPES };

    private final String[] scopes;
    private final String resourceEndpoint;

    private final TokenCredential tokenCredential;

    /**
     * Creates an instance of {@link EntraCommunicationTokenCredentialOptions}.
     *
     * @param resourceEndpoint The URI of the Azure Communication Services resource.
     * @param tokenCredential The credential capable of fetching an Entra user token.
     * @param scopes The scopes required for the Entra user token. If not provided,
     * @throws IllegalArgumentException thrown if resourceEndpoint,tokenCredential
     * or scopes parameters fail the validation.
     */
    public EntraCommunicationTokenCredentialOptions(String resourceEndpoint, TokenCredential tokenCredential,
                                                    String[] scopes) {
        if (resourceEndpoint == null || resourceEndpoint.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "The initialization parameter [resourceEndpoint] cannot be null or empty.");
        }
        if (tokenCredential == null) {
            throw new IllegalArgumentException("The initialization parameter [tokenCredential] "
                + "cannot be null or empty.");
        }
        this.scopes = validateScopes(scopes);


        this.resourceEndpoint = resourceEndpoint;
        this.tokenCredential = tokenCredential;
    }

    private static String[] validateScopes(String[] scopes) {
        String errorMessage = "Scopes must not be null or empty. Ensure all scopes start with either "
            + "{EntraCommunicationTokenScopes.TeamsExtensionScopePrefix} or "
            + "{EntraCommunicationTokenScopes.CommunicationClientsScopePrefix}.";
        if (scopes == null || scopes.length == 0) {
            throw new IllegalArgumentException(errorMessage);
        }

        String firstValueStartsWith = getValidPrefix(scopes[0]);
        if (firstValueStartsWith == null) {
            throw new IllegalArgumentException(errorMessage);
        }

        for (int i = 1; i < scopes.length; i++) {
            if (!scopes[i].startsWith(firstValueStartsWith)) {
                throw new IllegalArgumentException(errorMessage);
            }
        }
        return  scopes;
    }

    private static String getValidPrefix(String scope) {
        if (scope.startsWith(EntraCommunicationTokenScopes.TEAMS_EXTENSION_SCOPE_PREFIX)) {
            return EntraCommunicationTokenScopes.TEAMS_EXTENSION_SCOPE_PREFIX;
        } else if (scope.startsWith(EntraCommunicationTokenScopes.COMMUNICATION_CLIENTS_SCOPE_PREFIX)) {
            return EntraCommunicationTokenScopes.COMMUNICATION_CLIENTS_SCOPE_PREFIX;
        }
        return null;
    }


    /**
     * Get the URI of the Azure Communication Services resource.
     * @return the URI of the Azure Communication Services resource.
     */
    public String getResourceEndpoint() {
        return this.resourceEndpoint;
    }

    /**
     * The credential capable of fetching an Entra user token.
     * @return the credential capable of fetching an Entra user token.
     */
    public TokenCredential getTokenCredential() {
        return this.tokenCredential;
    }

    /**
     * @return The scopes required for the Entra user token.
     */
    public String[] getScopes() {
        return this.scopes;
    }
}
