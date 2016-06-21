package Core;

import java.io.Serializable;
import java.util.Random;

public class Field implements Serializable {

	private int[][] field;
	private int rowCount;
	private int columnCount;
	private int stoneCount = rowCount * columnCount - 1;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		field = new int[rowCount][columnCount];

		generateRandomField();

	}

	private void generateSortedField() {
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				if (i == rowCount - 1 && j == columnCount - 1) {
					field[i][j] = 0;
				} else {
					field[i][j] = (4 * i) + j + 1;
				}
			}
		}
	}

	public void suffle() {
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				field[i][j] = 0;
			}
		}

		this.generateRandomField();

	}

	private void generateRandomField() {
		Random a = new Random();

		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				int value = a.nextInt(rowCount * columnCount + 1);
				boolean isGenerated = false;
				while (!(isGenerated)) {
					if (!(isValueInTheField(value)) && value != 0) {
						field[i][j] = value;
						isGenerated = true;
					} else {
						value = a.nextInt(rowCount * columnCount + 1);
					}
				}
			}
		}
		field[getXbyValue(rowCount * columnCount)][getYbyValue(rowCount * columnCount)] = 0;

	}

	private boolean isValueInTheField(int value) {
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				if (field[i][j] != 0 && value == field[i][j]) {
					return true;
				}
			}
		}

		return false;

	}

	public boolean isSolved() {
		if (field[rowCount - 1][columnCount - 1] != 0) {
			return false;
		}
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {

				if (field[i][j] != 0 && field[i][j] != (4 * i) + j + 1) {

					return false;
				}
			}

		}

		return true;

	}

	public void swap(int x1, int y1, int x2, int y2) {
		int temp;
		temp = field[x1][y1];
		field[x1][y1] = field[x2][y2];
		field[x2][y2] = temp;

	}

	public int getXbyValue(int value) {
		int x = 0;
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				if (field[i][j] == value) {
					x = i;
				}
			}
		}
		return x;

	}

	public int getYbyValue(int value) {
		int y = 0;
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				if (field[i][j] == value) {
					y = j;
				}
			}
		}
		return y;

	}

	public int getValue(int x, int y) {
		return field[x][y];

	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getStoneCount() {
		return stoneCount;
	}

	public void setStoneCount(int stoneCount) {
		this.stoneCount = stoneCount;
	}

}
