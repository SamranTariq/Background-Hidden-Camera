package com.example.backgroundhiddencamera;

public class GetBackStateFlags {

    public boolean isLocationFound = false;
    public boolean isPhotoCaptured = false;
    public boolean isTheftTriggered = false;
    public boolean isEmailSent = false;
    public boolean isSmsSent = false;
    public boolean isDataDeleted = false;
    public boolean isScreenOn = true;
    public boolean isTriggerCommandReceived = false;
    public boolean isNetworkAvailable = false;

    public GetBackStateFlags() {
        reset();
    }

    @Override
    public String toString() {
        return "isLocationFound = " + isLocationFound + ", isPhotoCaptured = "
                + isPhotoCaptured + ", isTheftTriggered = " + isTheftTriggered
                + ", isEmailSent = " + isEmailSent + ", isSmsSent = "
                + isSmsSent + ", isDataDeleted = " + isDataDeleted
                + ", isScreenOn = " + isScreenOn + ", isRcvdCommand_1 = "
                + isTriggerCommandReceived + ", isNetworkAvailable = "
                + isNetworkAvailable;
    }

    public void reset() {
        isLocationFound = isPhotoCaptured = isTheftTriggered = isEmailSent = isSmsSent = isDataDeleted = isTriggerCommandReceived = isNetworkAvailable = false;
        isScreenOn = true;
    }
}