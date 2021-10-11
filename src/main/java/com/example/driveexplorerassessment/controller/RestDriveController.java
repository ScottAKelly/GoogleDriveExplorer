package com.example.driveexplorerassessment.controller;

import com.example.driveexplorerassessment.interfaces.IAuthenticationService;
import com.example.driveexplorerassessment.interfaces.IDriveService;
import com.example.driveexplorerassessment.model.FileListViewModel;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@RequestMapping("rest-drive")
public class RestDriveController {

    private final IDriveService DRIVE_SERVICE;
    private final IAuthenticationService AUTH_SERVICE;
    public RestDriveController(IDriveService driveService, IAuthenticationService authService) {
        DRIVE_SERVICE = driveService;
        AUTH_SERVICE = authService;
    }

    @GetMapping("")
    public String index() {
        return "rest-drive";
    }

    @GetMapping(value = {"/files", "/files/{searchQuery}/{exactMatch}" }, produces = "application/json")
    @ResponseBody
    public FileListViewModel getFiles(@PathVariable(required = false) String searchQuery,
                                      @PathVariable(required = false) boolean exactMatch)
            throws IOException, GeneralSecurityException {
        var model = new FileListViewModel();

        //var structure = DRIVE_SERVICE.getFolderStructure();

        if (searchQuery == null || searchQuery.isEmpty()) {
            var parent = DRIVE_SERVICE.getParents("1l3QbzLedEHl_FKp-hWJTzvWFFtH4kxZF");
            model.setParent(parent);
            model.setFiles(DRIVE_SERVICE.getChildren(parent.getId()));
        }
        else {
            model.setFiles(DRIVE_SERVICE.getFiles(searchQuery, exactMatch));
        }
        return model;
    }

    @GetMapping(value = "/files/children/{parentId}", produces = "application/json")
    @ResponseBody
    public FileListViewModel getChildren(@PathVariable(required = false) String parentId) throws IOException, GeneralSecurityException {
        var parent = DRIVE_SERVICE.getParents(parentId);
        var children = DRIVE_SERVICE.getChildren(parent.getId());

        var model = new FileListViewModel();
        model.setParent(parent);
        model.setFiles(children);
        return model;
    }
}
