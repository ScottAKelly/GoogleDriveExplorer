package com.example.driveexplorerassessment.controller;

import com.example.driveexplorerassessment.interfaces.IAuthenticationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drive-auth")
public class AuthenticationController {

    private final IAuthenticationService AUTH_SERVICE;

    public AuthenticationController(IAuthenticationService authenticationService) {
        AUTH_SERVICE = authenticationService;
    }

    @PostMapping("/receive-code/{responseString}")
    public void receiveCode(@PathVariable String responseString) {
        // Save code and use to post back to google to get auth token
    }
}
