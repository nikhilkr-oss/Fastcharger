package com.example.fastcharger.customwidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class YelloButton extends AppCompatButton {

    public YelloButton(Context context) {
        super(context);
        setFont();
    }

    public YelloButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public YelloButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"font/poppinsmedd.ttf");
//        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/Raleway-Medium.ttf");
        setTypeface( normal, Typeface.NORMAL );

       /* Typeface bold = Typeface.createFromAsset( getContext().getAssets(), "fonts/Raleway-Medium.ttf" );
        setTypeface( bold, Typeface.BOLD );*/
    }
}
