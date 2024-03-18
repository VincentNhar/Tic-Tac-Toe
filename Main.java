/*     Group Names      | Student Number
 * Vincent Nhar Calonzo | 101272540
 * Sabirin Kulmiye      | 101363605
 * Evan James           | 101351543
 */

import java.util.Scanner;

public class Main {
    public static void mainMenu() {
        System.out.print("|------------- Tic Tac Toe ---------------|");
        System.out.print("\n|  Choose An Option From The Menu Below   |");
        System.out.print("\n|  1. Play                                |");
        System.out.print("\n|  2. Exit                                |");
        System.out.print("\n|-----------------------------------------|\n");
    }

    public static void gameMode() {
        System.out.print("|------------- Tic Tac Toe ---------------|");
        System.out.print("\n|  Choose Game Mode                       |");
        System.out.print("\n|  1. vs Human                            |");
        System.out.print("\n|  2. vs Computer                         |");
        System.out.print("\n|-----------------------------------------|\n");
    }

    public static void symbolMenu() {
        System.out.print("|------------- Tic Tac Toe ---------------|");
        System.out.print("\n|  Choose Symbol                          |");
        System.out.print("\n|  1. X                                   |");
        System.out.print("\n|  2. O                                   |");
        System.out.print("\n|-----------------------------------------|\n");
    }

    public static void AIMenu() {
        System.out.print("|------------- Tic Tac Toe ---------------|");
        System.out.print("\n|  Choose A Difficulty                    |");
        System.out.print("\n|  1. Weak                                |");
        System.out.print("\n|  2. Strong                              |");
        System.out.print("\n|-----------------------------------------|\n");
    }

    public static void turnMenu(String optionOne, String optionTwo, boolean vsHuman) {
        System.out.print("|------------- Tic Tac Toe ---------------|");
        System.out.print("\n|  Who Goes First                         |");
        System.out.printf("\n|  1. %-10s                          |", optionOne);
        System.out.printf("\n|  2. %-10s                          |", optionTwo);
        if (vsHuman)
            System.out.print("\n|  3. Random                              |");
        System.out.print("\n|-----------------------------------------|\n");
    }

    private static void VersusComputer(String name, boolean playerFirstMove, int playerSymbol, int cpuSymbol,
            boolean strongAI) {
        Tictactoe board = new Tictactoe();
        int moves = 0;
        int pos;
        int gameCondition = -1;
        String winner;

        while (gameCondition == -1) {
            if (playerFirstMove) {
                board.displayBoard();
                pos = getValidNumber(String.format("%s's turn to move: ", name), false);
            } else
                pos = (strongAI) ? board.strongAIInput(cpuSymbol) : board.weakAIInput();

            if (pos >= 1 && pos <= 9) {
                if (board.isMoveValid(pos)) {
                    board.makeMove(pos, playerFirstMove ? playerSymbol : cpuSymbol);
                    moves++;
                    gameCondition = moves > 4 ? board.checkWinner() : -1;
                    playerFirstMove = !playerFirstMove;
                } else
                    System.out.println("Location already taken! Please try again");
            } else
                System.out.println("Invalid input. Input must be in range of 1-9");
        }

        if (gameCondition != 0) {
            winner = (gameCondition == playerSymbol) ? name : "CPU";
            System.out.println("===== GAME OVER =====");
            if (winner == "CPU")
                board.displayBoard();

            System.out.println(winner + " Wins!\n");
        } else
            System.out.println("Draw!\n");
    }

    // Human Opponent (bonus)
    private static void VersusHuman(String name1, String name2, boolean playerOneTurn, int player1Symbol,
            int player2Symbol) {
        // Scanner keyboard = new Scanner(System.in);
        Tictactoe board = new Tictactoe();
        int moves = 0;
        int pos;
        int gameCondition = -1;
        String winner;

        board.displayBoard();
        while (gameCondition == -1) {
            if (playerOneTurn)
                pos = getValidNumber(String.format("%s's turn to move: ", name1), false);
            else
                // player two's turn
                pos = getValidNumber(String.format("%s's turn to move: ", name2), false);

            if (pos >= 1 && pos <= 9) {
                if (board.isMoveValid(pos)) {
                    board.makeMove(pos, playerOneTurn ? player1Symbol : player2Symbol);
                    moves++;
                    // starts checking the board for a winner after 4 moves
                    gameCondition = moves > 4 ? board.checkWinner() : -1;
                    playerOneTurn = !playerOneTurn; // toggles to control whose turn it is to make a move
                    System.out.println("\n");
                    board.displayBoard();
                } else
                    System.out.println("Location already taken! Please try again");
            } else
                System.out.println("Invalid input. Input must be in range of 1-9");
        }
        if (gameCondition != 0) {
            winner = (gameCondition == player1Symbol) ? name1 : name2;

            System.out.println("\n======= GAME OVER =======\n");
            board.displayBoard();
            System.out.println(winner + " Wins!\n");
        } else
            System.out.println("Draw!\n");
    }

