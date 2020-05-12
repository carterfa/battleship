import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String[][] ocean = new String[10][10];
        int shipCount = 5;

        System.out.println("***** Welcome to battleship! *****\n The ocean awaits!");
        fillOcean(ocean);
        printMap(ocean);
        playerShipDeploy(ocean, input, shipCount);
        enemyShipDeploy(ocean, shipCount);
        battle(ocean, input, shipCount);
    }

    //Prints top grid row
    public static void gridrow(String[][] arr) {
        String gridString = "   ";

        for (int i = 0; i < arr.length; i++) {
            gridString += i;
        }

        gridString += "   ";

        System.out.println(gridString);
    }

    //Fills ocean
    public static void fillOcean(String[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            Arrays.fill(arr[i], " ");
        }
    }

    //Prints map of ocean
    public static void printMap(String[][] arr) {

        gridrow(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].equals("&") || arr[i][j].equals("*")) {
                    System.out.print(" ");
                }else{
                    System.out.print(arr[i][j]);
                }
            }
            System.out.print("| " + i);
            System.out.println();
        }

        gridrow(arr);
    }

    //Deploy ships
    public static void playerShipDeploy(String[][] ocean, Scanner input, int shipCount) {
        for (int i = 0; i < shipCount; i++) {
            boolean deployedShip = false;
            int xInt;
            int yInt;

            System.out.println("Where will you deploy ship no. " + (i + 1) + "?");

            while (!deployedShip) {

                System.out.print("X coordinate: ");
                xInt = validPos(ocean, input, validInt(input.nextLine().trim()));

                System.out.print("Y coordinate: ");
                yInt = validPos(ocean, input, validInt(input.nextLine().trim()));

                if (!ocean[yInt][xInt].equals("@")) {
                    ocean[yInt][xInt] = "@";
                    deployedShip = true;
                } else {
                    System.out.println("This position is unavailable!");
                }
            }
        }

        System.out.println("All ships deployed!");
        printMap(ocean);
    }

    //Computer ship deployment
    public static void enemyShipDeploy(String[][] ocean, int shipCount) {
        for (int i = 0; i < shipCount; i++) {
            System.out.println("Enemy deploying ship no. " + (i + 1) + "!");
            boolean deployedShip = false;
            int xInt;
            int yInt;

            while (!deployedShip) {
                xInt = randomPos(ocean[0].length);
                yInt = randomPos(ocean.length);

                if (!ocean[yInt][xInt].equals("@")) {
                    ocean[yInt][xInt] = "&";
                    deployedShip = true;
                }
            }

        }
        System.out.println("Enemy ships deployed! Let the battle begin!");
        printMap(ocean);
    }

    public static void battle(String[][] ocean, Scanner input, int shipCount) {
        int playerShips = shipCount;
        int computerShips = shipCount;

        while (playerShips > 0 && computerShips > 0) {

            String playerResult = playerAttack(ocean, input);

            if (playerResult.equals("X")) {
                playerShips--;
            } else if (playerResult.equals("!")) {
                computerShips--;
            }

            printResult(playerShips, computerShips, ocean);

            String computerResult = computerAttack(ocean);
            if (computerResult.equals("X")) {
                playerShips--;
            } else if (computerResult.equals("!")) {
                computerShips--;
            }

            printResult(playerShips, computerShips, ocean);

        }

        if (computerShips <= 0){
            System.out.println("You destroyed all the enemy ships! You won!");
        }else{
            System.out.println("You lost! Better luck next time!");
        }

    }

    //Verify result
    public static void printResult(int playerShips, int computerShips, String[][] ocean) {
        System.out.println("Your ships: " + playerShips + " | " + "Enemy Ships: " + computerShips);
        printMap(ocean);
    }


    //Player hit
    public static String playerAttack(String[][] ocean, Scanner input) {
        System.out.println("Where will you attack?");

        int xInt;
        int yInt;

        System.out.print("X coordinate: ");
        xInt = validPos(ocean, input, validInt(input.nextLine().trim()));

        System.out.print("Y coordinate: ");
        yInt = validPos(ocean, input, validInt(input.nextLine().trim()));

        if (ocean[yInt][xInt].equals("@")) {
            System.out.println("You just blew up your own ship!");
            ocean[yInt][xInt] = "X";
        } else if (ocean[yInt][xInt].equals("&")) {
            System.out.println("You sunk one of the computer's ships!");
            ocean[yInt][xInt] = "!";
        } else {
            System.out.println("Miss! No ships were hit!");
            ocean[yInt][xInt] = "-";
        }
        return ocean[yInt][xInt];
    }

    public static String computerAttack(String[][] ocean) {

        int xInt = -1;
        int yInt = -1;
        boolean fired = false;

        while(!fired) {
            xInt = randomPos(ocean[0].length);
            yInt = randomPos(ocean.length);

            switch (ocean[yInt][xInt]) {
                case ("&") -> {
                    System.out.println("They just blew up their own ship!");
                    ocean[yInt][xInt] = "!";
                    fired = true;
                }
                case ("@") -> {
                    System.out.println("Oh no! They sunk one of your ships!");
                    ocean[yInt][xInt] = "X";
                    fired = true;
                }
                case (" ") -> {
                    System.out.println("They missed! No ships were hit!");
                    ocean[yInt][xInt] = "*";
                    fired = true;
                }
            }
        }

        return ocean[yInt][xInt];
    }

    //Validation of position
    public static int validPos(String[][] arr, Scanner input, int a) {
        while (a < 0 || a > (arr.length - 1)) {
            System.out.println("Not a valid input.");
            a = validInt(input.nextLine().trim());
        }
        return a;
    }

    //Validation of integer input
    public static int validInt(String position) {
        try {
            return Integer.parseInt(position);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    //Random position for computer
    public static int randomPos(int grid) {
        Random rand = new Random();
        return rand.nextInt((grid));
    }


}
