package application.bo;

import application.Panneau;
import javafx.scene.paint.Color;


public abstract class Forme implements Dessin {
    protected double x, y;
    protected static Panneau calque;
	protected Color myColor=Color.STEELBLUE;
	protected Double myWidth=Double.valueOf(2);

    public Forme(double x, double y, Color color, double width) {
        this.x = x;
        this.y = y;
        myColor=color;
        myWidth=width;
    }
    
    public Forme(double x2, double y2) {
    	this.x = x2;
        this.y = y2;
	}

	public static void setCalque(Panneau panneau) {
        calque = panneau;
    }

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Color getMyColor() {
		return myColor;
	}

	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}

	public Double getMyWidth() {
		return myWidth;
	}

	public void setMyWidth(Double myWidth) {
		this.myWidth = myWidth;
	}
    
}