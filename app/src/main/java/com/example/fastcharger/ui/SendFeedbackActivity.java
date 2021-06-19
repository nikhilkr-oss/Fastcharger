package com.example.fastcharger.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fastcharger.R;
import com.example.fastcharger.customwidgets.YelloEditText;


public class SendFeedbackActivity extends AppCompatActivity {

    private YelloEditText mFeedbackEt, mUrEmailEt;
    private AppCompatButton mSend;
    private ConstraintLayout mScreenshot;
    private AppCompatImageView mImageview, mIcon;
    private TextView mAdScrnshtText;
    private ImageView bakbtn;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CustomTheme);
        setContentView(R.layout.activity_send_feedback);

        initViews();



        bakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mFeedbackEt.getText().toString().isEmpty()) {
                    mFeedbackEt.setError("Enter your feedback");
                } else {
                    String[] emails = new String[]{"feedback@boomvpn.me"};
                    shareTextToEmail(SendFeedbackActivity.this, emails,
                            "Feedback Boom VPN", mFeedbackEt.getText().toString());


                }
            }
        });

        mScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


    }

    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }


    private void shareTextToEmail(Context context, String[] email, String subject, String text) {
        Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
        selectorIntent.setData(Uri.parse("mailto:"));

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);


        emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        emailIntent.setType("image/png");
        emailIntent.setSelector(selectorIntent);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Boom VPN Feedback"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(context, "No Intent Handler Found", Toast.LENGTH_SHORT).show();

        }

    }

    private void initViews() {

        mFeedbackEt = findViewById(R.id.et_send_feedbk);
        mUrEmailEt = findViewById(R.id.et_email);
        mSend = findViewById(R.id.send_feedback);
        mScreenshot = findViewById(R.id.ll_2);
        mImageview = findViewById(R.id.img_scrnsht);
        mIcon = findViewById(R.id.icon_gal);
        mAdScrnshtText = findViewById(R.id.tv_ad_scrnsht);
        bakbtn = findViewById(R.id.backbutton);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            mImageview.setVisibility(View.VISIBLE);
            mIcon.setVisibility(View.GONE);
            mAdScrnshtText.setVisibility(View.GONE);
            mImageview.setImageURI(imageUri);
        }


    }


}

