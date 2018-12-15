package com.example.hemant.gitpullrequest;

public class PullRequest {

    private String mTitle;
    private String mAvatarUrl;

    public PullRequest(String title, String avatarUrl) {
        mTitle = title;
        mAvatarUrl = avatarUrl;
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
}
