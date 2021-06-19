package com.example.fastcharger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fastcharger.fragments.BatteryFragment;
import com.example.fastcharger.fragments.HistoryFragment;
import com.example.fastcharger.fragments.MoreappFragment;
import com.example.fastcharger.fragments.SettingsFragment;
import com.example.fastcharger.ui.PrivacyActivity;
import com.example.fastcharger.ui.SendFeedbackActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigation;
    private DrawerLayout leftdrawer;
    Integer RatingCount = 5;
    Dialog dialog;
Button buttonno;
    LinearLayout speedtestmenu, getRateusmenu, sendFeedbakmenu, checkUpdatemenu, shareAppmenu, Rateusmenu, privacyPolicymenu, SplitTunnel, Summary, Faq;
    ToggleButton firststar, secstar, thirdstar, fourstar, fivestar;

//    private ImageView mBatteryIndicatorView;
//    private TextView mBatteryPercentageView;
//    private TextView mBatteryStatusView;
//    private TextView mBatteryHealthView;
//    private TextView mBatteryTemperatureView;
//    private TextView mBatteryVoltageView;
//    private TextView mBatteryTechnologyView;
//
//    // Аниматор фона
//    private ValueAnimator mAnimator;

//    private BroadcastReceiver mBatteryStateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Изменился заряд батарейки, отобразим его
//            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
//                int maximumBattery = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
//                int currentBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//
//                float fPercent = ((float) currentBattery / (float) maximumBattery) * 100f;
//
//                int percent = Math.round(fPercent);
//                int drawableLevel = 100 * percent;
//
//                mBatteryPercentageView.setText(getString(R.string.battery_percentage, percent));
//
//                int batteryStatus = getBatteryStatusFromIntent(intent);
//
//                if (batteryStatus == 2) {
//                    // Запускаем аниматор
////                  if (mAnimator == null) {
//////                      createAnimator();
////                  }
////                  mAnimator.start();
//              } else {
////                  if (mAnimator != null) {
////                      mAnimator.cancel();
////                      mAnimator.removeAllUpdateListeners();
////                      mAnimator = null;
////                  }
////                  mBatteryIndicatorView.getDrawable().setLevel(drawableLevel);
//              }
//                }
//            int batteryStatus=getBatteryStatusFromIntent(intent);
//                mBatteryStatusView.setText(getResources()
//                        .getStringArray(R.array.battery_status)[batteryStatus]);
//
//                int health = getBatteryHealthFromIntent(intent);
//
//                mBatteryHealthView.setText(getResources()
//                        .getStringArray(R.array.battery_health)[health]);
//
//                int temperature = getBatteryTemperatureFromIntent(intent);
//                int voltage = getBatteryVoltageFromIntent(intent);
//
//                mBatteryTemperatureView.setText(getString(R.string.battery_temperature, (float) temperature / 10f));
//                mBatteryVoltageView.setText(getString(R.string.battery_voltage, voltage));
//                mBatteryTechnologyView.setText(getBatteryTechnologyFromIntent(intent));
//            }
//
//    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        privacyPolicymenu = findViewById(R.id.privcypolcmenu);
        sendFeedbakmenu = findViewById(R.id.feedbkmenu);
        Rateusmenu = findViewById(R.id.rateus);
        Rateusmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        privacyPolicymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ntentfeed = new Intent(MainActivity.this, PrivacyActivity.class);
                startActivity(ntentfeed);
            }
        });
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_rate_us);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        firststar = dialog.findViewById(R.id.firststr);
        secstar = dialog.findViewById(R.id.secondtstr);
        thirdstar = dialog.findViewById(R.id.thirdstr);
        fourstar = dialog.findViewById(R.id.fourstar);
        fivestar = dialog.findViewById(R.id.fvestr);
//        mhandview = dialog.findViewById(R.id.fivestarhand);
        firststar.setOnClickListener(this);
        secstar.setOnClickListener(this);
        thirdstar.setOnClickListener(this);
        fourstar.setOnClickListener(this);
        fivestar.setOnClickListener(this);
        firststar.setChecked(true);
        secstar.setChecked(true);

        thirdstar.setChecked(true);

        fourstar.setChecked(true);

        fivestar.setChecked(true);

