package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormesController implements Initializable {

    @FXML
    private Panneau panneau;

    @FXML
    private ColorPicker coloor;

    @FXML
	private TextField widd;
    
    @FXML
    private TextArea label;
    
    @FXML
    private void goma(ActionEvent event) {
        panneau.changer(TypeForme.GOMA);
        panneau.goma=true;
    }
    
    @FXML
    private void cursor(ActionEvent event) {
        panneau.changer(TypeForme.CURSOR);
        panneau.goma=false;
    }
    
    @FXML
    private void formeLine(ActionEvent event) {
        panneau.changer(TypeForme.LINE);
        panneau.goma=false;
    }

    @FXML
    private void formeCirculaire(ActionEvent event) {
        panneau.changer(TypeForme.CERCLE);
        panneau.goma=false;
    }

    @FXML
    private void formeCarree(ActionEvent event)  {
        panneau.changer(TypeForme.CARRE);
        panneau.goma=false;
    }

    @FXML
    private void formeRectangle(ActionEvent event) {
        panneau.changer(TypeForme.RECTANGLE);
        panneau.goma=false;
    }

    @FXML
    private void changerColor(ActionEvent event) {
        panneau.changerColor(coloor.getValue());
    }

    @FXML
    private void changerWidth(ActionEvent event) {
    	System.out.println(widd.getText());
        panneau.changerWidth((int) Double.parseDouble(widd.getText()));
    }
    
    @FXML
    private void toutEffacer(ActionEvent event)  {
        panneau.toutEffacer();
    }
    
    @FXML
    private void toutAfficher(ActionEvent event)  {
    	panneau.afficherTous();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {  }

    public void clearAnnotation() {
    	label.clear();
    }

    public void addAnnotation(String string) {
    	label.setWrapText(true);
    	label.setText(label.getText()+"\n"+string);
    }
}
