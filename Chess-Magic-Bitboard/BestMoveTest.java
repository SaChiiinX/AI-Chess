import org.junit.*;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class BestMoveTest {
    Pieces[] white = new Pieces[6];
    Pieces[] black = new Pieces[6];
    long whiteBoard;
    long blackBoard;
    Pieces pW;
    Pieces nW;
    Pieces bW;
    Pieces rW;
    Pieces qW;
    Pieces kW;
    Pieces pB;
    Pieces nB;
    Pieces bB;
    Pieces rB;
    Pieces qB;
    Pieces kB;

    @Test
    public void alphaBeta(){
        Game game = new Game();
        System.out.println("\n\n\nNew game started! \n\u2659 white, \u265F black");
        System.out.println(game.getGameBoard().toString());
        for(int i = 0; i < 120; i++){
            System.out.println("\n\nMove #" + (i+1));
            System.out.println(i % 2 == 0 ? "White to move..." : "Black to move...");
            Move bestMove = AlphaBeta.computeMove(game, 4);
            if(bestMove == null){
                System.out.println("Game Over");
                break;
            }
            game.doMove(bestMove);
            System.out.println(game.getGameBoard().toString());
        }
    }

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.run(BestMoveTest.class);
    }
}