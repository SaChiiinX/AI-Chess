import java.util.Scanner;

public class Ai extends Player{

    // Depth that AlphaBeta will search to, default set to 3
    private int depth = 3;

    public Ai(Game currGame, int depth){
        super(currGame);
        this.depth = depth;
    }
    public Move getMove(Scanner input){
        // Return the move calculated to be best by AlphaBeta search
        return AlphaBeta.computeMove(game, depth);
    }
}
