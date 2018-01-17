package net.ukr.jura.fuelcompon.activity;

import net.ukr.jura.compon.base.BaseActivity;
import net.ukr.jura.compon.components.MultiComponents;
import net.ukr.jura.fuelcompon.R;

public class MainActivity extends BaseActivity {
    @Override
    public MultiComponents getScreen() {
        return getComponent(getString(R.string.main));
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
    // prov
}
