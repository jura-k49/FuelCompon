package net.ukr.jura.compon.tools;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class StaticVM {

    public static View findViewByName(View v, String name) {
        View vS = null;
        ViewGroup vg;
        int id;
        String nameS;
        if (v instanceof ViewGroup) {
            vg = (ViewGroup) v;
            int countChild = vg.getChildCount();
            id = v.getId();
            if (id > -1) {
                nameS = v.getContext().getResources().getResourceEntryName(id);
                if (name.equals(nameS)) {
                    return v;
                }
            }
            for (int i = 0; i < countChild; i++) {
                vS = findViewByName(vg.getChildAt(i), name);
                if (vS != null) {
                    return vS;
                }
            }
        } else {
            id = v.getId();
            if (id != -1) {
                nameS = v.getContext().getResources().getResourceEntryName(id);
                if (name.equals(nameS)) return v;
            }
        }
        return vS;
    }
}
