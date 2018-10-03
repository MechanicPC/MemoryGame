package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.text.Font;
/**
* Klasa Game - odpowiada za zasady samej rozgrywki  gry Memory zawiera metody logiczne gry
*  @author Pawel Cielma
*  @version Final
*/
public class Game implements Runnable {

	private Node selectedImage;
	private Node selected1 = null;
	private Node selected2 = null;
	private int boardDimension;
	private int indeksOfFolder;
	private int imageSize;
	private int gPaneDimension;
	private int myMatches = 0;
	private int opMatches = 0;
	private int numErrors = 0;
	private int CARD_IN_DECK = 20;
	private int gap = 5;
	private double paneWidth;
	@SuppressWarnings("unused")
	private boolean lostConnection = false;
	private boolean isServer = false;
	private boolean clientConnected = false;
	private boolean myTurn = false;
	private boolean gameOver = false;
	private boolean gameStateUpdated = false;
	private String folderName;
	private String myName = null;
	private String opName = null;
	private ArrayList<Integer> deckArrayList = new ArrayList<Integer>();
	private MeshView doubleSidedCard;
	private Thread thread;
	private GridPane gPane = new GridPane();
	private Pane pane = new Pane();
	private AnchorPane anchorPane = new AnchorPane();
	private Label lblMyName = new Label();
	private Label lblOpName = new Label();
	private Label lblMyMatches = new Label();
	private Label lblOpMatches = new Label();
	private Label lblWaitingForPlayer = new Label();
	private Label lblStatus = new Label();
	private Label lblWin = new Label();
	private Label lblLose = new Label();
	private ImageView winner;
	private ImageView loser;
	
	private Animation animation = new Animation();
	private Card card = new Card();
	private NetSocket netSocket = new NetSocket();

	public int getgPaneDimension() {return gPaneDimension;}
	public Thread getThread() {return thread;}
	public NetSocket getNetSocket() {return netSocket;}
	public boolean isServer() {return isServer;}

	public Game(double paneWidth, int boardDimension, String folderName, String playerName, int indeksOfFolder) {
		this.indeksOfFolder = indeksOfFolder;
		this.myName = playerName;
		this.boardDimension = boardDimension;
		this.folderName = folderName;
		this.paneWidth = paneWidth;
		/**
		 * Rozpoczęcie gry to sprawdzenie czy jest mozliwosc polaczenia z serwerem jesli
		 * nie to sami go tworzymy.
		 * 
		 * Gracz pierwszy staje sie serwerem do ktorego gracz drugi sie podlacza jako
		 * klient
		 */
		if (!netSocket.connectToServer()) {
			netSocket.initializeServer();
			isServer = true;
			myTurn = true;

			disableBoard(true);
			shuffleBoard();

		} else {
			clientConnected = true;
			isServer = false;
			lblWaitingForPlayer.setVisible(false);

			disableBoard(true);
			getBoardStateFromServer();
		}
		thread = new Thread(this, "Game");
		thread.setDaemon(true);
		thread.start();
	};
	/**
	 * Nastepuje przygotowanie kart ktore będa brały udział w rozgrywce
	 */
	private ArrayList<Integer> shuffleBoard() {
		List<Integer> cardList = new ArrayList<Integer>();
		for (int i = 1; i < CARD_IN_DECK + 1; i++) {
			cardList.add(i);
		}
		/**
		 * Tasujemy karty
		 */
		Collections.shuffle(cardList);
		/**
		 * Następuje dublowanie decku plsu ograniczenie liczby kart wymaganej dla danej
		 * gry Dostepne opcje (2x2), (4x4), (6x6)
		 */
		deckArrayList.addAll(cardList.subList(0, (boardDimension * boardDimension) / 2));
		deckArrayList.addAll(deckArrayList);
		/**
		 * Ponowne Tasowanie Decku dla pewnosci i wprowadzenia wiekszej losowosci
		 */
		Collections.shuffle(deckArrayList);
		deckArrayList.add(indeksOfFolder);
		deckArrayList.add(boardDimension);

		return deckArrayList;
	}
	/**
	 * Następuje przygotowanie Tablicy z rozłożonymi kartami (2x2), (4x4), (6x6) z
	 * odpowiednimi odstepami jesli jest mniej kart w danej grze sa odpowiednio
	 * wieksze im wiecej kart sa mniejsze by zmiescic sie w ustalonym rozmiarze okna
	 */

