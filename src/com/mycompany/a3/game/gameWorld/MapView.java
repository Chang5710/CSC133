package com.mycompany.a3.game.gameWorld;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.TextArea;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.EnergyStation;

public class MapView extends Container implements Observer{

	static private int mapViewHeight;
	static private int mapViewWidth;
	private GameWorld gw = null;
	static private Point mapViewOrigin;
	private TextArea ta;
	
	/** 
	 * Constructor 
	 */
	public MapView(GameWorld gw) {
		
		this.gw = gw;
		
		MapView.mapViewHeight = this.getHeight();
		MapView.mapViewWidth = this.getWidth();
		
		//static private Point2D mapViewOrigin;
		this.setLayout(new BorderLayout());
		this.getAllStyles().setFgColor(ColorUtil.BLUE);
		this.getAllStyles().setBorder(Border.createLineBorder(2));
		
		

		MapView.mapViewHeight = this.getHeight();
		MapView.mapViewWidth = this.getWidth();
		setWidth(1000);
		setHeight(1000);

	}
	
	public static int getMapViewHeight() { return mapViewHeight; }
	public static int getMapViewWidth() { return mapViewWidth; }
	public static void setMapViewHeight(int height) { mapViewHeight = height; }
	public static void setMapViewWidth(int width) { mapViewWidth = width; }
	
	public void setMapViewOrigin(Point p) { MapView.mapViewOrigin = p; }
	public static Point getMapViewOrigin() { return mapViewOrigin; }
	
	@Override
	public void update(Observable observable, Object data) {
		
//		GameWorld gw = (GameWorld)observable;
//		gw.map();
//		IIterator iter = gw.getGameObjects().getIterator();
//		String output = "";
//		while(iter.hasNext()) {
//			output = output + iter.getNext().toString()+"\n";
//		}
//		ta.setText(output);
//		this.repaint();
		
		this.gw = (GameWorld) data;
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		IIterator itr = gw.getGameObjects().getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if (tempObject instanceof IDrawable) {
				((IDrawable) tempObject).draw(g, pCmpRelPrnt);
			}
		}
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		
		boolean move = false; //sign flag if object is selected

		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x,y);
		Point pCmpRelPrnt = new Point(getX() , getY());
		x -= getX(); //X relative to mapview 
		y -= getY();
		System.out.println("Clicked " + "(" + x + "," + y + ")");
		IIterator iter2 = gw.getGameObjects().getIterator();
		while(iter2.hasNext()) {
			GameObject obj = iter2.getNext();
			if(obj instanceof ISelectable) {
				if(((ISelectable)obj).isSelected()) {
				//	System.out.println("Valid Move");
					if(!gw.getPositionToggle()) break;
					
					if(obj instanceof EnergyStation) {
						obj.setLocation(new Point2D(x-obj.getSize()/2,y-obj.getSize()/2));
					}else {
						//BASE 
						obj.setLocation(new Point2D(x,y));
					}

					move = true;
					repaint();
					((ISelectable)obj).setSelected(false);
					return;

				}else {
				//	System.out.println("Invalid Move");
					((ISelectable)obj).setSelected(false);
				}
			}
		}
		
		if(!move) {
			IIterator iter = gw.getGameObjects().getIterator();
			while(iter.hasNext()) {
				GameObject obj = iter.getNext();
				if(obj instanceof ISelectable) {
					if(((ISelectable)obj).contains(pPtrRelPrnt, pCmpRelPrnt)) {
						((ISelectable)obj).setSelected(true);
						System.out.println("Object Selected");
					}else {
						//Not selected
						((ISelectable)obj).setSelected(false);
					}
				}
				repaint();
			}
		}
	}


}
