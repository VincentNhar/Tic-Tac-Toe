import java.util.Random;

public class Tictactoe {
    //states    
    private final int MAX_MOVES = 9;
    private int numMoves;
    private final int ROW_LENGTH = 3;
    private final int COL_LENGTH = 3;
    private int[][] board;
    
    // constructor
    public Tictactoe(){ 
        board = new int[ROW_LENGTH][COL_LENGTH];
        numMoves = 0;
    }
    
    public boolean makeMove(int pos, int symbol){        
        if(numMoves < MAX_MOVES){
            board[getIndexOfPos(pos)[0]][getIndexOfPos(pos)[1]] = symbol;            
            numMoves++;
            return true;
        }
        return false;
    }   

    public void displayBoard(){
        int num = 0;
        for(int row = 0; row < ROW_LENGTH; row++){
            for(int col = 0; col < COL_LENGTH; col++){
                ++num;
                if(board[row][col] == 1)
                    System.out.print(" X ");
                else if(board[row][col] == 2)
                    System.out.print(" O ");
                else
                    System.out.printf(" %d ",num);                
                if(col < 2) System.out.print("|");
            }
            if(row < 2)
                System.out.printf("%n===|===|===%n");
            else
                System.out.println("\n");
        }
    }

    public int weakAIInput(){
        Random rand = new Random();
        return rand.nextInt(9) + 1;
    }

    public int strongAIInput(int symbol){
        int bestScore = -999;
        int bestMove = 0;

        // traverse the board and checks for possible moves
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                if(board[row][col] == 0){
                    // updates the board with the possible move and its number of moves count
                    board[row][col] = symbol;
                    numMoves++;
                    int score = minimax(symbol,board, 0, false);

                    // reset the board from original state
                    board[row][col] = 0;
                    numMoves--;

                    // checks if the score minimax function has returned has higher value than the current bestScore
                    if(score > bestScore){
                        bestScore = score;  //sets current score as best score
                        bestMove = getPosOfIndex(row, col); // sets the current position as the best move
                    }
                }
            }
        }
        return bestMove;
    }
    
    private int minimax(int symbol,int[][] currBoard,int depth, boolean isMax){
        int winner = checkWinner();
        if(winner != -1){
            if(winner == symbol)
                return 10 - depth;                
            else if (winner == 0)
                return 0;
            else
                return depth - 10;
        }
        if(isMax){
            // Maximizer
            int bestScore = -999;
            // traverse the board and checks for possible moves
            for(int row=0;row<3;row++){
                for(int col=0;col<3;col++){
                    if(board[row][col] == 0){
                        // updates the board with the possible move and its number of moves count
                        board[row][col] = symbol;
                        numMoves++;
                        int score = minimax(symbol,currBoard, depth+1, false);                        
                        // reset the board from original state
                        currBoard[row][col] = 0;
                        numMoves--;
                        // set the bestScore to whichever value is higher, since we are currently maximizing (Strong AI move)
                        bestScore = Math.max(score,bestScore);
                    }
                }
            }
            return bestScore;
        }else{
            // Minimizer
            int bestScore = 999;
            // traverse the board and checks for possible moves
            for(int row=0;row<3;row++){
                for(int col=0;col<3;col++){
                    if(currBoard[row][col] == 0){
                        // updates the board with the possible move and its number of moves count
                        currBoard[row][col] = (symbol == 2) ? 1:2;
                        numMoves++;                        
                        int score = minimax(symbol,currBoard, depth+1, true);                        
                        // reset the board from original state
                        currBoard[row][col] = 0;
                        numMoves--;
                        // set the bestScore to whichever value is lower, since we are currently minimizing (player move)
                        bestScore = Math.min(score,bestScore);                        
                    }
                }
            }
            return bestScore;
        }
    }

    //HELPER METHODS

    //isMoveValid
    public boolean isMoveValid(int pos){
        int[] index = getIndexOfPos(pos);    
        if (board[index[0]][index[1]] == 0) return true;        
        return false;
    }

    public int checkWinner(){
        //checks for condition to win in row, column and diagonal
        //returns the symbol value of the winner

        // checks row
        for(int i=0;i<3;i++){
            if(board[i][0] != 0 && 
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2])
                return board[i][0];
        }
        // check column
        for(int i=0;i<3;i++){
            if(board[0][i] != 0 && 
                board[0][i] == board[1][i] &&
                board[1][i] == board[2][i])
                return board[0][i];
        }
        // check diagonal
        if(board[1][1] != 0 && 
        (board[0][2] == board[1][1] && board[1][1] == board[2][0])
        || (board[0][0] == board[1][1] && board[1][1] == board[2][2])){
            return board[1][1];
        }
        //return 0 if board is full
        if(numMoves == 9){
            return 0;
        }

        return -1;
    }
    // converts 2d array indices into a single int value (position)
    private int[] getIndexOfPos(int pos){
        int[] index = new int[2];
    
        switch (pos) {
            case 1 -> {
                index[0] = 0;
                index[1] = 0;
                return index;
            }
            case 2 -> {
                index[0] = 0;
                index[1] = 1;
                return index;
            }
            case 3 -> {
                index[0] = 0;
                index[1] = 2;
                return index;
            }
            case 4 -> {
                index[0] = 1;
                index[1] = 0;
                return index;
            }
            case 5 -> {
                index[0] = 1;
                index[1] = 1;
                return index;
            }
            case 6 -> {
                index[0] = 1;
                index[1] = 2;
                return index;
            }
            case 7 -> {
                index[0] = 2;
                index[1] = 0;
                return index;
            }
            case 8 -> {
                index[0] = 2;
                index[1] = 1;
                return index;
            }
            case 9 -> {
                index[0] = 2;
                index[1] = 2;
                return index;
            }
        }
        return null;
    }
    // converts a single int value (position) into 2d array indices
    private int getPosOfIndex(int row,int col){
        int pos = -1;
        switch(row){
            case 0:
                switch(col){
                    case 0:
                        pos = 1;
                        break;
                    case 1:
                        pos = 2;
                        break;
                    case 2:
                        pos = 3;
                        break;
                }
                break;
            case 1:
                switch(col){
                    case 0:
                        pos = 4;
                        break;
                    case 1:
                        pos = 5;
                        break;
                    case 2:
                        pos = 6;
                        break;
                }
                break;
            case 2:
                switch(col){
                    case 0:
                        pos = 7;
                        break;
                    case 1:
                        pos = 8;
                        break;
                    case 2:
                        pos = 9;
                        break;
                }
                break;
        }
        return pos;
    }
}
