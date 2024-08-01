public class Board{  
    // Whose turn  
    private Pieces[] white_pieces;
    private Pieces[] black_pieces;

    public Board(){
        white_pieces = new Pieces[6];
        black_pieces = new Pieces[6];
        
        for(short i = 1; i <= 6; i++){
            white_pieces[i-1] = new Pieces(i);
            black_pieces[i-1] = new Pieces((short)(-i));
        }
    }

    public Board(Pieces[] white_pieces, Pieces[] black_pieces){
        this.white_pieces = white_pieces;
        this.black_pieces = black_pieces;
    }

    public short updateBoard(boolean color, long move, int pieceTypeIndex, int previousIndex, boolean captured){
        long same_colorBit;
        Pieces[] op_pieces;

        // Updates bitboard of the color that did the move 
        if (color){
            same_colorBit = white_pieces[pieceTypeIndex].getBitboard();
            same_colorBit = Utility.zeroBit(same_colorBit, previousIndex);
            same_colorBit = Utility.maskSetBit(same_colorBit,move);
            this.white_pieces[pieceTypeIndex].updateBitBoard(same_colorBit);
            op_pieces = black_pieces;
        }else{
            same_colorBit = black_pieces[pieceTypeIndex].getBitboard();
            same_colorBit = Utility.zeroBit(same_colorBit, previousIndex);
            same_colorBit = Utility.maskSetBit(same_colorBit, move);
            this.black_pieces[pieceTypeIndex].updateBitBoard(same_colorBit);
            op_pieces = white_pieces;
        }

        // Checks if piece was taken
        if (captured){
            // Checks what type of piece was taken 
            for(short i = 0; i < 6; i++){
                long tempBitboard = op_pieces[i].getBitboard();
                if(Utility.maskGetBit(tempBitboard, move) != 0){
                    tempBitboard = Utility.maskZeroBit(tempBitboard, move);
                    
                    // Zeroes out the bit of opponent's board that corresponded to the move
                    if (color){
                        this.black_pieces[i].updateBitBoard(tempBitboard);
                    }else{
                        this.white_pieces[i].updateBitBoard(tempBitboard);
                    }
                    // Returns piece type that was taken
                    i++;
                    return i;
                }
            }
        }
        // No piece was taken
        return 0;
    }

    public void revertBoard(boolean color, long move, int pieceTypeIndex, int previousIndex, int captured){
        long same_colorBit;
        Pieces[] op_pieces;

        // Updates bitboard of the color that did the move 
        if (color){
            same_colorBit = white_pieces[pieceTypeIndex].getBitboard();
            same_colorBit =  move ^ Utility.setBit(same_colorBit, previousIndex);
            this.white_pieces[pieceTypeIndex].updateBitBoard(same_colorBit);
            op_pieces = black_pieces;
        }else{
            same_colorBit = black_pieces[pieceTypeIndex].getBitboard();
            same_colorBit =  move ^ Utility.setBit(same_colorBit, previousIndex);
            this.black_pieces[pieceTypeIndex].updateBitBoard(same_colorBit);
            op_pieces = white_pieces;
        }
        
        if (captured > 0){
            captured--;
            long temp_bitboard = op_pieces[captured].getBitboard();
            temp_bitboard = Utility.maskSetBit(temp_bitboard, move);

            if (color){
                this.black_pieces[captured].updateBitBoard(temp_bitboard);
            }else{
                this.white_pieces[captured].updateBitBoard(temp_bitboard);
            }
        }
    }

    public long getFullBlack(){
        long fullBitboard = black_pieces[0].getBitboard();
        for(int i = 1; i < black_pieces.length; i++){
            fullBitboard = fullBitboard | black_pieces[i].getBitboard();
        }
        return fullBitboard;
    }

    public long getFullWhite(){
        long fullBitboard = white_pieces[0].getBitboard();
        for(int i = 1; i < white_pieces.length; i++){
            fullBitboard = fullBitboard | white_pieces[i].getBitboard();
        }

        return fullBitboard;
    }

    public Pieces[] getWhitePieces(){
        return this.white_pieces;
    }

    public Pieces[] getBlackPieces(){
        return this.black_pieces;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        String newRow = "   +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+";
        // String newRow = "-----------------------------------"; if – (endashes) don't work on ur computer use this one then
        String newCol = "| ";
        String space = " ";
        String letters = "abcdefgh";
        
        // Append column labels (a-g) at the bottom
        str.append("\n").append("     ");
        for (int i = 0; i < 8; i++) {
            str.append(letters.charAt(i)).append("   ");
        }

        int index = 56;
        str.append("\n").append(newRow).append("\n");
        
        for(int row = 8; row > 0; row--){
            str.append(space);
            str.append(row).append(" "); // append row numbers on the left side
            for(int col = 8; col > 0; col--){
                str.append(newCol);
                for(int pieceIndex = 0; pieceIndex < 6; pieceIndex++){

                    if(Utility.getBit(white_pieces[pieceIndex].getBitboard(), index) != 0){
                        str.append(white_pieces[pieceIndex].toString());
                        break;
                    }else if(Utility.getBit(black_pieces[pieceIndex].getBitboard(), index) != 0){
                        str.append(black_pieces[pieceIndex].toString());
                        break;
                    }else if(pieceIndex == 5){
                        str.append(" ");
                    }
                }

                str.append(space);
                index++;
            }   
            index-= 16;
            str.append(newCol).append("\n");
            str.append(newRow).append("\n");
        }

        return str.toString();
    }
}
