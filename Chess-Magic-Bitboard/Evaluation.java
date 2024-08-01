public class Evaluation {

    public static double sachEvaluate(Game game) {
        boolean color = game.getTurn();
        Pieces[] sameColor;
        Pieces[] opColor;
        int eval = 0;

        eval += color ? game.getWhiteNumMoves() - game.getBlackNumMoves() : game.getBlackNumMoves() - game.getWhiteNumMoves();
        eval /= 7;

        if (color){
            sameColor = game.getGameBoard().getWhitePieces();
            opColor = game.getGameBoard().getBlackPieces();
            //eval += 1;
        } else{
            sameColor = game.getGameBoard().getBlackPieces();
            opColor = game.getGameBoard().getWhitePieces();
        }

        if(sameColor[4].getBitboard() == 0){
            eval -= 0.9;
        }

        int numOfMoves = game.getNumMoves();
        if((color && (Utility.getIndex(sameColor[4].getBitboard()) == 3) && (numOfMoves < 20)) 
            || (!color && (Utility.getIndex(sameColor[4].getBitboard()) == 59)) && (numOfMoves < 20) ){
            eval += 0.5*((20-numOfMoves)/numOfMoves);
        }

        double[] pieceVals = {1,3,3.1,5,9};
        for(int i = 0; i < 5; i++){
            long pieceBoard1 = sameColor[i].getBitboard();
            long pieceBoard2 = opColor[i].getBitboard();
            while (pieceBoard1 != 0) {
                eval += pieceVals[i];
                pieceBoard1 -= Utility.getLSB(pieceBoard1);
            }

            while (pieceBoard2 != 0) {
                eval -= pieceVals[i];
                pieceBoard2 -= Utility.getLSB(pieceBoard2);
            }
        }

        return eval;
    }
}
