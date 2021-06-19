package com.example.fastcharger.customwidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class PoppinsSemiBold extends AppCompatTextView {

    public PoppinsSemiBold(Context context) {
        super(context);
        setFont();

    }

    public PoppinsSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();

    }

    public PoppinsSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();

    }

    private void setFont() {
//        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Medium.ttf");
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "font/poppinssemibold.ttf");
        setTypeface(normal, Typeface.NORMAL);
    }

}
