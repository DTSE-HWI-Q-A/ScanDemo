package com.huawei.scankitupiita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.ml.scan.HmsScan;

public class DefinedActivity extends AppCompatActivity {
    public static final String TAG = DefinedActivity.class.getSimpleName();
    private RemoteView remoteView;
    public static final String SCAN_RESULT = "scanResult";
    int mScreenWidth;
    int mScreenHeight;
    final int SCAN_FRAME_SIZE = 300;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_defined);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        int scanFrameSize = (int) (SCAN_FRAME_SIZE * density);

        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;

        remoteView =  new RemoteView.Builder().setContext(this).setBoundingBox(rect).setFormat(HmsScan.ALL_SCAN_TYPE).build();
        remoteView.onCreate(savedInstanceState);
        remoteView.setOnResultCallback((result) -> {
            if(result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())){
                Intent intent = new Intent();
                intent.putExtra(SCAN_RESULT, result[0]);
                setResult(RESULT_OK, intent);
                DefinedActivity.this.finish();
            }
        });


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = findViewById(R.id.rim);
        frameLayout.addView(remoteView, params);

        ImageView backBtn = findViewById(R.id.back_img);
        backBtn.setOnClickListener(v -> DefinedActivity.this.finish());

    }


    @Override
    protected void onStart() {
        super.onStart();
        remoteView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
    }



}