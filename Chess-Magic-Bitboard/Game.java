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
    private int wMoves;
    private int bMoves;
    private int numMoves;
    int halfMoves;

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
         halfMoves = 0;
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
        halfMoves = 0;
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

    public void doMove(Move move){
        long moveBit = move.getMoveBit();
        short pieceType = move.getPieceType();
        int pieceTypeIndex = pieceType > 0 ? pieceType-1 : -pieceType-1;
        int previousIndex = move.getPreviousIndex();
        boolean captured = move.didCapture();
        move.halfMoves = halfMoves;

        // Pushes the current bitboard of the piece onto the move history
        this.move_history.push(move);

        // Updates the piece with the new move
        short captured_type = this.gameBoard.updateBoard(this.turn, moveBit, pieceTypeIndex, previousIndex,captured);
        
        // And Pushes the type of the piece taken
        this.capture_history.push(captured_type);

        // Changes whose turn it is
        this.updateTurn();

        // Updates Number of total moves
        this.numMoves++;

        // Reset halfmoves count if pawn or capture move
        if (captured || (pieceType == 1) || (pieceType == -1)){
            halfMoves = 0;
        }
    }

    public void undoMove(){
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

        // 
        this.numMoves--;

        // Revert halfmove count to what it was before move was done (stored in move just popped)
        this.halfMoves = move.halfMoves;
    }

    public void updateTurn(){
        this.turn = !this.turn;
    }

    // Check if game is over by checking if either king is missing
    public boolean isGameOver() {
        boolean retVal;
        Pieces[] whitePieces = gameBoard.getWhitePieces();
        Pieces[] blackPieces = gameBoard.getBlackPieces();
        long wKing = whitePieces[5].getBitboard();
        long bKing = blackPieces[5].getBitboard();
        long wNonKing = whitePieces[0].getBitboard() | whitePieces[1].getBitboard() | whitePieces[2] .getBitboard() | whitePieces[3].getBitboard() | whitePieces[4].getBitboard();
        long bNonKing = blackPieces[0].getBitboard() | blackPieces[1].getBitboard() | blackPieces[2].getBitboard() | blackPieces[3].getBitboard() | blackPieces[4].getBitboard();
        retVal = (wKing == 0) || (bKing == 0) || (halfMoves >= 75) || (wNonKing == 0 && bNonKing == 0);

        return retVal;
    }

    public LinkedList<MoveSet> generateLegalMoveSets(){
        LinkedList<MoveSet> moves = new LinkedList<MoveSet>();
        long white = this.getGameBoard().getFullWhite();
        long black = this.getGameBoard().getFullBlack();
        int numMoves = this.getNumMoves();
        boolean turn = this.getTurn();
        LinkedList<MoveSet> temp;
        int totalMoves = 0;
        Pieces[] myPieces = turn ? this.getGameBoard().getWhitePieces() : this.getGameBoard().getBlackPieces();
        if(numMoves < 30){
            for(int i = 0; i < 6; i++){
                temp = myPieces[i].generateMoves(white, black);
                for(MoveSet moveSet : temp){
                    // Check if the move is an en passant capture
                    if (isEnPassantCapture(moveSet)) {
                        moves.add(moveSet); // Add en passant capture move to legal moves
                    } else if (isCastlingMove(moveSet)) {
                        moves.add(moveSet); // Add castling move to legal moves
                    } else {
                        moves.add(moveSet); // Add regular move to legal moves
                        totalMoves += Long.bitCount(moveSet.getBitboard());
                    }
                }
                moves.addAll(temp);
            }
        }
        else{
            for(int i = 5; i >= 0; i--){
                temp = myPieces[i].generateMoves(white, black);
                for(MoveSet moveSet : temp){
                    // Check if the move is an en passant capture
                    if (isEnPassantCapture(moveSet)) {
                        moves.add(moveSet); // Add en passant capture move to legal moves
                    } else if (isCastlingMove(moveSet)) {
                        moves.add(moveSet); // Add castling move to legal moves
                    } else {
                        moves.add(moveSet); // Add regular move to legal moves
                        totalMoves += Long.bitCount(moveSet.getBitboard());
                    }
                }
                moves.addAll(temp);
            }
        }

        if(turn){
            wMoves = totalMoves;
        }else{
            bMoves = totalMoves;
        }

    return moves;
}

    // Helper method to check if a move is an en passant capture
    private boolean isEnPassantCapture(MoveSet moveSet) {
        long bitboard = moveSet.getBitboard(); // Get the destination square of the move

        // Determine the source square of the move
        long sourceBitboard = 0;
        long tempBitboard = bitboard;
        while (tempBitboard != 0) {
            sourceBitboard = Utility.getLSB(tempBitboard); // Get the least significant bit
            tempBitboard &= tempBitboard - 1; // Clear the least significant bit
        }

        int toIndex = Long.numberOfTrailingZeros(bitboard); // Get the destination square index
        int fromIndex = Long.numberOfTrailingZeros(sourceBitboard); // Get the source square index

        // Check if the moved piece is a pawn and it moved two squares forward
        if (Math.abs(fromIndex - toIndex) == 16) {
            // Check if there is an enemy pawn to the left or right of the destination square
            long enemyPawns = turn ? this.getGameBoard().getFullBlack() : this.getGameBoard().getFullWhite();
            long targetPawnFile = 0x0101010101010101L << (toIndex % 8);
            long leftEnemyPawn = (bitboard << 1) & enemyPawns & targetPawnFile;
            long rightEnemyPawn = (bitboard >>> 1) & enemyPawns & targetPawnFile;

            // Return true if there is an adjacent enemy pawn, indicating an en passant capture
            return leftEnemyPawn != 0 || rightEnemyPawn != 0;
        }

        return false;
}

