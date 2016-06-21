package Core;

import consoleUI.ConsoleUI;

public class Main {
	private ConsoleUI consoleUI;

	public Main() {
		consoleUI = new ConsoleUI();
		consoleUI.startGame();

	}

	public static void main(String[] args) {
		new Main();

	}

}
