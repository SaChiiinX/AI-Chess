import java.util.Collections;
import java.util.LinkedList;

public class Pieces{
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
    public long getBitboard(){
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


    public LinkedList<MoveSet> generateMoves(long white, long black){
        LinkedList<MoveSet> retList;

        switch (this.type){
            // Pawn
            case(1):
            case(-1):
            boolean isWhite;
            long opponentPieces;
            long samePieces;
            if (this.type > 0){
                isWhite = true;
                opponentPieces = black;
                samePieces = white;
            }else{
                isWhite = false;
                opponentPieces = white;
                samePieces = black;
            }
            long pawnBitboard = this.getBitboard();
            long allBlockers = white | black;
            retList.add(generatePawnCapturingLeft(isWhite, pawnBitboard, opponentPieces));
            retList.add(generatePawnCapturingRight(isWhite, pawnBitboard, opponentPieces));
            retList.add(generatePawnMoveOneSquare(isWhite, pawnBitboard, allBlockers));
            retList.add(generatePawnMoveTwoSquares(isWhite, pawnBitboard, allBlockers));
            break;
            
            // Knight
            case(2):
            case(-2):
            retList = generateKnightMoves(isWhite, this.bitboard, samePieces);
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
            retList.add(generateKingMoves(isWhite, this.bitboard, samePieces));
            break;
            default:
            throw new UnsupportedOperationException("Invalid Piece");
        }
        Collections.shuffle(retList);
        return retList; 
    }      
    
    public MoveSet generateKingMoves(boolean isWhite, long kingPos, long sameColor){
        // Set up bitboard for attack bitboard
        long bitboard = kingMovesMap.get(kingPos) & ~sameColor;
        // Set pieceType, will not change
        short pieceType = isWhite ? 6 :(short) -6;
        // Set up num, starts at 0 for "first" knight, will increment throughout the loop
        short num = 0;
        // retrieve precomputed moves from the hashmap
        return new MoveSet(pieceType, num, bitboard);
    }


    public MoveSet generateRookMoves(){
        return null;
    }

    public MoveSet generateBishopMoves(){
        return null;
    }

    public static MoveSet getKnightMoves(boolean isWhite, short num, long knightPos, long sameColor){
        long bitboard = knightMovesMap.get(knightPos) & ~sameColor;
        short pieceType = isWhite ? 2 : (short)-2;
        return new MoveSet(pieceType, num, bitboard);
    }

    public LinkedList<MoveSet> generateKnightMoves(boolean isWhite, long knightPos, long sameColor){
        // Set up return list of moves for all knights
        LinkedList<MoveSet> moveSetList = new LinkedList<MoveSet>();
        // Get "first" knight in the bitboard
        long movingPiece = Utility.getLSB(knightPos);
        // Set up num, starts at 0 for "first" knight, will increment throughout the loop
        short num = 0;
        // Set up MoveSet to use for each knight's moves
        MoveSet attack;
        // As long as there is a bit set to 1 in the knight bitboard
        while(movingPiece != 0){
            // Get MoveSet for the single knight
            attack = getKnightMoves(isWhite, num, movingPiece, sameColor);
            // Put new MoveSet in the list
            moveSetList.add(attack);
            // Increment num for the next knight (e.g. second LSB after first was computed)
            num++;
            // Remove already computed knight from knight bitboard
            knightPos -= movingPiece;
            // Get next LSB/next knight on the board, 0 if none
            movingPiece = Utility.getLSB(knightPos);
        }
        return moveSetList;
    }

    public MoveSet generatePawnCapturingLeft(boolean isWhite, long pawnBitboard, long opponentPieces){
        long leftCaptureMask = isWhite ? 0x7F7F7F7F7F7F7F7FL : 0xFEFEFEFEFEFEFEFEL;
        long opponentsShifted = isWhite ? opponentPieces >>> 9 : opponentPieces << 7;
        long bitboard = (pawnBitboard & opponentsShifted) & leftCaptureMask;
        short pieceType = isWhite ? 1 : (short)-1;
        short num = isWhite ? (short)9 : 7;
        return new MoveSet(pieceType, num, bitboard);
    }

    public MoveSet generatePawnCapturingRight(boolean isWhite, long pawnBitboard, long opponentPieces){
        long rightCaptureMask = isWhite ? 0xFEFEFEFEFEFEFEFEL : 0x7F7F7F7F7F7F7F7FL;
        long opponentsShifted = isWhite ? opponentPieces >>> 7 : opponentPieces << 9;
        long bitboard = (pawnBitboard & opponentsShifted) & rightCaptureMask;
        short pieceType = isWhite ? 1 : (short)-1;
        short num = isWhite ? (short)7 : 9;
        return new MoveSet(pieceType, num, bitboard);
    }

    public MoveSet generatePawnMoveOneSquare(boolean isWhite, long pawnBitboard, long allPieces){
        long piecesShifted = isWhite ? allPieces >>> 8 : allPieces << 8;
        long bitboard = pawnBitboard & ~piecesShifted;
        short pieceType = isWhite ? 1 : (short)-1;
        short num = 8;
        return new MoveSet(pieceType, num, bitboard);
    }

    public MoveSet generatePawnMoveTwoSquares(boolean isWhite, long pawnBitboard, long allPieces){
        long startingRank = isWhite ? 0xFF000000000000L : 0xFFL;
        long piecesOne = isWhite ? allPieces >>> 8 : allPieces << 8;
        long piecesTwo = isWhite ? allPieces >>> 16 : allPieces << 16;
        long bitboard = (pawnBitboard & ~piecesTwo & ~piecesOne & startingRank);
        short pieceType = isWhite ? 1 : (short)-1;
        short num = 16;
        return new MoveSet(pieceType, num, bitboard);
    }


}