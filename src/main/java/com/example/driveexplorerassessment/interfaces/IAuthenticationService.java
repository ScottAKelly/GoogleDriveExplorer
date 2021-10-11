package com.example.driveexplorerassessment.interfaces;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

// TODO: Refactor interface to have implementations for UI based auth.
// Implement Google redirect callback receiveCallback();
// Implement retrieving auth token from google using auth code from callback getAuthtoken(string code);
// Implement session variable for storing auth token per user.
public interface IAuthenticationService {
    Credential getCredentials(NetHttpTransport httpTransport) throws IOException;
}
