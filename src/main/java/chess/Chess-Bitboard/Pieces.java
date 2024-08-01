import java.util.Collections;
import java.util.LinkedList;

public class Pieces{
    public final short WHITE_KING = 6;
    public final short BLACK_KING= -6;
    public final short WHITE_QUEEN = 5;
    public final short BLACK_QUEEN = -5;
    public final short WHITE_ROOK = 4;
    public final short BLACK_ROOK = -4;
    public final short WHITE_BISHOP = 3;
    public final short BLACK_BISHOP = -3;
    public final short WHITE_KNIGHT = 2;
    public final short BLACK_KNIGHT = -2;
    public final short WHITE_PAWN = 1;
    public final short BLACK_PAWN = -1;

    // bitboard for the pieces
    private long bitboard;
    // type of the piece
    private short type;

    // a function that generates bitboards for each piece at the starting position
    // white pieces -> positive numbers; black pieces -> negative numberss
    public Pieces(short type){
        this.type = type;

        switch (type){
            // Pawn
            case(1):
            case(-1):
            this.bitboard = 0b1111111100000000L;
            break;
            
            // Knight
            case(2):
            case(-2):
            this.bitboard = 0b01000010L;
            break;
            
            // Bishop
            case(3):
            case(-3):
            this.bitboard = 0b00100100L;
            break;
            
            // Rook
            case(4):
            case(-4):
            this.bitboard = 0b10000001L;
            break;
            
            // Queen
            case(5):
            case(-5):
            this.bitboard = 0b00001000L;
            break;
                
            // King
            case(6):
            case(-6):
            this.bitboard = 0b00010000L;
            break;
            default:
            throw new UnsupportedOperationException("Invalid Index given for Piece type");
        }

        if (type == -1){
            this.bitboard = this.bitboard << 40;
        }else if(type < 0){
            this.bitboard = (this.bitboard << 56);
        }
    }

    public Pieces(short assign, long curPositions){
        this.type = assign;
        this.bitboard = curPositions;
    }

    // return the bitboard 
    public long getBitBoard(){
        return this.bitboard;
    }

    // return the type of the piece
    public short getType(){
        return this.type;
    }

    // get the color of the piece
    public boolean getColor(){
        return this.type > 0;
    }

    // given a move, update the bitboard to reflect the move
    public void updateBitBoard(long move){
        this.bitboard = move;
    }

    // print the pieces on the board
    public String toString() {
        char retChar;
        switch (this.type) {
            // Pawn
            case 1:
                retChar = '\u2659'; // white pawn
                break;
            case -1:
                retChar = '\u265F'; // black pawn
                break;
                
            // Knight
            case 2:
                retChar = '\u2658'; // white knight
                break;
            case -2:
                retChar = '\u265E'; // black knight
                break;
                
            // Bishop
            case 3:
                retChar = '\u2657'; // white bishop
                break;
            case -3:
                retChar = '\u265D'; // black bishop
                break;
                
            // Rook
            case 4:
                retChar = '\u2656'; // white rook
                break;
            case -4:
                retChar = '\u265C'; // black rook
                break;
                
            // Queen
            case 5:
                retChar = '\u2655'; // white queen
                break;
            case -5:
                retChar = '\u265B'; // black queen
                break;
                    
            // King
            case 6:
                retChar = '\u2654'; // white king
                break;
            case -6:
                retChar = '\u265A'; // black king
                break;
                
            default:
                throw new UnsupportedOperationException("Invalid Piece");
        }
        
        return retChar + "";
    }

