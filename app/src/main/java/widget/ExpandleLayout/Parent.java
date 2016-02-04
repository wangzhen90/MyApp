package widget.ExpandleLayout;

import java.util.ArrayList;

/**
 * Created by dell on 2016/1/11.
 */
public class Parent {

    public ArrayList<String> children = new ArrayList<>();
    public String name;
    public boolean expandable;
    public boolean isOpen = false;

    public Parent(String name) {
        this.name = name;
    }
}
