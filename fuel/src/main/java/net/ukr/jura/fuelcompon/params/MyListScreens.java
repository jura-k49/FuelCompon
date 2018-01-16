package net.ukr.jura.fuelcompon.params;

import android.content.Context;

import net.ukr.jura.compon.base.ListScreens;
import net.ukr.jura.compon.components.ParamComponent;
import net.ukr.jura.compon.components.ParamMap;
import net.ukr.jura.compon.components.ParamModel;
import net.ukr.jura.compon.components.ParamView;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.tools.Constants;
import net.ukr.jura.fuelcompon.R;
import net.ukr.jura.fuelcompon.fragments.MapFragment;
import net.ukr.jura.fuelcompon.network.Api;
import net.ukr.jura.fuelcompon.network.TestInternetProvider;

public class MyListScreens extends ListScreens {

    public MyListScreens(Context context) {
        super(context);
    }

    @Override
    public void initScreen() {
        addActivity(context.getString(R.string.main), R.layout.activity_fuel)
                .addFragmentsContainer(R.id.content_frame, context.getString(R.string.tickets))
                .addNavigator(new Navigator().add(R.id.radio1, context.getString(R.string.tickets))
                        .add(R.id.radio2, context.getString(R.string.map))
                        .add(R.id.radio3, context.getString(R.string.group))
                        .add(R.id.radio4, context.getString(R.string.mapF)));

        addFragment(context.getString(R.string.tickets), R.layout.fragment_tickets)
                .addNavigator(new Navigator().add(R.id.question, context.getString(R.string.help)))
                .addComponent(ParamComponent.TC.PAGER_F, new ParamView(R.id.pager,
                        new String[] {context.getString(R.string.active_tickets), context.getString(R.string.archive_tickets)})
                        .setTab(R.id.tabs, R.array.tab_tickets));

        addFragment(context.getString(R.string.active_tickets), R.layout.fragment_recycler_splash)
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE)
                                .internetProvider(TestInternetProvider.class)
                                .addToBeginning("type",1),
                        new ParamView(R.id.recycler, "type",
                                new int[] {R.layout.item_active_tickets, R.layout.item_active_tickets_begining})
                                .setSplashScreen(R.id.splash));

        addFragment(context.getString(R.string.mapF), MapFragment.class);


        addFragment(context.getString(R.string.map), R.layout.fragment_map)
                .addComponentMap(R.id.map, new ParamModel(Api.MARKER_MAP, "lat,lon").typeParam(ParamModel.TypeParam.NAME)
                        .internetProvider(TestInternetProvider.class), new ParamMap(true)
                        .coordinateValue(50.0276271, 36.2237879)
                        .markerImg(R.drawable.loc, Constants.MARKER_NAME_NUMBER, R.drawable.azs, R.drawable.azs2)
                        .markerClick(R.id.infoWindow));


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
