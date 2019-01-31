package application.bo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import application.Panneau;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@XmlRootElement(name = "carre")
@XmlAccessorType(XmlAccessType.FIELD)
public class myCarre extends myRectangle {
    public myCarre(double x, double y, double lar, Color color, double width) {
        super(x, y, lar, lar, color, width);
    }

    public myCarre(myCarre forme, double width) {
        	super(forme, width);
    	}	

	public myCarre(int coordX, int coordY, int largeur) {
		super(coordX, coordY, largeur, largeur);
	}

	@Override
    public void affiche() {
    	r= new Rectangle(x-lar/2, y-lng/2, lar, lar);
        r.setStrokeWidth(myWidth);
        r.setStroke(myColor);
        r.setFill(null);
        r.setOnMouseClicked(
      			evtClick -> {
  					System.out.println("Clicked!");
					System.out.println("this is a Rectangle [("+(x-lar/2)+", "+(y-lng/2)+"), "+lar+"]");
					Panneau.goma(calque, r);
  				
      		});
        calque.getChildren().add(r);

        Panneau.shapes.add(r);
        Panneau.formes.add(this);
    }
}
