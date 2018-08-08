package com.act2;

import java.util.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Table extends General{

	private ArrayList<List<String>> keys;
	private ArrayList<List<String>> values;
	private String fileName;
	private static final String orZero = " or Zero";

	public Table(){

	}

	public Table(String path){
		fileName = path;
		resetTable(true);
	}

	public Table(ArrayList<List<String>> keyList, ArrayList<List<String>> valueList, String argFile){
		keys = new ArrayList<List<String>>(keyList);
		values = new ArrayList<List<String>>(valueList);
		fileName = argFile;
	}

	public String getKeyAt(int row, int column){
		return keys.get(row).get(column);
	}

	public String getValueAt(int row, int column){
		return values.get(row).get(column);
	}

	public ArrayList<List<String>> getKeyArray(){
		return keys;
	}

	public ArrayList<List<String>> getValueArray(){
		return values;
	}

	private void cellGenerator(int rowSize, int colSize){
		String[] keyList = new String[rowSize];
		String[] valueList = new String[rowSize];

		for (int column = 0; column < colSize; column++) {

			for (int row = 0; row < rowSize; row++) {

				do {

					keyList[row] = randomText();

				} while (checkUniqueInList(keyList[row], keyList, row) == false);

				valueList[row] = randomText();

			}

			keys.add(Arrays.asList(keyList));
			values.add(Arrays.asList(valueList));
			keyList = new String[rowSize];
			valueList = new String[rowSize];

		}
	}

	private void cellGenerator(int colSize){
		String[] keyList;
		String[] valueList;
		int rowSize = randomNumber();

		for (int column = 0; column < colSize; column++) {
			rowSize = randomNumber();
			keyList = new String[rowSize];
			valueList = new String[rowSize];

			for (int row = 0; row < rowSize; row++) {

				do {
					keyList[row] = randomText();
				} while (checkUniqueInList(keyList[row], keyList, row) == false);

				valueList[row] = randomText();

			}

			keys.add(Arrays.asList(keyList));
			values.add(Arrays.asList(valueList));

		}

	}

	private String randomText(){
		return RandomStringUtils.randomAscii(3, 5);
	}

	private int randomNumber(){
		return RandomUtils.nextInt(2, 5);
	}

	private void createDefinedTable() throws NumberFormatException{
		int rowSize;
		int colSize;

		keys = new ArrayList<List<String>>();
        values = new ArrayList<List<String>>();
		rowSize = getIntegerInput("Input size of Row", orZero, 1);
		colSize = getIntegerInput("Input size of Column", orZero, 1);

		cellGenerator(rowSize, colSize);
	}

	private void createRandomTable(int column) throws NumberFormatException{
		int colSize;

		keys = new ArrayList<List<String>>();
		values = new ArrayList<List<String>>();

		cellGenerator(column);
	}

	public void resetTable(boolean isRandom){
		keys = null;
		values = null;

		if (isRandom) {
			createRandomTable(randomNumber());
		} else {
			createDefinedTable();
		}

		printAndUpdate();
	}

	private void printAndUpdate(){
		printTable();
		updateFile();
	}

	public void printTable(){
		Iterator<List<String>> iterKey = keys.iterator();
		Iterator<List<String>> iterVal = values.iterator();
		printLine("");

		while (iterKey.hasNext()) {
			List<String> keyList = iterKey.next();
			List<String> valList = iterVal.next();
			Iterator<String> iterKeyList = keyList.iterator();
			Iterator<String> itrValList = valList.iterator();

			while (iterKeyList.hasNext()) {
				printText(String.valueOf(
				(new StringBuilder())
				.append("\t|(")
				.append(iterKeyList.next())
				.append(",")
				.append(itrValList.next())
				.append(")")));
				printText("");
			}
			printText("|");
			printLine("");
		}

	}

	public void searchTable(String searchedText, String keyOrVal, ArrayList<List<String>> arrayList){
		int found = 0;
		int column = 0;
		boolean hasEverFound = false;

		for(List<String> listCol : arrayList){
			int row = 0;

			for(String listRow :listCol){
				int lastIndex = 0;
				int tempFound = 0;
				found = 0;

				while(lastIndex != -1) {
					lastIndex = listRow.indexOf(searchedText,lastIndex);

					if(lastIndex != -1){
						lastIndex += 1;
						tempFound++;
					}

				}

				found += tempFound;

				if(found > 0){
					hasEverFound = true;

					if(keyOrVal.equals("key")){

						printLine(String.valueOf(
						(new StringBuilder())
						.append(toString(found))
						.append(" occurences at key (")
						.append(toString(row))
						.append(",")
						.append(toString((column)))
						.append(")")));

					}
					else{
						printLine(String.valueOf(
						(new StringBuilder())
						.append(toString(found))
						.append(" occurences at value (")
						.append(toString(row))
						.append(",")
						.append(toString((column)))
						.append(")")));
					}
				}
				row++;
			}
			column++;
		}
		String message;

		message = (hasEverFound == true ? " " : "No occurences in " + keyOrVal);
		printLine(message);
	}

	public void editTable(){
		int row;
		int col;
		int answer;

			do {
				row = getIntegerInput("Input row to edit", "", 0);
				col = getIntegerInput("Input column to edit", "", 0);
			} while (checkIfCoordinateExists(row, col) == false);

			printLine("Choose what to edit");
			printLine("1.Key 2.Value 3.Both");
			answer = getIntegerInput(3);

			switch(answer){

				case 1:
				case 3:
					String key = "";

					do {
						printLine("Input new key for [" + row + "],[" + col + "]");
						key = getStringInput();
					} while (checkUnique(key, false) == false);

					keys.get(col).set(row, key);

					if (answer != 3) {
						break;
					}
				case 2 :
					printLine("Input new value for [" + row + "],[" + col + "]");
					values.get(col).set(row , getStringInput());
					break;
				default :
					printLine("This should not appear");
					break;

			}
			printAndUpdate();

	}

	public void sortTable(int order){
		Comparator<String> compare = new Comparator<String>(){
			@Override
				public int compare(String o1, String o2) {
					int temp = o1.length() - o2.length();
					o1 = o1.replaceAll(" ", "");
					o2 = o2.replaceAll(" ", "");
					int compareTo = o1.compareTo(o2);
					if(compareTo == 0){
						return temp;
					}
					else{
						return compareTo;
					}
				}
			};
		ArrayList<List<String>> tempTable = new ArrayList<List<String>>();
		ArrayList<String> toSort;
		ArrayList<String> toSortNoDelimiter;

		for (int col = 0; col < keys.size(); col++) {
			toSort = new ArrayList<String>();

			for (int row = 0; row < keys.get(col).size(); row++) {
				toSort.add(String.join(" ", keys.get(col).get(row), values.get(col).get(row)));
			}
			Collections.sort(toSort, compare);

			if (order == 2) {
				Collections.reverse(toSort);
			}

			tempTable.add(toSort);

		}

		String[] splitted;
		ArrayList<String> keyList;
		ArrayList<String> valueList;

		keys = new ArrayList<List<String>>();
		values = new ArrayList<List<String>>();

		for (List<String> row : tempTable) {

			keyList = new ArrayList<String>();
			valueList = new ArrayList<String>();

			for (String combined : row) {

				splitted=combined.split(" ");
				keyList.add(splitted[0]);
				valueList.add(splitted[1]);

			}

			keys.add(keyList);
			values.add(valueList);

		}

		printAndUpdate();

	}

	public void add(){
		int addAmount;
		String[] keyList;
		String[] valueList;

		addAmount = getIntegerInput("\nInput how many values to add", orZero, 1);
		keyList = new String[addAmount];
		valueList = new String[addAmount];

		for (int pointer = 0; pointer < addAmount; pointer++) {
			do {
				keyList[pointer] = getStringInput("\nInput key");
			}while (checkUniqueInList(keyList[pointer], keyList, pointer) == false);

			valueList[pointer] = getStringInput("\nInput value");
		}

		keys.add(Arrays.asList(keyList));
		values.add(Arrays.asList(valueList));
		printAndUpdate();

	}

	public void setFileName(String newName){
		this.fileName = newName;
	}

	private void updateFile()  {
		try{
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			for (int i = 0; i < keys.size(); i++){

				for (int j = 0; j < keys.get(i).size(); j++)
				{
					printWriter.print(getKeyAt(i,j));
					printWriter.print(" ");
					printWriter.print(getValueAt(i,j));
					printWriter.print("\t");
				}
				printWriter.println();

			}
			printWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			printLine(e.getMessage());
		}

	}

	private boolean checkIfCoordinateExists(int row, int column){
		boolean coordExists = false;
		String message = "";

		for (int i = 0; i <= column; i++) {

			if (column < keys.size()) {

				if (row < keys.get(i).size()) {
					coordExists = true;
				}
				else {
					coordExists = false;
				}

			}

		}
		message = (coordExists == true ? " " : "Invalid Coordinates");
		printLine(message);
		return coordExists;
	}

	private boolean checkUnique(String checkKey, boolean ignoreMessage){
		String message;
		String hasDupli = "Key has duplicates\n";
		boolean isUnique = true;

		for (int i = 0; i < keys.size(); i++) {

			for (int j = 0; j < keys.get(i).size(); j++) {

				if (checkKey.equals(keys.get(i).get(j))) {
					isUnique = false;
				}

			}

		}
		message = (isUnique ? " " : hasDupli);

		message = (ignoreMessage ? " " : message);
		printText(message);

		return isUnique;
	}

	private boolean checkUniqueInList(String checkKey, String[] stringList, int pointer){
		boolean isUnique = true;
		String message;

		isUnique = checkUnique(checkKey, true);

		for (int i = 0; i < pointer; i++) {

			if (checkKey.equals(stringList[i])) {
				isUnique = false;
			}
		}
		message = (isUnique ? " " : "Key has duplicates");
		printText(message);
		return isUnique;
	}


}