// Helper method to check if a move is a castling move
    private boolean isCastlingMove(MoveSet moveSet) {
        long bitboard = moveSet.getBitboard();
        int toIndex = Long.numberOfTrailingZeros(bitboard); // Get the destination square index

        // Check if the moved piece is a king and it moved two squares horizontally
        if (Math.abs(toIndex - 4) == 2) {
            // Check if the king is castling to the left (queenside)
            if (toIndex < 4) {
                // Check if the king is on its initial position and the rook is on its initial position
                if (isKingOnInitialPosition(turn) && isRookOnInitialPosition(turn, true)) {
                    return true;
                }
            }
            // Check if the king is castling to the right (kingside)
            else {
                // Check if the king is on its initial position and the rook is on its initial position
                if (isKingOnInitialPosition(turn) && isRookOnInitialPosition(turn, false)) {
                    return true;
                }
            }
        }

        return false;
}

    // Helper method to check if the king is on its initial position for castling
    private boolean isKingOnInitialPosition(boolean isWhite) {
        int row = isWhite ? 7 : 0;
        long kings = isWhite ? this.getGameBoard().getWhitePieces()[5].getBitboard() : this.getGameBoard().getBlackPieces()[5].getBitboard();
        return (kings & (1L << (row * 8 + 4))) != 0; // Check if the king is present at the initial position
}

    // Helper method to check if the rook is on its initial position for castling
    private boolean isRookOnInitialPosition(boolean isWhite, boolean queenside) {
        int row = isWhite ? 7 : 0;
        int rookFile = queenside ? 0 : 7;
        long rooks = isWhite ? this.getGameBoard().getWhitePieces()[4].getBitboard() : this.getGameBoard().getBlackPieces()[4].getBitboard();
        return (rooks & (1L << (row * 8 + rookFile))) != 0; // Check if the rook is present at the initial position
}

    public boolean isCheck(){
        Pieces[] myPieces = !turn ? this.gameBoard.getWhitePieces(): this.gameBoard.getBlackPieces();
        Pieces[] oppPieces = !turn ? this.gameBoard.getBlackPieces() : this.gameBoard.getWhitePieces();
        Pieces king = !turn ? this.gameBoard.getWhitePieces()[5] : this.gameBoard.getBlackPieces()[5];
        long kingPos = king.getBitboard();
        short kingType = king.getType();
        short num = 0;
        long samePieces = myPieces[0].getBitboard() | myPieces[1].getBitboard() | myPieces[2].getBitboard() | myPieces[3].getBitboard() | myPieces[4].getBitboard() | myPieces[5].getBitboard();
        Move move = this.move_history.peek();
        short pieceType = move.getPieceType();
        
        // pawn checks
        long temp_pieces = oppPieces[0].getBitboard();
        if(((pieceType != 1) || (pieceType != -1)) && ((Pieces.generatePawnCapturingLeft(!turn, kingPos, temp_pieces).getBitboard() != 0) || 
                  (Pieces.generatePawnCapturingRight(!turn, kingPos, temp_pieces).getBitboard() != 0))){
            return true;
        }

        // knight checks
        temp_pieces = oppPieces[1].getBitboard();
        if(((pieceType != 2) || (pieceType != -2)) &&  ((Pieces.getKnightMoves(kingType, num, kingPos, samePieces).getBitboard() & temp_pieces) != 0)){
            return true;
        }

        // queen and rook checks
        temp_pieces = turn ? (this.gameBoard.getWhitePieces()[3].getBitboard() & this.gameBoard.getWhitePieces()[4].getBitboard()) 
                            : (this.gameBoard.getBlackPieces()[3].getBitboard() & this.gameBoard.getBlackPieces()[4].getBitboard());
        if((Pieces.getRookMoves(kingType, num, kingPos, samePieces, temp_pieces).getBitboard() & temp_pieces) != 0){
            return true;
        }

        // queen and bishop checks
        temp_pieces = turn ? (this.gameBoard.getWhitePieces()[2].getBitboard() & this.gameBoard.getWhitePieces()[4].getBitboard()) 
                            : (this.gameBoard.getBlackPieces()[2].getBitboard() & this.gameBoard.getBlackPieces()[4].getBitboard());
        if((Pieces.getBishopMoves(kingType, num, kingPos, samePieces, temp_pieces).getBitboard() & temp_pieces) != 0){
            return false;
        }
        

        return false;
    }

    public short validMove(short pieceType, int startingPos, long move){
        boolean color = pieceType > 0;
        Pieces piece = color ? gameBoard.getWhitePieces()[pieceType - 1] : gameBoard.getBlackPieces()[(-pieceType) - 1];
        long startingBitboard = Utility.setBit(0L, startingPos);
        // If piece bitboard does not have a piece at the specified starting position, not valid
        if((piece.getBitboard() & startingBitboard) == 0){
            return 0;
        }
        long white = gameBoard.getFullWhite();
        long black = gameBoard.getFullBlack();
        LinkedList<MoveSet> moves = piece.generateMoves(white, black);
        for(MoveSet valid : moves){
            long validBits = valid.getBitboard();
            if(pieceType == 1 || pieceType == -1){
                if((startingBitboard & validBits) != 0 && color){
                    if((move & black) != 0){
                        return 2;
                    }
                    return 1;
                }
                else if((startingBitboard & validBits) != 0){
                    if((move & white) != 0){
                        return 2;
                    }
                    return 1;
                }
            }
            else{
                if((move & validBits) != 0 && color){
                    if((move & black) != 0){
                        return 2;
                    }
                    return 1;
                }
                if((move & validBits) != 0){
                    if((move & white) != 0){
                        return 2;
                    }
                    return 1;
                }
            }
            
        }

        return 0;
    }

    public void startGame(Player p1, Player p2, Scanner input){
        System.out.println(this.getGameBoard().toString());
        while(!this.isGameOver()){
            Move mve = p1.getMove(input);
            if(mve == null){
                return;
            }
            this.doMove(mve);
            System.out.println(this.getGameBoard().toString());
            if(this.isGameOver()){
                break;
            }
            mve = p2.getMove(input);
            if(mve == null){
                return;
            }
            this.doMove(mve);
            System.out.println(this.getGameBoard().toString());
        }
    }

    public static int getAiDepth(Scanner input){
        // Default depth is 3
        int depth = 3;

        System.out.println("What depth search would you like the AI to use?");
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

    public static void determinePlayers(Game game, int userChoice, Scanner input){
        Player first;
        Player second;
        switch(userChoice){
            // Player vs Player
            case 1:
            first = new Real(game);
            second = new Real(game);
            break;

            // Player vs Ai
            case 2:
            int depth = getAiDepth(input);
            Random random = new Random();
            int coin = random.nextInt(2);
            if(coin != 0){
                first = new Real(game);
                second = new Ai(game, depth);
            }else{
                first = new Ai(game, depth);
                second = new Real(game);
            }
            break;

            // Ai vs Ai
            case 3:
            System.out.println("For the first AI player:");
            int depth1 = getAiDepth(input);
            System.out.println("For the second AI player:");
            int depth2 = getAiDepth(input);
            first = new Ai(game, depth1);
            second = new Ai(game, depth2);
            break;

            // Quit
            default:
            return;
        }

        game.startGame(first, second, input);
    }

    // Ask the user what kind of game to play
    public static int getChoice(Game game, int maxAttempts, Scanner input){
        String choices = "\n [1] Player vs Player\n [2] Player vs Ai\n [3] Ai vs Ai\n [4] Quit";
        String startUp = "\n What type of game would you like to start? Enter a corresponding number:" + choices;
       
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
                System.out.println("Input was not an integer. Try inputing a number from 1-4 instead (" + attempts + "/" + maxAttempts + ")");
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
        Utility.initializeMaps();
        Scanner input = new Scanner(System.in);
        while(play){
            Game game = new Game();
            int userChoice = getChoice(game, 10, input);
            if(userChoice == 4){
                break;
            }
            determinePlayers(game, userChoice, input);
            System.out.println("Would you like to play another game? Type \"y\", any other key will quit the game");
            play = input.nextLine().toLowerCase().equals("y");
       }
       System.out.println("Thank you for playing. Goodbye.");
       input.close();
    }
}
