package robot;

public class Action
{
    public ActionType type;
    public int parameter;
    
    public Action() {
        super();
    }
    
    public Action(final ActionType type, final int parameter) {
        super();
        this.type = type;
        this.parameter = parameter;
    }
}
