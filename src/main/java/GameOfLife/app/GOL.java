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
    public static String[] coordinatesString = {"0,1","0,-1","1,1","1,0","1,-1","-1,1","-1,0","-1,-1"};
    public static int[][] gridArr;
    public static int[][] checkArr;
    public static void main( String[] args )
    {
        // initialization of variables
        int rows = 0;
        int cols = 0;
        int startX = 0;
        int startY = 0;
        List<String> fileContents = new ArrayList<String>();
        List<String> datalist = new ArrayList<String>();
        File inputData;
        String dataPath = "C:\\Users\\Brendon\\JavaProjects\\GameOfLife\\src\\main\\java\\GameOfLife\\app\\data.txt";

        // asking for path to data.txt
        Console cons = System.console();
        String playerInput = cons.readLine("Input path to data.txt: \n");
        if((inputData = new File(playerInput)).isFile() && inputData.getName().equals("data.txt")) {
            dataPath = inputData.getPath();
        } else {
            System.out.println("Data.txt not found in given path, using default path instead");
        }

        // saving data read from data.txt to a list variable
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
    
        // getting input from read data
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

        // setting up the game board as well as the other boards to help manipulate it
        char[][] dataArr = new char[datalist.size()][datalist.get(0).length()];;
        gridArr = new int[rows][cols];
        checkArr = new int[rows][cols];

        for(int i = 0; i < datalist.size(); i++) {
            char[] charArr = datalist.get(i).toCharArray();
            for(int j = 0; j < charArr.length; j++) {
                if(charArr[j] == '*') {
                    dataArr[i][j] = '*';
                } else {
                    dataArr[i][j] = ' ';
                }
            }
        }

        // setting up game of life grid using data points
        for(int i = 0; i < dataArr.length; i++) {
            for(int j = 0; j < dataArr[i].length; j++) {
                if(dataArr[i][j] == '*') {
                    gridArr[startX+i][startY+j] = 1;
                } else {
                    gridArr[startX+i][startY+j] = 0;
                }
            }
        }
        
        // looping game of life for 5 generations
        for(int loop = 0; loop < 5; loop++) {
            for(int i = 0; i < gridArr.length; i ++) {
                for(int j = 0; j < gridArr[i].length; j++) {
                    checkTile(i, j);
                }
            }
            for(int i = 0; i < gridArr.length; i ++) {
                for(int j = 0; j < gridArr[i].length; j++) {
                    gridArr[i][j] = checkArr[i][j];
                }
            }
        }

        // display game of life board after 5 generations
        for(int i = 0; i < gridArr.length; i++) {
            for(int j = 0; j < gridArr[i].length; j++) {
                System.out.print(gridArr[i][j]);
            }
            System.out.println();
        }
    }

    //checktile method to determine if tile lives or dies
    public static void checkTile(int x, int y) {
        int livingNeighbours = 0;
        for(String s : coordinatesString) {
            String[] splitCoords = s.split(",");
            int row = Integer.parseInt(splitCoords[0]);
            int col = Integer.parseInt(splitCoords[1]);
            try{
                livingNeighbours += gridArr[x+row][y+col];
            } catch (IndexOutOfBoundsException e) {

            }
        }
        if (livingNeighbours > 1 && livingNeighbours < 4) {
            checkArr[x][y] = 1;
        } else {
            checkArr[x][y] = 0;
        }
    }
}
