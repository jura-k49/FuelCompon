package net.ukr.jura.compon.tools;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static Calendar stringToDate(String st) {
        Calendar c;
        String dd = "";
        if (st.indexOf("T") > 0) {
            dd = st.split("T")[0];
        } else {
            dd = st;
        }
        Log.d("QWERT","DDDD="+dd);
        String[] d = dd.split("-");
        c = new GregorianCalendar(Integer.valueOf(d[0]),
                Integer.valueOf(d[1]) - 1,
                Integer.valueOf(d[2]));
        return c;
    }
}
