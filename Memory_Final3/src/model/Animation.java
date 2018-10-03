package model;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Klasa Animation - odpowiada za utworzenie animacji rotacji odkrywanej i
 * zakrywanej karty gry
 * 
 * @author Marcin Kowalski
 * @version Final
 *
 */
public class Animation {
	/**
	 * @param cardRotationDelay
	 *            - opoznienie w milisekundach juz odkrytej karty przed ponownym jej
	 *            zakryciem
	 * @param cardRotationDuration
	 *            - dlugosc trwania w milisekundach obrotu karty
	 * 
	 */
	private final int cardRotationDelay = 1500;
	private final int cardRotationDuration = 1000;

	/**
	 * Metoda createRotator - tworzy animacje rotacji odkrycia karty, zaczyna od
	 * wartosci FromaAngle 0 do 180 wykonujac pol obrotu wokol osi
	 * 
	 * @return zwraca animacje rotacji
	 */
	public RotateTransition createRotator(Node mw) {
		RotateTransition rotator = new RotateTransition(Duration.millis(cardRotationDuration), mw);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(180);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);
		rotator.play();

		return rotator;
	}

	/**
	 * Metoda createRotatorBack - tworzy animacje rotacji zakrycia odkrytej karty
	 * Zaczyna od wartości FromaAngle 180 do 360 wykonujac pol obrotu wokol osi
	 * 
	 * @return zwraca animacje rotacji
	 */

	public RotateTransition createRotatorBack(Node mw) {
		RotateTransition rotator = new RotateTransition(Duration.millis(cardRotationDuration), mw);
		rotator.setDelay(Duration.millis(cardRotationDelay));
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(180);
		rotator.setToAngle(360);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);

		return rotator;
	}
}