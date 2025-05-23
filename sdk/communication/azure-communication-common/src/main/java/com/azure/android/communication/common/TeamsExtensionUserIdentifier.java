// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.communication.common;

/**
 * Communication identifier for Microsoft Teams Phone user who is using a Communication Services resource to extend their Teams Phone set up.
 */
public final class TeamsExtensionUserIdentifier extends CommunicationIdentifier {

    private final String userId;

    private final String tenantId ;

    private final String resourceId ;

    private final CommunicationCloudEnvironment cloudEnvironment;

    /**
     * Creates a new instance of TeamsExtensionUserIdentifier. // Removed extra space
     *
     * @param userId The Id of the Microsoft Teams Extension user,
     *               i.e. the Entra ID object Id of the user.
     * @param tenantId The tenant Id of the Microsoft Teams Extension user.
     * @param resourceId The Communication Services resource Id.
     * @param cloudEnvironment The cloud that the Microsoft Teams Extension user belongs to.
     * @throws IllegalArgumentException thrown if userId, tenantId, or resourceId parameter is null or empty,
     *                                  or if cloudEnvironment is null. // More specific if you like
     */
    public TeamsExtensionUserIdentifier(String userId, String tenantId, String resourceId, CommunicationCloudEnvironment cloudEnvironment) {
        this.userId = validateNotNullOrEmpty(userId, "userId");
        this.tenantId = validateNotNullOrEmpty(tenantId, "tenantId");
        this.resourceId = validateNotNullOrEmpty(resourceId, "resourceId");

        if (cloudEnvironment == null) {
            throw new IllegalArgumentException("The initialization parameter [cloudEnvironment] cannot be null.");
        }
        this.cloudEnvironment = cloudEnvironment;

        generateRawId();
    }

    private String validateNotNullOrEmpty(String value, String paramName) {
        // You can use com.azure.core.util.CoreUtils.isNullOrEmpty(value) if available
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The initialization parameter [" + paramName + "] cannot be null or empty.");
        }
        return value; // Return the value to allow assignment and validation in one line
    }

    /**
     * Creates  a new instance of TeamsExtensionUserIdentifier
     *
     * @param prefix Prefix of identifier.
     * @param identifier The full Id of the TeamsExtensionUserIdentifier without prefix.
     * @throws IllegalArgumentException thrown if TeamsExtensionUserIdentifier cannot be initialized.
     */
    public TeamsExtensionUserIdentifier(String prefix, String identifier) {
//todo test if the value is nonsense.
        this.cloudEnvironment = getCloudEnvironment(prefix);

        String[] segments = identifier.split("_");
        if(segments.length != 3)
        {
            throw  new IllegalArgumentException("Cannot initialize TeamsExtensionUserIdentifier.");
        }

        this.resourceId =     segments[0];
        this.tenantId =     segments[1];
        this.userId =     segments[2];
        this.setRawId(prefix+identifier);
    }

    private static CommunicationCloudEnvironment getCloudEnvironment(String cloudPrefix) {
        switch (cloudPrefix) {
            case ACS_USER_DOD_CLOUD_PREFIX:
                return CommunicationCloudEnvironment.DOD;
            case ACS_USER_GCCH_CLOUD_PREFIX:
                return CommunicationCloudEnvironment.GCCH;
            case ACS_USER_PREFIX:
                return CommunicationCloudEnvironment.PUBLIC;
        }

        throw  new IllegalArgumentException("Cannot initialize CommunicationCloudEnvironment.");
    }

    /** The Communication Services resource Id. */
    public String getResourceId() {
        return resourceId;
    }

    /** Get the tenant Id of the Microsoft Teams Extension user. */
    public String getTenantId() {
        return tenantId;
    }

    /** Get the Id of the Microsoft Teams Extension user, i.e. the Entra ID object Id of the user. */
    public String getUserId() {
        return userId;
    }

    /**
     * Get the cloud that the identifier belongs to.
     */
    public CommunicationCloudEnvironment getCloudEnvironment() {
        return cloudEnvironment;
    }

//todo what about orders of methods?

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof TeamsExtensionUserIdentifier)) {
            return false;
        }

        TeamsExtensionUserIdentifier thatId = (TeamsExtensionUserIdentifier) that;

        if (cloudEnvironment != null && !cloudEnvironment.equals(thatId.cloudEnvironment)) {
            return false;
        }

        if (thatId.cloudEnvironment != null && !thatId.cloudEnvironment.equals(this.cloudEnvironment)) {
            return false;
        }

        return super.equals(that);
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
