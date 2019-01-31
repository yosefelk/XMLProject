package application;

import java.util.*;

import application.bo.Forme;
import application.bo.myCarre;
import application.bo.myCercle;
import application.bo.myLine;
import application.bo.myRectangle;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;

 enum TypeForme {LINE, CERCLE, RECTANGLE, CARRE, CURSOR, GOMA};

public class Panneau extends Pane {
    private TypeForme type = TypeForme.LINE;
	private Color coolor=Color.RED;
	private int width=Integer.valueOf(2);
    public static List<Shape> shapes = new ArrayList<>();
    public static List<Forme> formes = new ArrayList<>();
	private double xEnd=0;
	private double yEnd=0;
	private double yStart=0;
	private double xStart=0;
	private boolean draggStarted=false;
	static boolean goma=false;

    public Panneau() {
        Forme.setCalque(this);
        /*
         // CETTES INSTRUCTIONS POUR DESSIGNE LE LES COORDENNEES
        final NumberAxis x = new NumberAxis();
        final NumberAxis y = new NumberAxis();
        x.setLowerBound(0);
        x.setUpperBound(300);
        y.setLowerBound(0);
        y.setUpperBound(300);
        x.setAutoRanging(false);
        y.setAutoRanging(false);
        x.setSide(Side.TOP);
        y.setSide(Side.BOTTOM);

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(x, y);
        lineChart.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getWidth());
        lineChart.setLayoutY(5);
        lineChart.setLegendVisible(true);
        lineChart.setCreateSymbols(true);
        lineChart.setAlternativeColumnFillVisible(true);
        lineChart.setAlternativeRowFillVisible(true);
        this.getChildren().add(lineChart);
        */
        setOnMousePressed(
        		evtClick -> {  
        			 xStart=evtClick.getX();
        			 yStart=evtClick.getY(); 
        		}
        );
        
        setOnMouseDragged(
				evtDragg -> {
					xEnd = evtDragg.getX();  
					yEnd = evtDragg.getY();
					if(shapes.size()>0 && this.getChildren().size()>1 && draggStarted){
						this.getChildren().remove(this.getChildren().size()-1);
						shapes.remove(shapes.size()-1);
						formes.remove(formes.size()-1);
					}

					if(type!=TypeForme.GOMA && type!=TypeForme.CURSOR) draggStarted=true;
	    			tracerForme((xStart+xEnd)/2, (yStart+yEnd)/2, Math.abs(xStart-xEnd), Math.abs(yStart-yEnd));
				}

		);
        
    	setOnMouseClicked(
				evtDragg -> {
					if(type!=TypeForme.GOMA && type!=TypeForme.CURSOR){
						draggStarted=false;
					}
				}

		);
        
    }
    
