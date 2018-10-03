package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa Data - odpowiada za przechowywanie i wyswietlenie w przejrzystej tabeli
 * rezultatow rozgrywek
 * 
 * @author Marcin Kowalski
 * @version Final
 *
 */
public class Data {

	public static String ip;
	public static String name;
	public static int port;
	public static ArrayList<String> folderList = new ArrayList<String>(
			Arrays.asList("Airplanes", "Animals", "Flowers"));
	public static ObservableList<Results> resultsList = FXCollections.observableArrayList();
	public static String location;
	public static String usr = System.getenv("USERNAME");

	/**
	 * Metoda loadData - metoda ladująca dane z pliku txt za pomoca skanera
	 * odczytujacego stringi z tego pliku i uzyskane dane wprowadzajaca do listy
	 * rezultatow - resultsList
	 */

	public static void loadData() {
		String player1;
		String player2;
		String result;
		String date;
		try {
			if (!Paths.get("C:/Users/" + usr + "/Memory").toFile().isDirectory()) {
				File dir = new File("C:/Users/" + usr + "/Memory");
				dir.mkdir();
			} else if (Paths.get("C:/Users/" + usr + "/Memory/Wyniki.txt").toFile().isFile()) {
				File plik = new File("C:/Users/" + usr + "/Memory/Wyniki.txt");
				Scanner in = new Scanner(plik, "UTF-8");
				while (in.hasNext()) {

					player1 = in.next();
					player2 = in.next();
					result = in.next();
					date = in.next();

					resultsList.add(new Results(player1, player2, result, date));
				}
				in.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda loadData - metoda zapisujaca dane rezultatow z listy -resultsList do
	 * pliku txt za pomoca PrintWriter
	 */
	public static void saveResults() {
		try {
			File plik = new File("C:/Users/" + usr + "/Memory/Wyniki.txt");
			PrintWriter out = new PrintWriter(plik);
			for (int i = 0; i < resultsList.size(); i++) {
				out.printf("%s %s %s %s\n", resultsList.get(i).getPlayer1(), resultsList.get(i).getPlayer2(),
						resultsList.get(i).getResult(), resultsList.get(i).getDate());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}