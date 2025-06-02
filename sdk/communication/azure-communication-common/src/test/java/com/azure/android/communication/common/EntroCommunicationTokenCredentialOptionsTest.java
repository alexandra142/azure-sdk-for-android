package com.azure.android.communication.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.azure.android.core.credential.TokenCredential;
import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import java9.util.concurrent.CompletableFuture;

public class EntroCommunicationTokenCredentialOptionsTest extends TokenCredentialBaseTest {
       @Test
       public void GetCredentials() {

           InteractiveBrowserCredential tokenCredential = new InteractiveBrowserCredentialBuilder()
               .clientId("<your-client-id>")
               .tenantId("<your-tenant-id>")
               .redirectUrl("<your-redirect-uri>")
               .build();
           String resourceEndpoint = "https://<your-resource>.communication.azure.com";
           String[] scopes = new String[] { "https://auth.msft.communication.azure.com/TeamsExtension.ManageCalls" };

           EntraCommunicationTokenCredentialOptions entraTokenCredentialOptions = new EntraCommunicationTokenCredentialOptions(resourceEndpoint, (TokenCredential) tokenCredential, scopes);
           CommunicationTokenCredential credential = new CommunicationTokenCredential(entraTokenCredentialOptions);
   }
}
