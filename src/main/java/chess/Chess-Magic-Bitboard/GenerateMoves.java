import java.util.HashMap;
import java.util.*;

public class GenerateMoves {

    // PAWNS HERE
    // pawns capturing up left
    public static MoveSet generatePawnCapturingLeft(boolean isWhite, long pawnBitboard, long opponentPieces) {
        long leftCaptureMask = 0xFEFEFEFEFEFEFEFEL;
        long opponentsShifted = isWhite ? opponentPieces >>> 7 : opponentPieces << 9;
        long bitboard = (pawnBitboard & opponentsShifted) & leftCaptureMask;
        short pieceType = isWhite ? 1 : (short)-1;
        short num = isWhite ? (short)9 : 7;
        return new MoveSet(pieceType, num, bitboard);
    }

    // pawns capturing up right
    public static MoveSet generatePawnCapturingRight(boolean isWhite, long pawnBitboard, long opponentPieces) {
        long rightCaptureMask = 0x7F7F7F7F7F7F7F7FL;
        long opponentsShifted = isWhite ? opponentPieces >>> 9 : opponentPieces << 7;
        long bitboard = (pawnBitboard & opponentsShifted) & rightCaptureMask;
        short pieceType = isWhite ? 1 : (short)-1;
        short num = isWhite ? (short)7 : 9;
        return new MoveSet(pieceType, num, bitboard);
    }

    // pawns moving 1 square forward
    public static MoveSet generatePawnMoveOneSquare(boolean isWhite, long pawnBitboard, long allPieces) {
        long piecesShifted = isWhite ? allPieces >>> 8 : allPieces << 8;
        System.out.println(piecesShifted);
        long bitboard = pawnBitboard & ~piecesShifted;
        System.out.println(bitboard);
        short pieceType = isWhite ? 1 : (short)-1;
        short num = 8;
        return new MoveSet(pieceType, num, bitboard);
    }

    // pawns moving 2 squares forward
    public static MoveSet generatePawnMoveTwoSquares(boolean isWhite, long pawnBitboard, long allPieces) {
        long startingRank = isWhite ? 0xFF00L : 0xFF000000000000L;
        long piecesOne = isWhite ? allPieces >>> 8 : allPieces << 8;
        long piecesTwo = isWhite ? allPieces >>> 16 : allPieces << 16;
        long bitboard = (pawnBitboard & ~piecesTwo & ~piecesOne & startingRank);
        short pieceType = isWhite ? 1 : (short)-1;
        short num = 16;
        return new MoveSet(pieceType, num, bitboard);
    }


    // knight moves hashmap
    private static final HashMap<Long, Long> knightMovesMap = new HashMap<>();

    // king moves hashmap
    private static final HashMap<Long, Long> kingMovesMap = new HashMap<>();

    // populate the knight map
    private static void initializeKnightMoves() {
        for (int i = 0; i < 64; i++) {
            long knightPos = 1L << i;
            long knightMoves = generateKnightMoves(knightPos);
            knightMovesMap.put(knightPos, knightMoves);
        }
    }

    // populate the king map
    private static void initializeKingMoves() {
        for (int i = 0; i < 64; i++) {
            long kingPos = 1L << i;
            long kingMoves = generateKingMoves(kingPos);
            kingMovesMap.put(kingPos, kingMoves);
        }
    }

    // KNIGHTS HERE ----------------------------
    // generate knight moves for a given knight position
    public static long generateKnightMoves(long knightPos) {
        long knightMoves = 0L;
        long a_file = Utility.a_file;
        long h_file = Utility.h_file;

        // calc all possible knight moves
        long m1 = (knightPos & ~(a_file | (a_file << 1))) << 6;
        long m2 = (knightPos & ~(a_file | h_file)) << 15;
        long m3 = (knightPos & ~(h_file | (h_file >>> 1))) << 17;
        long m4 = (knightPos & ~(h_file | (h_file >>> 1))) << 10;
        long m5 = (knightPos & ~(h_file | a_file)) >>> 6;
        long m6 = (knightPos & ~(h_file | a_file)) >>> 15;
        long m7 = (knightPos & ~(a_file | (a_file << 1))) >>> 17;
        long m8 = (knightPos & ~(a_file | (a_file << 1))) >>> 10;

        // combine all possible moves
        knightMoves |= (m1 | m2 | m3 | m4 | m5 | m6 | m7 | m8);

        // mask out moves that go off the board
        knightMoves &= ~((knightPos << 6) | (knightPos << 15) | (knightPos << 17) | 
                        (knightPos << 10) | (knightPos >>> 6) | (knightPos >>> 15) | 
                        (knightPos >>> 17) | (knightPos >>> 10));
    
        return knightMoves;
    }

    // KING HERE ----------------------------
    // generate king moves for a given king position
    private static long generateKingMoves(long kingPos) {
        long kingMoves = 0L;
        long a_file = Utility.a_file;
        long h_file = Utility.h_file;

        // calc all possible king moves (l = left, r = right, u = up, d = down)
        long l = (kingPos & ~a_file) << 1;
        long r = (kingPos & ~h_file) >>> 1;
        long u = kingPos << 8;
        long d = kingPos >>> 8;
        long ul = (kingPos & ~a_file) << 9;
        long ur = (kingPos & ~h_file) << 7;
        long dl = (kingPos & ~a_file) >>> 7;
        long dr = (kingPos & ~h_file) >>> 9;

        // combine all possible moves
        kingMoves |= (l | r | u | d | ul | ur | dl | dr);

        // mask out moves that go off the board
        kingMoves &= 0xFFFFFFFFFFFFFF00L;

        return kingMoves;
    }

    // Generate knight moves for one knight in a given position
    public static MoveSet getKnightMoves(boolean isWhite, short num, long knightPos, long sameColor){
        long bitboard = knightMovesMap.get(knightPos) & ~sameColor;
        short pieceType = isWhite ? 2 : (short)-2;
        return new MoveSet(pieceType, num, bitboard);
    }

    // gen knight moves for all knights in specific position
    public static LinkedList<MoveSet> getAllKnightMoves(boolean isWhite, long knightPos, long sameColor) {
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

    // gen king moves for a specific king position
    public static MoveSet getKingMoves(boolean isWhite, long kingPos, long sameColor) {
        // Set up bitboard for attack bitboard
        long bitboard = kingMovesMap.get(kingPos) & ~sameColor;
        // Set pieceType, will not change
        short pieceType = isWhite ? 6 :(short) -6;
        // Set up num, starts at 0 for "first" knight, will increment throughout the loop
        short num = 0;
        // retrieve precomputed moves from the hashmap
        return new MoveSet(pieceType, num, bitboard);
    }

    public static void main(String[] args){
        long pawns = 0b1111111100000000L;
        long opponents = 0b1111110100000000000000000L;
        MoveSet moves = generatePawnMoveTwoSquares(true, pawns, opponents);
        System.out.println(moves.getBitboard());
    }

    public static long getRookMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRookMoves'");
    }

    public static long getBishopMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBishopMoves'");
    }
}
