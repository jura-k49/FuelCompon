package net.ukr.jura.fuelcompon.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ukr.jura.compon.interfaces_classes.IErrorDialog;
import net.ukr.jura.fuelcompon.R;

public class ErrorDialog extends DialogFragment implements IErrorDialog {
    private String titl, mes;

    private TextView title;
    private TextView message;
    private View cancel;
    private View.OnClickListener listener;
    private int count;

    public ErrorDialog() {
        setStyle(STYLE_NO_TITLE, 0);
    }

    private View parentLayout;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
//        if (dialog != null) {
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.dialog_error, container, false);
        title = (TextView) parentLayout.findViewById(R.id.title);
        message = (TextView) parentLayout.findViewById(R.id.message);
        cancel = parentLayout.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        count = 0;
        handler.postDelayed(whiteTitle, 50);
        return parentLayout;
    }

    Handler handler = new Handler();

    Runnable whiteTitle = new Runnable() {
        @Override
        public void run() {
            if (title == null || message == null) {
                if (count >= 10) return;
                count++;
                handler.postDelayed(whiteTitle, 150);
            } else {
                title.setText(titl);
                message.setText(mes);
            }
        }
    };

    @Override
    public void setTitle(String title) {
        titl = title;
    }

    @Override
    public void setMessage(String message) {
        mes = message;
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
