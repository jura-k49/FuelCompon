package net.ukr.jura.compon.base;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import net.ukr.jura.compon.R;
import net.ukr.jura.compon.interfaces_classes.ParentModel;
import net.ukr.jura.compon.json_simple.Field;
import net.ukr.jura.compon.json_simple.ListRecords;
import net.ukr.jura.compon.json_simple.Record;

import static android.view.View.inflate;

public abstract class ActivityDrawer extends BaseActivity{

    protected abstract void initMenu();
    protected abstract String getDrawerFragment();
    protected ListRecords menuList = new ListRecords();
    public Field menu = new Field("menu", Field.TYPE_LIST, menuList);
    protected int menuStart;
    protected DrawerLayout drawer;

    @Override
    protected View getContentView(Bundle savedInstanceState) {
        View view = inflate(this, R.layout.activity_drawer, null);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer);
        containerFragmentId = R.id.content_frame;
        nameDrawer = getDrawerFragment();
        startDrawerFragment(nameDrawer, R.id.left_drawer);
        return view;
    }

    @Override
    public void initView() {
        menuStart = -1;
        initMenu();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public void addMenuItem(String icon, String iconSelect, String title, String nameFragment) {
        addMenuItem(icon, iconSelect, title, nameFragment, false);
    }

    public void addDivider(){
        Record item = new Record();
        item.add(new Field("select", Field.TYPE_INTEGER, 0));
        menuList.add(item);
    }

    public void addMenuItem(String icon, String iconSelect, String title, String nameFragment, boolean start) {
        Record item = new Record();
        item.add(new Field("icon", Field.TYPE_STRING, icon));
        item.add(new Field("iconSelect", Field.TYPE_STRING, iconSelect));
        item.add(new Field("name", Field.TYPE_STRING, title));
        item.add(new Field("nameFunc", Field.TYPE_STRING, nameFragment));
        if (start && menuStart < 0) {
            item.add(new Field("select", Field.TYPE_INTEGER, 2));
            menuStart = menuList.size();
        } else {
            item.add(new Field("select", Field.TYPE_INTEGER, 1));
        }
        menuList.add(item);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public ParentModel getParentModel(String name) {
        return null;
    }
}
