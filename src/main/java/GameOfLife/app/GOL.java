package GameOfLife.app;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GOL 
{
    public static void main( String[] args )
    {
        int rows, cols, startX, startY;
        List<String> fileContents = new ArrayList<String>();
        List<String> datalist = new ArrayList<String>();
        File inputData;
        String dataPath = "C:\\Users\\Brendon\\JavaProjects\\GameOfLife\\src\\main\\java\\GameOfLife\\app\\data.txt";

        Console cons = System.console();
        String playerInput = cons.readLine("Input path to data.txt: \n");
        if((inputData = new File(playerInput)).isFile() && inputData.getName().equals("data.txt")) {
            dataPath = inputData.getPath();
        } else {
            System.out.println("Data.txt not found in given path, using default path instead");
        }

        try {
            InputStream is = new FileInputStream(dataPath);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            try {
                while((line = br.readLine()) != null) {
                    fileContents.add(line);
                }
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
        
        for(String s: fileContents) {
            if(s.contains("GRID")) {
                String[] splitString = s.split(" ");
                rows = Integer.parseInt(splitString[1]);
                cols = Integer.parseInt(splitString[2]);
            } else if (s.contains("START")) {
                String[] splitString = s.split(" ");
                startX = Integer.parseInt(splitString[1]);
                startY = Integer.parseInt(splitString[2]);
            } else if (s.contains("*")) {
                datalist.add(s);
            }
        }

        char[][] dataArr = new char[datalist.size()][datalist.get(0).length()];;
        int[][] gridArr = new int[rows][cols];

        for(String s : datalist) {
            int i = 0;
            char[] charArr = s.toCharArray();
            for(int j = 0; j < charArr.length; j++) {
                if(charArr[j] == '*') {
                    dataArr[i][j] = '*';
                } else {
                    dataArr[i][j] = ' ';
                }
                i++;
            }
        }

        for(int i = 0; i < dataArr.length; i++) {
            for(int j = 0; j < dataArr[i].length; j++) {
                if(dataArr[i][j] == '*') {
                    gridArr[startX+i][startY+j] = 1;
                } else {
                    gridArr[startX+i][startY+j] = 0;
                }
            }
        }

        System.out.println(gridArr.toString());
    }
}
