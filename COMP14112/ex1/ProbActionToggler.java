package robot;

public class ProbActionToggler{

    private Boolean probActions= false;

    public Boolean probActions(){
	return probActions;
    }

    public void toggle(){
	probActions= !probActions;
    }


}