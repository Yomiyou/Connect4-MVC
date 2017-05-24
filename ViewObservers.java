/**
 * @author michaelhawes
 * Date: 8 May 2017
 * File: Connect4ViewObserver.java
 */

package mvc;

public interface ViewObservers {
	void updateBoard(int row, int column, int player);
	void displayTurn(int player);
	void displayWinner();
	void resetBoard();
	void disableButton(int col);
}
