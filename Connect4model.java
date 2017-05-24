/**
 * @author michaelhawes
 * Date: 8 May 2017
 * File: Connect4model.java
 */

package mvc;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class Connect4model implements Connect4ModelInterface {
	

	private int[][] matrix = new int[6][7]; // holds all game data
	static ArrayList<ViewObservers> observerList = new ArrayList<ViewObservers>(); // holds all observers
	
	int row1 = 5;
	int row2 = 5;
	int row3 = 5;
	int row4 = 5;
	int row5 = 5;
	int row6 = 5;
	int row7 = 5;
	
	int player = 1;
	
	int gameHeight = 6;
	int gameWidth = 7;
	JPanel[][] board = new JPanel[gameHeight][gameWidth];  // mimic the game board
	
	
	@Override
	public void resetGame() {
	
		for (int[] i: matrix) 
			for(int j=0; j<i.length;j++) 
				i[j] = 0;
		
				this.resetGameObserver();
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public void changeTurn() {
		
		if (player == 2) {
			player = 1;
		}
		else {
			player = 2;
		}
	}

	@Override
	public int gameResult() {
		if (checkWin()) {
			return player;
		}
		return 0;
	}

	@Override
	public boolean checkWin() {
		
		int vertical = checkForWinVertical();
		int horizontal = checkForWinHorizontal();
		int diagonal = checkForWinDiagonal();
		
		System.out.println(horizontal);
		
		if (vertical > 0) {
			return true;
		}
		if (horizontal > 0) {
			return true;
		}
		if (diagonal > 0) {
			return true;
		}
		return false;
		
	}

	@Override
	public int checkForWinVertical() {
		
		for ( int row=0; row < 3; row++ ) {
			for ( int col=0; col<7; col++ ) {
				
				if ( matrix[row][col] != 0 &&
						matrix[row][col] == matrix[row+1][col] &&
						matrix[row][col] == matrix[row+2][col] &&
						matrix[row][col] == matrix[row+3][col] ) {
					
					return matrix[row][col];
					
				} //end if
			}
		}
		return 0;
	}
	
	@Override
	public int checkForWinHorizontal() {
		
		for ( int row=0; row < 6; row++ ) {
			for ( int col=0; col<4; col++ ) {
				
				if ( matrix[row][col] != 0 &&
						matrix[row][col] == matrix[row][col+1] &&
						matrix[row][col] == matrix[row][col+2] &&
						matrix[row][col] == matrix[row][col+3] ) {
					
					return matrix[row][col];
					
				} //end if
			}
		}
		return 0;
	}

	@Override
	public int checkForWinDiagonal() {
		
		for ( int row=0; row < 3; row++ ) {
			for ( int col=0; col<4; col++ ) {
				if ( matrix[row][col] != 0 &&
						matrix[row][col] == matrix[row+1][col+1] &&
						matrix[row][col] == matrix[row+2][col+2] &&
						matrix[row][col] == matrix[row+3][col+3] ) {
					
					return matrix[row][col];
					
				} //end if
			}
		}
	
		for ( int row=3; row < 6; row++ ) {
			for ( int col=0; col<4; col++ ) {
				if ( matrix[row][col] != 0 &&
						matrix[row][col] == matrix[row-1][col+1] &&
						matrix[row][col] == matrix[row-2][col+2] &&
						matrix[row][col] == matrix[row-3][col+3] ) {
					
					return matrix[row][col];
					
				} //end if
			}
		}
		return 0;
	}

	@Override
	public void dropToken(String string) throws Exception {
			
		
		// 
		switch(string) {
			case "Insert 1":
				int column1 = 0;
				this.findOpenToken(row1, column1);
				
				this.changeTurn();
				break;
			case "Insert 2":
				int column2 = 1;
				this.findOpenToken(row2, column2);
				
				this.changeTurn();
				break;
			case "Insert 3":
				int column3 = 2;
				this.findOpenToken(row3, column3);
			
				this.changeTurn();
				break;
			case "Insert 4":
				int column4 = 3;
				this.findOpenToken(row4, column4);
				
				this.changeTurn();
				break;
			case "Insert 5":
				int column5 = 4;
				this.findOpenToken(row5, column5);
				
				this.changeTurn();
				break;
			case "Insert 6":
				int column6 = 5;
				this.findOpenToken(row6, column6);
				
				this.changeTurn();
				break;
			case "Insert 7":
				int column7 = 6;
				this.findOpenToken(row7, column7);
				
				this.changeTurn();
				break;
			default:
				throw new Exception("Not Implemented");
		}
		
	}
	
	
	public void registerObserver(ViewObservers action) {
		observerList.add(action);
	}

	@Override
	public void notifyObserver(int row, int column) {
		
		for (ViewObservers o : observerList) {
			o.updateBoard(row, column, player);
			o.displayTurn(player);
			o.displayWinner();
		}
	
	}

	@Override
	public void findOpenToken( int row , int column) {
		
		boolean done = false;
		
			while (!done) {
				
				if ( matrix[row][column] == 0 && row > -1) {
					
					matrix[row][column] = player;
					notifyObserver(row, column);
					done = true;
					if (row < 1) {
						disableButtons(column);
					}
				}
				else {
					row--;
				}
			}
			System.out.println(Arrays.deepToString(matrix));
		
	}
	
	public void disableButtons(int col) {

		for (ViewObservers o: observerList) {
			o.disableButton(col);
		}
	}
	
	public void resetGameObserver() {
		for (ViewObservers o: observerList) {
			o.resetBoard();;
		}
	}
} // end class
