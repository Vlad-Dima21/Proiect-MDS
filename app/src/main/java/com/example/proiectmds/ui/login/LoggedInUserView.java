package com.example.proiectmds.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String password;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName,String password) {
        this.displayName = displayName;
        this.password = password;
    }

    void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    void setPassword(String password)
    {
        this.password = password;
    }

    String getDisplayName() {
        return this.displayName;
    }

    String getPassword() {
        return this.password;
    }
}