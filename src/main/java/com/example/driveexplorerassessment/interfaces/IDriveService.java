package com.example.driveexplorerassessment.interfaces;

import com.example.driveexplorerassessment.model.ParentFile;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

// TODO: Implement unit testing
public interface IDriveService  {
    File getFile(String fileId) throws IOException, GeneralSecurityException;
    List<File> getFolders() throws IOException, GeneralSecurityException;
    List<File> getFiles() throws IOException, GeneralSecurityException;
    List<File> getFiles(String searchQuery, boolean exactMatch) throws IOException, GeneralSecurityException;
    ParentFile getParents(String childId) throws IOException, GeneralSecurityException;
    List<File> getChildren(String parentId) throws IOException, GeneralSecurityException;
    ParentFile getFolderStructure() throws IOException, GeneralSecurityException;
}
