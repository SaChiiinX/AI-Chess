import java.util.LinkedList;

public class AlphaBeta {
    private static final double LOSS_VALUE = Integer.MIN_VALUE;
    private static final double WIN_VALUE = Integer.MAX_VALUE;
    private static final double NEG_INF = Long.MIN_VALUE;
    private static final double POS_INF = Long.MAX_VALUE;

    public static Move computeMove(Game game, int cutoffDepth) {
        if (game.isGameOver()){
            return null;
        }else{
            return computeBestMove(game, 0, NEG_INF, POS_INF, cutoffDepth);
        }
    }

    private static Move computeBestMove(Game game, int currDepth, double alpha, double beta, int cutoffDepth) {
        LinkedList<MoveSet> legalMoveSets = generateLegalMoveSets(game);
        Move bestMove = null;
        double bestValue = alpha;
        Move curMove = null;
        for(MoveSet moveSet : legalMoveSets){
            while(moveSet.getBitboard() != 0){
                curMove = moveSet.firstMove(game.getGameBoard().getWhitePieces(), game.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                game.doMove(curMove);
                // if check 
                // move 
                double moveValue = computeMin(game, currDepth, bestValue, beta, cutoffDepth);
                game.undoMove();
                if (moveValue > bestValue){
                    bestValue = moveValue;
                    bestMove = curMove;
                }
            }
        }
        return bestMove;
    }

    private static double computeMax(Game game, int currDepth, double alpha, double beta, int cutoffDepth){
        if(game.isGameOver()){
            return LOSS_VALUE;
        }else if(currDepth == cutoffDepth){
            return Evaluation.sachEvaluate(game);
        }
    
        double bestVal = alpha;
        Move curMove = null;
        LinkedList<MoveSet> legalMoveSets = generateLegalMoveSets(game);
        for(MoveSet moveSet : legalMoveSets){
            while(moveSet.getBitboard() != 0){
                curMove = moveSet.firstMove(game.getGameBoard().getWhitePieces(), game.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                game.doMove(curMove);
                double moveValue = computeMin(game, currDepth, bestVal, beta, cutoffDepth);
                game.undoMove();
            
                if(moveValue > bestVal){
                    bestVal = moveValue;
                    if (beta <= bestVal){
                        break;
                    }
                }
            }
        }
        return bestVal;
    }

    private static double computeMin(Game game, int currDepth, double alpha, double beta, int cutoffDepth){
        if (game.isGameOver()) {
            return WIN_VALUE;
        }else if(currDepth == cutoffDepth){
            return Evaluation.sachEvaluate(game);
        }

        double bestVal = beta;
        Move curMove = null;
        LinkedList<MoveSet> legalMoveSets = generateLegalMoveSets(game);
        for(MoveSet moveSet : legalMoveSets){
            while(moveSet.getBitboard() != 0){
                curMove = moveSet.firstMove(game.getGameBoard().getWhitePieces(), game.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                game.doMove(curMove);
                double moveValue = computeMax(game, (currDepth + 1), alpha, bestVal, cutoffDepth);
                game.undoMove();
                if(moveValue < bestVal){
                    bestVal = moveValue;
                    if (bestVal <= alpha){
                        break;
                    }
                }
            }
        }
        return bestVal;
    }

    public static LinkedList<MoveSet> generateLegalMoveSets(Game game){
        LinkedList<MoveSet> moves = new LinkedList<MoveSet>();
        long white = game.getGameBoard().getFullWhite();
        long black = game.getGameBoard().getFullBlack();
        int numMoves = game.getNumMoves();
        boolean turn = game.getTurn();
        Pieces[] myPieces = turn ? game.getGameBoard().getBlackPieces() : game.getGameBoard().getWhitePieces();
        if(numMoves < 15){
            for(int i = 0; i < 6; i++){
                moves.addAll(myPieces[i].generateMoves(white, black));
            }
        }
        else{
            for(int i = 5; i > 1; i--){
                moves.addAll(myPieces[i].generateMoves(white, black));
            }
            moves.addAll(myPieces[5].generateMoves(white, black));
            moves.addAll(myPieces[0].generateMoves(white, black));
        }

        return moves;
    }
}