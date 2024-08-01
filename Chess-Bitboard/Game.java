import java.util.Stack;
import java.util.Scanner;
import java.util.Random;
import java.util.LinkedList;

public class Game {
    private Stack<Move> move_history;
    private Stack<Short> capture_history;
    private Board gameBoard;
    private boolean turn;
    private short wCastle;
    private short bCastle;

    // Evaluation function info
    private int wMoves;
    private int bMoves;
    private int numMoves;

    // Maximum depth that we want AlphaBeta to search to for time purposes
    public static int maxDepth = 5;
    

    public Game(){
         this.move_history = new Stack<Move>();
         this.capture_history = new Stack<Short>();
         this.gameBoard = new Board();
         this.turn = true;
         wCastle = 0;
         bCastle = 0;
         wMoves = 0;
         bMoves = 0;
    }

    public Game(boolean turn, Board board){
        this.move_history = new Stack<>();
        this.capture_history = new Stack<>();
        this.turn = turn;
        this.gameBoard = board;
        this.wCastle = 0;
        this.bCastle = 0;
        wMoves = 0;
        bMoves = 0;
        numMoves = 0;
    }

    public Board getGameBoard(){
        return this.gameBoard;
    }

    public Stack<Move> getMoveHistory(){
        return this.move_history;
    }

    public Stack<Short> getCaptureHistory(){
        return this.capture_history;
    }

    public boolean getTurn(){
        return this.turn;
    }

    public int getWhiteNumMoves(){
        return wMoves;
    }

    public int getBlackNumMoves(){
        return bMoves;
    }

    public short getWhiteCastle(){
        return wCastle;
    }

    public short getBlackCastle(){
        return bCastle;
    }

    public int getNumMoves(){
        return numMoves;
    }

    public void updateTurn(){
        this.turn = !this.turn;
    }

    public void doMove(Move move){
        long moveBit = move.getMoveBit();
        short pieceType = move.getPieceType();
        int pieceTypeIndex = pieceType > 0 ? pieceType-1 : -pieceType-1;
        int previousIndex = move.getPreviousIndex();
        boolean captured = move.didCapture();

        // Pushes the current bitboard of the piece onto the move history
        this.move_history.push(move);

        // Updates the piece with the new move
        short captured_type = this.gameBoard.updateBoard(this.turn, moveBit, pieceTypeIndex, previousIndex,captured);
        
        // And Pushes the type of the piece taken
        this.capture_history.push(captured_type);

        // Changes whose turn it is
        this.updateTurn();

        // Increments total number of moves done
        this.numMoves++;
    }

    public void undoMove(){
        // Error Checking
        if (this.move_history.empty() || this.capture_history.empty()){
            throw new UnsupportedOperationException("No Moves to Undo");
        }
        
        Move move = this.move_history.pop();
        short captured_type = this.capture_history.pop();

        long moveBit = move.getMoveBit();
        short pieceType = move.getPieceType();
        int pieceTypeIndex = pieceType > 0 ? pieceType-1 : -pieceType-1;
        int previousIndex = move.getPreviousIndex();

        updateTurn();
        // Updates the piece with the new move
        this.gameBoard.revertBoard(this.turn, moveBit, pieceTypeIndex, previousIndex, captured_type);

        // Decrements total number of moves done
        this.numMoves--;
    }

