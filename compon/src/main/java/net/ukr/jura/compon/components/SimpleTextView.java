package net.ukr.jura.compon.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import net.ukr.jura.compon.R;

public class SimpleTextView extends TextView{
    private Context context;
    private String numberFormat, dateFormat;
    public SimpleTextView(Context context) {
        super(context);
        this.context = context;
    }

    public SimpleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttrs(attrs);
    }

    public SimpleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simple);
        numberFormat = a.getString(R.styleable.Simple_numberFormat);
        dateFormat = a.getString(R.styleable.Simple_dateFormat);
        a.recycle();
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
