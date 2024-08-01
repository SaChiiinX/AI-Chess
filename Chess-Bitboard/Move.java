keypublic class Move {
    private long move;
    private short pieceType;
    private int previousIndex;
    private boolean captured;
    
    public Move(long move, short piece, int previousIndex, boolean captured){
        this.move = move;
        this.pieceType = piece;
        this.previousIndex = previousIndex;
        this.captured = captured;
    }

    public long getMoveBit() {
        return this.move;
    }

    public short getPieceType() {
        return this.pieceType;
    }

    public int getPreviousIndex(){
        return this.previousIndex;
    }

    public boolean didCapture(){
        return this.captured;
    }

    public String toString(){
        String retString = "Piece: " + pieceType + " ";
        retString += "From: " + previousIndex + " ";
        retString += "To: " + Utility.getIndex(move) + " ";
        retString += "Capt? " + captured;

        return retString;
    }
}
