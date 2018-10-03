package controller;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
/**
* Klasa <b>Main</b> - klasa glowna odpowiadajaca za uruchomienie programu w architekturze Model View Controller
* 
* @author Marcin Kowalski
* @version Final
*
*/
public class Main extends Application {

	private double xOffset;
	private double yOffset;
	/**
	 * W ramach architektury Model View Controller następuje zaladowanie obiektow z
	 * pliku fxml znajdującego się w pliku opisującego strukturę okna za pomoca
	 * FXMLLoader
	 * 
	 * Dodajemy wizualny efekt transparentnosci odzielajacy okno MENU od wlasciwej
	 * czesci ekranow dla wizualnego rodzielenia tych elementow.
	 * 
	 * Dodajemy możliwosc przesuniecia przez przytrzymanie wcisnietej myszy na
	 * dowolnym miejscu okna
	 * 
	 * Tworzymy instancje kontrolera MainController, ustawiamy scene głowna i wyswietlamy
	 * okno
	 * 
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
			AnchorPane root = loader.load();
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			root.setOnMousePressed((MouseEvent event) -> {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			});
			root.setOnMouseDragged((MouseEvent event) -> {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			});
			Scene scene = new Scene(root);
			MainController loginController = loader.getController();
			loginController.setMain(this, primaryStage);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}