package com.example.driveexplorerassessment.services;

import com.example.driveexplorerassessment.interfaces.IAuthenticationService;
import com.example.driveexplorerassessment.interfaces.IDriveService;
import com.example.driveexplorerassessment.model.ParentFile;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class DriveService implements IDriveService {
    private final IAuthenticationService AUTH_SERVICE;
    private Drive DRIVE_SERVICE;
    private static final String APPLICATION_NAME = "Drive Explorer Assessment";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";

    public DriveService(IAuthenticationService authService) throws IOException, GeneralSecurityException {

        AUTH_SERVICE = authService;
        DRIVE_SERVICE = buildDrive();
    }

    @Override
    public String driveRootFolderId() {
        return "1l3QbzLedEHl_FKp-hWJTzvWFFtH4kxZF";
    }

    @Override
    public File getFile(String fileId) throws IOException, GeneralSecurityException {
        return DRIVE_SERVICE.files().get(fileId)
                .setFields("id, name, mimeType, iconLink, parents").execute();
    }

    @Override
    public List<File> getFolders() throws IOException, GeneralSecurityException {
        var query = DRIVE_SERVICE.files().list()
                .setFields("nextPageToken, files(id, name, mimeType, iconLink, parents)")
                .setQ("mimeType = '" + FOLDER_MIME_TYPE + "'");

        FileList result = query.execute();
        var files = new ArrayList<File>();
        files.addAll(result.getFiles());

        var nextPageToken = result.getNextPageToken();
        boolean completed = nextPageToken == null || nextPageToken.isEmpty();
        while (!completed) {
            var attempt = DRIVE_SERVICE.files().list().setPageToken(nextPageToken).execute();
            files.addAll(attempt.getFiles());

            var newPageToken = attempt.getNextPageToken();
            if (newPageToken == null || newPageToken.isEmpty()) {
                completed = true;
            }
            else {
                nextPageToken = newPageToken;
            }
        }

        return files;
    }

    @Override
    public List<File> getFiles() throws IOException, GeneralSecurityException {
        return getFiles("", false);
    }

    @Override
    public List<File> getFiles(String searchQuery, boolean exactMatch) throws IOException, GeneralSecurityException {
        var query = DRIVE_SERVICE.files().list()
                .setFields("nextPageToken, files(id, name, mimeType, iconLink, parents)");

        if (searchQuery != null && !searchQuery.isEmpty()) {
            if (exactMatch) {
                query = query.setQ("name = '" + searchQuery.replace("'", "\'") + "'");
            }
            else {
                query = query.setQ("name contains '" + searchQuery.replace("'", "\'") + "'");
            }
        }

        FileList result = query.execute();
        var files = new ArrayList<File>();
        files.addAll(result.getFiles());

        // Only make requests to get more pages if search query is empty
        if (searchQuery == null && searchQuery.isEmpty()) {
            var nextPageToken = result.getNextPageToken();
            boolean completed = nextPageToken == null || nextPageToken.isEmpty();
            while (!completed) {
                var attempt = DRIVE_SERVICE.files().list().setPageToken(nextPageToken).execute();
                files.addAll(attempt.getFiles());

                var newPageToken = attempt.getNextPageToken();
                if (newPageToken == null || newPageToken.isEmpty()) {
                    completed = true;
                } else {
                    nextPageToken = newPageToken;
                }
            }
        }

        return files;
    }

    @Override
    public ParentFile getParents(String childId) throws IOException, GeneralSecurityException {
        // TODO: Change this to a method that only gets folders. Files can't have children
        var files = getFolders();

        var child = getFile(childId);
        var result = new ParentFile();
        result.setId(child.getId());
        result.setName(child.getName());
        result.setIconLink(child.getIconLink());
        result.setMimeType(child.getMimeType());

        var parent = getParentRecursive(files, child);
        if (parent != null) {
            result.setParent(parent);
        }

        return result;
    }

    @Override
    public List<File> getChildren(String parentId) throws IOException, GeneralSecurityException {
        var search = String.format("parents in '%s'", parentId);
        var result = DRIVE_SERVICE.files().list()
                .setFields("nextPageToken, files(id, name, mimeType, iconLink, parents)")
                .setQ(search)
                .execute();

        var children = result.getFiles();
        return children;
    }

    @Override
    public ParentFile getFolderStructure() throws IOException, GeneralSecurityException {
        var files = getFolders();

        var rootDirectories = files.stream().filter((f) -> f.getParents() == null || f.getParents().size() <= 0);
        var root = rootDirectories.findFirst().orElse(null);

//        int i = 0;
//        boolean foundRoot = false;
//        while (!foundRoot) {
//            var workingFile = files[i];
//            var children = Arrays.stream(files)
//                    .filter((f) -> f.getParents().contains(workingFile.getId()));
//        }

        //var root = this.getStructureRecursive(service, "1l3QbzLedEHl_FKp-hWJTzvWFFtH4kxZF");

        return null;
    }

    private ParentFile getParentRecursive (List<File> source, File child) {
        var parentFile = source.stream().filter(f -> child.getParents().contains(f.getId())).findFirst().orElse(null);

        if (parentFile == null) {
            return null;
        }

        var currentParent = new ParentFile();
        currentParent.setId(parentFile.getId());
        currentParent.setName(parentFile.getName());
        currentParent.setMimeType(parentFile.getMimeType());
        currentParent.setIconLink(parentFile.getIconLink());

        var newParent = getParentRecursive(source, parentFile);
        if (newParent != null) {
            currentParent.setParent(newParent);
        }

        return currentParent;
    }

    private ParentFile getStructureRecursive(Drive service, String directoryId) throws IOException {
        var currentFile = service.files().get(directoryId).execute();

        var search = String.format("parents in '%s'", currentFile.getId());
        var result = service.files().list()
                .setFields("nextPageToken, files(id, name, mimeType, iconLink, parents)")
                .setQ(search)
                .execute();

        // TODO: Add a check for the type. If it's not a folder, don't make a child request
        List<ParentFile> children = new ArrayList<ParentFile>();
        var files = result.getFiles();
        for (var file : files) {
            var child = getStructureRecursive(service, file.getId());
//            child.setParentId(currentFile.getId());
//            child.setParentName(currentFile.getName());
            children.add(child);
        }

        var model = new ParentFile(currentFile);

        return model;
    }

    private Drive buildDrive() throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(httpTransport, JSON_FACTORY, AUTH_SERVICE.getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
