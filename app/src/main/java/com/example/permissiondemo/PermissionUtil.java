package com.example.permissiondemo;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PermissionUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_prefrance), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void updatePermissionPrefrence(String permission) {
        switch (permission) {
            case "camera":
                editor.putBoolean(context.getString(R.string.permission_camera),true);
                editor.commit();
                break;
            case "storage":
                editor.putBoolean(context.getString(R.string.permission_storage),true);
                editor.commit();
                break;
            case "contacs":
                editor.putBoolean(context.getString(R.string.permission_contacts),true);
                editor.commit();
                break;
        }
    }
    public boolean checkPermissionPrefrence(String permission){
        boolean isShown = false;
        switch (permission){
            case "camera":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_camera),false);
                break;
            case "storage":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_storage),false);
                break;
            case "contacts":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.permission_contacts),false);
                break;
        }
        return isShown;
    }
}
