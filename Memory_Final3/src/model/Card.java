package model;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Klasa Card - odpowiedzialna za stworzenie głownego elementu gry Memory karty
 * ktore beda potem odkrywane przez poszczegolnych graczy
 * 
 * @author Paweł Cielma
 * @version Final
 */
public class Card {

	private int W;
	private int H;

	public int getImageSize() {return W;}

	/**
	 * Metoda tworzaca karte do gry z folderu resources dająca możliwosc stworzenia
	 * karty o roznej tematyce do wyboru Airplanes Animals Flowers wybor z wybranej
	 * listy
	 * 
	 */
	public MeshView createCard(int i, int j, int imageSize, ArrayList<Integer> deckArrayList, String folderName,
			int boardDimension) {
		W = imageSize;
		H = imageSize;
		Image card = new Image(getClass().getResourceAsStream(
				"/" + folderName + "/200x200/pic" + deckArrayList.get(j + i * boardDimension) + ".jpg"));
		MeshView mw = new MeshView(createCardMesh());

		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(card);
		mw.setMaterial(material);

		return mw;
	}

	/**
	 * Metoda odpowiadajaca za zlozenie pliku obrazka z folderu resources o
	 * wymiarach 400x200 w kartę od dwoch stronach dajacych awers i rewers i efekt
	 * 3D przy obrocie tak zlozonej karty
	 * 
	 */
	public TriangleMesh createCardMesh() {
		TriangleMesh mesh = new TriangleMesh();

		mesh.getPoints().addAll(-1 * W / 2, -1 * H / 2, 0, 1 * W / 2, -1 * H / 2, 0, -1 * W / 2, 1 * H / 2, 0,
				1 * W / 2, 1 * H / 2, 0);
		mesh.getFaces().addAll(0, 0, 2, 2, 3, 3, 3, 3, 1, 1, 0, 0);
		mesh.getFaces().addAll(0, 4, 3, 7, 2, 6, 3, 7, 0, 4, 1, 5);
		mesh.getTexCoords().addAll(0, 0, 0.5f, 0, 0, 1, 0.5f, 1, 0.5f, 0, 1, 0, 0.5f, 1, 1, 1);

		return mesh;
	}
}