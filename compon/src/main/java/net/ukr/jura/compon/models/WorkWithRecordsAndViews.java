package net.ukr.jura.compon.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.components.SimpleImageView;
import net.ukr.jura.compon.components.SimpleTextView;
import net.ukr.jura.compon.interfaces_classes.IComponent;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;

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

    public void RecordToView(Record model, View view) {
        RecordToView(model, view, null, null);
    }

    public void RecordToView(Record model, View view, Navigator navigator, View.OnClickListener clickView) {
        this.model = model;
        this.view = view;
        this.navigator = navigator;
        this.clickView = clickView;
        context = view.getContext();
        setParam = false;
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
            if (f.name.equals(name)&& v instanceof TextView) {
                f.value = ((TextView) v).getText().toString();
                break;
            }
        }
    }

    private void setValue(View v) {
        int id = v.getId();
        String st;
        String name = v.getContext().getResources().getResourceEntryName(id);
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
}
