package net.ukr.jura.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import net.ukr.jura.compon.R;
import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.tools.StaticVM;

public class TextViewGrammar extends android.support.v7.widget.AppCompatTextView
        implements IComponent {
    private Context context;
    private int stringArray;
    private String [] textArray;
    private String alias;
    private Object data;
    public TextViewGrammar(Context context) {
        super(context);
        this.context = context;
    }

    public TextViewGrammar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttrs(attrs);
    }

    public TextViewGrammar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simple);
        alias = a.getString(R.styleable.Simple_alias);
        stringArray = a.getResourceId(R.styleable.Simple_stringArray, 0);
        a.recycle();
        textArray = null;
        if (stringArray != 0) {
            textArray = getResources().getStringArray(stringArray);
        }
    }

    @Override
    public void setData(Object data) {
        if (textArray != null) {
            int num;
            if (data instanceof Long) {
                long lon = (Long) data;
                num = (int) lon;
            } else if (data instanceof Integer) {
                num = (Integer) data;
            } else {
                return;
            }
            String st = StaticVM.TextForNumbet(num, textArray[0],
                    textArray[1], textArray[2]);
            setText(st);
        }
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public Object getData() {
        return null;
    }
}
