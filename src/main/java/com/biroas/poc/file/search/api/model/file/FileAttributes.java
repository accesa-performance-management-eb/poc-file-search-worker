package com.biroas.poc.file.search.api.model.file;

import java.util.Date;

public class FileAttributes {

    private long size;
    private Date creationDate;
    private Date lastModifiedDate;
    private Date lastAccessDate;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date modifiedDate) {
        this.lastModifiedDate = modifiedDate;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }
}
