package com.act2;

import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class General{

	Scanner scan = new Scanner(System.in);

	public String getStringInput(){
		String textToReturn;
		boolean hasSpace = false;
		boolean hasTab = false;
		boolean isEmpty = false;

		do {
			textToReturn = scan.nextLine();
			hasSpace = textToReturn.contains(" ");
			hasTab = textToReturn.contains("\t");
			isEmpty = textToReturn.isEmpty();

			if (hasSpace || hasTab) {
				printLine("Tabs and spaces are not accepted");
			}
			if (isEmpty) {
				printLine(" No value entered");
			}

		} while (hasSpace || hasTab || isEmpty);

		return textToReturn;
	}

	public String getStringInput(String message){
		String textToReturn;
		boolean hasSpace = false;
		boolean hasTab = false;
		boolean isEmpty = false;

		printLine(message);

		do{
			textToReturn = scan.nextLine();
			hasSpace = textToReturn.contains(" ");
			hasTab = textToReturn.contains("\t");
			isEmpty = textToReturn.isEmpty();

			if (hasSpace || hasTab) {
				printLine("Tabs and spaces are not accepted");
			}
			if (isEmpty) {
				printLine(" No value entered");
			}

		} while (hasSpace || isEmpty || hasTab);

		return textToReturn;
	}

	public int getIntegerInput(String message, String orZero, int min) throws NumberFormatException{
        boolean valid = true;
        int input = 0;
        do {
            printLine(message);

			try {
				input = Integer.valueOf(scan.nextLine());
				valid = true;
			} catch (NumberFormatException e){
				printLine("Invalid Input");
				valid = false;
			}

            if (input < min) {
                valid = false;
                printLine("Input cannot be negative value" + orZero);
            }

        } while (!valid);
        return input;
    }

	public int getIntegerInput(String orZero, int min, String message){
		return 0;
	}

	public int getIntegerInput(int maximum) throws NumberFormatException{
        boolean valid = true;
        int input = 0;

        do {
			try {
				input = Integer.valueOf(scan.nextLine());
	            valid = true;
			} catch (NumberFormatException e){
				printLine("Invalid Input");
				valid = false;
			}

            if(input <= 0 || input > maximum) {
                valid = false;
                printLine("Only accepts values 1-" + String.valueOf(maximum));
            }

        } while (!valid);

        return input;
    }

	public void printLine(String message){
		System.out.println(String.valueOf(message));
	}

	public void printLine(int message){
		System.out.println(String.valueOf(message));
	}

	public void printText(String message){
		System.out.print(String.valueOf(message));
	}

	public void printText(int message){
		System.out.print(String.valueOf(message));
	}

	public int toInt(String input){
		return Integer.valueOf(input);
	}

	public String toString(int number){
		return String.valueOf(number);
	}


}
