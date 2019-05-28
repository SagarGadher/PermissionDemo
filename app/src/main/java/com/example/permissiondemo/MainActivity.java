package com.example.permissiondemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 111;
    private static final int REQUEST_STORAGE = 121;
    private static final int REQUEST_CONTACTS = 131;
    private static final int REQUEST_ALL = 141;

    private static final int TEXT_CAMERA = 1;
    private static final int TEXT_STORAGE = 2;
    private static final int TEXT_CONTACTS = 3;

    private PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtil = new PermissionUtil(this);
    }

    private int checkPermission(int permission) {
        int status = PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case TEXT_CAMERA:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                break;
            case TEXT_STORAGE:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case TEXT_CONTACTS:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
        }
        return status;
    }

    private void requestPermission(int permission) {
        switch (permission) {
            case TEXT_CAMERA:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                break;
            case TEXT_STORAGE:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                break;
            case TEXT_CONTACTS:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                break;
        }
    }

    private void showPermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (permission == TEXT_CAMERA) {
            builder.setTitle("Camera Permission Required ....");
            builder.setMessage("App need camera access to work properly");
        } else if (permission == TEXT_STORAGE) {
            builder.setTitle("Storage Permission Required ....");
            builder.setMessage("App need storage access to work properly");
        } else if (permission == TEXT_CONTACTS) {
            builder.setTitle("Contact Permission Required ....");
            builder.setMessage("App need Contact access to work properly");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (permission == TEXT_CAMERA)
                    requestPermission(TEXT_CAMERA);
                else if (permission == TEXT_STORAGE)
                    requestPermission(TEXT_STORAGE);
                else if (permission == TEXT_CONTACTS)
                    requestPermission(TEXT_CONTACTS);
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void requstAllPermission(ArrayList<String> permissions) {
        String[] permissionList = new String[permissions.size()];
        permissions.toArray(permissionList);

        ActivityCompat.requestPermissions(MainActivity.this, permissionList, REQUEST_ALL);
    }

    public void CameraPermission(View view) {
        if (checkPermission(TEXT_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                showPermissionExplanation(TEXT_CAMERA);
            } else if (!permissionUtil.checkPermissionPrefrence("camera")) {
                requestPermission(TEXT_CAMERA);
                permissionUtil.updatePermissionPrefrence("camera");
            } else {
                Toast.makeText(this, "Inside Setting Allow Camera Permission", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                i.setData(uri);
                this.startActivity(i);
            }
        } else {
            Toast.makeText(this, "Already have Camera permission", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            i.putExtra("message", "You can take photos and videos.");
            startActivity(i);
        }
    }

    public void StoragePermission(View view) {
        if (checkPermission(TEXT_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPermissionExplanation(TEXT_STORAGE);
            } else if (!permissionUtil.checkPermissionPrefrence("storage")) {
                requestPermission(TEXT_STORAGE);
                permissionUtil.updatePermissionPrefrence("storage");
            } else {
                Toast.makeText(this, "Inside Setting Allow Storage Permission", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                i.setData(uri);
                this.startActivity(i);
            }
        } else {
            Toast.makeText(this, "Already have Storage permission", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            i.putExtra("message", "You can access any file");
            startActivity(i);
        }
    }

    public void ContactPermission(View view) {

        if (checkPermission(TEXT_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                showPermissionExplanation(TEXT_CONTACTS);
            } else if (!permissionUtil.checkPermissionPrefrence("contacts")) {
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                requestPermission(TEXT_CONTACTS);
                permissionUtil.updatePermissionPrefrence("contacts");
            } else {
                Toast.makeText(this, "Inside Setting Allow Contact Permission", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                i.setData(uri);
                this.startActivity(i);
            }
        } else {
            Toast.makeText(this, "Already have contact permission", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            i.putExtra("message", "You can read contact");
            startActivity(i);
        }
    }

    public void AllPErmission(View view) {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        ArrayList<String> permissionAvailable = new ArrayList<>();
        permissionAvailable.add(Manifest.permission.CAMERA);
        permissionAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        for (String permission : permissionAvailable) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }
        if (!permissionsNeeded.isEmpty())
            requstAllPermission(permissionsNeeded);
        else Toast.makeText(this, "You have already All Perissions....!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have camera Permission", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("message", "You can take photos and videos.");
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Camera Permission Denied...!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have Storage Permission", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("message", "You can read contact");
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Storage Permission Denied...!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have Contact Permission", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("message", "You can access any file ");
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Contact Permission Denied...!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_ALL:
                String result = "";
                int i = 0;
                for (String p : permissions) {
                    String status = "";
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        status = "GRANTED";
                    else
                        status = "DENIED";
                    result = result + "\n" + p + " : " + status;
                    i++;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Group Permission Details : ");
                builder.setMessage(result);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

    }
}
