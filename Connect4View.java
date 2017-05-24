/**
 * @author michaelhawes
 * Date: 8 May 2017
 * File: Connect4View.java
 */

package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Connect4View implements ViewObservers {

	
	/**
	 *  GLOBAL PROPERTIES
	 */

	JFrame gameBoard = new JFrame();
	JPanel[][] gridPanel;
	int gameHeight = 6;
	int gameWidth = 7;
	JPanel tokenPanel;
	JPanel dataPanel;
	JPanel buttonPanel;
	JPanel turnPanel;
	JPanel resultPanel;
	JPanel gameGrid;
	
	JTextField turn;
	JTextField result;
	
	boolean hasWon = false;
	
	
	String title = "Connect Four";
	JLabel whosTurn = new JLabel("Who's Turn: ");
	JLabel gameResult = new JLabel("Game Result: ");
	
	
	// Button
	JButton resetGame = new JButton("Reset Game");
	JButton exitGame = new JButton("Exit Game");
	
	JButton button1 = new JButton("Insert 1");
	JButton button2 = new JButton("Insert 2");
	JButton button3 = new JButton("Insert 3");
	JButton button4 = new JButton("Insert 4");
	JButton button5 = new JButton("Insert 5");
	JButton button6 = new JButton("Insert 6");
	JButton button7 = new JButton("Insert 7");
	
	Connect4model model;
	Connect4Controller controller;

	/**
	 * Constructor.
	 */
	public Connect4View() {

		// Set the title bar text
		gameBoard.setTitle(title);

		// Specify an action for the close button
		gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameBoard.setSize(700, 270);
		
		
		resetGame.addActionListener(new ButtonListener());
		exitGame.addActionListener(new ButtonListener());
		button1.addActionListener(new InsertListener());
		button2.addActionListener(new InsertListener());
		button3.addActionListener(new InsertListener());
		button4.addActionListener(new InsertListener());
		button5.addActionListener(new InsertListener());
		button6.addActionListener(new InsertListener());
		button7.addActionListener(new InsertListener());
		
		model = new Connect4model();
		controller = new Connect4Controller();
		
		
		model.registerObserver( (ViewObservers) this);
		
		createGameBoard();
		createDataRow();

		// Display the window
		gameBoard.setVisible(true);

	}
	

	/**
	 * Adds the insert buttons and panels to the gameboard.
	 */
	public void createGameBoard() {
		gameGrid = new JPanel();
		gameGrid.setSize(7, 6);
		gameGrid.setLayout(new GridLayout(gameWidth,gameHeight));
		
		gameGrid.add(button1);
		gameGrid.add(button2);
		gameGrid.add(button3);
		gameGrid.add(button4);
		gameGrid.add(button5);
		gameGrid.add(button6);
		gameGrid.add(button7);
		
		
		gridPanel = new JPanel[gameHeight][gameWidth];
		
		JPanel token;

		for ( int i=0; i<gameHeight; i++ ) {
			for ( int j=0; j<gameWidth; j++ ) {

				gridPanel[i][j] = new JPanel();

			}
		}

		for ( int i=0; i<gameHeight; i++ ) {
			for (int j = 0; j < gameWidth; j++) {
				
				token = gridPanel[i][j];
				token.setBorder(BorderFactory.createLineBorder(Color.black));
				gameGrid.add(token);

			}
		}
		gameBoard.add(gameGrid, BorderLayout.NORTH);
	}


	/**
	 * Shows whos turn and Game result. Also has reset and exit buttons.
	 */
	public void createDataRow() {

		dataPanel = new JPanel();
		turnPanel = new JPanel();
		resultPanel = new JPanel();
		
		turn = new JTextField(8);
		turn.setText("Player 1");
		
		result = new JTextField(8);
		result.setText("Pending...");
		
		turnPanel.add(whosTurn);
		turnPanel.add(turn);
		//turnPanel.add(whosTurnPlayer);

		resultPanel.add(gameResult);
		resultPanel.add(result);
		//resultPanel.add(gameResultPlayer);

		dataPanel.add(turnPanel);
		dataPanel.add(resultPanel);

		/**
		 * Panel that holds the bottom row buttons.
		 */
		buttonPanel = new JPanel();
		buttonPanel.add(resetGame);
		buttonPanel.add(exitGame);
		
		gameBoard.add(dataPanel, BorderLayout.WEST);
		gameBoard.add(buttonPanel, BorderLayout.EAST);

	}

	
	/**
	 * Private inner class that handles the event when
	 * the user clicks one of the radio buttons
	 */
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == resetGame) {
				model.resetGame();
			}
			if (e.getSource() == exitGame) {
				model.exit();
			}
			
			try {
				controller.controls(e.getActionCommand());
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}

		}
		
	}
	
	private class InsertListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				controller.dropToken(e.getActionCommand());
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			
		}
		
	}


	/**
	 * Sets the color of the token in specified row,col.
	 */
	@Override
	public void updateBoard(int row, int column, int player) {
		
		for ( int r=0; r<gameHeight; r++ ) {
			for ( int col=0; col<gameWidth; col++ ) {
				if (player == 1 && r == row && col == column) {
					gridPanel[row][column].setBackground(Color.GREEN);
				}
				else if (player == 2 && r == row && col == column) {
					gridPanel[row][column].setBackground(Color.RED);
				}
			}
		}
		
	}


	/**
	 * Displays whos turn.
	 */
	@Override
	public void displayTurn(int player) {
		
		if ( player == 1) {
			turn.setText("Player " + (player+1));
		}
		turn.setText("Player " + player);
		
	}


	/**
	 * Displays the winner.
	 * @param player
	 */
	@Override
	public void displayWinner() {
		int winner = controller.getWinnerFromModel();
		
		if (controller.checkModelForWin() == true) {
			result.setText("Player " + winner);
			JOptionPane.showMessageDialog(null, "Congratulations Player " + winner + "! " + "You won the game!");
		}
		
	}


	/**
	 * Resets the game board.
	 */
	@Override
	public void resetBoard() {
		
		for ( int row=0; row<gameHeight; row++ ) {
			for ( int col=0; col<gameWidth; col++ ) {
				gridPanel[row][col].setBackground(Color.LIGHT_GRAY);
			}
		}
		resetButtons();
		
	}
	
	
	/**
	 * turn all buttons on after reset of game.
	 */
	public void resetButtons() {
		button1.setEnabled(true);
		button2.setEnabled(true);
		button3.setEnabled(true);
		button4.setEnabled(true);
		button5.setEnabled(true);
		button6.setEnabled(true);
		button7.setEnabled(true);
	}
	
	
	/**
	 * disable buttons after column has max number of tokens.
	 */
	public void disableButton(int col) {
		col = col + 1;
		
		 switch( col ) {
		 case 1:
			 button1.setEnabled(false);
			 break;
		 case 2:
			 button2.setEnabled(false);
			 break;
		 case 3:
			 button3.setEnabled(false);
			 break;
		 case 4:
			 button4.setEnabled(false);
			 break;
		 case 5:
			 button5.setEnabled(false);
			 break;
		 case 6:
			 button6.setEnabled(false);
			 break;
		 case 7:
			 button7.setEnabled(false);
			 break;
			 default:
				break;
		 }
	}
	

	/**
	 * The main method creates an instance of the
	 * BorderPanelWindow class, causing it to display
	 * its window.
	 * @param args
	 */
	public static void main(String[] args) {
		new Connect4View();
	}
	
}
