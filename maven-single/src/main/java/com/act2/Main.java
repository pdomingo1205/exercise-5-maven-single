package com.act2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main{
    static General console = new General();

    public static void FileReader(String[] args){
        ArrayList<List<String>> keys = new ArrayList<List<String>>();
        ArrayList<List<String>> values = new ArrayList<List<String>>();
        ArrayList<String> insideKeys = new ArrayList<String>();
        ArrayList<String> insideValues = new ArrayList<String>();
        BufferedReader bReader;
        String[] rowContent;
        String[] rows;
        Table table = new Table();
        String path;

        if (args.length == 0 || !args[0].endsWith(".txt")) {
            console.printLine("Resorting to default path");
            path = "default.txt";
        }
        else {
            path = args[0];
        }

        Boolean isBroken = true;
        try {
            if (!path.isEmpty() &&
                path.length() > 4 &&
                path.endsWith(".txt"))
            {
                String line;
                bReader = new BufferedReader(new FileReader(path));

                while ((line = bReader.readLine()) != null) {
                     if(line == null || line.length()<2){
                         throw new IOException();
                     }

                     isBroken = false;
                     rows = line.split("\t");
                     for (String row : rows) {
                         rowContent = row.split(" ");

                         insideKeys.add(rowContent[0]);
                         insideValues.add(rowContent[1]);
                     }
                     keys.add(insideKeys);
                     values.add(insideValues);
                     insideKeys = new ArrayList<String>();
                     insideValues = new ArrayList<String>();
                }
                table = new Table(keys, values, path);
            }

            if(isBroken){
                throw new IOException();
            }



        } catch (IOException | ArrayIndexOutOfBoundsException IOExcept ) {
            console.printLine("Broken or nonexistent Table \n Creating new File");
            table = new Table(path);
        }

        menu(table);
    }




    public static void menu(Table table){
        final String menuMessage = " 1.Search Table \n 2.Edit Table \n 3.Add Row \n 4.Print Table \n 5.Sort Table \n 6.Reset Table \n 7.Exit";
        final String inputMessage = " Input text to search";
        final String errorMessage = " Input only numbers from 1-7";
        final String orderMessage = " What order \n 1.Ascending \n 2.Descending";
        int answer = 0;
	
        do{
            console.printLine(menuMessage);
            answer = console.getIntegerInput(7);

            switch (answer) {

                case 1:
                    String toSearch = console.getStringInput(inputMessage);
                    table.searchTable(toSearch, "key", table.getKeyArray());
                    table.searchTable(toSearch, "value", table.getValueArray());
                    break;
                case 2:
                    table.editTable();
                    break;
                case 3:
                    table.add();
                    break;
                case 4:
                    table.printTable();
                    break;
                case 5:
                    console.printLine(orderMessage);
                    int input;

                    do {
                        input = console.getIntegerInput(2);
                    } while (input != 1 && input != 2);

                    table.sortTable(input);
                    break;
                case 6:
                    table.resetTable(false);
                    break;
                case 7:
                    break;
                default :
                    console.printLine(errorMessage);
                    break;
            }
        } while(answer != 7);

    }

    public static void main(String[] args) {

        FileReader(args);


    }
}
