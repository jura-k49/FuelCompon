package net.ukr.jura.fuelcompon.params;

import android.content.Context;

import net.ukr.jura.compon.base.ListScreens;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.models.ParamComponent;
import net.ukr.jura.compon.models.ParamModel;
import net.ukr.jura.compon.models.ParamView;
import net.ukr.jura.fuelcompon.R;
import net.ukr.jura.fuelcompon.network.Api;

public class MyListScreens extends ListScreens {

    public MyListScreens(Context context) {
        super(context);
    }

    @Override
    public void initScreen() {
        addActivity(context.getString(R.string.main), R.layout.activity_fuel)
                .addFragmentsContainer(R.id.content_frame, context.getString(R.string.goals))
                .addNavigator(new Navigator().add(R.id.radio1, context.getString(R.string.goals))
                        .add(R.id.radio2, context.getString(R.string.group)));

        addFragment(context.getString(R.string.goals), R.layout.base_recycler, "Мои цели")
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.MY_GOALS));

        addFragment(context.getString(R.string.group), R.layout.base_recycler, "Групповые занятия")
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.GROUP_SCHEDULE),
                        new ParamView(R.id.recycler, "isSelected", new int[]{R.layout.group_lessons_item,
                                R.layout.group_lessons_sel_item, R.layout.group_lessons_sel_item}));
        super.initScreen();
    }
}
