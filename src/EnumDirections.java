public class EnumDirections
{
	public enum Direction
	{
		UP, DOWN, RIGHT, LEFT
	}
	
	Direction d;
	
		public EnumDirections(Direction directionsInput)
		{
			d = directionsInput;
		}
		
		public Direction getEnumDirection()
		{
			return d;
		}
		
		public void setEnumDirection(Direction directionsInput)
		{
			d = directionsInput;
		}
	
}
