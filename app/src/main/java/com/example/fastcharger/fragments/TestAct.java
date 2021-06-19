package com.example.fastcharger.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.fastcharger.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TestAct extends Activity {
    TextView textview;
    private Intent intent;
    private int mProgressStatus = 0;
    private int cnt = 0;
    private Context mContext;

    private float batteryLevel1;
    private String charging, chargingMethod;
    private UsageStatsManager mUsageStatsManager;
    private float batteryLevel2;
    private Calendar now1,now2;
    Date tym1;
    private float time1,time2;

    ActivityManager activitymanager;
    Context context;
    List<ActivityManager.RunningAppProcessInfo> RAP ;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1); //get the scale of battery (usually 100)
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1); //get the level of the battery


            // Are we charging or is the phone fully charged?
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;


            float percentage = level / (float) scale; //calculate the battery percentage of the battery according to the scale

            /*We need to get the current time and get the current battery percentage. We can easily know the next battery percentage;
             * if it's charging we get the upper percentage and vice-versa. */
            if(cnt < 1){
                batteryLevel2 = percentage + (float) 0.0001; //if the phone is charging, this is the next battery level we are expecting
                batteryLevel1 = percentage - (float) 0.0001; //if it is discharging, this is the next battery level we are expecting
                tym1 = Calendar.getInstance().getTime();
                //get Time 1
                cnt++;
            }

            //Define what happens when the phone is charging as well as when it's not charging
            if(isCharging){
                charging = "Charging";
//                info.setText("Hours Till Battery Full");
                if(percentage >= batteryLevel2){
                    //get Time 2
                    float p_left = (float) 1 - percentage;
                    Date tym2 = Calendar.getInstance().getTime();

                    long tym_diff = tym2.getTime() - tym1.getTime();
                    long mills = Math.abs(tym_diff);

                    long rem = (long) (mills * p_left * 100);

                    int hours = (int) (rem/(1000 * 60 * 60));
                    int mins = (int) (rem/(1000*60)) % 60;
                    long secs = (int) (rem / 1000) % 60;
                    Toast.makeText(getApplicationContext(), "" + hours + "h" + mins + "m", Toast.LENGTH_SHORT).show();

//                    report2.setText("" + hours + "h" + mins + "m");
                    cnt = 0;
                }
            }else{
//                info.setText("Hours Left Till Battery Dies");
                charging = "Not Charging";
                if(percentage <= batteryLevel1){
                    //get Time 2

                    Date tym2 = Calendar.getInstance().getTime();

                    long tym_diff = tym2.getTime() - tym1.getTime();
                    long mills = Math.abs(tym_diff);

                    long rem = (long) (mills * percentage * 100);

                    int hours = (int) (rem/(1000 * 60 * 60));
                    int mins = (int) (rem/(1000*60)) % 60;
                    long secs = (int) (rem / 1000) % 60;
//                    report2.setText("" + hours + "h" + mins + "m");

                    cnt = 0;
                }

            }
//
//            percent.setText("Battery - " + level + "%");
//            report1.setText("Charge State : "+charging);
//
//            mProgressStatus = (int) ((percentage) * 100);
//            mProgressBar.setProgress(mProgressStatus);
            //mTextViewPercentage.setText("" + mProgressStatus + "%");
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext = getApplicationContext();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver, iFilter);
        textview = (TextView)findViewById(R.id.textView1);

//        List<ApplicationInfo> packages;
//        PackageManager pm;
//        pm = getPackageManager();
//        //get a list of installed apps.
//        packages = pm.getInstalledApplications(0);

//        boolean granted = false;
//        AppOpsManager appOps = (AppOpsManager)
//                getSystemService(Context.APP_OPS_SERVICE);
//        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
//                android.os.Process.myUid(),getPackageName());
//
//        if (mode == AppOpsManager.MODE_DEFAULT) {
//            granted = (checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
//            Toast.makeText(getApplicationContext(), "notpermitted"+granted, Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//            finish();
//        } else {
//            granted = (mode == AppOpsManager.MODE_ALLOWED);
//            Toast.makeText(getApplicationContext(), "permitted"+granted, Toast.LENGTH_SHORT).show();
//            getRecentRunningPackages(getApplicationContext());


        }