    public static void createAnoot(Shape s){
    	s.setStroke(Color.GREEN);
    	if(s instanceof Line){
			Line l=(Line) s;
			Main.controller.clearAnnotation();
			Main.controller.addAnnotation("You have selected a Line [("+l.getStartX()+", "+l.getStartY()+") ==> ("+l.getEndX()+", "+ l.getEndY()+")]");
    		double m=(double) ((int)-((l.getEndY()-l.getStartY())/(l.getEndX()-l.getStartX())*10))/10;
    		for (int i = 0; i < Panneau.shapes.size(); i++) {
    			if(Panneau.shapes.get(i) instanceof Line){
        			Line l2=(Line) Panneau.shapes.get(i);
            		double m2=(double) ((int)-((l2.getEndY()-l2.getStartY())/(l2.getEndX()-l2.getStartX())*10))/10;
            		System.out.println("m="+m+" ==> m2="+m2);
            		if(m==m2){
            			Panneau.shapes.get(i).setStroke(Color.GREEN);
            			Main.controller.addAnnotation("Your Line is // with Line [("+l2.getStartX()+", "+l2.getStartY()+") ==> ("+l2.getEndX()+", "+ l2.getEndY()+")]");
            		}
            		else if(m*m2==-1){
            			Panneau.shapes.get(i).setStroke(Color.RED);
            			Main.controller.addAnnotation("Your Line is T with Line [("+l2.getStartX()+", "+l2.getStartY()+") ==> ("+l2.getEndX()+", "+ l2.getEndY()+")]");
            		}
            		else{
            			Panneau.shapes.get(i).setStroke(Color.BLUE);
            			Main.controller.addAnnotation("Your Line is /\\ with Line [("+l2.getStartX()+", "+l2.getStartY()+") ==> ("+l2.getEndX()+", "+ l2.getEndY()+")]");
            		}
    			}else{
    				Panneau.shapes.get(i).setStroke(Color.DARKGRAY);
    			}
    		}
		}
    	
    	else if(s instanceof Circle){
    		Circle c=(Circle) s;	
    		Main.controller.clearAnnotation();
			Main.controller.addAnnotation("You have selected a Circle [("+c.getCenterX()+", "+c.getCenterY()+"), "+c.getRadius()+"]");
    		for (int i = 0; i < Panneau.shapes.size(); i++) {
    			if(Panneau.shapes.get(i) instanceof Circle){
    				Circle c2=(Circle) Panneau.shapes.get(i);
            		
    			}else{
    				Panneau.shapes.get(i).setStroke(Color.DARKGRAY);
    			}
    		}
    	}
    	
    	else if(s instanceof Rectangle && ((Rectangle) s).getWidth()!=((Rectangle) s).getHeight()){
    		Rectangle r=(Rectangle) s;	
    		Main.controller.clearAnnotation();
			Main.controller.addAnnotation("You have selected a Rectangle [("+r.getX()+", "+r.getY()+"), "+r.getWidth()+", "+r.getHeight()+"]");
    		for (int i = 0; i < Panneau.shapes.size(); i++) {
    			if(Panneau.shapes.get(i) instanceof Rectangle && ((Rectangle) Panneau.shapes.get(i)).getWidth()!=((Rectangle) Panneau.shapes.get(i)).getHeight()){
    				Rectangle r2=(Rectangle) Panneau.shapes.get(i);
            		
    			}else{
    				Panneau.shapes.get(i).setStroke(Color.DARKGRAY);
    			}
    		}
    	}

    	else if(s instanceof Rectangle && ((Rectangle) s).getWidth()==((Rectangle) s).getHeight()){
    		Rectangle r=(Rectangle) s;	
    		Main.controller.clearAnnotation();
			Main.controller.addAnnotation("You have selected a Carre [("+r.getX()+", "+r.getY()+"), "+r.getWidth()+"]");
    		for (int i = 0; i < Panneau.shapes.size(); i++) {
    			if(Panneau.shapes.get(i) instanceof Rectangle && ((Rectangle) Panneau.shapes.get(i)).getWidth()==((Rectangle) Panneau.shapes.get(i)).getHeight()){
    				Rectangle r2=(Rectangle) Panneau.shapes.get(i);
            		
    			}else{
    				Panneau.shapes.get(i).setStroke(Color.DARKGRAY);
    			}
    		}
    	}
    	
    }
    
    public static void goma(Panneau calque, Shape s){
    	if(goma){
			Panneau.shapes.remove(s);
			calque.getChildren().remove(s);
    	}else{
    		createAnoot(s);
    	}
    }
    
    private void tracerForme(double x, double y, double lar, double lng) {
    	Forme forme = null;
    	switch (type) {
    	case LINE       : 		forme = new myLine(xStart, yStart, xEnd, yEnd, coolor, width);  break;
    	case CERCLE     :		forme = new myCercle(x, y, Math.max(lar,lng)/2, coolor, width); break;
    	case CARRE      :		forme = new myCarre(x, y, Math.max(lar,lng), coolor, width); 	break;
    	case RECTANGLE  : 		forme = new myRectangle(x, y, lar, lng, coolor, width); 		break;
		default			:		break;
    	}
    	if(forme!=null) {
        	forme.affiche();
    	}
    }

    public void changerWidth(int parseDouble) {
    	width = parseDouble; 
    }

    public void changer(TypeForme forme)  { 
    	type = forme; 
    }

    public void changerColor(Color color)  { 
    	coolor = color;
    }

    public void toutEffacer() {
    	for (int i = 1; i < this.getChildren().size(); i++) {
        	this.getChildren().remove(i);
        	formes.clear();
		}
    }
    
    public void afficherTous(){
    	for (int i = 0; i < Panneau.shapes.size(); i++) {
    		this.getChildren().add(Panneau.shapes.get(i));
		}
    }
}
