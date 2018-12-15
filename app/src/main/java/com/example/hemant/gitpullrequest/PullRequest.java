package com.example.hemant.gitpullrequest;

public class PullRequest {

    private String mTitle;
    private String mAvatarUrl;
    private String mDiffUrl;

    public PullRequest(String title, String avatarUrl, String diffUrl) {
        mTitle = title;
        mAvatarUrl = avatarUrl;
        mDiffUrl = diffUrl;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setAvatarUrl(String url) {
        mAvatarUrl = url;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setDiffUrl(String url) {
        mDiffUrl = url;
    }

    public String getDiffUrl() {
        return mDiffUrl;
    }
}