    // a function that generates moves for each piece
    // it calls helper functions for each piece type
    public LinkedList<Move> generateMoves(long white, long black){
        LinkedList<Move> retList;

        switch (this.type){
            // Pawn
            case(1):
            retList = generateWhitePawnMoves(white, black);
            break;
            case(-1):
            retList = generateBlackPawnMoves(white,black);
            break;
            
            // Knight
            case(2):
            case(-2):
            retList = generateKnightMoves(white,black);
            break;
            
            // Bishop
            case(3):
            case(-3):
            retList = generateBishopMoves(white,black);
            break;
            
            // Rook
            case(4):
            case(-4):
            retList = generateRookMoves(white,black);
            break;
            
            // Queen
            case(5):
            case(-5):
            retList = generateBishopMoves(white,black);
            retList.addAll(generateRookMoves(white,black));
            break;
                
            // King
            case(6):
            case(-6):
            retList = generateKingMoves(white,black);
            break;
            default:
            throw new UnsupportedOperationException("Invalid Piece");
        }
        Collections.shuffle(retList);
        return retList; 
    }       

    public LinkedList<Move> generateKingMoves(long white, long black) {
        LinkedList<Move> legalMoves = new LinkedList<Move>();
        long sameColor;
        long opColor;

        if (this.type > 0){
            sameColor = white;
            opColor = black;
        }else{
            sameColor = black;
            opColor = white;
        }

        long kingPos = this.bitboard;
        int[] moveLocations = {-9,-8,-7,-1,1,7,8,9};
        moveLocations = Utility.onBoardShifts(kingPos, moveLocations);

        for (int move : moveLocations){
            boolean captured = false;
            long mask = move > 0 ? kingPos << move : kingPos >>> -move;

            if ((mask == 0) || (sameColor & mask) != 0){
                continue;
            }
            
            if((opColor & mask) != 0){
                captured = true;
            }

            Move newMove = new Move(mask,this.type, Utility.getIndex(kingPos), captured);
            legalMoves.add(newMove);
        }
        return legalMoves;
    }

    public LinkedList<Move> generateRookMoves(long white, long black) {
        
        // the list that will hold all legal moves for the rook
        LinkedList<Move> legalMoves = new LinkedList<>();
        // the piece that is moving
        long movingPiece;
        long usedBitboard = bitboard;
        // the bitboard with all the pieces with the same color as the rook
        long color;
        // the bitboard with all the pieces with the opposite color to the rook
        long oppColor;

        // Intializes corresponding boards
        if(type > 0){
            color = white;
            oppColor = black;
        }
        else{
            color = black;
            oppColor = white;
        }

        // all of the possible shifts
        // left shifts are represented as positive, right shifts as negative
        int[] shifts = {1, 8, -1, -8};

        // a do-while loop that generates moves for each rook on the board
        do{
            // get one of the rooks on the board (the one whose location is the least significant bit)
            movingPiece = Utility.getLSB(usedBitboard);
            long ogPiece = movingPiece;

            // for each of the possible shifts
            for(int shift : shifts){
                movingPiece = ogPiece;

                // while the shift is valid (still on board)
                while(Utility.onBoard(movingPiece, shift)){
                    // update movingPiece so that is has rook in its new position
                    movingPiece = (shift > 0) ? (movingPiece << shift) : (movingPiece >>> -shift);

                    // "movingPiece & color" will not be 0 if the rook tries to move to a square that
                    // is occupied by a piece of the same color
                    if((movingPiece & color) != 0){
                        break;
                    }
                    // "movingPiece & oppColor" will not be 0 if the rook tries to move to a square that
                    // is occupied by an opponent piece
                    else if((movingPiece & oppColor) != 0){
                        // create a new move and specify that we captured the opponent piece
                        Move m = new Move(movingPiece, this.type, Utility.getIndex(ogPiece), true);
                        // add move to the list of legal moves
                        legalMoves.add(m);
                        break;
                    }
                    // else the square is not occupied
                    else{
                        // create a new move
                        Move m = new Move(movingPiece, this.type, Utility.getIndex(ogPiece), false);
                        // add move to the list of legal moves
                        legalMoves.add(m);
                    }
                    
                    
                }
            }
            usedBitboard -= ogPiece;
        }while(usedBitboard != 0);

        return legalMoves;
    }

