package com.azure.android.communication.common;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.azure.android.core.credential.TokenCredential;
import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;

import org.junit.jupiter.api.Test;

public class EntraCommunicationTokenCredentialOptionsTest extends TokenCredentialBaseTest {


    @Test
       public void GetCredentials() {

           InteractiveBrowserCredential tokenCredential = new InteractiveBrowserCredentialBuilder()
               .clientId("cd17d9da-3d0d-4357-bb9c-3a1d948636ce")
               .tenantId("be5c2424-1562-4d62-8d98-815720d06e4a")
               .redirectUrl("http://localhost:8080")
               .build();

           String resourceEndpoint = "https://<your-resource>.communication.azure.com";
           String[] scopes = new String[] { "https://auth.msft.communication.azure.com/TeamsExtension.ManageCalls" };

          EntraCommunicationTokenCredentialOptions entraTokenCredentialOptions = new EntraCommunicationTokenCredentialOptions(resourceEndpoint, (TokenCredential) tokenCredential, scopes);
         //  CommunicationTokenCredential credential = new CommunicationTokenCredential(entraTokenCredentialOptions);

           assertNull(null);
   }
}
