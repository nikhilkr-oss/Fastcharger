package com.example.fastcharger.customwidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class YelloEditText extends AppCompatEditText {
    public YelloEditText(Context context) {
        super(context);
        setFont();
    }

    public YelloEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public YelloEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "font/robotoreg.ttf");
//        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Medium.ttf");
        setTypeface(normal, Typeface.NORMAL);
    }
}
