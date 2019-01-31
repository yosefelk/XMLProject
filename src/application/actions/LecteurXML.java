package application.actions;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import application.bo.Forme;
import application.bo.myCarre;
import application.bo.myCercle;
import application.bo.myLine;
import application.bo.myRectangle;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

/**
 * Un lecteur XML est un processeur DOM responsable du chargement d'un dessin au
 * format XML defini par l'application.
 * 
 * Il utilise les methodes heritees de la classe ProcesseurDOM pour charger le
 * fichier XML dans un document DOM, ainsi que les methodes de lecture des
 * entiers.
 * 
 * Les methodes lisDessin et creeXX devront etre completees. Des methodes
 * utilitaires pourront venir completer celles definies par la classe
 * ProcesseurDOM ; elles devront dans ce cas etre OBLIGATOIREMENT definies en
 * "private" a  la fin de la classe LecteurXML.
 * 
 */
public class LecteurXML extends ProcesseurDOM {

  /**
   * Teste le chargement du fichier XML. Le contenu du fichier est ensuite
   * affiche dans la fenetre de l'application (classe FenetreBeAnArtist).
   * @param nomFichier le fichier d'entree a lire
   * @throws FileNotFoundException si le fichier n'existe pas
   */
  public static void teste(String nomFichier) throws FileNotFoundException {
    LecteurXML lecteur = new LecteurXML();
    final List<Forme> dessin = lecteur.lisDessin(nomFichier);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
       // FenetreDesign fenetre = new FenetreDesign();
        for (Forme forme : dessin) {
          System.out.println(forme);
        // fenetre.getPanneauDessin().ajouterVueForme(forme);
        }
      }
    });
  }

  /**
   * Charge le fichier XML donne dans un document DOM puis renvoie
   * l'integralite du dessin sous la forme d'une liste de vues representant
   * les formes stockees dans le fichier.
   * 
   * @param nomFichier le nom du fichier XML
   * @return l'integralite du dessin sous la forme d'une liste de vues
   * @throws FileNotFoundException si le fichier n'est pas trouve ou
   *             accessible
   */
  public List<Forme> lisDessin(String nomFichier) throws FileNotFoundException {
    List<Forme> dessin = new ArrayList<>();
    chargeDocument(nomFichier);
    Element racine = getDocument().getDocumentElement();
    NodeList listeNode = racine.getChildNodes();
    for (int index = 0; index < listeNode.getLength(); index++) {
      if (listeNode.item(index).getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) listeNode.item(index);
        dessin.add(creeForme(element));
      }
    }
    
    return dessin;
  }

  /**
   * Cree une forme et sa vue associee representees par l'element DOM donne,
   * puis renvoie cette vue. Cette methode invoque les methodes cree<Forme>
   * definies pour chacune des <Forme> consideree.
   * @param element l'element representant la vue et sa forme
   * @return la vue stockee dans l'element considere
   */
  public Forme creeForme(Element element) {
    Forme vue = null;
    String nom = element.getNodeName();
    if (nom.equals("rectangle")) {
      myRectangle forme = creeRectangle(element);
      double width = Double.valueOf(element.getAttribute("width"));
      int[] rgb = lisMotifs(element.getAttribute("couleur"), ",");
      Color couleur = new Color(rgb[0],rgb[1],rgb[2]);
      vue = new myRectangle(forme, width);
    } else if (nom.equals("carre")) {
      myCarre forme = creeCarre(element);
      double width = Double.valueOf(element.getAttribute("width"));
      int[] rgb = lisMotifs(element.getAttribute("couleur"), ",");
      Color couleur = new Color(rgb[0],rgb[1],rgb[2]);
      vue = new myCarre(forme, width);
    } else if (nom.equals("cercle")) {
      myCercle forme = creeCercle(element);
      double width = Double.valueOf(element.getAttribute("width"));
      int[] rgb = lisMotifs(element.getAttribute("couleur"), ",");
      Color couleur = new Color(rgb[0],rgb[1],rgb[2]);
      vue = new myCercle(forme, width);
    } else if (nom.equals("line")) {
      myLine forme = creeLigne(element);
      int[] rgb = lisMotifs(element.getAttribute("couleur"), ",");
      double width = Double.valueOf(element.getAttribute("width"));
      Color couleur = new Color(rgb[0],rgb[1],rgb[2]);
      vue = new myLine(forme, width);
    } 
    System.out.println(nom);
    return vue;
  }

  /**
   * Renvoie un nouveau rectangle represente par l'element DOM donne.
   * @param element l'element representant le rectangle
   * @return le rectangle stocke dans l'element considere
   */
  public myRectangle creeRectangle(Element element) {
    int coordX = lisAttribut(element, "x");
    int coordY = lisAttribut(element, "y");
    int largeur = lisAttribut(element, "largeur");
    int hauteur = lisAttribut(element, "hauteur");
    return new myRectangle(coordX,coordY,largeur,hauteur);
  }

  /**
   * Renvoie un nouveau carre represente par l'element DOM donne.
   * @param element l'element representant le carre
   * @return le carre stocke dans l'element considere
   */
  public myCarre creeCarre(Element element) {
    int coordX = lisAttribut(element, "x");
    int coordY = lisAttribut(element, "y");
    int largeur = lisAttribut(element, "largeur");
    return new myCarre(coordX,coordY,largeur);
  }

  /**
   * Renvoie un nouveau cercle represente par l'element DOM donne.
   * @param element l'element representant le cercle
   * @return le cercle stocke dans l'element considere
   */
  public myCercle creeCercle(Element element) {
    int coordX = lisAttribut(element, "x");
    int coordY = lisAttribut(element, "y");
    int largeur = lisAttribut(element, "largeur");
    return new myCercle(coordX,coordY,largeur);
  }

  /**
   * Renvoie la nouvelle ligne representee par l'element DOM donne.
   * @param element l'element representant la ligne
   * @return la ligne stockee dans l'element considere
   */
  public myLine creeLigne(Element element) {
    int coordX = lisAttribut(element, "x");
    int coordY = lisAttribut(element, "y");
    int largeur = lisAttribut(element, "largeur");
    int hauteur = lisAttribut(element, "hauteur");
    return new myLine(coordX,coordY,largeur,hauteur);
  }



}
