package com.lesgood.siswa.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.lesgood.siswa.R;


/**
 * Created by Agus on 1/3/17.
 */
public class TypefacedTextView extends android.support.v7.widget.AppCompatTextView {

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);
        styledAttrs.recycle();

        if (fontName == null) {
            fontName = "Roboto.Light.ttf";
        }
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        setTypeface(typeface);
    }

}
