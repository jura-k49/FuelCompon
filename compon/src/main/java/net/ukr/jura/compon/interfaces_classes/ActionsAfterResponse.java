package net.ukr.jura.compon.interfaces_classes;

import java.util.ArrayList;
import java.util.List;

import static net.ukr.jura.compon.interfaces_classes.ViewHandler.TYPE;

public class ActionsAfterResponse {
    public List<ViewHandler> viewHandlers = new ArrayList<>();

    public ActionsAfterResponse startScreen(String nameScreen) {
        viewHandlers.add(new ViewHandler(0, nameScreen));
        return this;
    }

    public ActionsAfterResponse preferenceSetToken(String nameField) {
        viewHandlers.add(new ViewHandler(0, TYPE.PREFERENCE_SET_TOKEN, nameField));
        return this;
    }

    public ActionsAfterResponse back() {
        viewHandlers.add(new ViewHandler(0, TYPE.BACK));
        return this;
    }
}
