/**
 * @author michaelhawes
 * Date: 8 May 2017
 * File: Connect4ModelInterface.java
 */

package mvc;

public interface Connect4ModelInterface {
	
	void resetGame();  // reset the game
	void exit(); // exit the game
	boolean checkWin();
	int checkForWinVertical(); 
	int checkForWinHorizontal();
	int checkForWinDiagonal();
	void dropToken(String string) throws Exception;
	void findOpenToken(int row, int column);
	void notifyObserver(int row, int column);
	void changeTurn();  // keeps track of who's turn it is in the game
	int gameResult();
	
}
