public class MoveSet {

    private short pieceType; // Positive/negative for white/black
    private short num; // Either 0/1 (0 = lower bit or 1 = higher bit in piece bitboard), or shift for pawn move
    private long bitboard;

    public short getPieceType(){
        return pieceType;
    }
    
    public short getNum(){
        return num;
    }

    public long getBitboard(){
        return bitboard;
    }

    public void orBitboards(long b1){
        bitboard = bitboard | b1;
    }

    public MoveSet(short pieceType, short num, long bitboard){
        this.pieceType = pieceType;
        this.num = num;
        this.bitboard = bitboard;
    }

    public Move firstMove(Pieces[] whitePieces, Pieces[] blackPieces){
        Move move = null;
        Pieces[] myPieces;
        long oppPieces;
        long dest;
        long start;
        boolean capture;
        if(pieceType > 0){
            myPieces = whitePieces;
            oppPieces = blackPieces[0].getBitboard() & blackPieces[1].getBitboard() & blackPieces[2].getBitboard() & blackPieces[3].getBitboard() & blackPieces[4].getBitboard() & blackPieces[5].getBitboard();
        }
        else{
            myPieces = blackPieces;
            oppPieces = whitePieces[0].getBitboard() & whitePieces[1].getBitboard() & whitePieces[2].getBitboard() & whitePieces[3].getBitboard() & whitePieces[4].getBitboard() & whitePieces[5].getBitboard();
        }
        if(pieceType == 1){
            // Case for white pawn
            start = Utility.getLSB(bitboard);
            dest = start << num;
        }
        else if(pieceType == -1){
            // Case for black pawn
            start = Utility.getLSB(bitboard);
            dest = start >>> num;
        }
        else{
            // Case for any other piece
            int pieceIndex = (pieceType > 0) ? pieceType-1 : -(pieceType)-1;
            long pieceBitboard = myPieces[pieceIndex].getBitboard();
            start = Utility.getLSB(pieceBitboard);
            for(int i = 0; i < num; i++){
                pieceBitboard -= start;
                start = Utility.getLSB(pieceBitboard);
            }
            dest = Utility.getLSB(bitboard);
        }
        capture = (dest & oppPieces) != 0;
        move = new Move(dest, pieceType, Utility.getIndex(start), capture);
        return move;
    }

    public MoveSet next(){
        bitboard = bitboard - Utility.getLSB(bitboard);
        return this;
    }
}
