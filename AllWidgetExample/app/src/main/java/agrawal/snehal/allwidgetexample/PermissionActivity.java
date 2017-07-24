package agrawal.snehal.allwidgetexample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import agrawal.snehal.allwidgetexample.permissionsupport.FragmentPermissionActivity;
import agrawal.snehal.allwidgetexample.permissionsupport.MultiplePermissionsActivity;

public class PermissionActivity extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
                        builder.setTitle("Need Storage Permission");
                        builder.setMessage("This app needs storage permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
                        builder.setTitle("Need Storage Permission");
                        builder.setMessage("This app needs storage permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                sentToSettings = true;
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                    SharedPreferences.Editor editor = permissionStatus.edit();
                    editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
                    editor.apply();
                } else {
                    proceedAfterPermission();
                }
            }
        });

        Button btnLaunchPermissionFragment = (Button) findViewById(R.id.btnLaunchPermissionFragment);
        btnLaunchPermissionFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PermissionActivity.this, FragmentPermissionActivity.class);
                startActivity(intent);
            }
        });

        Button btnLaunchMultiplePermission = (Button) findViewById(R.id.btnLaunchMultiplePermission);
        btnLaunchMultiplePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PermissionActivity.this, MultiplePermissionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void proceedAfterPermission() {
        Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            }
        }
    }
}

