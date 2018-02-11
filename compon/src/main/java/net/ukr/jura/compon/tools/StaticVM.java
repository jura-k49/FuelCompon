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
        String[] d = dd.split("-");
        c = new GregorianCalendar(Integer.valueOf(d[0]),
                Integer.valueOf(d[1]) - 1,
                Integer.valueOf(d[2]));
        return c;
    }

    public static String TextForNumbet(int num, String t1, String t2_4, String t5_9) {
        int n1 = num % 100;
        if (n1 < 21 && n1 > 4) {
            return t5_9;
        }
        n1 = num % 10;
        if (n1 == 1) {
            return t1;
        }
        if (n1 > 1 && n1 < 5) {
            return t2_4;
        }
        return t5_9;
    }
}
