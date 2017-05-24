/**
 * @author michaelhawes
 * Date: 8 May 2017
 * File: Connect4Controller.java
 */
package mvc;

public class Connect4Controller {
	
	Connect4model model;
	
	public Connect4Controller() {
		model = new Connect4model();
	}

	
	public void dropToken(String string) throws Exception {
		model.dropToken(string);
	}
	
	public void controls(String string) throws Exception {
		if (string.equals("Exit Game")) {
			model.exit();
		}
		if (string.equals("Reset Game")) {
			model.resetGame();
		}
	}
	
	public boolean checkModelForWin() { 
		if (model.checkWin() == true) {
			return true;
		}
		return false;
	}
	
	public int getWinnerFromModel() {
		return model.gameResult();
	}

}
