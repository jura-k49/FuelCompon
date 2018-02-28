package net.ukr.jura.fuelcompon.activity;

import android.util.Log;

import net.ukr.jura.compon.base.BaseActivity;
import net.ukr.jura.compon.components.MultiComponents;
import net.ukr.jura.fuelcompon.R;

import java.io.UnsupportedEncodingException;

public class MainActivity extends BaseActivity {
    @Override
    public MultiComponents getScreen() {
        return getComponent(getString(R.string.splash));
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
