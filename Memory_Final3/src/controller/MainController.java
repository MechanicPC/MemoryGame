package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Data;
import model.Game;
import model.Results;

/**
 * Klasa <b>MainController</b> - klasa odpowiadająca za działanie aplikacji
 * wyswietlanie graficzne interfejscu odnosi się do elementów interfejsu
 * zdefiniowanych w pliku FXML
 * 
 * @author Paweł Cielma
 * @version Final
 *
 *
 * 
 *          Mamy tu elementy interfejsu zdefiniowane w pliku FXML
 * 
 * 
 * 
 *          <b>@FXML RootPane</b> - głowny kontener aplikacji na ktorym beda
 *          wyswietlane poszczegolne jej elementy
 *          <p>
 *          <b>@FXML gamePane</b> - kontener/panel gry na nim beda wyswietlane i
 *          odkrywane karty samej rozgrywki
 *          <p>
 *          <b>@FXML settingsPane</b> - kontener/panel ustawien wyswietlane tu
 *          beda edytowalne ustawienia: Adres Ip, port, poziom trudnosci,
 *          kategoria obrazków gry
 *          <p>
 *          <b>@FXML resultsPane</b> - kontener/panel wyników bedzie w nim
 *          wyswietlana tabela z wynikami poszczegolnych rozgrywek
 *          <p>
 *          <b>@FXML menuPane</b> - kontener/panel głownych ikon aplikacji ktore
 *          beda nawigowały przechodzniem miedzy kontnerami
 *          <p>
 *          <b>@FXML userPane</b> - kontener/panel uzytkownika pozwalajacy na
 *          wpisanie nazwy gracza swtorzenie/dolaczenie do rozgrywki
 *          <p>
 *          <b>@FXML img_settings</b> - obraz ikonki ustawien gry
 *          <p>
 *          <b>@FXML img_user</b> - obraz ikonki uzytkownika
 *          <p>
 *          <b>@FXML img_results</b> - obraz ikonki wyników
 *          <p>
 *          <b>@FXML img_close</b> - obraz ikonki sluzacej do zamykania gry
 *          <p>
 *          <b>@FXML img_exit</b> - obraz ikonki sluzacej do wysjcia z calego
 *          programu
 *          <p>
 *          <b>@FXML rec_settings</b> - prostakat wywietlany po kliknieciu w
 *          ikonke ustawienia - daje to wizualne potwierdzenie ze jestesmy w
 *          panelu ustawien
 *          <p>
 *          <b>@FXML rec_user</b> - prostakat wywietlany po kliknieciu w ikonke
 *          gracza/gry - daje to wizualne potwierdzenie ze jestesmy w panelu
 *          gracza/gry
 *          <p>
 *          <b>@FXML rec_results</b> - prostakat wywietlany po kliknieciu w
 *          ikonke wynikow - daje to wizualne potwierdzenie ze jestesmy w panelu
 *          wynikow
 *          <p>
 *          <b>@FXML cbox_difficulty</b> - check box dla wyboru ustawien
 *          trudnosci jeden z 3 poziomow - zwieksza ilosc kart w grze im wyzszy
 *          <p>
 *          <b>@FXML cbox_category</b> - check box dla wyboru kategorii
 *          wyswietlany obrazkow: samoloty, zwierzeta, kwiatki
 *          <p>
 *          <b>@FXML txtFieldUser</b> - pole tkstow do wpisania nazwy gracza
 *          <p>
 *          <b>@FXML txtFieldPort</b> - pole tkstowe ustawien do wpisania portu
 *          potrzebnego do polaczenia
 *          <p>
 *          <b>@FXML txtFieldIPAddress</b> - pole tekstowe do wpisania nr ip
 *          potrzebnego do polaczenia
 *          <p>
 *          <b>@FXML tableResults</b> - tabela wyswietlajaca historyczne
 *          zakonczone rozgrywki - wyniki
 *          <p>
 *          <b>@FXML colPlayer1</b> - element tabeli 1 kolumna nazwa gracza nr 1
 *          <p>
 *          <b>@FXML colPlayer2</b> - element tabeli 2 kolumna nazwa gracza nr 2
 *          <p>
 *          <b>@FXML colResult</b> - element tabeli 3 kolumna rezultad gry
 *          <p>
 *          <b>@FXML colData</b> - element tabeli 4 kolumna zawierajaca date
 *          przeprowadzone gry
 * 
 */
public class MainController {

	@FXML
	private AnchorPane rootPane, gamePane, settingsPane, resultsPane, menuPane, userPane;
	@FXML
	private JFXButton btnLogin, btnCreateGame, btnConnectToGame;
	@FXML
	private ImageView img_settings, img_user, img_results, img_close, img_exit;
	@FXML
	private Rectangle rec_settings, rec_user, rec_results;
	@FXML
	private JFXComboBox<Integer> cbox_difficulty;
	@FXML
	private JFXComboBox<String> cbox_category;
	@FXML
	private JFXTextField txtFieldUser, txtFieldPort, txtFieldIPAddress;
	@FXML
	private TableView<Results> tableResults;
	@FXML
	private TableColumn<Results, String> colPlayer1;
	@FXML
	private TableColumn<Results, String> colPlayer2;
	@FXML
	private TableColumn<Results, String> colResult;
	@FXML
	private TableColumn<Results, String> colData;
	/**
	 * zmienna boolean - sprawdzajaca czy dana gra jest serwerem czy nie
	 */
	public boolean isServer = false;
	@SuppressWarnings("unused")
	private Main main;
	private Stage primaryStage;
	private Game game;

