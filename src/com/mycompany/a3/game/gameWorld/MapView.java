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

public class MapView extends Container implements Observer{

	static private int mapViewHeight;
	static private int mapViewWidth;
	private GameWorld gw = null;
	
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
		
		
		ta = new TextArea();
		ta.setEditable(false);
		ta.getAllStyles().setBgTransparency(0);
		MapView.mapViewHeight = this.getHeight();
		MapView.mapViewWidth = this.getWidth();
		setWidth(1000);
		setHeight(1000);
		this.add(CENTER,ta);
	}
	
	public static int getMapViewHeight() { return mapViewHeight; }
	public static int getMapViewWidth() { return mapViewWidth; }
	public static void setMapViewHeight(int height) { mapViewHeight = height; }
	public static void setMapViewWidth(int width) { mapViewWidth = width; }
	
	
	
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


}
