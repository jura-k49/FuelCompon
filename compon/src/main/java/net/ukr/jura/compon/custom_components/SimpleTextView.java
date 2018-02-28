package net.ukr.jura.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import net.ukr.jura.compon.R;
import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.interfaces_classes.OnChangeStatusListener;
import net.ukr.jura.compon.tools.StaticVM;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleTextView extends android.support.v7.widget.AppCompatTextView
        implements IComponent{
    private Context context;
    private String numberFormat, dateFormat;
    private Object data;
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

    @Override
    public void setData(Object data) {
        this.data = data;
        if (dateFormat != null && dateFormat.length() > 0) {
            if (data instanceof String) {
                String stt = new String((String) data);
                Date dat = stringToDate(stt).getTime();
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                setText(df.format(dat));
            } else if (data instanceof Long) {

            } else if (data instanceof Date) {

            }
        }
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener) {

    }

    @Override
    public String getString() {
        return getText().toString();
    }

    public Calendar stringToDate(String st) {
        Calendar c;
        String dd = "";
        String tt = "";
        String[] datTime;
        if (st.indexOf("T") > 0) {
            datTime = st.split("T");
            dd = datTime[0];
            tt = datTime[1].split("\\+")[0];
        } else {
            dd = st;
        }
        String[] d = dd.split("-");
        if (tt.length() > 0) {
            String[] t = tt.split(":");
            c = new GregorianCalendar(Integer.valueOf(d[0]),
                    Integer.valueOf(d[1]) - 1,
                    Integer.valueOf(d[2]),
                    Integer.valueOf(t[0]), Integer.valueOf(t[1]), Integer.valueOf(t[2]));
        } else {
            c = new GregorianCalendar(Integer.valueOf(d[0]),
                    Integer.valueOf(d[1]) - 1,
                    Integer.valueOf(d[2]));
        }
        return c;
    }
}
