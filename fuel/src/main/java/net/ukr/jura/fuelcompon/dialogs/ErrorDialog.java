package net.ukr.jura.fuelcompon.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private View.OnClickListener listener;

    public ErrorDialog() {
        setStyle(STYLE_NO_TITLE, 0);
    }

    private View parentLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.dialog_error, container, false);
        title = (TextView) parentLayout.findViewById(R.id.title);
        message = (TextView) parentLayout.findViewById(R.id.message);
        title.setText(titl);
        message.setText(mes);
//        parentLayout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//                if (listener != null) {
//                    listener.onClick(view);
//                }
//            }
//        });
        return parentLayout;
    }

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
