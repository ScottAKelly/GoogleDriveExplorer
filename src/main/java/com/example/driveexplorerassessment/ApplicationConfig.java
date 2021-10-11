package com.example.driveexplorerassessment;

import com.example.driveexplorerassessment.interfaces.IAuthenticationService;
import com.example.driveexplorerassessment.interfaces.IDriveService;
import com.example.driveexplorerassessment.services.DriveService;
import com.example.driveexplorerassessment.services.GoogleAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class ApplicationConfig {

    @Bean
    public IAuthenticationService iAuthenticationService() {
        return new GoogleAuthenticationService();
    }

    @Bean public IDriveService iDriveService() throws IOException, GeneralSecurityException {
        return new DriveService(iAuthenticationService());
    }
}
