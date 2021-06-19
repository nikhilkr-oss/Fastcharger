package com.example.fastcharger.fragments;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.usage.UsageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fastcharger.MainActivity;
import com.example.fastcharger.R;
import com.example.fastcharger.ui.Optimisation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.content.Context.BATTERY_SERVICE;

public class BatteryFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    Dialog dialog;
    ImageView navmenu, fastchargeic, fullchargic, tricklechrgic, wifi_ic, mobiledata_ic, aplanemod, brightness, sleepmod, rotationmod, syncmod, soundmod, blutooth, locationmod;

    private Context mContext;
    private TextView info, report1, report2, report3;
    private TextView batteryfulltimeleft;
    private TextView volttv;
    private TextView batcapacitytv;
    private TextView temptv;
    private TextView mTextViewPercentage;
    private Button optbutton;
    private Intent intent;
    private int mProgressStatus = 0;
    private int cnt = 0;
    private float batteryLevel1;
    private float batteryLevel2;
    private Calendar now1, now2;
    Date tym1;
    private float time1, time2;
    private String charging, chargingMethod;
    private UsageStatsManager mUsageStatsManager;
    Field field = null;
    LottieAnimationView fullchargeanimation;
    int voltage, temp;
    private WifiManager wifiManager;

    private class ProcessToKill {
        // A class object to keep processes to be killed
        private String packageName;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1); //get the scale of battery (usually 100)
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1); //get the level of the battery


            // Are we charging or is the phone fully charged?
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int tempc = (int) (temp / 10.0f);
            int volt = (int) (voltage / 1000.0f);
            volttv.setText(volt + "V");
            temptv.setText(tempc + "℃");
            float percentage = level / (float) scale; //calculate the battery percentage of the battery according to the scale

            /*We need to get the current time and get the current battery percentage. We can easily know the next battery percentage;
             * if it's charging we get the upper percentage and vice-versa. */
            if (cnt < 1) {
                batteryLevel2 = percentage + (float) 0.0001; //if the phone is charging, this is the next battery level we are expecting
                batteryLevel1 = percentage - (float) 0.0001; //if it is discharging, this is the next battery level we are expecting
                tym1 = Calendar.getInstance().getTime();
                //get Time 1
                cnt++;
            }

            //Define what happens when the phone is charging as well as when it's not charging
            if (isCharging) {
                charging = "Charging";
//                info.setText("Hours Till Battery Full");
                fastchargeic.setImageDrawable(context.getResources().getDrawable(R.drawable.fast_charge_active));
                fullchargeanimation.setVisibility(View.VISIBLE);
//                if(percentage >= batteryLevel2){
                //get Time 2



                float p_left = (float) 1 - percentage;
                Date tym2 = Calendar.getInstance().getTime();

                long tym_diff = tym2.getTime() - tym1.getTime();
                long mills = Math.abs(tym_diff);

                long rem = (long) (mills * p_left * 100);

                int hours = (int) (rem / (1000 * 60 * 60));
                int mins = (int) (rem / (1000 * 60)) % 60;
                long secs = (int) (rem / 1000) % 60;
//                batteryfulltimeleft.setText("" + hours + "h" + mins + "m");
                cnt = 0;
//                }
            } else {
                fullchargeanimation.setVisibility(View.INVISIBLE);

//                info.setText("Hours Left Till Battery Dies");
                charging = "Not Charging";
                fastchargeic.setImageDrawable(context.getResources().getDrawable(R.drawable.fast_charge));

//                if(percentage <= batteryLevel1){
                //get Time 2

                Date tym2 = Calendar.getInstance().getTime();

                long tym_diff = tym2.getTime() - tym1.getTime();
                long mills = Math.abs(tym_diff);

                long rem = (long) (mills * percentage * 100);

                int hours = (int) (rem / (1000 * 60 * 60));
                int mins = (int) (rem / (1000 * 60)) % 60;
                long secs = (int) (rem / 1000) % 60;
//                batteryfulltimeleft.setText("" + hours + "h" + mins + "m");

                cnt = 0;


            }

            mTextViewPercentage.setText("" + level + "%");
//            reposrt1.setText("Charge State : "+charging);

            mProgressStatus = (int) ((percentage) * 100);
