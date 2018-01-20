package net.ukr.jura.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.ukr.jura.compon.R;

public class PanelAnimate extends LinearLayout{
    private Context context;
    private View view;
    private int viewId;
    private RelativeLayout container;
    private int fadedScreenColorDefault = 0x50000000;
    private int fadedScreenColor = fadedScreenColorDefault;

    public PanelAnimate(Context context) {
        super(context);
        init(context, null);
    }

    public PanelAnimate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PanelAnimate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        setGravity(Gravity.BOTTOM);
        container = new RelativeLayout(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(lp);
        addView(container);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simple);
            viewId = a.getResourceId(R.styleable.Simple_viewLayoutId, 0);
            setView();
            fadedScreenColor = a.getColor(R.styleable.Simple_fadedScreenColor, fadedScreenColorDefault);
            setColor();
        }
    }

    private void setView() {
        if (viewId != 0) {
            view = LayoutInflater.from(context).inflate(viewId, null);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            container.addView(view);
        }
    }

    private void setColor() {
        if (fadedScreenColor != 0) {
            setBackgroundColor(fadedScreenColor);
        }
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
        setView();
    }

    public void  setFadedScreenColor(int color) {
        fadedScreenColor = color;
        setColor();
        setVisibility(GONE);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }
}
