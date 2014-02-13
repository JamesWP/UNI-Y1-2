package robot;

public class ProbActionToggler
{
    private Boolean probActions;
    
    public ProbActionToggler() {
        super();
        this.probActions = false;
    }
    
    public Boolean probActions() {
        return this.probActions;
    }
    
    public void toggle() {
        this.probActions = !this.probActions;
    }
}