    // PLAY TIC-TAC-TOE
    public static void runMenu() {
        int choice;
        boolean isRunning = true;
        while (isRunning) {
            // MAIN MENU
            mainMenu();
            choice = getValidNumber("Enter Selection: ", true);
            switch (choice) {
                case 1 -> {
                    // GAME MODE MENU
                    gameMode();
                    choice = getValidNumber("Enter Selection: ", true);
                    if (choice == 1) {
                        // vs Human
                        String namePlayerOne, namePlayerTwo;
                        int symbolPlayerOne, symbolPlayerTwo;
                        boolean flagFirstTurn;

                        // get players name
                        namePlayerOne = getValidStringInput("Enter Player One name: ");
                        namePlayerTwo = getValidStringInput("Enter Player Two name: ");

                        // determine players symbol
                        symbolMenu();
                        symbolPlayerOne = getValidNumber("Enter Player One symbol: ", true);
                        symbolPlayerTwo = (symbolPlayerOne == 1) ? 2 : 1; // 'player two' takes the symbol 'player one'
                                                                          // didn't take

                        // determine which player goes first
                        turnMenu(namePlayerOne, namePlayerTwo, true);
                        while (true) {
                            choice = getValidNumber("Enter Selection: ", false);
                            if (choice >= 1 && choice <= 3)
                                break;
                            System.out.println("Invalid Selection. Please choose from given selection ");
                        }
                        if (choice == 3)
                            flagFirstTurn = Math.random() < 0.5;
                        else
                            flagFirstTurn = (choice == 1) ? true : false; // Player One goes first if true

                        VersusHuman(namePlayerOne, namePlayerTwo, flagFirstTurn, symbolPlayerOne, symbolPlayerTwo);
                    } else {
                        // vs Computer
                        String playerName;
                        int playerSymbol, computerSymbol;
                        boolean isStrongAi;
                        boolean flagFirstTurn;

                        // get player name
                        playerName = getValidStringInput("Enter Player Name: ");

                        // get player symbol
                        symbolMenu();
                        playerSymbol = getValidNumber("Enter Player One symbol: ", true);
                        computerSymbol = (playerSymbol == 1) ? 2 : 1; // computer takes the symbol player didn't take

                        // DIFFICULTY MENU
                        AIMenu();
                        choice = getValidNumber("Enter selection: ", true);

                        isStrongAi = (choice != 1) ? true : false;

                        turnMenu(playerName, "Computer", false);
                        while (true) {
                            choice = getValidNumber("Enter Selection: ", false);
                            if (choice >= 1 && choice <= 2)
                                break;
                            System.out.println("Invalid Selection. Please choose from given selection ");
                        }
                        flagFirstTurn = (choice == 1) ? true : false; // Player One goes first if true
                        VersusComputer(playerName, flagFirstTurn, playerSymbol, computerSymbol, isStrongAi);
                    }
                    break;
                }
                case 2 -> {
                    System.out.println("|--- Thank you for playing Tic Tac Toe ---| " +
                            "\n|---       Have a nice day !   :)      ---|");
                    isRunning = false;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        runMenu();
    }

    // HELPER FUNCTIONS
    // validates user input (must be in the selection)
    public static int getValidNumber(String promptMessage, boolean forSelection) {
        Scanner keyboard = new Scanner(System.in);
        String userInput = "";
        int out = -1;
        boolean isInputValid = true;
        while (isInputValid) {
            try {
                System.out.print(promptMessage);
                userInput = keyboard.nextLine();
                out = Integer.parseInt(userInput);
                if (forSelection && (out > 2 || out < 1)) {
                    System.out.println("Invalid input. Please choose from the given selection");
                    continue;
                }
                isInputValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    // validates string (empty string isn't allowed)
    public static String getValidStringInput(String promptMessage) {
        Scanner keyboard = new Scanner(System.in);
        String userInput = "";
        boolean isInputValid = true;
        while (isInputValid) {
            System.out.print(promptMessage);
            userInput = keyboard.nextLine();
            if (userInput.length() > 0) {
                break;
            }
            System.out.println("Input cannot be empty");
        }
        return userInput;
    }
}