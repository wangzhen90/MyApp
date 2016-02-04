package widget.ExpandleLayout;

import java.util.ArrayList;

/**
 * Created by dell on 2016/1/12.
 */
public abstract class CustomizeAction {

    int iconId;
    String actionName;
    public ArrayList<CustomizeChildAction> childActions;
    public boolean expandable;//是否支持展开
    public boolean isOpen;//当前是否展开
    public CustomizeAction(int iconId, String actionName, ArrayList<CustomizeChildAction> childActions) {
        this.iconId = iconId;
        this.actionName = actionName;
        this.childActions = childActions;
    }

    public abstract void clickEvent();
}
