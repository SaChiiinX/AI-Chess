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
        LinkedList<Move> legalMoves = game.generateLegalMoves(); 
        Move bestMove = null;
        double bestValue = alpha;
        for(Move move : legalMoves){
            game.doMove(move);
            double moveValue = computeMin(game, currDepth, bestValue, beta, cutoffDepth);
            game.undoMove();
            if (moveValue > bestValue){
                bestValue = moveValue;
                bestMove = move;
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
        LinkedList<Move> legalMoves = game.generateLegalMoves();
        for(Move move : legalMoves){
            game.doMove(move);
            double moveValue = computeMin(game, currDepth, bestVal, beta, cutoffDepth);
            game.undoMove();
            
            if(moveValue > bestVal){
                bestVal = moveValue;
                if (beta <= bestVal){
                    break;
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
        LinkedList<Move> legalMoves = game.generateLegalMoves();
        for(Move move : legalMoves){    
            game.doMove(move);
            double moveValue = computeMax(game, (currDepth + 1), alpha, bestVal, cutoffDepth);
            game.undoMove();
            if(moveValue < bestVal){
                bestVal = moveValue;
                if (bestVal <= alpha){
                    break;
                }
            }
        }
        return bestVal;
    }
}