package com.example.hemant.gitpullrequest;

/**
 * P.O.J.O.(Plain Old Java Object) for pullRequest
 */

public class PullRequest {

    private String mTitle;
    private String mAvatarUrl;
    private String mDiffUrl;
    private int mIssueNumber;

    PullRequest(String title, String avatarUrl, String diffUrl, int issueNumber) {
        mTitle = title;
        mAvatarUrl = avatarUrl;
        mDiffUrl = diffUrl;
        mIssueNumber = issueNumber;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    String getTitle() {
        return mTitle;
    }

    public void setAvatarUrl(String url) {
        mAvatarUrl = url;
    }

    String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setDiffUrl(String url) {
        mDiffUrl = url;
    }

    String getDiffUrl() {
        return mDiffUrl;
    }

    public void setIssueNumber(int number) {
        mIssueNumber = number;
    }

    int getIssueNumber() {
        return mIssueNumber;
    }
}
