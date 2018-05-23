package com.biroas.poc.file.search.api.model.file;

public class File {

    private String id;
    private String fileName;
    private boolean isDirectory;
    private String parentDirectory;
    private FileAttributes fileAttributes = new FileAttributes();
    private FileType fileType = new FileType();
    private String systemName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(String parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public FileAttributes getFileAttributes() {
        return fileAttributes;
    }

    public void setFileAttributes(FileAttributes fileAttributes) {
        this.fileAttributes = fileAttributes;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Id: ").append(this.id);
        stringBuilder.append(", FileName: ").append(this.fileName);
        stringBuilder.append(", FileType: ").append(this.fileType);
        stringBuilder.append(", ParentDirectory: ").append(this.parentDirectory);
        stringBuilder.append(", SystemName: ").append(this.systemName);
        return stringBuilder.toString();
    }
}
