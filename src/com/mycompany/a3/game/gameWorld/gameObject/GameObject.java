package com.mycompany.a3.game.gameWorld.gameObject;

import java.util.Random;
import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;

public abstract class GameObject implements ICollider {
	
	private int color;
	private int size;
	private Point2D location;
	final private int gameWorldWidth = 1000;
	final private int gameWorldHigh = 1000;
	protected Vector<GameObject> OverCollided;
	
	//update a object in random location 
	public GameObject(int Color) {
		Random rn = new Random();
		double x = rn.nextDouble() * gameWorldWidth;
		double y = rn.nextDouble() * gameWorldHigh;
		this.location = new Point2D(x,y);
		this.color = Color;
		OverCollided = new Vector<GameObject>();
		System.out.println("OVerCollided initialized");
		}
	public boolean isThere(GameObject target) {
		if(OverCollided.isEmpty()) return false;
		return OverCollided.contains(target);
	}
	
	public void addCollided(GameObject collided) {
		OverCollided.add(collided);
	}
	
	public void delNotCollided(GameObject target) {
		OverCollided.removeElement(target);
	}
	
	//update a object with set location
	public GameObject(int Color, double x, double y) {
		this.location = new Point2D(x,y);
		this.color = Color;
		OverCollided = new Vector<GameObject>();
	}
	
	//getter and setter
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setColor(int Color) {
		this.color= Color;
	}

	public int getColor() {
		return this.color;
	}
	
	public double getX() {
		return location.getX();
	}
	
	public double getY() {
		return location.getY();
	}
	
	public void setX(double x) {
		location.setX(x);
	}
	
	public void setY(double y) {
		location.setY(y);
	}
	
	public int getGameWorldWidth() {
		return gameWorldWidth;
	}
	
	public int getGameWorldHigh() {
		return gameWorldHigh;
	}
	
	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}
	
	//To string the color
	public String getColorToString() {
		
		String colors = "[" + ColorUtil.red(this.color) + ","
							+ ColorUtil.green(this.color) + ","
							+ ColorUtil.blue(this.color) + "]";
		return colors;
				
	}

}


