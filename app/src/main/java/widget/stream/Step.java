package widget.stream;

import java.util.ArrayList;

/**
 * Created by dell on 2016/1/7.
 */
public class Step  {
    public String stepName;
    public ArrayList<String> stepActions;
    public String stepPerson;

    public Step preStep;
    public Step nextStep;

    public Circle drawable;

    public Step(String stepName, String stepPerson, ArrayList<String> stepActions) {
        this.stepName = stepName;
        this.stepPerson = stepPerson;
        this.stepActions = stepActions;
    }



}
