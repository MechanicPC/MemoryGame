package model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Klasa TestResults testująca klasę Results -zgodnie z wymogiem projektu testy
 * powinny w 100% klasę testowaną
 * 
 * @author Marcin Kowalski
 * @version Final
 *
 */
public class TestResults {
	/**
	 * TEST createResultsAndReturnPlayer1 - test ten tworzy obiekt typu Results i
	 * sprawdza czy zwracana nazwa gracza nr1 jest prawidłowa
	 */
	@Test
	public void createResultsAndReturnPlayer1() {
		/**
		 * Ustalenie Parametrów na potrzeby Testu
		 */
		String player1 = "Pawel";
		String player2 = "Marcin";
		String result = "15:4";
		String date = "2018-06-19";
		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results(player1, player2, result, date);
		/**
		 * Sprawdzenie metody getPlayer1
		 */
		Assert.assertTrue(results.getPlayer1().equals("Pawel"));

	}

	/**
	 * TEST createResultsAndReturnPlayer2 - test ten tworzy obiekt typu Results i
	 * sprawdza czy zwracana nazwa gracza nr2 jest prawidłowa
	 */
	@Test
	public void createResultsAndReturnPlayer2() {
		/**
		 * Ustalenie Parametrów na potrzeby Testu
		 */
		String player1 = "Pawel";
		String player2 = "Marcin";
		String result = "15:4";
		String date = "2018-06-19";
		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results(player1, player2, result, date);
		/**
		 * Sprawdzenie metody getPlayer2
		 */
		Assert.assertTrue(results.getPlayer2().equals("Marcin"));

	}

	/**
	 * TEST createResultsAndReturnResult - test ten tworzy obiekt typu Results i
	 * sprawdza czy zwracany wynik gry jest prawidłowy
	 */
	@Test
	public void createResultsAndReturnResult() {
		/**
		 * Ustalenie Parametrów na potrzeby Testu
		 */
		String player1 = "Pawel";
		String player2 = "Marcin";
		String result = "15:4";
		String date = "2018-06-19";
		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results(player1, player2, result, date);
		/**
		 * Sprawdzenie metody getResult
		 */
		Assert.assertTrue(results.getResult().equals("15:4"));

	}

	/**
	 * TEST createResultsAndReturnDate - test ten tworzy obiekt typu Results i
	 * sprawdza czy zwracana data odbytej gry jest prawidłowa
	 */
	@Test
	public void createResultsAndReturnDate() {
		/**
		 * Ustalenie Parametrów na potrzeby Testu
		 */
		String player1 = "Pawel";
		String player2 = "Marcin";
		String result = "15:4";
		String date = "2018-06-19";
		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results(player1, player2, result, date);
		/**
		 * Sprawdzenie metody getDate
		 */
		Assert.assertTrue(results.getDate().equals("2018-06-19"));

	}

	/**
	 * TEST TestingSettingPlayer1 - test ten tworzy obiekt typu Results i sprawdza
	 * czy metoda ustawienia nowej nazwy gracza nr1 działa
	 */
	@Test
	public void TestingSettingPlayer1() {
		/**
		 * Ustalenie Parametru na potrzeby Testu
		 */
		String player1 = "Zbigniew";

		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results("Marcin", "Pawel", "15:4", "2018-06-19");
		results.setPlayer1(player1);
		/**
		 * Sprawdzenie wyniku zmiany parametru
		 */
		Assert.assertTrue(results.getPlayer1().equals("Zbigniew"));
	}

	@Test
	public void TestingSettingPlayer2() {
		/**
		 * Ustalenie Parametru na potrzeby Testu
		 */
		String player2 = "Franciszek";

		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results("Marcin", "Pawel", "15:4", "2018-06-19");
		results.setPlayer2(player2);
		/**
		 * Sprawdzenie wyniku zmiany parametru
		 */
		Assert.assertTrue(results.getPlayer2().equals("Franciszek"));
	}

	@Test
	public void TestingSettingResult() {
		/**
		 * Ustalenie Parametru na potrzeby Testu
		 */
		String result = "10:10";

		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results("Marcin", "Pawel", "15:4", "2018-06-19");
		results.setResult(result);
		/**
		 * Sprawdzenie wyniku zmiany parametru
		 */
		Assert.assertTrue(results.getResult().equals("10:10"));
	}

	@Test
	public void TestingSettingDate() {
		/**
		 * Ustalenie Parametu na potrzeby Testu
		 */
		String date = "2018-06-30";

		/**
		 * Stworzenie Obiektu typu Results
		 */
		Results results = new Results("Marcin", "Pawel", "15:4", "2018-06-19");
		results.setDate(date);
		/**
		 * Sprawdzenie wyniku zmiany parametru
		 */
		Assert.assertTrue(results.getDate().equals("2018-06-30"));
	}

}
