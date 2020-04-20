package com.amazonaws.services.s3.model;

import java.io.File;

public class PutObjectRequest {

    private final String storageName;
    private final String key;
    private final File file;

    public PutObjectRequest(String storageName, String key, File file) {
        this.storageName = storageName;
        this.key = key;
        this.file = file;
    }

    public String getStorageName() { return storageName; }
    public String getKey() { return key; }
    public File getFile() { return file; }


    public void setCannedAcl(CannedAccessControlList acl) {}
}
