package server;

public class Monster {
	int Type,MaxHP,X,Y,CurrentHP,Hunger;
	
	public String toString()
	{
		return Integer.toString(Type)+ " " + Integer.toString(MaxHP)+ " " + Integer.toString(X)+ " " + Integer.toString(Y)+ " " + Integer.toString(CurrentHP)+ " " + Integer.toString(Hunger);
	}
	public Monster(int Type,int MaxHP,int X,int Y,int CurrentHP,int Hunger)
	{
		this.Type = Type;
		this.MaxHP = MaxHP;
		this.X = X;
		this.Y = Y;
		this.CurrentHP = CurrentHP;
		this.Hunger = Hunger;
	}
	
	public int getType()
	{
		return this.Type;
	}
	
	public int getMaxHP()
	{
		return this.MaxHP;
	}
	
	public int getX()
	{
		return this.X;
	}
	
	public int getY()
	{
		return this.Y;
	}
	
	public int getCurrentHP()
	{
		return this.CurrentHP;
	}
	
	public int getHunger()
	{
		return this.Hunger;
	}
	
	public void setType(int Type)
	{
		this.Type = Type;
	}
	
	public void setMaxHP(int MaxHP)
	{
		this.MaxHP = MaxHP;
	}
	
	public void setX(int X)
	{
		this.X = X;
	}
	
	public void setY(int Y)
	{
		this.Y = Y;
	}
	
	public void setCurrentHP(int CurrentHP)
	{
		this.CurrentHP = CurrentHP;
	}
	
	public void setHunger(int Hunger)
	{
		this.Hunger = Hunger;
	}
}
