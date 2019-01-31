package application.bo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import application.Panneau;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class myRectangle extends Forme {
    protected double lar;
	protected double lng;
	protected Rectangle r=null;

    public myRectangle(double x, double y, double lar, double lng, Color color, double width) {
        super(x, y, color, width);
        this.lar = lar;
        this.lng = lng;
    }
    public myRectangle(int x, int y, int lar, int lng) {
        super(x, y);
        this.lar = lar;
        this.lng = lng;
    }
    public myRectangle(myRectangle forme, double width) {
    	super(forme.getX(), forme.getY());
        this.lar = forme.getLar();
        this.lng = forme.getLng();
        this.myWidth=width;
	}
	@Override
    public void affiche() {
          r= new Rectangle(x-lar/2, y-lng/2, lar, lng);
          r.setStrokeWidth(myWidth);
          r.setStroke(myColor);
          r.setFill(null);
          r.setOnMouseClicked(
        			evtClick -> {
    					System.out.println("Clicked!");
  					System.out.println("this is a Rectangle [("+(x-lar/2)+", "+(y-lng/2)+"), "+lar+", "+lng+"]");
  					Panneau.goma(calque,r);
    				
        		});
          calque.getChildren().add(r);
          Panneau.shapes.add(r);
          Panneau.formes.add(this);
    }

	@Override
	public Shape myShape() {
		return r;
	}

	public double getLar() {
		return lar;
	}

	public void setLar(double lar) {
		this.lar = lar;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r = r;
	}

}
