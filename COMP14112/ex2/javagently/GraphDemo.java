package javagently;

public class GraphDemo {

	
	public static void main(String[] args)
	{
		Graph mygraph = new Graph("Graphing Demo", "Value", "Distance");

		int limit=20;
		mygraph.setLimits(0,limit,0,1);
		mygraph.setColor(2);
		for (double i=0; i<limit; i+=.1)
		{
			mygraph.add(i,Math.sin(i));
			
	        try {Thread.sleep(50);}
	        catch (InterruptedException e) {}
	        
			mygraph.showGraph();
		}
		
		mygraph.nextGraph();
		
		mygraph.setColor(1);
		for (double i=0; i<limit; i+=.1)
		{
			mygraph.add(i,Math.cos(i));
			
	        try {Thread.sleep(20);}
	        catch (InterruptedException e) {}
	        
			mygraph.showGraph();
		}
		
		
	}

}
