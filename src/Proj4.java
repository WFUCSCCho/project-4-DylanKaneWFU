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
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

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

        double[] runningTimes = new double[3];
        ArrayList<ArrayList<DataObj>> sortLists = new ArrayList<>();
        String[] listTitles = {"Sorted", "Shuffled", "Reversed"};

        //TODO: time the functions


        //Write to output file
        File file = new File("analysis.txt");
        boolean needsHeader = !file.exists();

        FileOutputStream analysisFile = new FileOutputStream("analysis.txt", true);
        PrintWriter analysisWriter = new PrintWriter(analysisFile);

        //write results to analysis.txt file in csv format

        //check if file is empty, if so add a header
        if (needsHeader) analysisWriter.println(
                "Lines Read," +
                        "Sorted Time," +
                        "Shuffled Time," +
                        "Reversed Time");

        analysisWriter.printf("%d,%.9f,%.9f,%d,%d,%d\n",
                numLines,
                runningTimes[0],
                runningTimes[1],
                runningTimes[2],
        );

    }
}
