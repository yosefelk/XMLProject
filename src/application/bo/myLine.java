package application.bo;

import application.Panneau;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;


public class myLine extends Forme {
    private double xEnd;
    private double yEnd;
    private Line l=null;

    public myLine(double x, double y, double xEnd, double yEnd, Color color, double width) {
        super(x, y, color, width);
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }
    
    public myLine(myLine forme, double width) {
    	super(forme.getX(), forme.getY());
        this.xEnd = forme.getxEnd();
        this.yEnd = forme.getyEnd();
        this.myWidth=width;
	}

	public myLine(int coordX, int coordY, int xend, int yend) {
		super(coordX, coordY);
        this.xEnd = xend;
        this.yEnd = yend;
	}

	public void affiche() {
        l=new Line(x, y, xEnd, yEnd);
        l.setStrokeWidth(myWidth);
        l.setStroke(myColor);
        l.setOnMouseClicked(
    			evtClick -> {
					System.out.println("Clicked!");
					System.out.println("this is a Line [("+x+", "+y+") ==> ("+xEnd+", "+ yEnd+")]");
					Panneau.goma(calque, l);
    		});
        calque.getChildren().add(l);
        Panneau.shapes.add(l);
        Panneau.formes.add(this);
    }

	public Shape myShape() {
		// TODO Auto-generated method stub
		return l;
	}

	public double getxEnd() {
		return xEnd;
	}

	public void setxEnd(double xEnd) {
		this.xEnd = xEnd;
	}

	public double getyEnd() {
		return yEnd;
	}

	public void setyEnd(double yEnd) {
		this.yEnd = yEnd;
	}

	public Line getL() {
		return l;
	}

	public void setL(Line l) {
		this.l = l;
	}

}