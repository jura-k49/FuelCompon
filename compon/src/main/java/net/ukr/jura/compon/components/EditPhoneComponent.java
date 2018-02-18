package net.ukr.jura.compon.components;

import android.util.Log;
import android.view.View;

import net.ukr.jura.compon.base.BaseComponent;
import net.ukr.jura.compon.custom_components.EditPhone;
import net.ukr.jura.compon.interfaces_classes.IBase;
import net.ukr.jura.compon.interfaces_classes.OnChangeStatusListener;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.param.ParamComponent;

public class EditPhoneComponent extends BaseComponent {

    EditPhone editPhone;

    public EditPhoneComponent(IBase iBase, ParamComponent paramMV) {
        super(iBase, paramMV);
    }

    @Override
    public void initView() {
        editPhone = (EditPhone) parentLayout.findViewById(paramMV.paramView.viewId);
        if (editPhone == null) {
            Log.i("SMPL", "Не найден EditPhone в " + paramMV.nameParentComponent);
            return;
        }
        editPhone.setOnChangeStatusListener(statusListener);
    }

    OnChangeStatusListener statusListener = new OnChangeStatusListener() {
        @Override
        public void changeStatus(View v, Object status) {
            iBase.sendActualEvent(paramMV.paramView.viewId, status);
//            switch ((Integer) status) {
//                case 3 :    // стало не валидным
//                    iBase.sendActualEvent(paramMV.paramView.viewId, new Boolean(false));
//                    break;
//                case 4 :    // стало валидным
//                    iBase.sendActualEvent(paramMV.paramView.viewId, new Boolean(true));
//                    break;
//            }
        }
    };

    @Override
    public void changeData(Field field) {

    }
}
