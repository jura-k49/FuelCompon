package net.ukr.jura.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import net.ukr.jura.compon.R;

import java.util.ArrayList;
import java.util.List;

public class PagerIndicator extends LinearLayout{
    private Context context;
    private int ITEM_ID, ITEM_SELECT_ID;
    private int DIAMETR;
    private boolean SELECT_ONE;
    private int count;
    private int selectPosition;
    private List<LinearLayout> list;
    public PagerIndicator(Context context) {
        super(context);
        this.context = context;
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttributes(attrs);
    }

    protected void setAttributes(AttributeSet attrs){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
        ITEM_ID = a.getResourceId(R.styleable.PagerIndicator_itemId, 0);
        ITEM_SELECT_ID = a.getResourceId(R.styleable.PagerIndicator_itemSelectId, 0);
        SELECT_ONE = a.getBoolean(R.styleable.PagerIndicator_selectOne, true);
        DIAMETR = (int) a.getDimension(R.styleable.PagerIndicator_diametrItem, 25f);
        a.recycle();
        selectPosition = 0;
        list = new ArrayList<>();
        count = -1;
    }

    public void setCount(int count) {
        this.count = count;
        initIndicator();
    }

    public void setSelect(int position) {
        if (count < 0) {
            selectPosition = position;
        } else {
            Log.d("QWERT","selectPosition="+selectPosition+" position="+position);
            list.get(selectPosition).setBackgroundResource(ITEM_ID);
            selectPosition = position;
            list.get(selectPosition).setBackgroundResource(ITEM_SELECT_ID);
        }
    }

    private void initIndicator() {
        removeAllViews();
        list.clear();
        for (int i = 0; i < count; i++) {
            LinearLayout ll = new LinearLayout(context);
            LayoutParams lp = new LayoutParams(DIAMETR, DIAMETR);
            lp.setMargins(DIAMETR, 0, DIAMETR, 0);
            ll.setLayoutParams(lp);
            if (selectPosition == i) {
                ll.setBackgroundResource(ITEM_SELECT_ID);
            } else {
                ll.setBackgroundResource(ITEM_ID);
            }
            list.add(ll);
            addView(ll);
        }
    }
}
