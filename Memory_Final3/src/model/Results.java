package model;

/**
 * Klasa Results bedzie wykorzystywana do stworzenia finalnego rezultatu danej
 * rozgrywki, zawiera podstawowe parametry opisujace rezultat danej gry miedzy
 * dwoma graczami
 * 
 * @author Marcin Kowalski
 * @version Final
 *
 */
public class Results {
	private String player1;
	private String player2;
	private String result;
	private String date;

	/**
	 * Kontrutor Results zawiera podstawowe parametry przeprowadzonej rozgrywki
	 * miedzy graczami
	 * 
	 * @param player1
	 *            nazwa gracza nr1
	 * @param player2
	 *            nazwa gracza nr2
	 * @param result
	 *            wynik rozgrywki
	 * @param date
	 *            data rozgrywki
	 */
	public Results(String player1, String player2, String result, String date) {
		this.player1 = player1;
		this.player2 = player2;
		this.result = result;
		this.date = date;

	}

	/**
	 * Metoda Get zwracajaca nazwe gracza nr1
	 * 
	 * @return nazwa gracza1
	 */
	public String getPlayer1() {
		return player1;
	}

	/**
	 * Metoda Get zwracajaca nazwe gracza nr2
	 * 
	 * @return nazwa gracza2
	 */
	public String getPlayer2() {
		return player2;
	}

	/**
	 * Metoda Get zwracajaca wynik rozgrywki
	 * 
	 * @return wynik rozgrywki
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Metoda Get zwracajaca datę rozgrywki
	 * 
	 * @return datę przeprowadzonej gry
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Metoda Set ustawiająca nazwe gracza nr1
	 * 
	 * @param player1
	 *            nazwa gracza nr1
	 */
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	/**
	 * Metoda Set ustawiająca nazwe gracza nr2
	 * 
	 * @param player2
	 *            nazwa gracza2
	 */
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	/**
	 * Metoda Set ustawiająca wyniki rozgrywki
	 * 
	 * @param result
	 *            wynik gry
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Metoda ustawiająca datę rozgrywki
	 * 
	 * @param date
	 *            data gry
	 */
	public void setDate(String date) {
		this.date = date;
	}

}