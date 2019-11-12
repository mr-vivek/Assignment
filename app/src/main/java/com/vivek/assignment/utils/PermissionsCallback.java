package com.vivek.assignment.utils;

@SuppressWarnings("WeakerAccess")
public abstract class PermissionsCallback {
    public void onGranted(boolean newPermissionsGranted) {
    }

    public void onDenied() {
    }
}

