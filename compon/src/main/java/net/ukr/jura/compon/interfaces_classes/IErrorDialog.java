package net.ukr.jura.compon.interfaces_classes;

import android.view.View;

public interface IErrorDialog {
    public void setTitle(String title);
    public void setMessage(String message);
    public void setOnClickListener(View.OnClickListener listener);
}
