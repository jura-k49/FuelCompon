package net.ukr.jura.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

import net.ukr.jura.compon.R;

public class SimpleImageView extends RoundedImageView {
    private Context context;
    private int placeholder;
    public SimpleImageView(Context context) {
        super(context);
        this.context = context;
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttrs(attrs);
    }

    public SimpleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simple);
        placeholder = a.getResourceId(R.styleable.Simple_placeholder, 0);
//        dateFormat = a.getString(R.styleable.Simple_dateFormat);
        a.recycle();
    }

    public int getPlaceholder() {
        return placeholder;
    }
}
