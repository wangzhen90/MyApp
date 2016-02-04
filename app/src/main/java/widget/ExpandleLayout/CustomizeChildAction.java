package widget.ExpandleLayout;

/**
 * Created by dell on 2016/1/12.
 */
public abstract class CustomizeChildAction {
    int iconId;
    String actionName;

    public CustomizeChildAction(String actionName) {
        this.actionName = actionName;
    }

    public abstract void clickEvent();
}