//            killbg();
//  kilnw();
//        ActivityManager mActivityManager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
////        startActivity(new Intent(this,TestAct.class));
//       startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//        for (ApplicationInfo packageInfo : packages) {
//            if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1)continue;
//            if(packageInfo.packageName.equals("mypackage")) continue;
//            mActivityManager.killBackgroundProcesses(packageInfo.packageName);
//            textview  .setText(textview.getText() + packageInfo.processName + "\n");
//        }

//        startActivity(new Intent(this,TestAct.class));
//        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
//
//        for(ActivityManager.RunningAppProcessInfo runningProcess : runningProcesses)
//        {
//            if(runningProcess.processName.equals(getPackageName())) {
////                android.os.Process.sendSignal(runningProcess.pid, android.os.Process.SIGNAL_KILL);
//                textview  .setText(textview.getText() + runningProcess.processName + "\n");
//            }
//
//        }
////        for(ActivityManager.RunningAppProcessInfo processInfo: RAP ){
////
////            textview.setText(textview.getText()
////            + processInfo.processName + "\n");
////
////        }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private  List<String> getRecentRunningPackages(Context context) {
        List<String> packages = new ArrayList<>();
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Toast.makeText(getApplicationContext(), "new ver", Toast.LENGTH_SHORT).show();
            try {
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

                UsageStatsManager usm = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
                PackageManager pm = context.getPackageManager();

                long time = System.currentTimeMillis();
                List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST,  time - 24*60*60*1000, time);
                Log.i(TAG," " + appList.size());
                if (appList != null && appList.size() > 0) {
                    for (UsageStats ust : appList) {
                        textview.setText(textview.getText() +ust.getPackageName()  + "\n");

                        if(false){
                            ApplicationInfo ai = pm.getApplicationInfo(ust.getPackageName(),PackageManager.GET_META_DATA);

                            boolean system = (ai.flags&ai.FLAG_SYSTEM) > 0;
                            boolean stoped = (ai.flags&ai.FLAG_STOPPED) > 0;
                            boolean persisited = (ai.flags&ai.FLAG_PERSISTENT) > 0;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String s =format.format(new Date(ust.getFirstTimeStamp()));
                            String t =format.format(new Date(ust.getLastTimeStamp()));
                            Log.i("app_usage: ",  String.format("%80s system = %-6b stop = %-6b persisted = %-6b ",ust.getPackageName(),system,stoped,persisited) + " " + s + " " + t);
                        }

//                        if(excepts != null && excepts.contains(ust.getPackageName())){
//                            continue;
//                        }
                        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                        am.killBackgroundProcesses(ust.getPackageName());
                        packages.add(ust.getPackageName());
                    }
                }
            }catch (Throwable t){
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
                  textview  .setText(textview.getText() + packageInfo.packageName + "\n");

              }

            //alternative killmethod


           /* ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo t:tasks){
//                  if(excepts != null && excepts.contains(t.processName)){
//                    continue;
//
//                }

                packages.add(t.processName);*/
        }

        return packages;
    }
    private void kilnw() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void killbg() {
//        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        boolean granted = false;
        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(getApplicationContext().APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getApplicationContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        String currentApp = "NULL";
        List<String> appInfos = getListOfLaunchableApps();
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            PackageManager pm = getApplicationContext().getPackageManager();
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
           if (appList != null && appList.size() > 0) {

               for (UsageStats usageStats : appList) {
                   if (appInfos.contains(usageStats.getPackageName())) {
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                           Log.i("***", "" + "RUNNING "+usageStats.getPackageName() + "=" + usm.isAppInactive(usageStats.getPackageName()));
                       }
                       ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                       am.killBackgroundProcesses(usageStats.getPackageName());
                       textview  .setText(textview.getText() + usageStats.getPackageName() + "\n");
//

                   }
               }

       } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
            Log.e("TODO adapter***", "INCOMPLETE CODE foreground is: " + currentApp);
      }
    }
    public List<String> getListOfLaunchableApps() {

        final PackageManager pm = getPackageManager();
        Intent intend = new Intent(Intent.ACTION_MAIN, null);
        intend.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ri = pm.queryIntentActivities(intend, 0);
        List<String> appInfos = new ArrayList<String>(0);
        for (ResolveInfo resolveInfo : ri) {
            try {
                ApplicationInfo a = pm.getApplicationInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_META_DATA);
                appInfos.add(a.packageName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return appInfos;
    }
}