//            mProgressBar.setProgress(mProgressStatus);
            //mTextViewPercentage.setText("" + mProgressStatus + "%");
        }
    };


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BatteryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BatteryFragment newInstance(String param1, String param2) {
        BatteryFragment fragment = new BatteryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
//        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        mContext.registerReceiver(mBroadcastReceiver, iFilter);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_battery, container, false);
        initall();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            BatteryManager batteryManager = (BatteryManager) getActivity().getSystemService(BATTERY_SERVICE);

            long remaining_time = batteryManager.computeChargeTimeRemaining();

            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(remaining_time),
                    TimeUnit.MILLISECONDS.toMinutes(remaining_time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remaining_time)),
                    TimeUnit.MILLISECONDS.toSeconds(remaining_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remaining_time)));


            String mins= String.valueOf(TimeUnit.MILLISECONDS.toMinutes(remaining_time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remaining_time)));

            batteryfulltimeleft.setText("" + TimeUnit.MILLISECONDS.toHours(remaining_time) + "h" + mins + "m");

        }

        String bc = String.valueOf(((MainActivity) getActivity()).getBatteryCapacity());
        batcapacitytv.setText(bc + " mah");
        mContext = getActivity();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver, iFilter);
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        checkWifi();

        optbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TestAct.class);
                startActivity(intent);


            }
        });
        navmenu.setOnClickListener(this);
        mobiledata_ic.setOnClickListener(this);
        wifi_ic.setOnClickListener(this);
        soundmod.setOnClickListener(this);
        sleepmod.setOnClickListener(this);
        syncmod.setOnClickListener(this);
        locationmod.setOnClickListener(this);
        aplanemod.setOnClickListener(this);
        rotationmod.setOnClickListener(this);
        blutooth.setOnClickListener(this);
        brightness.setOnClickListener(this);


        return view;
    }

    private void killBgProcesses() {
        ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo pid : am.getRunningAppProcesses()) {
            android.os.Process.sendSignal(15884, android.os.Process.SIGNAL_KILL);
            Showtoast(String.valueOf(15884));
        }