    public LinkedList<Move> inCheck(LinkedList<Move> moves){
        LinkedList<Move> legals = new LinkedList<>();
        Pieces king; 

        for(Move move : moves){
            boolean valid = true;
            this.doMove(move);
            king = !turn ? this.gameBoard.getWhitePieces()[5] : this.gameBoard.getBlackPieces()[5];
            long whiteBoard = this.gameBoard.getFullWhite();
            long blackBoard = this.gameBoard.getFullBlack();
            
            LinkedList<Move> temp = !turn ? king.generateWhitePawnMoves(whiteBoard, blackBoard) : king.generateBlackPawnMoves(whiteBoard, blackBoard);
            for(Move move1 : temp){ 
                if(move1.didCapture()){
                    long tempMove = move1.getMoveBit();
                    long opPawn = turn ? this.gameBoard.getWhitePieces()[0].getBitBoard() : this.gameBoard.getBlackPieces()[0].getBitBoard();

                    if ((tempMove & opPawn) != 0){
                        valid = false;
                        break;
                    }  
                }
            }

            if(!valid){
                this.undoMove();
                continue;
            }


            temp = king.generateKnightMoves(whiteBoard, blackBoard);
            for(Move move1 : temp){ 
                if(move1.didCapture()){
                    long tempMove = move1.getMoveBit();
                    long opKnight = turn ? this.gameBoard.getWhitePieces()[1].getBitBoard() : this.gameBoard.getBlackPieces()[1].getBitBoard();

                    if ((tempMove & opKnight) != 0){
                        valid = false;
                        break;
                    }  
                }
            }

            if(!valid){
                this.undoMove();
                continue;
            }

            temp = king.generateBishopMoves(whiteBoard, blackBoard);
            for(Move move1 : temp){ 
                if(move1.didCapture()){
                    long tempMove = move1.getMoveBit();
                    long opQueen = turn ? this.gameBoard.getWhitePieces()[4].getBitBoard() : this.gameBoard.getBlackPieces()[4].getBitBoard();
                    long opBishop = turn ? this.gameBoard.getWhitePieces()[2].getBitBoard() : this.gameBoard.getBlackPieces()[2].getBitBoard();

                    if (((tempMove & opQueen) != 0) || ((tempMove & opBishop) != 0)){
                        valid = false;
                        break;
                    }
                    
                }
            }

            if(!valid){
                this.undoMove();
                continue;
            }

            temp = king.generateRookMoves(whiteBoard, blackBoard);
            for(Move move1 : temp){ 
                if(move1.didCapture()){
                    long tempMove = move1.getMoveBit();
                    long opQueen = turn ? this.gameBoard.getWhitePieces()[4].getBitBoard() : this.gameBoard.getBlackPieces()[4].getBitBoard();
                    long opRook = turn ? this.gameBoard.getWhitePieces()[3].getBitBoard() : this.gameBoard.getBlackPieces()[3].getBitBoard();

                    if (((tempMove & opQueen) != 0) || ((tempMove & opRook) != 0)){
                        valid = false;
                        break;
                    }
                    
                }
            }

            this.undoMove();
            if(valid){
                legals.add(move);
            }
        }

        return legals;
    }

    // Check if game is over by checking if either king is missing
    public boolean isGameOver() {
        long wKing = this.gameBoard.getWhitePieces()[5].getBitBoard();
        long bKing = this.gameBoard.getBlackPieces()[5].getBitBoard();

        return (wKing == 0) || (bKing == 0);
    }

    // Get a list of all possible legal moves given this board position
    public LinkedList<Move> generateLegalMoves() {
        LinkedList<Move> legalMoves = new LinkedList<>();
        Random random = new Random();
        Pieces[] pieces;

        if (this.turn){
            pieces = this.gameBoard.getWhitePieces();
        }else{
            pieces = this.gameBoard.getBlackPieces();
        }
        long whiteBoard = this.gameBoard.getFullWhite();
        long blackBoard = this.gameBoard.getFullBlack();


        int[] moveOrder = {1,2,0,3,4,5};
        for(int i = 0; i < 3; i++){
            int swap = random.nextInt(4);
            int temp = moveOrder[i];
            moveOrder[i] = moveOrder[swap];
            moveOrder[swap] = temp;
        }

        for(int i = 0; i < moveOrder.length; i++){
            legalMoves.addAll(pieces[moveOrder[i]].generateMoves(whiteBoard,blackBoard));
        }

        // Updates total number of moves done
        if(turn){
            wMoves = legalMoves.size();
        }else{
            bMoves = legalMoves.size();
        }      

        //legalMoves = inCheck(legalMoves);
        return legalMoves;
    }

