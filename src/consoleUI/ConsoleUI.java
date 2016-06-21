package consoleUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.print.attribute.standard.NumberUpSupported;

import Core.Field;

public class ConsoleUI {
	private Field field;
	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private long startMillis;

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public void startNewGame() {
		field.suffle();
		startMillis = System.currentTimeMillis();
	}

	public void startGame() {
		// field = new Field(4,4);

		try {
			this.field = load();
		} catch (ClassNotFoundException e) {
			// exception
		} catch (IOException e) {
			// exception
		}
		if (field == null) {
			field = new Field(4, 4);

		}
		startMillis = System.currentTimeMillis();
		do {

			if (field.isSolved()) {
				System.out.println("solved");
				System.exit(0);
			}

			update();
			handleInput();

		} while (true);
	}

	public void update() {
		System.out.println("Time: " + getPlayingTime());
		for (int i = 0; i < field.getRowCount(); i++) {
			for (int j = 0; j < field.getColumnCount(); j++) {
				int value = field.getValue(i, j);
				if (value != 0) {
					if (value < 10) {
						System.out.print(value + "   ");
					} else {
						System.out.print(value + "  ");
					}
				} else {
					System.out.print("-" + "   ");
				}
			}
			System.out.println();
		}

	}

	public void handleInput() {
		try {
			getInput();
		} catch (WrongFormatException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void getInput() throws WrongFormatException, IOException {
		String input = readLine();
		switch (input) {
		case "w":
			move(1, 0);
			break;
		case "s":
			move(-1, 0);
			break;
		case "a":
			move(0, 1);
			break;
		case "d":
			move(0, -1);
			break;
		case "up":
			move(1, 0);
			break;
		case "down":
			move(-1, 0);
			break;
		case "left":
			move(0, 1);
			break;
		case "right":
			move(0, -1);
			break;
		case "new":
			startNewGame();
			break;
		case "exit":
			save();
			System.exit(0);
			break;
		default:
			throw new WrongFormatException("input is in incorrect format");
		}
	}

	private void move(int dx, int dy) {
		int x1 = field.getXbyValue(0);
		int y1 = field.getYbyValue(0);
		int x2 = x1 + dx;
		int y2 = y1 + dy;

		if (canBeMoved(x1, dx) && canBeMoved(y1, dy)) {
			field.swap(x1, y1, x2, y2);
		}

	}

	private boolean canBeMoved(int x, int dx) {
		if (x + dx < 0) {
			return false;
		}

		else if (x + dx >= field.getRowCount()) {
			return false;
		}

		return true;

	}

	public long getStartMillis() {
		return startMillis;
	}

	public int getPlayingTime() {
		Long time = System.currentTimeMillis() - getStartMillis();
		int intTime = time.intValue();
		return intTime / 1000;
	}

	public void save() throws IOException {

		FileOutputStream out = new FileOutputStream("stones.bin");
		ObjectOutputStream s = new ObjectOutputStream(out);

		s.writeObject(this.field);
		out.close();

	}

	public Field load() throws IOException, ClassNotFoundException {
		File file = new File("stones.bin");
		if (file.exists()) {
			FileInputStream in = new FileInputStream("stones.bin");
			ObjectInputStream s = new ObjectInputStream(in);
			Field loadedField = (Field) s.readObject();
			in.close();
			return loadedField;
		} else {
			return null;
		}
	}
}
