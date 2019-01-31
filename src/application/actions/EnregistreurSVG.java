package application.actions;

import org.w3c.dom.Element;

import application.bo.Forme;
import application.bo.myCercle;
import application.bo.myLine;
import application.bo.myRectangle;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Un enregistreur SVG est un processeur DOM responsable de l'enregistrement
 * d'un dessin au format SVG standard.
 * 
 * Il suit exactement les memes principes que ceux de l'enregistreur XML (classe
 * EnregistreurXML). Les methodes enregistreDessin et creeElementXxxx devront
 * etre completees. Des methodes utilitaires pourront venir completer celles
 * definies par la classe ProcesseurDOM ; elles devront dans ce cas etre
 * OBLIGATOIREMENT definies en "private"a  la fin de la classe EnregistreurXML.
 *
 */
public class EnregistreurSVG extends ProcesseurDOM {

  /**
   * Teste l'enregistrement du dessin dans un fichier SVG. Le fichier XML
   * d'entree est prealablement lu, puis sauvagarde dans un fichier de sortie
   * au format SVG.
   * 
   * @param nomFichierEntree le nom du fichier XML d'entree lu
   * @param nomFichierSortie le nom du fichier SVG de sortie ecrit
   * @throws FileNotFoundException si l'un des noms des fichiers n'est pas
   *             valide
   */
  public static void teste(String nomFichierEntree, String nomFichierSortie) throws FileNotFoundException {
    LecteurXML lecteur = new LecteurXML();
    final List<Forme> dessin = lecteur.lisDessin(nomFichierEntree);
    EnregistreurSVG enregistreur = new EnregistreurSVG();
    enregistreur.enregistreDessin(nomFichierSortie, dessin);
    
  }

  /**
   * Enregistre le dessin donne dans un fichier SVG.
   * @param nomFichier le nom du fichier SVG de sauvegarde
   * @param dessin le dessin forme de la liste des vues des formes
   * @throws FileNotFoundException si le nom du fichier n'est pas valide
   */
  public void enregistreDessin(String nomFichier, List<Forme> dessin) throws FileNotFoundException {
    creeDocumentSVG();
    Element racine = getDocument().getDocumentElement();
    for (int i = 0; i < dessin.size(); i++) {
      racine.appendChild(this.creeElementVueForme(dessin.get(i)));
    }
    enregistreDocument(nomFichier);
  }

  /**
   * Cree un element DOM au format SVG representant la vue donnee d'une forme
   * et retourne cet element. Cette methode invoque les methodes
   * creeElement<Forme> en fonction du type de la vue.
   * @param vueForme la vue d'une forme
   * @return l'element DOM representant la vue d'une forme
   */
  public Element creeElementVueForme(Forme Forme) {
    Element element = null;
    String nom = Forme.getClass().getSimpleName();
    if (nom.equals("myRectangle") || nom.equals("myCarre")) {
      myRectangle rec = (myRectangle) Forme;
      element = creeElementRectangle(rec); 
      element.setAttribute("style", "stroke:" + "rgb(222,55,3)" + ";stroke-width:"+rec.getMyWidth());
      element.setAttribute("fill", "none");     
    } else if (nom.equals("myCercle")) {
      myCercle cer = (myCercle)Forme;
      element = creeElementCercle(cer);
      element.setAttribute("style", "stroke:" + "rgb(222,55,3)" + ";stroke-width:"+cer.getMyWidth());
      element.setAttribute("fill", "none");
     
    } else if (nom.equals("myLine")) {
      myLine l = (myLine) Forme;
      element = creeElementLine(l);
      element.setAttribute("style", "stroke:" + "rgb(222,55,3)" + ";stroke-width:"+l.getMyWidth());
      element.setAttribute("fill", "none");
    } else {
      throw new Error("Forme non geree");
    }
    getDocument().getDocumentElement().appendChild(element);
    return element;
  }

  /**
   * Renvoie un nouvel element DOM au format SVG representant le rectangle
   * donne.
   * @param forme le rectangle
   * @return element DOM representant le rectangle
   */
  public Element creeElementRectangle(myRectangle forme) {
    Element element = getDocument().createElement("rect");
    element.setAttribute("x", String.valueOf(forme.getX()));
    element.setAttribute("y", String.valueOf(forme.getY()));
    element.setAttribute("width", String.valueOf(forme.getLar()));
    element.setAttribute("height", String.valueOf(forme.getLng()));
    return element;
  }


  /**
   * Renvoie un nouvel element DOM au format SVG representant le cercle donne.
   * @param forme le cercle
   * @return element DOM representant le cercle
   */
  public Element creeElementCercle(myCercle forme) {
    Element element = getDocument().createElement("circle");
    int cx = (int) forme.getX();
    int cy = (int) forme.getY();
    int rayon = (int) forme.getRayon();
    element.setAttribute("cx", String.valueOf(cx));
    element.setAttribute("cy", String.valueOf(cy));
    element.setAttribute("r", String.valueOf(rayon));
    return element;
  }

  /**
   * Renvoie un nouvel element DOM au format SVG representant la ligne donnee.
   * @param forme la ligne
   * @return element DOM representant la ligne
   */
  public Element creeElementLine(myLine forme) {
    Element element = getDocument().createElement("line");
    int x1 = (int) forme.getX();
    int y1 = (int) forme.getY();
    int x2 = (int) forme.getxEnd();
    int y2 = (int) forme.getyEnd();
    element.setAttribute("x1", String.valueOf(x1));
    element.setAttribute("y1", String.valueOf(y1));
    element.setAttribute("x2", String.valueOf(x2));
    element.setAttribute("y2", String.valueOf(y2));
    return element;
  }
}
