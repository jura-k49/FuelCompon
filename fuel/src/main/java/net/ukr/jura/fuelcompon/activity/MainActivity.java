package net.ukr.jura.fuelcompon.activity;

import net.ukr.jura.compon.base.BaseActivity;
import net.ukr.jura.compon.models.MultiComponents;
import net.ukr.jura.fuelcompon.R;

public class MainActivity extends BaseActivity {
//    @Override
//    public int getProgressLayout() {
//        return R.layout.dialog_progress;
//    }
//
//    @Override
//    public int getDialogLayout() {
//        return R.layout.base_dialog;
//    }

    @Override
    public MultiComponents getScreen() {
        return getComponent(getString(R.string.main));
    }
}
