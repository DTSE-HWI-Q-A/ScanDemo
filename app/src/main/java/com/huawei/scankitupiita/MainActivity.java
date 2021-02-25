package com.huawei.scankitupiita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.huawei.hms.ml.scan.HmsScan;

public class MainActivity extends AppCompatActivity {
    private Button scan;
    private static final int  DEFINED_CODE = 8888;
    private static final int REQUEST_CODE_SCAN = 0X01;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan = findViewById(R.id.button);
        scan.setOnClickListener((v)->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        DEFINED_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode == DEFINED_CODE) {

            this.startActivityForResult(new Intent(this, DefinedActivity.class), REQUEST_CODE_SCAN);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            HmsScan hmsScan = data.getParcelableExtra(DefinedActivity.SCAN_RESULT);
            if (hmsScan != null && !TextUtils.isEmpty(hmsScan.getOriginalValue())) {
                Toast.makeText(MainActivity.this, hmsScan.getOriginalValue(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