    public LinkedList<Move> generateCastles(Pieces[] pieces){
        LinkedList<Move> castles = new LinkedList<>();
        short castleRights; 
        if(this.turn){
            castleRights = wCastle;
        }else{
            castleRights = bCastle;
        }

        switch (castleRights){
            case 0:
            break;
            case 1:
            //Move move = new Move()
            break;
            case 2:
            break;
            case 3:
            break;
            default:
            throw new UnsupportedOperationException("Invalid number for castleRights");
        }   
        
        return castles;
    }

    public void startGame(Player p1, Player p2){
        while(!this.isGameOver()){
            System.out.println(this.getGameBoard().toString());
            Move mve = p1.getMove();
            this.doMove(mve);
            System.out.println(this.getGameBoard().toString());
            if(this.isGameOver()){
                break;
            }
            mve = p2.getMove();
            this.doMove(mve);
            System.out.println(this.toString());
        }
    }

    public static int getAiDepth(){
        // Default depth is 3
        int depth = 3;

        Scanner input = new Scanner(System.in);
        System.out.println("\nWhat depth search would you like the AI to use?");
        String entry = input.nextLine();
        try {
            depth = Integer.parseInt(entry);
        } catch (NumberFormatException e) {
            System.out.println("Input was not an integer. The AI will use a default depth of 3.");
        }
        if(depth > maxDepth){
            System.out.println("The depth you chose was too large. The AI will use a default depth of 3.");
            depth = 3;
        }
        return depth;
    }

    public static void determinePlayers(Game game, int userChoice){
        Player first;
        Player second;
        switch(userChoice){
            // Player vs Player
            case 1:
            first = new Real();
            second = new Real();
            break;

            // Player vs Ai
            case 2:
            int depth = getAiDepth();
            Random random = new Random();
            int coin = random.nextInt(2);
            if (coin != 0) {
                first = new Real();
                second = new Ai(game, depth);
                System.out.println("\nNew game started! \n\u2659 white, \u265F black");
                System.out.println("\nYou play white.");
            } else {
                first = new Ai(game, depth);
                second = new Real();
                System.out.println("\nNew game started! \n\u2659 white, \u265F black");
                System.out.println("\nYou play black.");
            }
            break;

            // Ai vs Ai
            case 3:
            System.out.println("For the first AI player:");
            int depth1 = getAiDepth();
            System.out.println("For the second AI player:");
            int depth2 = getAiDepth();
            first = new Ai(game, depth1);
            second = new Ai(game, depth2);
            break;

            // Quit
            default:
            return;
        }

        game.startGame(first, second);
    }

    // Ask the user what kind of game to play
    public static int getChoice(Game game, int maxAttempts){
        Scanner input = new Scanner(System.in);
        String choices = "\n [1] Player vs Player\n [2] Player vs Ai\n [3] Ai vs Ai\n [4] Quit";
        String startUp = "\nWhat type of game would you like to start? \nEnter a corresponding number:" + choices;
       
        System.out.println(startUp);
        int quit = 4;
        int userChoice = 0;
        int attempts = 0;

        while(attempts < maxAttempts){
            String entry = input.nextLine();
            try {
                userChoice = Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                attempts++;
                System.out.println("Input was not an integer. \nTry inputing a number from 1-4 instead (" + attempts + "/" + maxAttempts + ")");
                input.nextLine();
            }
            if(attempts == maxAttempts){break;}
            if (!((userChoice >= 1) && (userChoice <= 4))){
                attempts++;
                System.out.println("Choose a number from 1-4 (" + attempts + "/" + maxAttempts + ")");
            }
            else{
                return userChoice;
            }
        }
        return quit;
    }

    public static void main(String args[]){
        boolean play = true;
        while(play){
            Game game = new Game();
            int userChoice = getChoice(game, 10);
            if(userChoice == 4){
                break;
            }
            determinePlayers(game, userChoice);
            Scanner input = new Scanner(System.in);
            System.out.println("Would you like to play another game? Type \"y\", any other key will quit the game");
            play = input.next().toLowerCase().equals("y");
       }
       System.out.println("Thank you for playing. Goodbye.");
    }
}
