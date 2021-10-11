package com.example.driveexplorerassessment.model;

import com.google.api.services.drive.model.File;

import java.util.List;

public class FileListViewModel {
    private ParentFile parent;
    private List<File> files;
    private int resultCount;

    public int getResultCount() { return resultCount; }

    public void setResultCount(int resultCount) { this.resultCount = resultCount; }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public ParentFile getParent() {
        return parent;
    }

    public void setParent(ParentFile parent) {
        this.parent = parent;
    }
}
