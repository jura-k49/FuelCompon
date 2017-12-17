package net.ukr.jura.compon.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ukr.jura.compon.interfaces_classes.MoreWork;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.Record;
import net.ukr.jura.compon.models.ParamView;
import net.ukr.jura.compon.models.WorkWithRecordsAndViews;

public class BaseProviderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int[] layoutItemId;
    public String fieldType;
    private BaseProvider provider;
    private Context context;
    private WorkWithRecordsAndViews modelToView;
    private String layout;
    private Navigator navigator;
//    private boolean startFlag;
    private MoreWork moreWork;
    private BaseComponent baseComponent;
    private boolean isClickItem;

//    public void setStartFlag(boolean b) {
//        startFlag = b;
//    }

    public BaseProviderAdapter(BaseComponent baseComponent) {
        context = baseComponent.activity;
        this.baseComponent = baseComponent;
        this.provider = baseComponent.provider;
        navigator = baseComponent.navigator;
        isClickItem = false;
        if (navigator != null) {
            for (ViewHandler vh : navigator.viewHandlers) {
                if (vh.viewId == 0) {
                    isClickItem = true;
                    break;
                }
            }
        }
        ParamView paramView = baseComponent.paramMV.paramView;
        if (paramView != null) {
            layoutItemId = paramView.layoutTypeId;
            fieldType = paramView.fieldType;
        } else {
            layoutItemId = null;
            fieldType = "";
        }
        modelToView = new WorkWithRecordsAndViews();
        layout = "";
//        startFlag = false;
        moreWork = null;
        layout = "item_recycler_" + baseComponent.paramMV.nameParentComponent;
        moreWork = baseComponent.paramMV.moreWork;
        if (baseComponent.paramMV.additionalWork != null) {
            try {
                moreWork = (MoreWork) baseComponent.paramMV.additionalWork.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (fieldType.length() == 0) {
            return 0;
        } else {
            Field f = provider.get(position).getField(fieldType);
            if (f.type == Field.TYPE_STRING) {
                return Integer.valueOf((String) f.value);
            } else {
                if (f.type == Field.TYPE_INTEGER) {
                    return (int) provider.get(position).getValue(fieldType);
                } else {
                    long ll = (Long) f.value;
                    return (int) ll;
                }
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (layoutItemId == null) {
            view = LayoutInflater.from(context).inflate(context.getResources().
                    getIdentifier(layout, "layout", context.getPackageName()), parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(layoutItemId[viewType], parent, false);
        }
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Record record = (Record) provider.get(position);
        modelToView.RecordToView(provider.get(position),
                holder.itemView, navigator, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseComponent.clickItem.onClick(holder, view, holder.getAdapterPosition());
            }
        });
        if (isClickItem) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseComponent.clickItem.onClick(holder, null, holder.getAdapterPosition());
                }
            });
        }
        if (moreWork != null) {
            moreWork.afterBindViewHolder(record, holder);
        }
    }

    @Override
    public int getItemCount() {
        return provider.getCount();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
