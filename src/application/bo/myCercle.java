package application.bo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import application.Panneau;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public class myCercle extends Forme {
    private double rayon;
    private Circle c=null;

    public myCercle(double x, double y, double rayon, Color color, double width) {
        super(x, y, color, width);
        this.rayon = rayon;
    }

    public myCercle(myCercle forme, double width) {
    	super(forme.getX(), forme.getY());
        this.rayon = forme.getRayon();
        this.myWidth=width;
	}

	public myCercle(int coordX, int coordY, int rayon) {
		 super(coordX, coordY);
	      this.rayon = rayon;
	}

	@Override
    public void affiche() {
          c= new Circle(x, y, rayon);
          c.setStrokeWidth(myWidth);
          c.setStroke(myColor);
          c.setFill(null);
          c.setOnMouseClicked(
      			evtClick -> {
  					System.out.println("Clicked!");
					System.out.println("this is a Circle ["+(x-rayon)+", "+(y-rayon)+", "+2*rayon+"]");
					Panneau.goma(calque, c);
      		});
          calque.getChildren().add(c);
          Panneau.shapes.add(c);
          Panneau.formes.add(this);
    }

	@Override
	public Shape myShape() {
		return c;
	}

	public double getRayon() {
		return rayon;
	}

	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

	public Circle getC() {
		return c;
	}

	public void setC(Circle c) {
		this.c = c;
	}

}