	/**
	 * Metoda setMain punkt wejścia dla wszystkich aplikacji JavaFX
	 * 
	 * @param main
	 *            - parametr z klasy main
	 * @param primaryStage
	 *            - element na ktorym mozna ustawic glowna scene aplikacji
	 */
	public void setMain(Main main, Stage primaryStage) {
		this.main = main;
		this.primaryStage = primaryStage;
		Data.loadData();
		tableResults.setItems(Data.resultsList);
	}

	/**
	 * ActionEvent - <b>btnCreateGame</b> - proces nastapujacy po wcisnieciu
	 * przycisku stworz grę w panelu Uzytkownika
	 */
	@FXML
	void btnCreateGame(ActionEvent event) {
		if (txtFieldUser.getText().isEmpty()) {
			StackPane stackPane = new StackPane();
			stackPane.setPrefHeight(75);
			stackPane.setPrefWidth(300);
			stackPane.setLayoutX(100);
			stackPane.setLayoutY(200);
			userPane.getChildren().add(stackPane);
			JFXDialogLayout content = new JFXDialogLayout();
			content.setBody(new Text("Wprowadz nick gracza"));
			content.setStyle("-fx-background-color: #d0dde0;");
			JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
			JFXButton okButton = new JFXButton("Ok");
			okButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					dialog.close();
				}
			});
			content.setActions(okButton);
			dialog.show();
		} else {
			/**
			 * Pobierane są IP i nr Portu z pol ustawień
			 */
			Data.ip = txtFieldIPAddress.getText();
			Data.port = Integer.parseInt(txtFieldPort.getText());
			/**
			 * Tworzona jest gra o odpowiednich parametrach (panel gry odpowiedniego
			 * rozmiaru, poziom trudnosci gry, kategoria obrazkow, nazwa gracza i
			 * odpowiednia lista obrazkow)
			 * 
			 */
			game = new Game(gamePane.getHeight(), cbox_difficulty.getValue(), cbox_category.getValue(),
					txtFieldUser.getText(), Data.folderList.indexOf(cbox_category.getValue()));
			/**
			 * Stworzony jest board z kartami na ktorych gracze beda grac odpowiedniej
			 * wysokosci i szerokosci
			 */
			AnchorPane board = game.createBoard(gamePane.getHeight(), gamePane.getWidth());
			gamePane.getChildren().add(board);
			/**
			 * Panel Ustawien i Wynikow, Gracza zostaje ukryty odkrywamy Panel Gry
			 */
			settingsPane.setVisible(false);
			resultsPane.setVisible(false);
			userPane.setVisible(false);
			gamePane.setVisible(true);
			/**
			 * Ikony Menu Uzytkownik, Ustawienia, Wyniki sa dezaktywowane - by podczas gry
			 * nie mozna bylo z nich skorzystac
			 */
			img_user.setDisable(true);
			img_settings.setDisable(true);
			img_results.setDisable(true);
			/**
			 * Ikona Menu Koniec gry zostaje aktywowana gracze beda mieli mozliwosc jej
			 * przerwania
			 */
			img_close.setDisable(false);
		}
	}

	/**
	 * MouseEvent handleButtonAction - metoda odpowiadajaca za zachowanie panelu gry
	 * przy wcisnieciu kolejnych przyciskow Menu Gry Jeżeli klikniemy w ikonke
	 * Ustawien to:
	 * 
	 */
	@SuppressWarnings({ "deprecation" })
	@FXML
	private void handleButtonAction(MouseEvent event) {
		if (event.getTarget() == img_settings) {
			/**
			 * Panel ustawien się wyświetli w aplikacji
			 */
			settingsPane.setVisible(true);
			/**
			 * Pojawi się prostokąt łaczacy ikone Ustawien z panelem Ustawien potwierdzajacy
			 * graficznie ze jestemy teraz na panelu Ustawien
			 */
			rec_settings.setVisible(true);
			/**
			 * Panel Wyniki, Użytkownik, Gra, i odpowiadajace im graficzne prostokaty
			 * łaczace ikony z panelem zostaja ukryte
			 */
			resultsPane.setVisible(false);
			userPane.setVisible(false);
			gamePane.setVisible(false);
			rec_user.setVisible(false);
			rec_results.setVisible(false);
			/**
			 * Jeżeli klikniemy w ikonkę Użytkownik to:
			 */
		} else if (event.getTarget() == img_user) {
			/**
			 * Panel Użytkownik się wyświetli w aplikacji
			 */
			userPane.setVisible(true);
			/**
			 * Pojawi się prostokąt łaczacy ikone Uzytkownik z panelem Użytkownika
			 * potwierdzajacy graficznie ze jestemy teraz na panelu Uzytkownika
			 */
			rec_user.setVisible(true);
			/**
			 * Panel Ustawienia, Wyniki, Gra, i odpowiadajace im graficzne prostokaty
			 * łaczace ikony z panelem zostaja ukryte
			 */
			settingsPane.setVisible(false);
			resultsPane.setVisible(false);
			gamePane.setVisible(false);
			rec_settings.setVisible(false);
			rec_results.setVisible(false);
			/**
			 * Jeżeli klikniemy w ikonkę Wyniki to:
			 */
		} else if (event.getTarget() == img_results) {
			/**
			 * Panel Wyniki się wyświetli w aplikacji
			 */
			resultsPane.setVisible(true);
			/**
			 * Pojawi się prostokąt łaczacy ikone Wyniki z panelem Wyniki potwierdzajacy
			 * graficznie ze jestemy teraz na panelu Wyniki
			 */
			rec_results.setVisible(true);
			/**
			 * Panel Ustawienia, Użytkownik, Gra, i odpowiadajace im graficzne prostokaty
			 * łaczace ikony z panelem zostaja ukryte
			 */
			settingsPane.setVisible(false);
			userPane.setVisible(false);
			gamePane.setVisible(false);
			rec_settings.setVisible(false);
			rec_user.setVisible(false);
			/**
			 * Jeżeli klikniemy w ikonkę Koniec Gry to:
			 */
		} else if (event.getTarget() == img_close) {
			/**
			 * Panel Ustawien, Wynikow, Uzytkownika, Gry, i odpowiadajace im prostokaty
			 * laczace panal z ikonami zostaja ukryte
			 */
			settingsPane.setVisible(false);
			resultsPane.setVisible(false);
			userPane.setVisible(false);
			gamePane.setVisible(false);
			rec_settings.setVisible(false);
			rec_user.setVisible(false);
			rec_results.setVisible(false);
			/**
			 * Pozostaje tylko Menu Gry które na nowo ma aktywowane Ikony Uzytkownik,
			 * Ustawienia, Wyniki, pozwalajace na uruchomienie nowej gry lbu dolaczenie do
			 * niej
			 */
			img_user.setDisable(false);
			img_settings.setDisable(false);
			img_results.setDisable(false);
			img_close.setDisable(false);
			/**
			 * Dotatkowo jesli koniec gry kliknal graz ktory stworzyl serwrer gry jest on
			 * zamykany wraz z polaczeniem
			 */
			game.getThread().stop();
			gamePane.getChildren().clear();
			if (game.isServer()) {
				game.getNetSocket().closeServer();
			}
			System.gc();
			/**
			 * Jeżeli klikniemy w ikonkę Wyjście zamykana jest całkowicie aplikacja
			 */

		} else if (event.getTarget() == img_exit) {
			primaryStage.close();
		}
	};

	/**
	 * Przy Inicjalizacji gry pierwszym jej wlaczeniu nastepuje: Wyświetlenie Panelu
	 * uzytkownik i ukrycie paneli Ustawienia, Wyniki, Gry Wylaczamy takze przycisk
	 * koniec gry nie jest on aktywny do momentu je stworzenia Ustawiane sa tez
	 * podstawowe domyslne wartości tak by bez ewentualnego przechodzenia do
	 * ustawień uruchomić grę na domyślnych ustawieniach Następuje także połączenie
	 * pliku tekstowego wyników z tabelą wyswietlana w Panelu Wynikow i ich
	 * zaprezentowanie w formie tabeli graficznej
	 */
	public void initialize() {
		settingsPane.setVisible(false);
		resultsPane.setVisible(false);
		userPane.setVisible(true);
		gamePane.setVisible(false);
		rec_settings.setVisible(false);
		rec_user.setVisible(false);
		rec_results.setVisible(false);
		img_close.setDisable(true);

		// Initialize default value:
		cbox_difficulty.getItems().addAll(2, 4, 6);
		cbox_difficulty.setValue(4);
		cbox_category.getItems().addAll(Data.folderList);
		cbox_category.setValue("Animals");
		txtFieldPort.setText("20000");
		txtFieldIPAddress.setText("localhost");

		colPlayer1.prefWidthProperty().bind(tableResults.widthProperty().multiply(0.3));
		colPlayer2.prefWidthProperty().bind(tableResults.widthProperty().multiply(0.3));
		colResult.prefWidthProperty().bind(tableResults.widthProperty().multiply(0.15));
		colData.prefWidthProperty().bind(tableResults.widthProperty().multiply(0.25));

		colPlayer1.setCellValueFactory(new PropertyValueFactory<Results, String>("player1"));
		colPlayer2.setCellValueFactory(new PropertyValueFactory<Results, String>("player2"));
		colResult.setCellValueFactory(new PropertyValueFactory<Results, String>("result"));
		colData.setCellValueFactory(new PropertyValueFactory<Results, String>("date"));
	}
}