package com.example.fastcharger.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastcharger.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PrivacyActivity extends AppCompatActivity {
     ImageView  bakbtn;
ImageView email;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CustomTheme);

        setContentView(R.layout.activity_privacy);
        bakbtn = findViewById(R.id.bakbutton);

        email= findViewById(R.id.fab);
        bakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        email.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mailid = "feedback@boomvpn.me";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + mailid)); // only email apps should handle this
//                intent.putExtra(Intent.EXTRA_EMAIL, "nikhil@gmail.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                startActivity(intent);

            }
        });        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Privacy Policy");
//collapsingToolbar.setExpandedTitleColor(R.color.white);
//        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(R.color.white));
//        collapsingToolbar.setCollapsedTitleTextColor(ColorStateList.valueOf(R.color.white));

//      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);

        Typeface face= Typeface.createFromAsset(getAssets(),"font/robotobold.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(face);
        collapsingToolbar.setExpandedTitleTypeface(face);
        TextView textView=findViewById(R.id.txt_content);
        textView.setText("Boom VPN believes in privacy and online freedom and that it should be very clear how we manage and protect your personal information.\n" +
                "\n" +
                "We want you to understand what limited information we collect and what may happen to that information when you use any of the Boom VPN Services. This document explains the privacy rules applicable to all information collected when you use the Boom VPN Services.\n" +
                "If you have any questions or concerns, please contact us by clicking here\n" +
                "Processing of your personal data\n" +
                "\n" +
                "The core of our service is the Boom VPN Private Network which provides private and secure data transmission.\n" +
                "If you are using the Boom VPN Service, our app establishes and maintains an encrypted tunnel between your device and our network.\n" +
                "We ensure that we never log browsing history, traffic destination, data content, or DNS queries.\n" +
                "Some information is however necessary for technical functioning of the services.\n" +
                "In order to maintain excellent customer support and quality of service, Boom VPN collects the following information related to your VPN usage:\n" +
                "We collect informations related to the client you have activated, such as device model, country code, and app version.\n" +
                "Knowing those basic informations allows our Support Team to troubleshoot technical issues with you.\n" +
                "We collect information about whether you have successfully established a VPN connection, connection time, and the VPN location.\n" +
                "This minimal information assists us in providing technical support, such as identifying connection problems, and to enable us to identify and fix network issues.\n" +
                "We counts amount of traffic used by clients.\n" +
                "Although we provide unlimited data transfer on our free servers, if we notice that a single free user use more traffic than thousands of others combined, thereby affecting the quality of service for other free users, that user with the abnormal network traffic will be refused.\n" +
                "\n" +
                "Your surfing is private and safe. When you disconnect from Boom VPN servers there is no warranty to be secure and safe yet.\n" +
                "All the consequences are at your own risk.\n" +
                "Please note that Boom VPN helps you secure your internet session and does not protect your device from malware, trojans or viruses.\n" +
                "We recommend you to use anti-virus solutions in order to secure your devices as well.\n" +
                "Third-Party Advertisers\n" +
                "\n" +
                "Boom VPN uses admob as third-parties (the \"Third Party\") for advertisement.\n" +
                "Third Party may use technologies to access some data including but not limited to cookies to estimate the effectiveness of their advertisements.\n" +
                "Activities of the Third Party is not controlled by Boom VPN and this is not included in Boom VPN privacy policy but is related to the privacy policies of our third party advertisers.\n" +
                "\n" +
                "Boom VPN is not a publisher of the Third Party content accessed through the Service and is not responsible for the content, accuracy, timeliness or delivery of any opinions, advice, statements, messages, services, graphics, data or any other information provided to or by the Third Party as accessible through the Service.\n" +
                "\n" +
                "The Third Party content is not under the control of Boom VPN and Boom VPN is not liable for any damage or loss caused or alleged to be caused by or in connection with use of or reliance on any Third Party.\n" +
                "The inclusion of any links to Third Party content are provided solely as a convenience to you and does not imply an endorsement or recommendation by Boom VPN of any third party resources or content.\n" +
                "Please check out more details on Google Admob Help\n" +
                "Changes to the Privacy Policy\n" +
                "\n" +
                "We might need to change this Privacy Policy from time to time to reflect changes in our business or operations, without prior notice to you.\n" +
                "\n" +
                "Your continued use of the Services constitutes your acceptance of our Privacy Policy.\n" +
                "\n" +
                "©Copyright 2020 boomvpn.com \n" +
                "\n" +
                "\n");
        collapsingToolbar.setCollapsedTitleTypeface(face);
//        bakbtn = findViewById(R.id.backbutton);
//        bakbtn.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }


}