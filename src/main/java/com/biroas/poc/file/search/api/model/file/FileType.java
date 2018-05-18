package com.biroas.poc.file.search.api.model.file;

import java.io.Serializable;

public class FileType implements Serializable {

    private String extension;
    private boolean isImage;
    private boolean isVideo;
    private boolean isAudio;
    private boolean isText;
    private boolean isOther;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isAudio() {
        return isAudio;
    }

    public void setAudio(boolean audio) {
        isAudio = audio;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public boolean isOther() {
        return isOther;
    }

    public void setOther(boolean other) {
        isOther = other;
    }
}
