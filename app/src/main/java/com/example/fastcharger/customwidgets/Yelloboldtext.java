package com.example.fastcharger.customwidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class Yelloboldtext extends AppCompatTextView {

    public Yelloboldtext(Context context) {
        super(context);
        setFont();

    }

    public Yelloboldtext(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();

    }

    public Yelloboldtext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();

    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "font/poppinsbold.ttf");
        setTypeface(normal, Typeface.NORMAL);
    }



}
