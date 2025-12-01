import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {

    public static <T extends Comparable>  ArrayList<T> chooseArray(ArrayList<T> a, int i) {
        ArrayList<T> arrayData = new ArrayList<>(a);
        if (i == 0) Collections.sort(arrayData);
        if (i == 1) Collections.shuffle(arrayData);
        if (i == 2) Collections.reverse(arrayData);
        return arrayData;
    }

    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java Proj4 <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);
        System.out.print("");

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        //Read dataset into an array file
        ArrayList<DataObj> arrayData = new ArrayList<>();

        for (int i = 0; i < numLines; i++) {
            String obj = inputFileNameScanner.nextLine();
            String[] objInfo;

            objInfo = obj.split(",");
            DataObj currData = new DataObj(objInfo[0],
                    Double.parseDouble(objInfo[1]),
                    Integer.parseInt(objInfo[2]),
                    Integer.parseInt(objInfo[3]),
                    objInfo[4]
            );

            arrayData.add(currData);

        }

        //Write to output file
        File file = new File("analysis.txt");
        boolean needsHeader = !file.exists();

        FileOutputStream analysisFile = new FileOutputStream("analysis.txt", true);
        PrintWriter analysisWriter = new PrintWriter(analysisFile);

        ArrayList<DataObj> currentArray;

        long startTimer;
        long endTimer;

        /*
        LISTS OF EACH SORTING TIME
            0. Shuffled
            1. Sorted
            2. Reversed
        */

        /*
        NESTED LISTS OF EACH OPERATION
            0. Insert
            1. Search
            2. Delete
        */


        double[][] operateTimes = new double[3][3];

        //write results to analysis.txt file in csv format
        for (int i = 0; i < 3; i++) {
            SeparateChainingHashTable<DataObj> h = new SeparateChainingHashTable<>();
            currentArray = chooseArray(arrayData, i);

            for (int j = 0; j < 3; j++) {

                startTimer = System.nanoTime();

                if (j == 0) {
                    //time insert
                    for (DataObj dataObj : currentArray) {
                        h.insert(dataObj);
                    }
                }
                else if (j == 1) {
                    //time search
                    for (DataObj dataObj : currentArray) {
                        boolean searchRes = h.contains(dataObj);
                    }
                }
                else {
                    //time delete
                    for (DataObj dataObj : currentArray) {
                        h.remove(dataObj);
                    }
                }

                endTimer = System.nanoTime();
                operateTimes[i][j] = (endTimer - startTimer) / 1_000_000_000.0;
            }


        }

        //check if file is empty, if so add a header
        if (needsHeader) analysisWriter.println(
                "Lines Read," +
                        "Sorted Time (Insert)," +
                        "Sorted Time (Search)," +
                        "Sorted Time (Delete)," +
                        "Shuffled Time (Insert)," +
                        "Shuffled Time (Insert)," +
                        "Shuffled Time (Delete)," +
                        "Reversed Time (Insert)," +
                        "Reversed Time (Search)," +
                        "Reversed Time (Delete)");

        analysisWriter.print(numLines + ",");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                analysisWriter.printf("%.9f",
                        operateTimes[i][j]
                );
                if (j != 2) analysisWriter.print(",");
            }
            if (i != 2) analysisWriter.print(",");
        }

        analysisWriter.println();
        analysisWriter.flush();
        analysisWriter.close();

    }
}
