package agrawal.snehal.allwidgetexample.permissionsupport;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import agrawal.snehal.allwidgetexample.R;

public class PermissionsFragment extends Fragment {

    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    private View view;
    private TextView txtPermissions;


    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    public PermissionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_permission, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity();
        permissionStatus = getActivity().getSharedPreferences("permissionStatus", Context.MODE_PRIVATE);

        if (null != view) {
            txtPermissions = view.findViewById(R.id.txtPermissions);
            Button btnCheckPermissions = view.findViewById(R.id.btnCheckPermissions);

            btnCheckPermissions.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Need Storage Permission");
                            builder.setMessage("This app needs phone permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_CALLBACK_CONSTANT);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else if (permissionStatus.getBoolean(Manifest.permission.READ_PHONE_STATE, false)) {
                            //Previously Permission Request was cancelled with 'Dont Ask Again',
                            // Redirect to Settings after showing Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Need Storage Permission");
                            builder.setMessage("This app needs storage permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    sentToSettings = true;
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(getActivity(), "Go to Permissions to Grant Phone", Toast.LENGTH_LONG).show();
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
                            //just request the permission
                            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_CALLBACK_CONSTANT);
                        }
                        txtPermissions.setText("Permissions Required");

                        SharedPreferences.Editor editor = permissionStatus.edit();
                        editor.putBoolean(Manifest.permission.READ_PHONE_STATE, true);
                        editor.apply();
                    } else {
                        //You already have the permission, just go ahead.
                        proceedAfterPermission();
                    }
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void proceedAfterPermission() {
        txtPermissions.setText("We've got all permissions");
        Toast.makeText(getActivity(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
                txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs phone permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_CALLBACK_CONSTANT);
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
                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            }
        }
    }
}
