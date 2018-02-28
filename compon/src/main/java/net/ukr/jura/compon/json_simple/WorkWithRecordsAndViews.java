package net.ukr.jura.compon.json_simple;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.custom_components.SimpleImageView;
import net.ukr.jura.compon.custom_components.SimpleTextView;
import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.interfaces_classes.Visibility;
//import net.ukr.jura.compon.json_simple.Field;
//import net.ukr.jura.compon.json_simple.ListRecords;
//import net.ukr.jura.compon.json_simple.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class WorkWithRecordsAndViews {
    protected Record model;
    protected View view;
    protected Navigator navigator;
    protected View.OnClickListener clickView;
    protected Context context;
    protected String[] param;
    protected Record recordResult;
    private boolean setParam;
    private Visibility[] visibilityManager;
//    private String quote = "\"";
//    private String quoteColon = "\":";
//    private StringBuffer sb;

    public void RecordToView(Record model, View view) {
        RecordToView(model, view, null, null, null);
    }

    public void RecordToView(Record model, View view, Navigator navigator,
                             View.OnClickListener clickView, Visibility[] visibilityManager) {
        this.model = model;
        this.view = view;
        this.navigator = navigator;
        this.clickView = clickView;
        context = view.getContext();
        setParam = false;
        this.visibilityManager = visibilityManager;
        enumViewChild(view);
    }

    public Record ViewToRecord(View view, String par) {
        recordResult = new Record();
        param = par.split(",");
        for (String st : param) {
            recordResult.add(new Field(st, Field.TYPE_STRING, null));
        }
        setParam = true;
        enumViewChild(view);
        return recordResult;
    }

    private void enumViewChild(View v) {
        ViewGroup vg;
        int id;
        if (v instanceof ViewGroup) {
            vg = (ViewGroup) v;
            int countChild = vg.getChildCount();
            id = v.getId();
            if (id > -1) {
                setValue(v);
            }
            for (int i = 0; i < countChild; i++) {
                enumViewChild(vg.getChildAt(i));
            }
        } else {
            id = v.getId();
            if (id != -1) {
                setValue(v);
            }
        }
    }

    private void setRecordField(View v, String name) {
        for (Field f : recordResult) {
            if (f.name.equals(name)) {
                if (v instanceof IComponent) {
                    f.value = ((IComponent) v).getString();
                    break;
                }
                if (v instanceof TextView) {
                    f.value = ((TextView) v).getText().toString();
                    break;
                }
            }
        }
    }

    private void setValue(View v) {
        int id = v.getId();
        String st;
        String name = v.getContext().getResources().getResourceEntryName(id);
        if (v instanceof IComponent) {
            st = ((IComponent) v).getAlias();
            if (st != null && st.length() > 0) {
                name = st;
            }
        }
        if (setParam) {
            setRecordField(v, name);
        }
        if (navigator != null) {
            for (ViewHandler vh : navigator.viewHandlers) {
                if (id == vh.viewId) {
                    v.setOnClickListener(clickView);
                    break;
                }
            }
        }
        if (visibilityManager != null && visibilityManager.length > 0) {
            for (Visibility vis : visibilityManager) {
                if (vis.viewId == id) {
                    if (model.getBooleanVisibility(vis.nameField)) {
                        v.setVisibility(View.VISIBLE);
                    } else {
                        v.setVisibility(View.GONE);
                    }
                    break;
                }
            }
        }
        if (model == null) {
            return;
        }
        Field field = model.getField(name);
        if (field != null) {
            if (v instanceof IComponent) {
                ((IComponent) v).setData(field.value);
                return;
            }

            if (v instanceof TextView) {
                if (field.value instanceof String) {
                    ((TextView) v).setText((String )field.value);
                    return;
                }
                if (field.value instanceof Number) {
                    if (v instanceof SimpleTextView) {
                        st = ((SimpleTextView) v).getNumberFormat();
                        if (st != null) {
                            ((SimpleTextView) v).setText(new Formatter().format(st, field.value).toString());
                        } else {
                            ((SimpleTextView) v).setText(field.value.toString());
                        }
                    } else {
                        ((TextView) v).setText(field.value.toString());
                    }
                    return;
                }
                if(field.value instanceof Date) {
                    SimpleDateFormat format;
                    if (v instanceof SimpleTextView) {
                        st = ((SimpleTextView) v).getDateFormat();
                        if (st != null) {
                            ((SimpleTextView) v).setText(new Formatter().format(st, field.value).toString());
                        } else {
                            format = new SimpleDateFormat();
                            ((TextView) v).setText(format.format((Date) field.value));
                        }
                    } else {
                        format = new SimpleDateFormat();
                        ((TextView) v).setText(format.format((Date) field.value));
                    }
                    return;
                }
                return;
            }
        }

        if (v instanceof ImageView) {
            if (field != null) {
                st = (String) field.value;
                if (st == null) {
                    st = "";
                }
            } else {
                st = "";
            }
            if (st.length() == 0) return;
            if (st.contains("/")) {
                if (!st.contains("http")) {
                    st = ComponGlob.getInstance().networkParams.baseUrl + st;
                }
                Glide.with(view.getContext())
                        .load(st)
                        .into((ImageView) v);
            } else {
                if (v instanceof SimpleImageView) {
                    ((ImageView) v).setImageDrawable(view.getContext()
                            .getResources().getDrawable(((SimpleImageView) v).getPlaceholder()));
                } else {
                    ((ImageView) v).setBackgroundResource(view.getContext().getResources()
                            .getIdentifier(st, "drawable", view.getContext().getPackageName()));
                }
            }
        }
    }

//    public String modelToJson(Field field) {
//        sb = new StringBuffer( 1000);
//        if (field.type == Field.TYPE_RECORD) {
//            recordToJson((Record) field.value);
//        } else {
//            listToJson((ListRecords) field.value);
//        }
//        return sb.toString();
//    }
//
//    public void recordToJson(Record rec) {
//        sb.append("{");
//        String separator = "";
//        for (Field f : rec) {
//            sb.append(separator);
//            separator = ",";
//            switch (f.type) {
//                case Field.TYPE_STRING :
//                    sb.append(quote + f.name + quoteColon + quote + (String) f.value + quote);
//                    break;
//                case Field.TYPE_INTEGER :
//                    sb.append(quote + f.name + quoteColon + (Integer) f.value);
//                    break;
//                case Field.TYPE_LONG :
//                    sb.append(quote + f.name + quoteColon + (Long) f.value);
//                    break;
//                case Field.TYPE_DOUBLE :
//                    sb.append(quote + f.name + quoteColon + (Double) f.value);
//                    break;
//                case Field.TYPE_LIST:
//                    sb.append(quote + f.name + quoteColon);
//                    listToJson((ListRecords) f.value);
//                    break;
//            }
//        }
//        sb.append("}");
//    }
//
//    private void listToJson(ListRecords listRecords) {
//        sb.append("[");
//        String separator = "";
//        for (Record r : listRecords) {
//            sb.append(separator);
//            separator = ",";
//            recordToJson(r);
//        }
//        sb.append("]");
//    }
}