    public LinkedList<Move> generateBishopMoves(long white, long black) {
        // the list that will hold all legal moves for the bishop
        LinkedList<Move> legalMoves = new LinkedList<>();
        // the piece that is moving
        long movingPiece;
        long usedBitboard = bitboard;
        // the bitboard with all the pieces with the same color as the bishop
        long color;
        // the bitboard with all the pieces with the opposite color to the bishop
        long oppColor;

        // Intializes corresponding boards
        if(type > 0){
            color = white;
            oppColor = black;
        }
        else{
            color = black;
            oppColor = white;
        }

        // all of the possible shifts
        // left shifts are represented as positive, right shifts as negative
        int[] shifts = {7, 9, -7, -9};

        // a do-while loop that generates moves for each bishop on the board
        do{
            // get one of the bishops on the board (the one whose location is the least significant bit)
            movingPiece = Utility.getLSB(usedBitboard);
            long ogPiece = movingPiece;

            // for each of the possible shifts
            for(int shift : shifts){
                movingPiece = ogPiece;
                // while the shift is valid (still on board)
                while(Utility.onBoard(movingPiece, shift)){
                    // update movingPiece so that is has bishop in its new position
                    movingPiece = (shift > 0) ? (movingPiece << shift) : (movingPiece >>> -shift);
                    // "movingPiece & color" will not be 0 if the bishop tries to move to a square that
                    // is occupied by a piece of the same color
                    if((movingPiece & color) != 0){
                        break;
                    }
                    // "movingPiece & oppColor" will not be 0 if the bishop tries to move to a square that
                    // is occupied by an opponent piece
                    else if((movingPiece & oppColor) != 0){
                        // create a new move and specify that we captured the opponent piece
                        Move m = new Move(movingPiece, this.type, Utility.getIndex(ogPiece), true);
                        // add move to the list of legal moves
                        legalMoves.add(m);
                        break;
                    }
                    // else the square is not occupied
                    else{
                        // create new move
                        Move m = new Move(movingPiece, this.type, Utility.getIndex(ogPiece), false);
                        // add move to list of legal moves
                        legalMoves.add(m);
                    }
                }
            }
            usedBitboard -= ogPiece;
        }while(usedBitboard != 0);

        return legalMoves;
    }

    public LinkedList<Move> generateKnightMoves(long white, long black) {
        // the list that will hold all legal moves for the knight
        LinkedList<Move> legalMoves = new LinkedList<>();

        // knight's bitboard
        long newBitBoard = this.bitboard;
        // the piece that is moving
        long movingPiece;
        // all of the possible shifts
        // left shifts are represented as positive, right shifts as negative
        int[] shiftFactor = {6, 10, 15, 17, -6, -10, -15, -17};
        
        // the bitboard with all the pieces with the same color as the knight
        long sameColor;
        // the bitboard with all the pieces with the opposite color to the knight
        long opColor;

        // Intializes corresponding boards
        if (this.type > 0){
            sameColor = white;
            opColor = black;
        }else{
            sameColor = black;
            opColor = white;
        }

        // a do-while loop that generates moves for each knight on the board
        do{
            // get one of the knights on the board (the one whose location is the least significant bit)
            movingPiece = Utility.getLSB(newBitBoard);
            // go through the shifts to only keep valid ones
            int[] legalShifts = Utility.onBoardShifts(movingPiece, shiftFactor);

            // for each valid shift
            for(int shift : legalShifts) {
                // initialize captured to false
                boolean captured = false;
                // mask will represent the piece in its new position after the shift
                long mask = shift > 0 ? movingPiece << shift : movingPiece >>> -shift;

                // "sameColor & mask" will be > 0 only if the knight attempts to move
                // to a square occupied by a piece of the same color
                if ((mask == 0) || ((sameColor & mask) != 0)){
                    continue;

                // "opColor & mask" will be > 0 only if the knight attempts to move
                // to a square occupied by an opponent piece
                }else if ((opColor & mask) != 0){
                    // capture the opponent piece
                    captured = true;
                }
 
                // create the new move
                Move newMove = new Move(mask,this.type, Utility.getIndex(movingPiece), captured);
                // add move to list of legal moves
                legalMoves.add(newMove);
            }
            newBitBoard -= movingPiece; 
        }while(movingPiece != 0);
        return legalMoves;
    }

