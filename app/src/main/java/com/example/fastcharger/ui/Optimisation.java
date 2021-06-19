package com.example.fastcharger.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.fastcharger.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Optimisation extends AppCompatActivity {
    private ImageView bakbtn;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CustomTheme);

        setContentView(R.layout.activity_optimisation);
        bakbtn = findViewById(R.id.backbutton);
initbgkill();
        bakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        videoView.getFitsSystemWindows();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoView.setLayoutParams(params);
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // do nothing here......
                return true;
            }
        });
        //Creating MediaController
        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri = getUriFromRawFile(this, R.raw.optimizing);
        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);

        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                videoView.setVisibility(View.GONE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initbgkill() {
        boolean granted = false;
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
            Toast.makeText(getApplicationContext(), "notpermitted"+granted, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            finish();
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
            Toast.makeText(getApplicationContext(), "permitted"+granted, Toast.LENGTH_SHORT).show();
            getRecentRunningPackages(getApplicationContext());


        }
    }

    private List<String> getRecentRunningPackages(Context context) {
        List<String> packages = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Toast.makeText(getApplicationContext(), "new ver", Toast.LENGTH_SHORT).show();
            try {
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

                UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                PackageManager pm = context.getPackageManager();
                long time = System.currentTimeMillis();
                List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time - 24 * 60 * 60 * 1000, time);
                Log.i(TAG, " " + appList.size());
                if (appList != null && appList.size() > 0) {
                    for (UsageStats ust : appList) {


                        if (false) {
                            ApplicationInfo ai = pm.getApplicationInfo(ust.getPackageName(), PackageManager.GET_META_DATA);

                            boolean system = (ai.flags & ai.FLAG_SYSTEM) > 0;
                            boolean stoped = (ai.flags & ai.FLAG_STOPPED) > 0;
                            boolean persisited = (ai.flags & ai.FLAG_PERSISTENT) > 0;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String s = format.format(new Date(ust.getFirstTimeStamp()));
                            String t = format.format(new Date(ust.getLastTimeStamp()));
                            Log.i("app_usage: ", String.format("%80s system = %-6b stop = %-6b persisted = %-6b ", ust.getPackageName(), system, stoped, persisited) + " " + s + " " + t);
                        }

//                        if(excepts != null && excepts.contains(ust.getPackageName())){
//                            continue;
//                        }
                        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                        am.killBackgroundProcesses(ust.getPackageName());
//                        android.os.Process.sendSignal(6988, android.os.Process.SIGNAL_KILL);
                        packages.add(ust.getPackageName());
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "old ver", Toast.LENGTH_SHORT).show();

            PackageManager pm;
            pm = getPackageManager();
//              //get a list of installed apps.
            List<PackageInfo> pkgs = pm.getInstalledPackages(0);

            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            String myPackage = getPackageName();
            for (PackageInfo packageInfo : pkgs) {
//                if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1)continue;
                if (packageInfo.packageName.equals(myPackage)) continue;
//                  android.os.Process.sendSignal(packageInfo.pid, android.os.Process.SIGNAL_KILL);

                mActivityManager.killBackgroundProcesses(packageInfo.packageName);

            }
        }
        return packages;

    }

    private Uri getUriFromRawFile(Optimisation optimisation, int optimizing) {
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(this.getPackageName())
                .path(String.valueOf(optimizing))
                .build();
    }
}