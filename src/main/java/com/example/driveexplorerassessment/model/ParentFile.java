package com.example.driveexplorerassessment.model;

import com.google.api.services.drive.model.File;

import java.util.List;

public class ParentFile {
    private ParentFile parent;
    private String id;
    private String name;
    private String mimeType;
    private String iconLink;
    private boolean isRoot = parent == null;

    public ParentFile() {

    }

    public ParentFile(File file) {
        this.id = file.getId();
        this.iconLink = file.getIconLink();
        this.mimeType = file.getMimeType();
        this.name = file.getName();
    }

    public ParentFile getParent() {
        return parent;
    }

    public void setParent(ParentFile parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public boolean isRoot() {
        return isRoot;
    }
}
