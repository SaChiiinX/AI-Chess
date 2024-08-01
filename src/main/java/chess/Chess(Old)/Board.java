import java.util.*;

public class Board {
    private final int pawn = 1, knight = 2, bishop = 3, rook = 4, queen = 5, king = 6;
    private final char[] white_chars = {'P', 'N', 'B', 'R', 'Q', 'K'};
    private final char[] black_chars = {'p', 'n', 'b', 'r', 'q', 'k'};
    private int[] white_pieces = new int[32];
    private int[] black_pieces = new int[32];
    private int[] board = new int[64];

    Board() {
        for (int i = 0; i < 8; i++) {
            board[i+8] = pawn;
            board[i+48] = -pawn;
        }

        board[0] = rook;
        board[1] = knight;
        board[2] = bishop;
        board[3] = queen;  
        board[4] = king;
        board[5] = bishop;
        board[6] = knight;
        board[7] = rook;

        board[56] = rook;
        board[57] = knight;
        board[58] = bishop;
        board[59] = queen;  
        board[60] = king;
        board[61] = bishop;
        board[62] = knight;
        board[63] = rook;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        String boardString =  "  0 | 1 | 2 | 3 | 4 | 5 | 6 | 7\n";
        String sepString = "--------------------------------\n";
        String befChaString = "| ";
        String afChaString = " | "; 

        str.append(boardString).append(sepString);
        for(int i = 0; i < 8; i++){
            str.append(befChaString);
            for(int j = 0; j < 8; j++){
                str.append(intToString(this.board[i*8+j])).append(afChaString);
            }
            str.append("\n").append(sepString);
        }

        return str.toString();
    }

    private String intToString(int piece) {
        if(piece  == 0){
            return " ";
        }else if(piece > 0){
            return white_chars[piece-1] + "";
        }else{
            return black_chars[-piece-1] + "";
        }


    }

    public LinkedList<Integer> getLegalMoves(int piece, int pos){
        LinkedList<Integer> legalMoves = new LinkedList<>();
        switch(piece){
            // Pawn
            case 1:
                if((8 <= pos) && (pos <= 15)){
                    if(opponentPresent(piece, pos + 16))
                        legalMoves.add(pos + 16);
                }
                if(!opponentPresent(piece, pos + 8))
                    legalMoves.add(pos + 8);
                if(opponentPresent(piece, pos + 7))
                    legalMoves.add(pos + 7);
                if(opponentPresent(piece, pos + 7))
                    legalMoves.add(pos + 9);
                break;
            case -1:
                if((48 <= pos) && (pos <= 55)){
                    if(!opponentPresent(piece, pos - 16))
                        legalMoves.add(pos - 16);
                }
                if(!opponentPresent(piece, pos - 8))
                    legalMoves.add(pos -8);
                // I think this math is right? But if not someone let me know
                if(opponentPresent(piece, pos - 7))
                    legalMoves.add(pos - 7);
                if(opponentPresent(piece, pos - 9))
                    legalMoves.add(pos - 9);
                break;
            // Knight
            case 2:
                break;
            case -2:
                break;
            // Queen
            case 5:
                // TODO: I don't think we can fall through to both unfortunately but could just copy
                piece = 3;
            case -5:
                piece = -3;
            // Bishop
            case 3:
                break;
            case -3:
                break;
            // Rook
            case 4:
                break;
            case -4:
                break;
            // King
            case 6:
                break;
            case -6:
            /*
             * +7, +8, +9
             * -1, pos, +1
             * -9, -8, -7
             * 
             * Check if in 0 <= Possible Moves <= 63
             * Castle Boolean? Should be added to pieces
             *  - 0 = Can't castle
             *  - 1 = Castle leftside only
             *  - 2 = Castle rightside only
             *  - 3 = Castle either side
             * isCheckedSquare
             */
            int[] posMoves = {7+pos,8+pos,9+pos,-1+pos,1+pos,-9+pos,-8+pos,-7+pos};
            // Piece at index
            int tempPiece;
            int posPosition;
            int castle = 0;
            for(int i = 0; i < 8; i++){
                posPosition = posMoves[i];
                tempPiece = this.board[posPosition];
                if(tempPiece == 0 || ((tempPiece > 0 ^ piece > 0) && !isCheckedSquare(posMoves[i], piece > 0))){
                    legalMoves.add(posMoves[i]);
                }
            }
            switch (castle) {
                case 3:
                legalMoves.add(pos+2);
                legalMoves.add(pos-2);
                break;
                case 2:
                legalMoves.add(pos+2);
                break;
                case 1:
                legalMoves.add(pos-2);
                break;
                case 0:
                break;
                default:
                throw new UnsupportedOperationException("Unimplemented method 'isCheckedSquare'");
            }

            break;
        }
        return null;
    }


    private boolean isCheckedSquare(int move, boolean isWhite) {
        throw new UnsupportedOperationException("Unimplemented method 'isCheckedSquare'");
    }

    /* TODO: This is my recommended helper function for checking if an opponent is present in a certain
     * position, but I don't think it's very efficient and perhaps we should rethink bitboard?
     * Fine with others' opinions though
     * I can see this being much more efficient with bitboards bc rn we have to go through
     * the entire list of pieces any time we want to check if a piece is present as opposed
     * to like 6 bit ands?
     */
    private boolean opponentPresent(int piece, int pos){
        if(piece < 0) {
            for (int i = 1; i < white_pieces.length; i += 2) {
                if (white_pieces[i] == pos) {
                    return true;
                }
            }
        }
        else{
            for (int i = 1; i < black_pieces.length; i += 2) {
                if (black_pieces[i] == pos) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Board b1 = new Board();
        System.out.println(b1.toString());
    }
}