//        buttonrate = dialog.findViewById(R.id.rate_btn);
        buttonno = dialog.findViewById(R.id.cancel_button);
        buttonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openDrawer();
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
////            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.curve_shap);
        }
        sendFeedbakmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ntentfeed = new Intent(MainActivity.this, SendFeedbackActivity.class);
                startActivity(ntentfeed);
            }
        });
        bottomNavigation = findViewById(R.id.bottom_navigation);

        leftdrawer = findViewById(R.id.drawer_layout);
        openDrawer();

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(BatteryFragment.newInstance("", ""));
    }

    public double getBatteryCapacity() {
        Object mPowerProfile_ = null;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
             batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");
//            Toast.makeText(MainActivity.this, batteryCapacity + " mah",
//                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  batteryCapacity;
    }

    public void closeDrawer() {
        if (leftdrawer.isDrawerOpen(GravityCompat.END)) {
            leftdrawer.closeDrawer(GravityCompat.END);
        } else {
            leftdrawer.openDrawer(GravityCompat.END);
        }
    }

    public void openDrawer() {
        if (leftdrawer.isDrawerOpen(GravityCompat.START)) {
            leftdrawer.closeDrawer(GravityCompat.START);
        } else {
            leftdrawer.openDrawer(GravityCompat.START);
        }
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_battery:
                            openFragment(BatteryFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_history:
                            openFragment(HistoryFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_settings:
                            openFragment(SettingsFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_moreapps:
                            openFragment(MoreappFragment.newInstance("", ""));
                            return true;
                    }
                    return false;


                }
//        mBatteryIndicatorView = (ImageView) findViewById(R.id.battery_indicator);
//        mBatteryPercentageView = (TextView) findViewById(R.id.battery_percentage);
//        mBatteryStatusView = (TextView) findViewById(R.id.battery_status);
//        mBatteryHealthView = (TextView) findViewById(R.id.battery_health);
//        mBatteryTemperatureView = (TextView) findViewById(R.id.battery_temperature);
//        mBatteryVoltageView = (TextView) findViewById(R.id.battery_voltage);
//        mBatteryTechnologyView = (TextView) findViewById(R.id.battery_technology);
            };

    @Override
    protected void onStart() {
        super.onStart();
//        startListeningForBattery();

        //Start Service
//        Intent intent = new Intent(MainActivity.this, NotifierService.class);
//        startService(intent);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        super.onStop();
//        stopListeningForBattery();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rateus:
                dialog.show();
                openDrawer();
                break;
            case R.id.shareappmenu:
                openDrawer();
                break;

            case R.id.moreappmenu:
                openDrawer();
                break;

        }

//    private void startListeningForBattery() {
//        IntentFilter batteryFilter = new IntentFilter();
//        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        registerReceiver(mBatteryStateReceiver, batteryFilter);
//    }

//    private void stopListeningForBattery() {
//        unregisterReceiver(mBatteryStateReceiver);
//    }

//    private int getBatteryStatusFromIntent(Intent batteryIntent) {
//        int status = 0;
//
//        if (isBatteryPresent(batteryIntent)) {
//            status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS,
//                    BatteryManager.BATTERY_STATUS_UNKNOWN);
//        }
//
//        return status;
//    }

//    private int getBatteryHealthFromIntent(Intent batteryIntent) {
//        int health = 0;
//
//        if (isBatteryPresent(batteryIntent)) {
//            health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH,
//                    BatteryManager.BATTERY_HEALTH_UNKNOWN);
//        }
//
//        return health;
//    }

//    private int getBatteryTemperatureFromIntent(Intent batteryIntent) {
//        return batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
//    }

//    private int getBatteryVoltageFromIntent(Intent batteryIntent) {
//        int voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
//        return (voltage < 100) ? voltage * 1000 : voltage;
//    }

//    private String getBatteryTechnologyFromIntent(Intent batteryIntent) {
//        String technology = getString(R.string.battery_technology_not_present);
//
//        if (isBatteryPresent(batteryIntent)) {
//            technology = batteryIntent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
//
//            if (TextUtils.isEmpty(technology)) {
//                technology = getString(R.string.battery_technology_unknown);
//            }
//        }
//
//        return technology;
//    }

//    private boolean isBatteryPresent(Intent batteryIntent) {
//        return batteryIntent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, true);
//    }

//    private void createAnimator() {
//        mAnimator = ValueAnimator.ofInt(0, 10000);
//        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        mAnimator.setDuration(4000);
//        mAnimator.setInterpolator(new DecelerateInterpolator());
//        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
////
//                android.widget.Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
    }
}