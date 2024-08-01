public class Evaluation {

    public static double sachEvaluate(Game game) {
        boolean color = game.getTurn();
        Pieces[] sameColor;
        Pieces[] opColor;
        int eval = 0;
        int numOfMoves = game.getNumMoves();
        //long centerCol1 = Utility.a_file << 3;
        //long centerCol2 = Utility.a_file << 4;
        
        if (color){
            sameColor = game.getGameBoard().getWhitePieces();
            opColor = game.getGameBoard().getBlackPieces();
            long queenBitboard = sameColor[4].getBitBoard();
            //long pawnBitboard = sameColor[0].getBitBoard();

            eval += (game.getWhiteNumMoves() - game.getBlackNumMoves())/7.5;

            if(numOfMoves < 30){
                eval += 0.1;

                /* 
                if(((pawnBitboard & centerCol1) != 0) && ((pawnBitboard & centerCol2) != 0)){
                    eval += 0.05;
                }
                */

                if(Utility.getIndex(queenBitboard) == 3 ){
                    eval += 0.5*((30-numOfMoves)/numOfMoves);
                }
            }

            if(queenBitboard == 0){
                eval -= 1.1;
            }

        } else{
            sameColor = game.getGameBoard().getBlackPieces();
            opColor = game.getGameBoard().getWhitePieces();
            long queenBitboard = sameColor[4].getBitBoard();
            long pawnBitboard = sameColor[0].getBitBoard();

            eval += (game.getBlackNumMoves() - game.getWhiteNumMoves())/7.5;

            if(queenBitboard == 0){
                eval -= 0.9;
            }

            if(numOfMoves < 30){

                /* 
                if(((pawnBitboard & centerCol1) != 0) && ((pawnBitboard & centerCol2) != 0)){
                    eval += 0.05;
                }
                */

                if(Utility.getIndex(queenBitboard) == 59){
                    eval += 0.5*((30-numOfMoves)/numOfMoves);
                }
            }   
        }

        double[] pieceVals = {1,3,3.1,5,9};
        for(int i = 0; i < 5; i++){
            long pieceBoard1 = sameColor[i].getBitBoard();
            long pieceBoard2 = opColor[i].getBitBoard();
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