	/**
	 * Metoda Tworząca Board Kart
	 * @param height - parametr wysokość Board
	 * @param width - parametr szerokość Board
	 * @return - zwraca nam utworozny Board Kart
	 */
	public AnchorPane createBoard(double height, double width) {

		anchorPane.setPrefHeight(height);
		anchorPane.setPrefWidth(width);
		pane.setPrefHeight(height);
		pane.setPrefWidth(185);
		pane.setLayoutX(5);
		pane.setLayoutY(0);

		gPane.setHgap(gap);
		gPane.setVgap(gap);
		gPane.setPadding(new Insets(gap, gap, gap, gap * 2));
		gPane.setLayoutX(195);
		gPane.setLayoutY(0);
		
		Font font = new Font("Calibri", 20);
		Color color = Color.WHITE;
		
		Font fontLblWaitingForPlayer = new Font("Arial Bold", 50);
		Color colorLblWaitingForPlayer = Color.RED;

		Label vs = new Label();
		lblStatus.setTextFill(color);
		lblStatus.setFont(font);
		lblWin.setFont(font);
		lblWin.setTextFill(color);
		lblLose.setFont(font);
		lblLose.setTextFill(color);
		lblWaitingForPlayer.setText("  OCZEKIWANIE NA\nDRUGIEGO GRACZA");
		lblWin.setText("WYGRAŁEŚ !!!");
		lblLose.setText("PRZEGRAŁEŚ !!!");
		lblWaitingForPlayer.setPrefSize(550, 200);
		lblWaitingForPlayer.setLayoutX(250);
		lblWaitingForPlayer.setLayoutY(200);
		lblWaitingForPlayer.setTextFill(colorLblWaitingForPlayer);
		lblWaitingForPlayer.setFont(fontLblWaitingForPlayer);
		lblMyName.setPrefHeight(50);
		lblMyName.setPrefWidth(120);
		lblMyName.setFont(font);
		lblMyName.setTextFill(color);
		lblOpName.setPrefHeight(50);
		lblOpName.setPrefWidth(120);
		lblOpName.setFont(font);
		lblOpName.setTextFill(color);
		lblMyMatches.setPrefHeight(50);
		lblMyMatches.setPrefWidth(50);
		lblMyMatches.setText("0");
		lblMyMatches.setFont(font);
		lblMyMatches.setTextFill(color);
		lblOpMatches.setPrefHeight(50);
		lblOpMatches.setPrefWidth(50);
		lblOpMatches.setText("0");
		lblOpMatches.setFont(font);
		lblOpMatches.setTextFill(color);
		vs.setText("vs");
		vs.setFont(font);
		vs.setTextFill(color);

		lblMyName.setLayoutX(10);
		lblMyName.setLayoutY(20);
		lblOpName.setLayoutX(10);
		lblOpName.setLayoutY(130);
		vs.setLayoutX(90);
		vs.setLayoutY(90);
		lblMyMatches.setLayoutX(140);
		lblMyMatches.setLayoutY(20);
		lblOpMatches.setLayoutX(140);
		lblOpMatches.setLayoutY(130);
		
		Image win = new Image(getClass().getResourceAsStream("/Image_res/winner.gif"));//, 300, 300, false, false);
		Image loose = new Image(getClass().getResourceAsStream("/Image_res/looser.gif"));//, 300, 300, false, false);
		winner = new ImageView(win);
		loser = new ImageView(loose);
		
		pane.getChildren().addAll(lblMyName, lblOpName, lblMyMatches, lblOpMatches, vs);

		imageSize = (int) ((paneWidth - (boardDimension + 1) * gap) / boardDimension);
		gPaneDimension = (boardDimension + 1) * gap + imageSize * boardDimension;
		/**
		 * W zależności od rozmiaru Decku każda z kart otrzmuje indwywiduany
		 * identyfikator Id: XXYY, część XX - jest potrzebna do określenia ktora karta
		 * została kliknieta część YY jest to nr karty słuzący do porowniania czy dwie
		 * klikniete karty to te same czy nie
		 */
		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {
				doubleSidedCard = card.createCard(i, j, imageSize, deckArrayList, folderName, boardDimension);
				doubleSidedCard.setId(
						Integer.toString((j + i * boardDimension) + 10) + (deckArrayList.get(j + i * boardDimension)));
				gPane.add(doubleSidedCard, i, j);
				doubleSidedCard.setOnMouseClicked((MouseEvent evt) -> {
					selectedImage = evt.getPickResult().getIntersectedNode();

					gameStateUpdated = true;
					animation.createRotator(selectedImage).play();
				});
			}
		}
		anchorPane.getChildren().addAll(pane, gPane, winner, loser, lblWaitingForPlayer, lblStatus, lblWin, lblLose);
		winner.setLayoutX(5);
		winner.setLayoutY(350);
		lblStatus.setLayoutX(30);
		lblStatus.setLayoutY(250);
		lblWin.setLayoutX(30);
		lblWin.setLayoutY(300);
		lblLose.setLayoutX(30);
		lblLose.setLayoutY(300);
		loser.setLayoutX(5);
		loser.setLayoutY(350);
		winner.setVisible(false);
		loser.setVisible(false);
		lblWin.setVisible(false);
		lblLose.setVisible(false);
		return anchorPane;
	}
	/**
	 * Metoda getBoardStateFromServer - słuzy do sciagniecia tablicy kart będacych
	 * elementem gry z serwera przez klienta
	 */
	@SuppressWarnings("unchecked")
	private void getBoardStateFromServer() {
		deckArrayList = new ArrayList<Integer>();
		try {
			ArrayList<Integer> fromServer = (ArrayList<Integer>) netSocket.getInputObject().readObject();
			deckArrayList = (ArrayList<Integer>) fromServer.clone();
			folderName = Data.folderList.get(deckArrayList.get(deckArrayList.size() - 2));
			boardDimension = deckArrayList.get(deckArrayList.size() - 1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metoda sendBoardStateToClient - służy do wysłania  tablicy kart
	 * będacych elementem gry do klienta
	 */
	private void sendBoardStateToClient() {
		try {
			netSocket.getOutputObject().writeObject(deckArrayList);
			netSocket.getOutputObject().flush();
		} catch (IOException e) {
			e.printStackTrace();
			numErrors++;
		}
	}
	/**
	 * Metoda sendUserNameToOponent - słuzy do wyslania nazwy do przeciwnika tak by
	 * bylo wiadomo kto z kim gra
	 */
	private void sendUserNameToOponent() {
		try {
			netSocket.getOutputData().writeUTF(myName);
			netSocket.getOutputData().flush();
		} catch (IOException e) {
			e.printStackTrace();
			numErrors++;
		}
	}
	/**
	 * Metoda getUserNameFromOponent - słuzy do otrzymania nazwy od przeciwnika tak
	 * by bylo wiadomo kto z kim gra
	 */
	private void getUserNameFromOponent() {
		deckArrayList = new ArrayList<Integer>();
		try {
			opName = netSocket.getInputData().readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!gameOver) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameTick();
			if (isServer && !clientConnected) {
				netSocket.listenForServerRequest();
				sendBoardStateToClient();
				clientConnected = true;
				lblWaitingForPlayer.setVisible(false);
				disableBoard(false);
			}
		}
	}

	/**
	 * Obsluga utraty połaczenia z klientem (zamkniecie watku): sprawdza czy wciaz
	 * mamy polaczenie jesli liczba bledow przekroczy 10 nastpeuje utrata polaczenia
	 * Aktualizacja stołu z kartami i wysłanie informacji o stanie stołu do przeciwnika
	 * Aktualizacja etykiet interfejsu użytkownika
	 */
	@SuppressWarnings("deprecation")
	private void gameTick() {
		if (numErrors > 10) {
			lostConnection = true;
			getThread().stop();
		}
		updateBoardStateOnCardSelection();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				lblMyName.setText(myName);
				lblMyMatches.setText(String.valueOf(myMatches));
				lblOpName.setText(opName);
				lblOpMatches.setText(String.valueOf(opMatches));
				if (myTurn) {
					lblStatus.setText("Twoja tura");
				} else {
					lblStatus.setText("Tura przeciwnika");
				}
			}
		});
	}
	/**
	 * Metoda updateBoardStateOnCardSelection - sprawdza czy tury i pola tekstowe sa
	 * zaktualizowane sprawdza czy my albo przeciwnik cos wybralismy i chcemy
	 * zaktualizowac Tablice kart
	 */
	private void updateBoardStateOnCardSelection() {

		/**
		 * Gdy moja tura
		 */
		if (myTurn && gameStateUpdated) {
			gameStateUpdated = false;
			if (opName == null) {
				sendUserNameToOponent();
				getUserNameFromOponent();

			}
			try {
				netSocket.getOutputData().writeUTF(selectedImage.getId());
				netSocket.getOutputData().flush();
			} catch (IOException e) {
				e.printStackTrace();
				numErrors++;
			}
		}
		/**
		 * Gdy Tura przeciwnika
		 */
		if (!myTurn) {
			if (opName == null) {
				sendUserNameToOponent();
				getUserNameFromOponent();
			}
			try {
				String selectedImageIdString = netSocket.getInputData().readUTF();
				int selectedImageIdInt = Integer.parseInt(selectedImageIdString.substring(0, 2)) - 10;
				selectedImage = gPane.getChildren().get(selectedImageIdInt);
				animation.createRotator(selectedImage).play();
			} catch (IOException e) {
				e.printStackTrace();
				numErrors++;
			}
		}
		if (selected1 == null) {
			selected1 = selectedImage;
			selectedImage = null;
		} else {
			selected1.setDisable(true);
			selected2 = selectedImage;
			selectedImage = null;
		}
		/**
		 * jesli wybrano dwie karty sprawdzamy czy sa zgodne
		 */
		if (selected1 != null && selected2 != null) {
			checkForMatch();
		}
	}
	/**
	 * Metoda checkForMatch - sprawdza czy klikniete dwie karty sa zgodne jesli sa
	 * dodaje punkt jesli nie tura przechodzi na przeciwnika
	 */
	private void checkForMatch() {
		disableBoard(true);

		if (selected1.getId().substring(2).equals(selected2.getId().substring(2))) {
			if (myTurn) {
				disableBoard(false);
			}
			selected1.setDisable(true);
			selected1 = null;
			selected2.setDisable(true);
			selected2 = null;

			if (myTurn) {
				myMatches++;
			} else {
				opMatches++;
			}
			checkGameOver();
		} else {
			animation.createRotatorBack(selected1).play();
			selected1.setDisable(false);
			selected1 = null;
			animation.createRotatorBack(selected2).play();
			selected2 = null;

			myTurn = !myTurn;
			disableBoard(!myTurn);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (myTurn) {
						lblStatus.setText("Twoja tura");
					} else {
						lblStatus.setText("Tura przeciwnika");
					}
				}
			});
		}
		selected1 = null;
		selected2 = null;
	}
	/**
	 * Metoda checkGameOver - sprawdza czy nie osiagnieto finalu gry jesli ilosc
	 * punktow zdobytych przez dwoch graczy zgadza sie z iloscia kart bioracych
	 * udzial w grze (niezdublowanych) nastepuje koniec gry: Sprawdzenie kto wygral
	 * poprzez porownanie punktow z przciwnikiem i dodanie rezultatu rozgrywki do
	 * listy rezultatow ktora potem zostanie wyswietlona
	 */
	private void checkGameOver() {
		if ((myMatches + opMatches) != ((boardDimension * boardDimension) / 2)) {
			gameOver = false;
		} else {
			gameOver = true;
			if (myMatches > opMatches) {
				winner.setVisible(true);
				lblStatus.setVisible(true);
				lblWin.setVisible(true);
				
			} else if (myMatches == opMatches) {
				System.out.println("Draw " + myMatches + "Loser: " + opMatches);
			} else {
				loser.setVisible(true);
				lblStatus.setVisible(true);
				lblLose.setVisible(true);
			}
			String date = LocalDateTime.now().toLocalDate().toString();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Data.resultsList.add(new Results(myName, opName, myMatches + ":" + opMatches, date));
					Data.saveResults();
				}
			});
		}
	}
	/**
	 * Metoda disableBoard - odpowiada na wylaczanie tablicy gdy nie jest jeszcze
	 * nasza tura lub drugi gracz nie dolaczyl do gry. Zapobiega oszukiwaniu i
	 * dowolnemu przegladaniu tablicy z kartami gdy nie ma naszej tury lub gdy gra
	 * sie jeszcze nie rozpoczela
	 * 
	 * 
	 */
	private void disableBoard(boolean disableOrNot) {
		gPane.setDisable(disableOrNot);
	}
}