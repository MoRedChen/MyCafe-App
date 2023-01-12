package com.example.mycafe;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mycafe.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.hellosharedprefs";

    private String mName = "";
    private String mPassword = "";
    private String mEmail = "";
    private int mBalance = 0;
    private boolean mIslogin = false;
    private boolean mFirstOpen = true;

    private final String NAME_KEY = "name";
    private final String PASSWORD_KEY = "password";
    private final String EMAIL_KEY = "email";
    private final String BALANCE_KEY = "balance";
    private final String ISLOGIN_KEY = "isLogin";
    private final String FIRSTOPEN_KEY = "firstOpen";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"test@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Title");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Test Message");
                startActivity(emailIntent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu, R.id.nav_qa)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mName = mPreferences.getString(NAME_KEY, "");
        pref.edit().putString(NAME_KEY, mName);
        mPassword = mPreferences.getString(PASSWORD_KEY, "");
        pref.edit().putString(PASSWORD_KEY, mPassword);
        mEmail = mPreferences.getString(EMAIL_KEY, "");
        pref.edit().putString(EMAIL_KEY, mEmail);
        mBalance = mPreferences.getInt(BALANCE_KEY, 0);
        pref.edit().putInt(BALANCE_KEY, mBalance);
        mIslogin = mPreferences.getBoolean(ISLOGIN_KEY, false);
        pref.edit().putBoolean(ISLOGIN_KEY, mIslogin);
        mFirstOpen = mPreferences.getBoolean(FIRSTOPEN_KEY, true);
        pref.edit().putBoolean(FIRSTOPEN_KEY, mFirstOpen);


        if (mIslogin) {
            Button loginButton = (Button) findViewById(R.id.button_login);
            loginButton.setVisibility(View.GONE);
            Button logoutButton = (Button) findViewById(R.id.button_logout);
            logoutButton.setVisibility(View.VISIBLE);
            Button topUpButton = (Button) findViewById(R.id.button_topUp);
            topUpButton.setVisibility(View.VISIBLE);
            Button showBalanceButton = (Button) findViewById(R.id.button_showBalance);
            showBalanceButton.setVisibility(View.VISIBLE);
            View nav = navigationView.getHeaderView(0);
            LinearLayout headerLinear = (LinearLayout) nav.findViewById(R.id.nav_header_linear);
            headerLinear.setBackgroundResource(R.drawable.side_nav_bar);
            ImageView headerImage = (ImageView) nav.findViewById(R.id.nav_header_imageView);
            headerImage.setImageResource(R.mipmap.ic_user_round);
            TextView headerName = (TextView) nav.findViewById(R.id.nav_header_name);
            headerName.setText(mName);
            TextView headerEmail = (TextView) nav.findViewById(R.id.nav_header_email);
            headerEmail.setText(mEmail);
        } else {
            Button loginButton = (Button) findViewById(R.id.button_login);
            loginButton.setVisibility(View.VISIBLE);
            Button logoutButton = (Button) findViewById(R.id.button_logout);
            logoutButton.setVisibility(View.GONE);
            Button topUpButton = (Button) findViewById(R.id.button_topUp);
            topUpButton.setVisibility(View.GONE);
            Button showBalanceButton = (Button) findViewById(R.id.button_showBalance);
            showBalanceButton.setVisibility(View.GONE);
            View nav = navigationView.getHeaderView(0);
            LinearLayout headerLinear = (LinearLayout) nav.findViewById(R.id.nav_header_linear);
            headerLinear.setBackgroundResource(R.drawable.side_nav_bar_unknown);
            ImageView headerImage = (ImageView) nav.findViewById(R.id.nav_header_imageView);
            headerImage.setImageResource(R.mipmap.ic_unknown_round);
            TextView headerName = (TextView) nav.findViewById(R.id.nav_header_name);
            headerName.setText("Unknown");
            TextView headerEmail = (TextView) nav.findViewById(R.id.nav_header_email);
            headerEmail.setText("");
        }


        createNotificationChannel();

        showAd();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mName = getSharedPreferences("USER", MODE_PRIVATE).getString(NAME_KEY, "");
        mPassword = getSharedPreferences("USER", MODE_PRIVATE).getString(PASSWORD_KEY, "");
        mEmail = getSharedPreferences("USER", MODE_PRIVATE).getString(EMAIL_KEY, "");
        mBalance = getSharedPreferences("USER", MODE_PRIVATE).getInt(BALANCE_KEY, 0);
        mIslogin = getSharedPreferences("USER", MODE_PRIVATE).getBoolean(ISLOGIN_KEY, false);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(NAME_KEY, mName);
        preferencesEditor.putString(PASSWORD_KEY, mPassword);
        preferencesEditor.putString(EMAIL_KEY, mEmail);
        preferencesEditor.putInt(BALANCE_KEY, mBalance);
        preferencesEditor.putBoolean(ISLOGIN_KEY, mIslogin);
        preferencesEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirstOpen = true;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(FIRSTOPEN_KEY, mFirstOpen);
        preferencesEditor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mName = getSharedPreferences("USER", MODE_PRIVATE).getString(NAME_KEY, "");
        mPassword = getSharedPreferences("USER", MODE_PRIVATE).getString(PASSWORD_KEY, "");
        mEmail = getSharedPreferences("USER", MODE_PRIVATE).getString(EMAIL_KEY, "");
        mBalance = getSharedPreferences("USER", MODE_PRIVATE).getInt(BALANCE_KEY, 0);
        mIslogin = getSharedPreferences("USER", MODE_PRIVATE).getBoolean(ISLOGIN_KEY, false);

        outState.putString("name", mName);
        outState.putString("password", mPassword);
        outState.putString("email", mEmail);
        outState.putInt("balance", mBalance);
        outState.putBoolean("isLogin", mIslogin);
    }


    public void showAd() {
        if (mFirstOpen) {
            DialogFragment newFragment = new AdDialogFragment();
            newFragment.show(getSupportFragmentManager(), "ad");
            mFirstOpen = false;
        }
    }

    public void onClickDialogs(View view) {
        switch(view.getId()) {
            case R.id.button_topUp:
                topUp();   break;
            case R.id.button_showBalance:
                showBalance();   break;
            case R.id.button_login:
                signIn();   break;
            case R.id.button_logout:
                logOut();   break;
        }
    }

    public void showBalance() {
        DialogFragment newFragment = new ShowBalanceDialogFragment();
        newFragment.show(getSupportFragmentManager(), "balance");
    }

    public void topUp() {
        DialogFragment newFragment = new TopUpDialogFragment();
        newFragment.show(getSupportFragmentManager(), "topUp");
    }

    public int getBalance() {
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        int num = getSharedPreferences("USER", MODE_PRIVATE)
                .getInt("balance", 0);
        return num;
    }

    public void addBalance(int input) {
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        int num = getSharedPreferences("USER", MODE_PRIVATE)
                .getInt("balance", 0);
        int balance = num+input;
        pref.edit()
                .putInt("balance", balance)
                .commit();
        sendNotification(balance);
    }

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    long[] vibrate_effect = {500, 500, 500, 500};

    private NotificationCompat.Builder getNotificationBuilder(int balance){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Top Up Successfully!")
                .setContentText("current balance: "+balance)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_cafe_foreground_dark_bold)
                .setVibrate(vibrate_effect);
        return notifyBuilder;
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification(int balance) {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(balance);
        notifyBuilder.addAction(R.mipmap.ic_cafe_foreground, "Update Notification", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void signIn() {
        DialogFragment newFragment = new SigninDialogFragment();
        newFragment.show(getSupportFragmentManager(), "Signin");
    }

    public void logIn() {
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        pref.edit()
                .putBoolean("isLogin", true)
                .commit();
        mName = pref.getString(NAME_KEY, "");
        mEmail = pref.getString(EMAIL_KEY, "");
        Button loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setVisibility(View.GONE);
        Button logoutButton = (Button) findViewById(R.id.button_logout);
        logoutButton.setVisibility(View.VISIBLE);
        Button topUpButton = (Button) findViewById(R.id.button_topUp);
        topUpButton.setVisibility(View.VISIBLE);
        Button showBalanceButton = (Button) findViewById(R.id.button_showBalance);
        showBalanceButton.setVisibility(View.VISIBLE);
        LinearLayout headerLinear = (LinearLayout) findViewById(R.id.nav_header_linear);
        headerLinear.setBackgroundResource(R.drawable.side_nav_bar);
        ImageView headerImage = (ImageView) findViewById(R.id.nav_header_imageView);
        headerImage.setImageResource(R.mipmap.ic_user_round);
        TextView headerName = (TextView) findViewById(R.id.nav_header_name);
        headerName.setText(mName);
        TextView headerEmail = (TextView) findViewById(R.id.nav_header_email);
        headerEmail.setText(mEmail);
    }

    public void logOut() {
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        pref.edit()
                .putBoolean("isLogin", false)
                .commit();
        Button loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setVisibility(View.VISIBLE);
        Button logoutButton = (Button) findViewById(R.id.button_logout);
        logoutButton.setVisibility(View.GONE);
        Button topUpButton = (Button) findViewById(R.id.button_topUp);
        topUpButton.setVisibility(View.GONE);
        Button showBalanceButton = (Button) findViewById(R.id.button_showBalance);
        showBalanceButton.setVisibility(View.GONE);

        LinearLayout headerLinear = (LinearLayout) findViewById(R.id.nav_header_linear);
        headerLinear.setBackgroundResource(R.drawable.side_nav_bar_unknown);
        ImageView headerImage = (ImageView) findViewById(R.id.nav_header_imageView);
        headerImage.setImageResource(R.mipmap.ic_unknown_round);
        TextView headerName = (TextView) findViewById(R.id.nav_header_name);
        headerName.setText("Unknown");
        TextView headerEmail = (TextView) findViewById(R.id.nav_header_email);
        headerEmail.setText("");
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.action_search:
                Uri webpage = Uri.parse("https://www.starbucks.com.tw/home/index.jspx?r=100");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
                break;

            case R.id.action_map:
                Uri location = Uri.parse("geo:0,0?q=星巴克 楠梓門市");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}