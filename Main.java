import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Thin Ice of Barsoom
 * The program reads ice sheets data from a file, processes the data, and outputs the weak spots and cracks
 *
 * @author Jesse Atkinson
 * @version 1.0
 */
public class Main {
    /**
     * Loads the ice sheets from the input
     * @param scanner The scanner to read the input
     * @param numSheets The number of ice sheets to load
     * @return A 3D array representing the ice sheets
     */
    public static int[][][] loadIceSheets(Scanner scanner, int numSheets) {
        int[][][] iceSheets = new int[numSheets][][]; // Initialize 3D array to store ice sheet data
        for (int i = 0; i < numSheets; i++) {
            String[] dimensions = scanner.nextLine().split(" "); // Get the dimensions of the sheet
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            iceSheets[i] = new int[rows][cols];
            // Load the ice sheet data into 3D array
            for (int j = 0; j < rows; j++) {
                String[] values = scanner.nextLine().split(" ");
                for (int k = 0; k < cols; k++) {
                    iceSheets[i][j][k] = Integer.parseInt(values[k]);
                }
            }
        }
        return iceSheets;
    }
    /**
     * Finds the weak spots and cracks in the ice sheets
     * @param iceSheets The 3D array representing the ice sheets
     * @param numSheets The number of ice sheets
     */
    public static void findWeakSpotsAndCracks(int[][][] iceSheets, int numSheets) {
        int totalWeakSpots = 0, maxWeakSpots = 0, maxWeakSpotsSheet = 0, totalCracks = 0, crackCount = 0;
        int[][] crackDetails = new int[1000][3];  // Array to store crack location details
        System.out.println("PART A:");
        
        // Array for X and Y coordinates for neighbours
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int i = 0; i < numSheets; i++) {
            int numWeakSpots = 0;
            int numCracks = 0;

            for (int row = 0; row < iceSheets[i].length; row++) {
                for (int col = 0; col < iceSheets[i][row].length; col++) {
                    int cellValue = iceSheets[i][row][col];
                    // Check for weak spot
                    if (cellValue <= 200 && cellValue % 50 == 0) {
                        numWeakSpots++;
                        // Check for cracks in neighboring cells
                        for (int[] direction : directions) {
                            int nRow = row + direction[0];
                            int nCol = col + direction[1];
                            // Ensure neighbour cell is within the bounds of the sheet
                            if (nRow >= 0 && nRow < iceSheets[i].length && nCol >= 0 && nCol < iceSheets[i][row].length) {
                                if (iceSheets[i][nRow][nCol] % 10 == 0) {
                                    numCracks++;
                                    crackDetails[crackCount][0] = i;  // Store sheet index
                                    crackDetails[crackCount][1] = row;  // Store row
                                    crackDetails[crackCount][2] = col;  // Store column
                                    crackCount++;
                                    break;  // Stop after first crack found for this weak spot
                                }
                            }
                        }
                    }
                }
            }
            totalWeakSpots += numWeakSpots; // Tally the total number of weak spots
            totalCracks += numCracks; // Tally the total number of cracks
            // Check if current sheet has the most weak spots
            if (numWeakSpots > maxWeakSpots) {
                maxWeakSpots = numWeakSpots;
                maxWeakSpotsSheet = i;
            }
            System.out.println("Sheet " + i + " has " + numWeakSpots + " weak spots");
        }
        // Output results
        System.out.println("Total weak spots on all sheets = " + totalWeakSpots);
        System.out.println("Sheet " + maxWeakSpotsSheet + " has the highest number of weak spots = " + maxWeakSpots);
        System.out.println("\nPART B:");
        // Output crack location details
        for (int i = 0; i < crackCount; i++) {
            System.out.println("CRACK DETECTED @ [Sheet[" + crackDetails[i][0] + "](" + crackDetails[i][1] + "," + crackDetails[i][2] + ")]");
        }
        System.out.println("\nSUMMARY:");
        System.out.println("The total number of weak spots that have cracked = " + totalCracks);
        System.out.printf("The fraction of weak spots that are also cracks is %.3f\n", (double) totalCracks / totalWeakSpots);
    }
    /**
     * The main method of the program
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        String filePath = "Assignment 1/src/ICESHEETS_F24.TXT"; // Get the file path

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int numSheets = Integer.parseInt(scanner.nextLine()); // Get the number of sheets from the file
            int[][][] iceSheets = loadIceSheets(scanner, numSheets); // Load ice sheet data
            findWeakSpotsAndCracks(iceSheets, numSheets); // Process weak spots and cracks
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
