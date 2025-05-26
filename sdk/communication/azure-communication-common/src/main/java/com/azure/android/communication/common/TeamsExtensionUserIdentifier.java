// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.communication.common;

/**
 * Communication identifier for Microsoft Teams Phone user who is using a Communication Services resource
 * to extend their Teams Phone set up.
 */
public final class TeamsExtensionUserIdentifier extends CommunicationIdentifier {

    private final String userId;

    private final String tenantId;

    private final String resourceId;

    private final CommunicationCloudEnvironment cloudEnvironment;

    /**
     * Creates a new instance of TeamsExtensionUserIdentifier.
     *
     * @param userId The Id of the Microsoft Teams Extension user,
     *               i.e. the Entra ID object Id of the user.
     * @param tenantId The tenant Id of the Microsoft Teams Extension user.
     * @param resourceId The Communication Services resource Id.
     * @param cloudEnvironment The cloudEnvironment environment that the Microsoft Teams Extension user belongs to.
     *                         Defaults to PUBLIC if null.
     * @throws IllegalArgumentException thrown if userId, tenantId, or resourceId parameter is null or empty
     */
    public TeamsExtensionUserIdentifier(String userId, String tenantId, String resourceId,
                                        CommunicationCloudEnvironment cloudEnvironment) {
        this.userId = validateNotNullOrEmpty(userId, "userId");
        this.tenantId = validateNotNullOrEmpty(tenantId, "tenantId");
        this.resourceId = validateNotNullOrEmpty(resourceId, "resourceId");
        this.cloudEnvironment = cloudEnvironment != null ? cloudEnvironment : CommunicationCloudEnvironment.PUBLIC;

        generateRawId();
    }

    /**
     * Creates a TeamsExtensionUserIdentifier object
     *
     * @param userId ID of the Microsoft Teams Extension user i.e. the Entra ID object id of the user.
     * @param tenantId Tenant ID of the Microsoft Teams Extension user.
     * @param resourceId The Communication Services resource id.
     * @throws IllegalArgumentException if any parameter fail the validation.
     */
    public TeamsExtensionUserIdentifier(String userId, String tenantId, String resourceId) {
        this(userId, tenantId, resourceId, CommunicationCloudEnvironment.PUBLIC);
    }

    //todo can it be moved somewhere else and reused ?
    private static String validateNotNullOrEmpty(String value, String paramName) {
        if (value == null || value.trim().isEmpty()) {
            String message = "The initialization parameter [" + paramName + "] cannot be null or empty.";
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    //todo delete?
    /**
     * Creates  a new instance of TeamsExtensionUserIdentifier
     *
     * @param prefix Prefix of identifier.
     * @param identifier The full Id of the TeamsExtensionUserIdentifier without prefix.
     * @throws IllegalArgumentException thrown if TeamsExtensionUserIdentifier cannot be initialized.
     */
    public TeamsExtensionUserIdentifier(String prefix, String identifier) {
        this.cloudEnvironment = determineCloudEnvironment(prefix);

        String[] segments = identifier.split("_");
        if (segments.length != 3) {
            throw  new IllegalArgumentException("Cannot initialize TeamsExtensionUserIdentifier.");
        }

        this.resourceId = segments[0];
        this.tenantId = segments[1];
        this.userId = segments[2];
        this.setRawId(prefix + identifier);
    }

    /**
     * Determine the cloud based on identifier prefix.
     * @param cloudPrefix .
     * @return CommunicationCloudEnvironment.
     * @throws IllegalArgumentException thrown if CommunicationCloudEnvironment cannot be initialized.
     */
    public static CommunicationCloudEnvironment determineCloudEnvironment(String cloudPrefix) {
        switch (cloudPrefix) {
            case ACS_USER_DOD_CLOUD_PREFIX:
                return CommunicationCloudEnvironment.DOD;
            case ACS_USER_GCCH_CLOUD_PREFIX:
                return CommunicationCloudEnvironment.GCCH;
            case ACS_USER_PREFIX:
                return CommunicationCloudEnvironment.PUBLIC;
            default:
                throw  new IllegalArgumentException("Cannot initialize CommunicationCloudEnvironment.");
        }
    }

    /**
     * Get Microsoft Teams Extension user
     * @return ID of the Microsoft Teams Extension user i.e. the Entra ID object id of the user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get Microsoft Teams Extension user Tenant ID
     * @return Tenant ID of the Microsoft Teams Extension user.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Get Communication Services resource id.
     * @return the Communication Services resource id.
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Get cloud environment of the Teams Extension User identifier
     * @return cloud environment in which this identifier is created
     */
    public CommunicationCloudEnvironment getCloudEnvironment() {
        return cloudEnvironment;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof TeamsExtensionUserIdentifier)) {
            return false;
        }

        return super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private void generateRawId() {
        String identifierBase = this.resourceId + "_" + this.tenantId + "_" + this.userId;
        if (cloudEnvironment.equals(CommunicationCloudEnvironment.DOD)) {
            super.setRawId(ACS_USER_DOD_CLOUD_PREFIX +  identifierBase);
        } else if (cloudEnvironment.equals(CommunicationCloudEnvironment.GCCH)) {
            super.setRawId(ACS_USER_GCCH_CLOUD_PREFIX + identifierBase);
        } else {
            super.setRawId(ACS_USER_PREFIX + identifierBase);
        }
    }
}