//        ArrayList<String> pkgs = new ArrayList<>();
//
//        PackageManager pm = getActivity().getPackageManager();
//        List<InslattedAppsData> apps = new ArrayList<InslattedAppsData>();
//        List<PackageInfo> packs = pm.getInstalledPackages(0);
//        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
//        for (int i = 0; i < packs.size(); i++) {
//
//            PackageInfo p = packs.get(i);
////            if ((!isSystemPackage(p))) {
//            if (pm.getLaunchIntentForPackage(p.packageName) != null) {
//
//                String packages = p.applicationInfo.packageName;
//                pkgs.add(packages);
//                final ActivityManager am = (ActivityManager) getActivity()
//                        .getSystemService(ACTIVITY_SERVICE);
//                am.killBackgroundProcesses(p.packageName);
//                Showtoast(packages);
//            }
//        }


    }

    public void setMobileDataState(boolean mobileDataEnabled) {
        try {
            TelephonyManager telephonyService;
            telephonyService = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            Method setMobileDataEnabledMethod = Objects.requireNonNull(telephonyService).getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
        } catch (Exception ex) {
            Log.e("MainActivity", "Error setting mobile data state", ex);
        }
    }

    public boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            blutooth.setImageDrawable(getResources().getDrawable(R.drawable.ic_bluetooth_on));

            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            blutooth.setImageDrawable(getResources().getDrawable(R.drawable.ic_bluetooth_off));

            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }


    private void Showtoast(String msg) {
        Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initall() {

        navmenu = view.findViewById(R.id.navmenu);
        fullchargeanimation = view.findViewById(R.id.chargingcircleanim);
        mTextViewPercentage = view.findViewById(R.id.batteryperctxt);
        batteryfulltimeleft = view.findViewById(R.id.batteryremtimeTV);
        batcapacitytv = view.findViewById(R.id.batterycapacitytv);
        volttv = view.findViewById(R.id.voltagetxtview);
        optbutton = view.findViewById(R.id.optimizebbtn);
        temptv = view.findViewById(R.id.temptxtv);
        fullchargic = view.findViewById(R.id.fullchrgic);
        tricklechrgic = view.findViewById(R.id.tricklechrgic);
        fastchargeic = view.findViewById(R.id.fastchargeic);
        wifi_ic = view.findViewById(R.id.wifiic);
        mobiledata_ic = view.findViewById(R.id.dataic);
        locationmod = view.findViewById(R.id.locationic);
        blutooth = view.findViewById(R.id.blutoothic);
        brightness = view.findViewById(R.id.brightnessic);
        aplanemod = view.findViewById(R.id.aplanemodic);
        sleepmod = view.findViewById(R.id.lockscrenic);
        rotationmod = view.findViewById(R.id.rotationic);
        syncmod = view.findViewById(R.id.syncic);
        soundmod = view.findViewById(R.id.soundic);

//checkWifi();
    }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BatteryManager mBatteryManager = (BatteryManager) activity.getSystemService(Context.BATTERY_SERVICE);
//            Long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
//            Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
//
//            if (chargeCounter != null && capacity != null) {
//                long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
//                return value;
//            }
//        }
//
//        return 0;

    public void lockDeviceRotation(boolean value) {
        if (value) {
            int currentOrientation = getActivity().getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        }
    }

    private void checkWifi() {
        wifiManager.getWifiState();
        if (wifiManager.isWifiEnabled()) {
            disableWifi();
            wifi_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_off));


        } else {
            enableWifi();
            wifi_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_on));

        }
    }


    public void enableWifi() {
        wifiManager.setWifiEnabled(true);
        Toast.makeText(getActivity(), "Wifi enabled", Toast.LENGTH_SHORT).show();
    }


    // automatic turn off the gps
    public void turnGPSOff() {
//        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
//        intent.putExtra("enabled", false);
//        sendBroadcast(intent);


    }

    public void disableWifi() {
        wifiManager.setWifiEnabled(false);
        Toast.makeText(getActivity(), "Wifi Disabled", Toast.LENGTH_SHORT).show();
    }


    private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }

    public void turnGPSOn() {
        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getActivity().sendBroadcast(poke);
        }

    }

    @SuppressLint("InlinedApi")
    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    @SuppressLint("NewApi")
    public void airplaneModeOn(View view) {
        try {

            android.provider.Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON,
                    isAirplaneModeOn(getActivity()) ? 0 : 1);

            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !isAirplaneModeOn(getActivity()));
            getActivity().sendBroadcast(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception occured during Airplane Mode ON", Toast.LENGTH_LONG)
                    .show();
        }
    }

    boolean DataOnOff(boolean status, Context context) {
        int bv = 0;
        try {
            if (bv == Build.VERSION_CODES.FROYO) {
                //android 2.2 versiyonu için
                Method dataConnSwitchmethod;
                Class<?> telephonyManagerClass;
                Object ITelephonyStub;
                Class<?> ITelephonyClass;

                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);

                telephonyManagerClass = Class.forName(telephonyManager
                        .getClass().getName());
                Method getITelephonyMethod = telephonyManagerClass
                        .getDeclaredMethod("getITelephony");
                getITelephonyMethod.setAccessible(true);
                ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
                ITelephonyClass = Class.forName(ITelephonyStub.getClass()
                        .getName());

                if (status) {
                    dataConnSwitchmethod = ITelephonyClass
                            .getDeclaredMethod("enableDataConnectivity");
                } else {
                    dataConnSwitchmethod = ITelephonyClass
                            .getDeclaredMethod("disableDataConnectivity");
                }
                dataConnSwitchmethod.setAccessible(true);
                dataConnSwitchmethod.invoke(ITelephonyStub);

            } else {
                // android 2.2 üstü versiyonlar için
                final ConnectivityManager conman = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                final Class<?> conmanClass = Class.forName(conman.getClass()
                        .getName());
                final Field iConnectivityManagerField = conmanClass
                        .getDeclaredField("mService");
                iConnectivityManagerField.setAccessible(true);
                final Object iConnectivityManager = iConnectivityManagerField
                        .get(conman);
                final Class<?> iConnectivityManagerClass = Class
                        .forName(iConnectivityManager.getClass().getName());
                final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                        .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(iConnectivityManager, status);
            }

            return true;

        } catch (Exception e) {
            Log.e("Mobile Data", "error turning on/off data");

            return false;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        mContext = getActivity();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver, iFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navmenu:

                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.dataic:
                DataOnOff(true, getActivity());
//getMobileDataState();
//Showtoast(""+getMobileDataState());
//                mobiledata_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_data_on));

                break;
            case R.id.wifiic:
//                WifiManager wifiManager = (WifiManager)this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                wifiManager.setWifiEnabled(true);

                checkWifi();

//                wifi_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_on));
                break;
            case R.id.blutoothic:
                if (setBluetooth(true)) {
                    setBluetooth(false);
                } else {
                    setBluetooth(true);

                }
                break;
            case R.id.rotationic:

                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                lockDeviceRotation(true);

//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//                if (lockDeviceRotation(true)) {
//                    lockDeviceRotation(false);
//                } else {
//                    lockDeviceRotation(true);
//
//                }
                break;
            case R.id.brightnessic:

                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.aplanemodic:
//                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//             intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
//               startActivity(intent);
                Settings.System.putInt(
                        getContext().getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, true ? 1 : 0);
                Intent i = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                i.putExtra("state", true);
                getContext().sendBroadcast(i);
// toggle airplane mode

                break;
            case R.id.syncic:

                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.locationic:

//                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
//                startActivity(intent);
                turnGPSOn();
                break;
            case R.id.soundic:
                AudioManager am;
                boolean dnd = false;
                NotificationManager notificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent mintent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(mintent);
                } else {
                    am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//                    soundmod.setImageDrawable(getResources().getDrawable(R.drawable.ic_donot_disturb_icon));
                    am.setRingerMode(0);
                    dnd = true;
                }
                if (dnd = false) {
                    am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

                    am.setRingerMode(1);

                }

                break;


        }

    }
}
