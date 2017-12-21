package net.ukr.jura.fuelcompon.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.ukr.jura.compon.tools.StaticVM;
import net.ukr.jura.fuelcompon.R;

public class BaseDialog extends DialogFragment {

    public static final int BUTTON_POSITIVE = 0,
            BUTTON_NEGATIVE = 1,
            BUTTON_CANCEL = 2;
    private OnClickListener clickPositive, clickNegative, clickCancel;
    private String titl, mes;
    private View.OnClickListener viewClick;

    private TextView title;
    private TextView message;
    private LinearLayout close;
    private LinearLayout done;
    private LinearLayout cancel;


    public BaseDialog() {
        setStyle(STYLE_NO_TITLE, 0);
    }

    private View parentLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.base_dialog, container, false);
        title = (TextView) StaticVM.findViewByName(parentLayout, "title");
        message = (TextView) StaticVM.findViewByName(parentLayout, "message");
        close = (LinearLayout) StaticVM.findViewByName(parentLayout, "close");
        if (close != null) {
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (clickNegative != null) {
                        clickNegative.onClick(BUTTON_NEGATIVE);
                    }
                }
            });
        }
        done = (LinearLayout) StaticVM.findViewByName(parentLayout, "done");
        if (done != null) {
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (viewClick != null) {
                        viewClick.onClick(done);
                    } else {
                        if (clickPositive != null) {
                            clickPositive.onClick(BUTTON_POSITIVE);
                        }
                    }
                }
            });
        }
        cancel = (LinearLayout) StaticVM.findViewByName(parentLayout, "cancel");
        if (cancel != null) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (clickCancel != null) {
                        clickCancel.onClick(BUTTON_CANCEL);
                    }
                }
            });
        }
        title.setText(titl);
        message.setText(mes);
        if (clickNegative != null) {
            close.setVisibility(View.VISIBLE);
        }
        if (clickCancel != null) {
            cancel.setVisibility(View.VISIBLE);
        }
        return parentLayout;
    }

    public void setTitle(String t) {
        titl = t;
    }

    public void setMessage(String m) {
        mes = m;
    }

    public void setOnClickListener(View.OnClickListener click) {
        viewClick = click;
    }

    public void setOnClickListener(OnClickListener cp, OnClickListener cn, OnClickListener cc) {
        clickPositive = cp;
        clickNegative = cn;
        clickCancel = cc;
    }

    public interface OnClickListener {
        public void onClick(int which);
    }
}
