public class MyTimer 
{
	long start = 0;
	long delay;

	public MyTimer(int delay)
	{
		this.delay = delay;
	}

	public void start() 
	{
		this.start = System.currentTimeMillis();
	}

	public boolean isExpired() 
	{
		return ((System.currentTimeMillis() - this.start) > this.delay);
	}

}


