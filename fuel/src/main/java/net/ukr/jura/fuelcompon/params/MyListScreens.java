package net.ukr.jura.fuelcompon.params;

import android.content.Context;

import net.ukr.jura.compon.base.ListScreens;
import net.ukr.jura.compon.param.ParamComponent;
import net.ukr.jura.compon.param.ParamMap;
import net.ukr.jura.compon.param.ParamModel;
import net.ukr.jura.compon.param.ParamView;
import net.ukr.jura.compon.interfaces_classes.FilterParam;
import net.ukr.jura.compon.interfaces_classes.Filters;
import net.ukr.jura.compon.interfaces_classes.Navigator;
import net.ukr.jura.compon.interfaces_classes.ViewHandler;
import net.ukr.jura.compon.tools.Constants;
import net.ukr.jura.fuelcompon.R;
import net.ukr.jura.fuelcompon.flawsBackEnd.FuelMoreWork;
import net.ukr.jura.fuelcompon.fragments.MapFragment;
import net.ukr.jura.fuelcompon.network.Api;
import net.ukr.jura.fuelcompon.network.TestInternetProvider;

import static net.ukr.jura.compon.param.ParamView.visibility;
import static net.ukr.jura.compon.interfaces_classes.FilterParam.Operation.equally;

public class MyListScreens extends ListScreens {

    public MyListScreens(Context context) {
        super(context);
    }

    @Override
    public void initScreen() {
        addActivity(context.getString(R.string.splash), R.layout.activity_splash)
                .addComponentSplash(context.getString(R.string.tutorial),
                        context.getString(R.string.auth), context.getString(R.string.main));

        addActivity(getString(R.string.tutorial), R.layout.activity_tutorial)
                .addComponent(ParamComponent.TC.PAGER_V, new ParamModel(Api.TUTORIAL)
                                .internetProvider(TestInternetProvider.class),
                        new ParamView(R.id.pager, R.layout.item_tutorial)
                                .visibilityManager(visibility(R.id.contin, "contin"),
                                        visibility(R.id.proceed, "proceed"))
                                .setIndicator(R.id.indicator).setFurtherView(R.id.further),
                        new Navigator().add(R.id.skip, context.getString(R.string.tutorial), true)
                                .add(R.id.skip, context.getString(R.string.auth))
                                .add(R.id.skip, ViewHandler.TYPE.BACK)
                                .add(R.id.proceed, context.getString(R.string.tutorial), true)
                                .add(R.id.proceed, context.getString(R.string.auth))
                                .add(R.id.proceed, ViewHandler.TYPE.BACK)
                                .add(R.id.contin, ViewHandler.TYPE.PAGER_PLUS));

        addActivity(context.getString(R.string.auth), R.layout.activity_auth)
                .addFragmentsContainer(R.id.content_frame, context.getString(R.string.auth_phone));

        addFragment(context.getString(R.string.auth_phone), R.layout.fragment_auth_phone)
                .addComponent(ParamComponent.TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator().add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
                                new ParamModel(ParamModel.POST, Api.LOGIN_PHONE, "phone"),
                                actionsAfterResponse()
                                        .startScreen(context.getString(R.string.auth_code)),
                                true, R.id.phone));

        addFragment(context.getString(R.string.auth_code), R.layout.fragment_auth_code)
                .addComponent(ParamComponent.TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator().add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
                                new ParamModel(ParamModel.POST, Api.LOGIN_CODE, "phone,code"),
                                actionsAfterResponse()
                                        .preferenceSetToken("token")
                                        .startScreen(context.getString(R.string.main))
                                        .back(),
                                true, R.id.code));

        addActivity(context.getString(R.string.main), R.layout.activity_fuel)
                .addFragmentsContainer(R.id.content_frame, context.getString(R.string.tickets))
                .addNavigator(new Navigator().add(R.id.radio1, context.getString(R.string.tickets))
                        .add(R.id.radio2, context.getString(R.string.map))
                        .add(R.id.radio5, context.getString(R.string.mapF)));

        addFragment(context.getString(R.string.tickets), R.layout.fragment_tickets, context.getString(R.string.my_tickets))
                .addNavigator(new Navigator().add(R.id.question, context.getString(R.string.help)))
                .addComponent(ParamComponent.TC.PAGER_F, new ParamView(R.id.pager,
                        new String[] {context.getString(R.string.active_tickets), context.getString(R.string.archive_tickets)})
                        .setTab(R.id.tabs, R.array.tab_tickets));

        addFragment(context.getString(R.string.active_tickets), R.layout.fragment_active)
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE),
                        new ParamView(R.id.recycler, "type",
                                new int[] {R.layout.item_active_tickets, R.layout.item_active_tickets_begining,
                                        R.layout.item_active_tickets_splash})
                                .visibilityManager(visibility(R.id.expect_receive, "pending"),
                                        visibility(R.id.confirm_payment, "awaits_payment"))
                                .setSplashScreen(R.id.splash),
                        new Navigator().add(R.id.confirm_payment, context.getString(R.string.awaits_payment))
                                .add(R.id.expect_receive, context.getString(R.string.new_wait))
                                .add(R.id.pay_tickets, context.getString(R.string.choice_fuel))
                                .add(0, context.getString(R.string.infoTicket), ViewHandler.TYPE_PARAM_FOR_SCREEN.RECORD),
                        0, FuelMoreWork.class);

        addFragment(context.getString(R.string.archive_tickets), R.layout.fragment_archive)
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ARCHIVE),
                        new ParamView(R.id.recycler, "type",
                                new int[] {R.layout.item_active_tickets, R.layout.item_active_tickets_begining})
                                .setSplashScreen(R.id.splash));

        addFragment(context.getString(R.string.mapF), MapFragment.class);

        addFragment(context.getString(R.string.map), R.layout.fragment_map)
                .addComponentMap(R.id.map, new ParamModel(Api.MARKER_MAP, "lat,lon").typeParam(ParamModel.TypeParam.NAME)
                        .internetProvider(TestInternetProvider.class), new ParamMap(true)
                        .coordinateValue(50.0276271, 36.2237879)
                        .markerImg(R.drawable.tab_map_green, Constants.MARKER_NAME_NUMBER, R.drawable.marker_map, R.drawable.marker_map)
                        .markerClick(R.id.infoWindow));

        addActivity(context.getString(R.string.help), R.layout.activity_help)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
                                            .add(R.id.call_operator, getString(R.string.choice_fuel)));

        addActivity(context.getString(R.string.infoTicket), R.layout.activity_info_ticket)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
                .addComponent(ParamComponent.TC.PANEL, new ParamModel(ParamModel.ARGUMENTS),
                        new ParamView(R.id.panel));

        addActivity(context.getString(R.string.new_wait), R.layout.activity_new_wait)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
                        .add(R.id.question, context.getString(R.string.help)))
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE)
                                .filter(new Filters(new FilterParam("status", equally, "pending"))),
                        new ParamView(R.id.recycler, R.layout.item_active_tickets));

        addActivity(context.getString(R.string.awaits_payment), R.layout.activity_awaits_payment)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
                        .add(R.id.question, context.getString(R.string.help)))
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE),
                        new ParamView(R.id.recycler, R.layout.item_awaits_payment),
                        null, 0, FuelMoreWork.class);

        addActivity(getString(R.string.choice_fuel), R.layout.activity_choice_fuel)
                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.NETWORKS),
                        new ParamView(R.id.recycler, "type",
                                new int[] {R.layout.item_choice_fuel, R.layout.item_choice_fuel_net}),
                        null, 0, FuelMoreWork.class);

        super.initScreen();
    }
}