    /* Methods to create a list of legal moves for all pawns
     * Returns a list of bitboards with one pawn in its new location 
     * and old location zeroed out.
     */

    public LinkedList<Move> generateWhitePawnMoves(long white, long black){
        LinkedList<Move> legalMoves = new LinkedList<>();
        long newBitBoard = this.bitboard;
        long movingPiece;
        int[] shifts = {8, 16, 7, 9};

        do{
            movingPiece = Utility.getLSB(newBitBoard);
            int[] legalShifts = Utility.onBoardShifts(movingPiece, shifts);
            for(int shift : legalShifts){
                // Create a mask to determine occupancy: piece to be moved shifted by given amount
                long mask = movingPiece << shift;
                // Case for moving forward
                if((shift % 8) == 0){
                    // If square immediately in front is not occupied
                    if((Utility.maskGetBit(white, movingPiece << 8) == 0) && (Utility.maskGetBit(black, movingPiece << 8) == 0)){
                        // If moving up 2 squares in starting position and shift position is unoccupied, 
                        // or only moving up 1, add to legal moves
                        if(((shift == 16) 
                                && (movingPiece < (1L << 16)) && (movingPiece > (1L << 7)) 
                                && (Utility.maskGetBit(black, mask) == 0) && (Utility.maskGetBit(white, mask) == 0)) 
                            || (shift == 8)){
                            Move newMove = new Move(mask, this.type, Utility.getIndex(movingPiece), false);
                            legalMoves.add(newMove);
                        }
                    }
                }
                // If square diagonally in front is occupied, moving there is legal
                else{
                    if(Utility.maskGetBit(black, movingPiece << shift) != 0){
                        Move newMove = new Move(mask, this.type, Utility.getIndex(movingPiece), true);
                        legalMoves.add(newMove);
                    }
                }
            }
            newBitBoard -= movingPiece;
        }while(movingPiece != 0);

        return legalMoves;
    }

    public LinkedList<Move> generateBlackPawnMoves(long white, long black) {
        LinkedList<Move> legalMoves = new LinkedList<>();
        long newBitBoard = this.bitboard;
        long movingPiece;
        int[] shifts = {-8, -16, -7, -9};

        do{
            movingPiece = Utility.getLSB(newBitBoard);
            int[] legalShifts = Utility.onBoardShifts(movingPiece, shifts);
            for(int shift : legalShifts){
                // Create a mask to determine occupancy: piece to be moved shifted by given amount
                long mask = movingPiece >>> -shift;
                // Case for moving forward
                if((shift % 8) == 0){
                    // If square immediately in front is not occupied
                    if((Utility.maskGetBit(white, movingPiece >>> 8) == 0) && (Utility.maskGetBit(black, movingPiece >>> 8) == 0)){
                        // If moving up 2 squares and in starting position and shift position is unoccupied, 
                        // or only moving up 1, add to legal moves
                        if(((shift == -16) 
                                && (movingPiece < (1L << 56)) && (movingPiece > (1L << 47))
                                && (Utility.maskGetBit(white, mask) == 0) && (Utility.maskGetBit(black, mask) == 0))
                            || (shift == -8)){
                            Move newMove = new Move(mask, this.type, Utility.getIndex(movingPiece), false);
                            legalMoves.add(newMove);
                        }
                    }
                }
                // If square diagonally in front is occupied, moving there is legal
                else{
                    if(Utility.maskGetBit(white, mask) != 0){
                        Move newMove = new Move(mask, this.type, Utility.getIndex(movingPiece), true);
                        legalMoves.add(newMove);
                    }
                }
            }
            newBitBoard -= movingPiece;
        }while(movingPiece != 0);

        return legalMoves;
    }
}