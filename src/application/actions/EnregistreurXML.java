package application.actions;

import org.w3c.dom.Element;

import application.bo.Forme;
import application.bo.myCarre;
import application.bo.myCercle;
import application.bo.myLine;
import application.bo.myRectangle;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Un enregistreur XML est un processeur DOM responsable de l'enregistrement
 * d'un dessin au format XML defini par l'application.
 * 
 * Il utilise les methodes heritees de la classe ProcesseurDOM pour creer un
 * document DOM et l'enregistrer dans un fichier XML, ainsi que les methodes
 * d'ecriture des entiers.
 * 
 * Les methodes enregistreDessin et creeElementXxxx devront etre completees. Des
 * methodes utilitaires pourront venir completer celles definies par la classe
 * ProcesseurDOM ; elles devront dans ce cas etre OBLIGATOIREMENT definies en
 * "private" a la fin de la classe EnregistreurXML.
 *
 */
public class EnregistreurXML extends ProcesseurDOM {

  /**
   * Teste l'enregistrement du dessin dans un fichier XML. Le fichier XML
   * d'entree est prealablement lu, puis sauvagarde dans un fichier de sortie.
   * Le fichier de sortie est ensuite charge et visualise par l'application.
   * 
   * @param nomFichierEntree le nom du fichier XML d'entree lu
   * @param nomFichierSortie le nom du fichier XML de sortie ecrit puis
   *            affiche
   * @throws FileNotFoundException si l'un des noms des fichiers n'est pas
   *             valide
   */
  public static void teste(String nomFichierEntree, String nomFichierSortie) throws FileNotFoundException {
   LecteurXML lecteur = new LecteurXML();
    final List<Forme> dessin = lecteur.lisDessin(nomFichierEntree);
    EnregistreurXML enregistreur = new EnregistreurXML();
    enregistreur.enregistreDessin(nomFichierSortie, dessin);
    LecteurXML.teste(nomFichierSortie);
  }

  /**
   * Enregistre le dessin donne dans un fichier.
   * @param nomFichier le nom du fichier de sauvegarde
   * @param dessin le dessin forme de la liste des vues des formes
   * @throws FileNotFoundException si le nom du fichier n'est pas valide
   */
  public void enregistreDessin(String nomFichier, List<Forme> dessin) throws FileNotFoundException {
    creeDocumentXML("dessin");
    Element racine = getDocument().getDocumentElement();
    for (int i = 0; i < dessin.size(); i++) {
      racine.appendChild(this.creeElementForme(dessin.get(i)));
    }
    enregistreDocument(nomFichier);
  }

  /**
   * Cree un element DOM representant la vue donnee d'une forme et retourne
   * cet element. Cette methode invoque les methodes creeElement<Forme> en
   * fonction du type de la vue.
   * @param Forme la vue d'une forme
   * @return l'element DOM representant la vue d'une forme
   */
  public Element creeElementForme(Forme Forme) {
    String nom = Forme.getClass().getSimpleName();
    Element element;
    String red;
    String green;
    String blue;
    if (nom.equals("myRectangle")) {
      myRectangle rect = (myRectangle) Forme;
      element = creeElementRectangle(rect);
      element.setAttribute("width", String.valueOf(Forme.getMyWidth()));
    } else if (nom.equals("myCarre")) {
      myCarre carre = (myCarre) Forme;
      element = creeElementCarre(carre);
      element.setAttribute("width", String.valueOf(Forme.getMyWidth()));
    } else if (nom.equals("myCercle")) {
      myCercle cer = (myCercle) Forme;
      element = creeElementCercle(cer);
      element.setAttribute("width", String.valueOf(Forme.getMyWidth()));
    }  else if (nom.equals("myLine")) {
      myLine line = (myLine) Forme;
      element = creeElementLigne(line);
      element.setAttribute("width", String.valueOf(Forme.getMyWidth()));
    } else {
      throw new Error("Forme non geree");
    }
    return element;
  }

  /**
   * Renvoie un nouvel elément DOM representant le rectangle donne.
   * @param forme le rectangle
   * @return element DOM representant le rectangle
   */
  public Element creeElementRectangle(myRectangle forme) {
    Element element = getDocument().createElement("rectangle");
    element.setAttribute("x", String.valueOf(forme.getX()));
    element.setAttribute("y", String.valueOf(forme.getY()));
    element.setAttribute("largeur", String.valueOf(forme.getLar()));
    element.setAttribute("hauteur", String.valueOf(forme.getLng()));
    return element;
  }

  /**
   * Renvoie un nouvel element DOM representant le carre donne.
   * @param forme le carre
   * @return element DOM representant le carre
   */
  public Element creeElementCarre(myCarre forme) {
    Element element = getDocument().createElement("carre");
    element.setAttribute("x", String.valueOf(forme.getX()));
    element.setAttribute("y", String.valueOf(forme.getY()));
    element.setAttribute("largeur", String.valueOf(forme.getLar()));
    return element;
  }


  /**
   * Renvoie un nouvel element DOM representant le cercle donnee.
   * @param forme le cercle
   * @return element DOM representant le cercle
   */
  public Element creeElementCercle(myCercle forme) {
    Element element = getDocument().createElement("cercle");
    element.setAttribute("x", String.valueOf(forme.getX()));
    element.setAttribute("y", String.valueOf(forme.getY()));
    element.setAttribute("rayon", String.valueOf(forme.getRayon()));
    return element;
  }

  /**
   * Renvoie un nouvel element DOM representant la ligne donnee.
   * @param forme la ligne
   * @return element DOM representant la ligne
   */
  public Element creeElementLigne(myLine forme) {
    Element element = getDocument().createElement("line");
    element.setAttribute("x", String.valueOf(forme.getX()));
    element.setAttribute("y", String.valueOf(forme.getY()));
    element.setAttribute("xEnd", String.valueOf(forme.getxEnd()));
    element.setAttribute("yEnd", String.valueOf(forme.getyEnd()));
    return element;
  }

}
