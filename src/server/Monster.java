package server;

import java.util.Random;

import common.*;

public class Monster extends Thread implements MapObject{
	public final static int MaxHP = 5;
	public final static int wait = 5000;
	Coordinate c;
	int ID,Type,CurrentHP,Hunger;
	
	public Monster(int id,Coordinate Co)
	{
		c=Co;
		CurrentHP=MaxHP;
		Hunger = 1;
		Type=0;
		ID=id;
		start();
	}
	
	public String toString()
	{
		return Integer.toString(Type)+ " " + Integer.toString(MaxHP)+ " " + Integer.toString((int) c.X())+ " " + Integer.toString((int) c.Y())+ " " + Integer.toString(CurrentHP)+ " " + Integer.toString(Hunger);
	}
	public Monster(int ID, int Type,int MaxHP,int X,int Y,int CurrentHP,int Hunger)
	{
		this.ID = ID;
		this.Type = Type;
		this.c = new Coordinate(X,Y);
		this.CurrentHP = CurrentHP;
		this.Hunger = Hunger;
	}
	
	public int getType()
	{
		return this.Type;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	
	
	public int getMaxHP()
	{
		return this.MaxHP;
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
	
	public void setCoordinate(Coordinate co)
	{
		c = new Coordinate(co);
	}
	
	public void setCurrentHP(int CurrentHP)
	{
		this.CurrentHP = CurrentHP;
	}
	
	public void setHunger(int Hunger)
	{
		this.Hunger = Hunger;
	}
	@Override
	public Coordinate Coordinates() {
		return c;
	}
	@Override
	public common.Resource Resource() {
		Random rnd = new Random();
		int resource = rnd.nextInt(2);
		if(resource == 1) return Resource.LEATHER;
		return Resource.MEAT;
	}
	@Override
	public int Health() {
		return CurrentHP;
	}
	@Override
	public void Damage(int amount) {
		CurrentHP=CurrentHP-amount;
	}

	public void run()
	{
		while(CurrentHP>0)
		{
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				return;
			}
			WorldMap.getInstance().monsterattack(c, 1);
			//Movement?
		}
	}
}
