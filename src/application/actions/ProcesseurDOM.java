package application.actions;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

/**
 * Un processeur DOM permet de creer, charger et enregistrer des documents XML
 * en utilisant le standard DOM et son implementation dans l'API Java. Il permet
 * egalement de manipuler les valeurs entieres stockes dans des attributs ou/et
 * des elements simples (feuilles de l'arbres DOM).
 * 
 */
public abstract class ProcesseurDOM {

  /**
   * L'implementation de DOM Core.
   */
  private DOMImplementation dom;

  /**
   * L'implementation de DOM Load & Save.
   */
  private DOMImplementationLS domLS;

  /**
   * Le document nouvellement cree, charge ou/et enregistre.
   */
  private Document document;

  /**
   * Construit les instances representant les implementations de DOM Core et
   * DOM Load & Save
   */
  protected ProcesseurDOM() {
    try {
      DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
      dom = registry.getDOMImplementation("XML 1.0");
      domLS = (DOMImplementationLS) registry.getDOMImplementation("LS");
    }
    catch (ClassNotFoundException event) {
    }
    catch (InstantiationException event) {
    }
    catch (IllegalAccessException event) {
    }
    catch (ClassCastException event) {
    }
  }

  /**
   * Renvoie le document cree, charge ou/et enregistre par l'une des methodes
   * associees.
   * @return Le document cree, charge ou/et enregistre.
   */
  public Document getDocument() {
    return document;
  }

  /**
   * Cree un nouveau document XML dont le nom de l'element racine est donne.
   * @param nomElementRacine le nom de l'element racine
   */
  public void creeDocumentXML(String nomElementRacine) {
    document = dom.createDocument(null, nomElementRacine, null);
  }

  /**
   * Crée un nouveau document SVG.
   */
  public void creeDocumentSVG() {
    document = dom.createDocument("http://www.w3.org/2000/svg", "svg", null);
  }

  /**
   * Charge un nouveau document XML dont le nom du fichier est donne.
   * @param nomFichier le nom du fichier XML (peut inclure le chemin complet)
   * @throws FileNotFoundException si le fichier n'est pas trouve ou
   *             accessible
   */
  public void chargeDocument(String nomFichier) throws FileNotFoundException {
    LSParser parser = domLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
    LSInput input = domLS.createLSInput();
    input.setByteStream(new FileInputStream(nomFichier));
    document = parser.parse(input);
  }

  /**
   * Enregistre le document precedemment cree ou charge dans le fichier donne.
   * @param nomFichier le nom du fichier de sauvegarde
   * @throws FileNotFoundException si le fichier ne peut etre sauvegarde
   *             (acces au repertoire non autorise par exemple)
   */
  public void enregistreDocument(String nomFichier) throws FileNotFoundException {
    LSSerializer writer = domLS.createLSSerializer();
    writer.getDomConfig().setParameter("xml-declaration", true);
    writer.getDomConfig().setParameter("format-pretty-print", true);
    LSOutput output = domLS.createLSOutput();
    output.setByteStream(new FileOutputStream(nomFichier));
    writer.write(document, output);
  }

  /**
   * Renvoie la valeur entiere stockee dans l'attribut donne.
   * @param element l'element contenant l'attribut considere
   * @param nomAttribut le nom de l'attribut
   * @return la valeur entiere stockee dans l'attribut
   * @throws NumberFormatException si la valeur stockee n'est pas un entier
   */
  public int lisAttribut(Element element, String nomAttribut) {
    return Integer.valueOf(element.getAttribute(nomAttribut));
  }

  /**
   * Ecris une valeur entiere dans un attribut.
   * @param element l'element contenant l'attribut considere
   * @param nomAttribut le nom de l'attribut
   * @param valeurAttribut la valeur entiere a stocker dans l'attribut
   */
  public void ecrisAttribut(Element element, String nomAttribut, int valeurAttribut) {
    element.setAttribute(nomAttribut, String.valueOf(valeurAttribut));
  }

  /**
   * Renvoie la valeur entiere stockee dans l'element simple donne. Soit
   * l'element event suivant : <a> <b>123</b> <c>456</c> </a>. L'invocation de
   * lisElementSimple(event, "b") renvoie l'entier 123.
   * @param eementParent l'element parent de l'element simple considere
   * @param nomElementFils le nom de l'element simple
   * @return la valeur entiere stockee dans l'element simple
   * @throws NumberFormatException si la valeur stockee n'est pas un entier
   */
  public int lisElementSimple(Element elementParent, String nomElementFils) {
    Node premier = elementParent.getElementsByTagName(nomElementFils).item(0);
    String valeur = premier.getChildNodes().item(0).getNodeValue();
    return Integer.valueOf(valeur);
  }

  /**
   * Ecris une valeur entiere donnee dans un element simple. Soit l'element event
   * suivant : <a/> L'invocation de ecrisElementSimple(event, "b", 123) modifie event
   * comme suit : <a> <b>123</b> </a> .
   * @param elementParent l'element parent de l'element simple considere
   * @param nomElementSimple le nom de l'element simple
   * @param valeurElementSimple la valeur entiere à stocker dans l'element
   *            simple
   */
  public void ecrisElementSimple(Element elementParent, String nomElementSimple, int valeurElementSimple) {
    Text texte = getDocument().createTextNode(String.valueOf(valeurElementSimple));
    Element element = getDocument().createElement(nomElementSimple);
    element.appendChild(texte);
    elementParent.appendChild(element);
  }

  /**
   * Decompose une chaine de caracteres constituee d'entiers separes par un ou
   * plusieur separateurs en un tableau d'entiers. Soit c la chaine
   * "1,2 3,4 5,6". L'invocation de lisMotifs(c, " ,") renvoie le tableau
   * {1,2,3,4,5,6}.
   * @param chaine La chaine a decomposer
   * @param separateurs Les caracteres possibles servant de separateurs
   * @return le tableau des entiers stockes dans la chaine
   * @throws NumberFormatException si l'une des valeurs stockees entre les
   *             separateurs n'est pas un entier
   */
  public int[] lisMotifs(String chaene, String separateurs) {
    StringTokenizer tokenizer = new StringTokenizer(chaene, separateurs);
    int[] motifs = new int[tokenizer.countTokens()];
    for (int i = 0; i < motifs.length; i++) {
      motifs[i] = Integer.valueOf(tokenizer.nextToken());
    }
    return motifs;
  }
}
