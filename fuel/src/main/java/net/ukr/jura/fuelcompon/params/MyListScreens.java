package net.ukr.jura.fuelcompon.params;

import android.content.Context;

import net.ukr.jura.compon.base.ListScreens;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
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
                .addFragmentsContainer(R.id.content_frame, context.getString(R.string.tickets))
                .addNavigator(new Navigator().add(R.id.radio1, context.getString(R.string.tickets))
                        .add(R.id.radio2, context.getString(R.string.group)));

        addFragment(context.getString(R.string.tickets), R.layout.fragment_tickets)
                .addNavigator(new Navigator().add(R.id.question, context.getString(R.string.help)))
                .addComponent(ParamComponent.TC.PAGER_F, new ParamView(R.id.pager,
                        new String[] {context.getString(R.string.active_tickets), context.getString(R.string.archive_tickets)})
                        .setTab(R.id.tabs, R.array.tab_tickets));

        addFragment(context.getString(R.string.active_tickets), R.layout.fragment_recycler_splash)
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.MY_GOALS),
                        new ParamView(R.id.recycler).setSplashScreen(R.id.splash));
        addFragment(context.getString(R.string.archive_tickets), R.layout.fragment_recycler)
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.GROUP_SCHEDULE),
                        new ParamView(R.id.recycler, "isSelected", new int[]{R.layout.item_group_lessons,
                                R.layout.item_group_lessons_sel, R.layout.item_group_lessons_sel}));

        addActivity(context.getString(R.string.help), R.layout.activity_help)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
                                            .add(R.id.call_operator, getString(R.string.choice_fuel)));

        addActivity(getString(R.string.choice_fuel), R.layout.activity_choice_fuel);

        addFragment(context.getString(R.string.goals), R.layout.base_recycler, "Мои цели")
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.MY_GOALS));

        addFragment(context.getString(R.string.group), R.layout.base_recycler, "Групповые занятия")
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.GROUP_SCHEDULE),
                        new ParamView(R.id.recycler, "isSelected", new int[]{R.layout.item_group_lessons,
                                R.layout.item_group_lessons_sel, R.layout.item_group_lessons_sel}));
        super.initScreen();
    }
}
