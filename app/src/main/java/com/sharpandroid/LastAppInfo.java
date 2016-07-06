package com.sharpandroid;

/**
 * Created by jerry on 16/7/6.
 */

public class LastAppInfo {
    private String lastAppVersion;
    private String downloadUrl;
    private String updateDescription;
    private Boolean isImposed;

    public String getLastAppVersion() {
        return lastAppVersion;
    }

    public void setLastAppVersion(String lastAppVersion) {
        this.lastAppVersion = lastAppVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    public Boolean getImposed() {
        return isImposed;
    }

    public void setImposed(Boolean imposed) {
        isImposed = imposed;
    }
}
