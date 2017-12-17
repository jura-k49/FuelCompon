package net.ukr.jura.compon.base;

import net.ukr.jura.compon.ComponGlob;
import net.ukr.jura.compon.network.NetworkParams;

public class SetSettings {
    public static void setNetworkParams(NetworkParams params) {
        ComponGlob.getInstance().networkParams = params;
    }

    public static void setListScreens(ListScreens listScreens) {
        ComponGlob.getInstance().setContext(listScreens.context);
        listScreens.setMapScreen(ComponGlob.getInstance().MapScreen);
        listScreens.initScreen();
    }
}
