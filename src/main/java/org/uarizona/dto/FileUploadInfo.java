package org.uarizona.dto;

import org.springframework.stereotype.Component;

@Component
public class FileUploadInfo {

    private String fileName;
    private String downloadUri;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }
}
