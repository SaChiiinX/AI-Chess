import org.junit.*;

public class BoardPositions {

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

    // Forced Mate Testing

    @Test
    public void forcedMate8(){ // Black to move
        // White pieces
        pW = new Pieces((short)1, (1L << 8) | (1L << 14) | (1L << 17) | (1L << 21) | (1L << 31)); // Pawns
        nW = new Pieces((short)2, (1L << 16) | (1L << 27)); // Knights
        bW = new Pieces((short)3, (1L << 9)); // Bishops
        rW = new Pieces((short)4, (1L << 3) | (1L << 10)); // Rooks
        qW = new Pieces((short)5, (1L << 41)); // Queen
        kW = new Pieces((short)6, (1L << 0)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 49) | (1L << 46) | (1L << 39)); // Pawns
        nB = new Pieces((short)-2, (1L << 19) | (1L << 36)); // Knights
        bB = new Pieces((short)-3, (1L << 35) | (1L << 63)); // Bishops
        rB = new Pieces((short)-4, (1L << 58) | (1L << 51)); // Rooks
        qB = new Pieces((short)-5, (1L << 61)); // Queen
        kB = new Pieces((short)-6, (1L << 57)); // King
        
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board fM8 = new Board(white, black);
        Game test = new Game(false, fM8);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + fM8.toString());

        // Actually runs the test
        for(int i = 0; i < 120; i++){
          System.out.println("\nMove " + (i+1));
          Move bestMove = AlphaBeta.computeMove(test, 3);
          if(bestMove == null){
              System.out.println("Game Over");
              break;
          }
          test.doMove(bestMove);
          System.out.println(test.getGameBoard().toString());
      }
        
        // Implement check

        // Board Visual
    /*

            a   b   c   d   e   f   g   h
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        8 |   | ♚ | ♜ |   |   | ♛ |   | ♝ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        7 |   | ♟ |   | ♜ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        6 |   | ♕ |   |   |   |   | ♟ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        5 |   |   |   | ♝ | ♞ |   |   | ♟ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        4 |   |   |   | ♘ |   |   |   | ♙ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        3 | ♘ | ♙ |   | ♞ |   | ♙ |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        2 | ♙ | ♗ | ♖ |   |   |   | ♙ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        1 | ♔ |   |   | ♖ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+

     */
    }

    @Test
    public void forcedMate6(){ // Black to move
        // White pieces
        pW = new Pieces((short)1, (1L << 21) | (1L << 14) | (1L << 17)); // Pawns
        nW = new Pieces((short)2, (1L << 16) | (1L << 27)); // Knights
        bW = new Pieces((short)3, (1L << 9) | (1L << 24)); // Bishops
        rW = new Pieces((short)4, (1L << 2) | (1L << 3)); // Rooks
        qW = new Pieces((short)5, (1L << 32)); // Queen
        kW = new Pieces((short)6, (1L << 8)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 40) | (1L << 49) | (1L << 46) | (1L << 39)); // Pawns
        nB = new Pieces((short)-2, (1L << 34)); // Knights
        bB = new Pieces((short)-3, (1L << 62) | (1L << 31)); // Bishops
        rB = new Pieces((short)-4, (1L << 58) | (1L << 59)); // Rooks
        qB = new Pieces((short)-5, (1L << 61)); // Queen
        kB = new Pieces((short)-6, (1L << 48)); // King
        
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board fM6 = new Board(white, black);
        Game test = new Game(false, fM6);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + fM6.toString());

        // Actually runs the test
        for(int i = 0; i < 120; i++){
          System.out.println("\nMove " + (i+1));
          Move bestMove = AlphaBeta.computeMove(test, 3);
          if(bestMove == null){
              System.out.println("Game Over");
              break;
          }
          test.doMove(bestMove);
          System.out.println(test.getGameBoard().toString());
      }

        // Implement check

        // Board Visual
    /*
    
            a   b   c   d   e   f   g   h
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        8 |   |   | ♜ | ♜ |   | ♛ | ♝ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        7 | ♚ | ♟ |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        6 | ♟ |   |   |   |   |   | ♟ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        5 | ♕ |   | ♞ |   |   |   |   | ♟ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        4 | ♗ |   |   | ♘ |   |   |   | ♝ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        3 | ♘ | ♙ |   |   |   | ♙ |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        2 | ♔ | ♗ |   |   |   |   | ♙ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        1 |   |   | ♖ | ♖ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+

     */
    }

    @Test
    public void forcedMate4(){ // Black to move
        // White pieces
        pW = new Pieces((short)1, (1L << 8) | (1L << 14) | (1L << 17) | (1L << 21) | (1L << 31) | (1L << 36)); // Pawns
        nW = new Pieces((short)2, (1L << 33) | (1L << 42)); // Knights
        bW = new Pieces((short)3, (1L << 9)); // Bishops
        rW = new Pieces((short)4, (1L << 3) | (1L << 10)); // Rooks
        qW = new Pieces((short)5, (1L << 4)); // Queen
        kW = new Pieces((short)6, (1L << 0)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 49) | (1L << 46) | (1L << 39)); // Pawns
        nB = new Pieces((short)-2, (1L << 34)); // Knights
        bB = new Pieces((short)-3, (1L << 35) | (1L << 63)); // Bishops
        rB = new Pieces((short)-4, (1L << 58) | (1L << 51)); // Rooks
        qB = new Pieces((short)-5, (1L << 61)); // Queen
        kB = new Pieces((short)-6, (1L << 56)); // King
        
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board fM4 = new Board(white, black);
        Game test = new Game(false, fM4);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + fM4.toString());

        // Actually runs the test
        for(int i = 0; i < 120; i++){
          System.out.println("\nMove " + (i+1));
          Move bestMove = AlphaBeta.computeMove(test, 3);
          if(bestMove == null){
              System.out.println("Game Over");
              break;
          }
          test.doMove(bestMove);
          System.out.println(test.getGameBoard().toString());
      }

        // Implement check

        // Board Visual
    /*

            a   b   c   d   e   f   g   h
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        8 | ♚ |   | ♜ |   |   | ♛ |   | ♝ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        7 |   | ♟ |   | ♜ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        6 |   |   | ♘ |   |   |   | ♟ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        5 |   | ♘ | ♞ | ♝ | ♙ |   |   | ♟ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        4 |   |   |   |   |   |   |   | ♙ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        3 |   | ♙ |   |   |   | ♙ |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        2 | ♙ | ♗ | ♖ |   |   |   | ♙ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        1 | ♔ |   |   | ♖ | ♕ |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+

     */
    }

    @Test
    public void forcedMate2(){ // Black to move
        // White pieces
        pW = new Pieces((short)1, (1L << 13) | (1L << 14) | (1L << 17)); // Pawns
        nW = new Pieces((short)2, (1L << 16) | (1L << 27)); // Knights
        bW = new Pieces((short)3, (1L << 9) | (1L << 42)); // Bishops
        rW = new Pieces((short)4, (1L << 3) | (1L << 10)); // Rooks
        qW = new Pieces((short)5, (1L << 32)); // Queen
        kW = new Pieces((short)6, (1L << 8)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 40) | (1L << 41) | (1L << 46) | (1L << 39)); // Pawns
        nB = new Pieces((short)-2, (1L << 34)); // Knights
        bB = new Pieces((short)-3, (1L << 35) | (1L << 45)); // Bishops
        rB = new Pieces((short)-4, (1L << 58) | (1L << 59)); // Rooks
        qB = new Pieces((short)-5, (1L << 61)); // Queen
        kB = new Pieces((short)-6, (1L << 57)); // King
        
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board fM2 = new Board(white, black);
        Game test = new Game(false, fM2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + fM2.toString());

        // Actually runs the test
        for(int i = 0; i < 120; i++){
            System.out.println("\nMove " + (i+1));
            Move bestMove = AlphaBeta.computeMove(test, 3);
            if(bestMove == null){
                System.out.println("Game Over");
                break;
            }
            test.doMove(bestMove);
            System.out.println(test.getGameBoard().toString());
        }

        // Implement check

        // Board Visual
    /*

            a   b   c   d   e   f   g   h
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        8 |   | ♚ | ♜ | ♜ |   | ♛ |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        7 |   |   |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        6 | ♟ | ♟ | ♗ |   |   | ♝ | ♟ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        5 | ♕ |   | ♞ | ♝ |   |   |   | ♟ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        4 |   |   |   | ♘ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        3 | ♘ | ♙ |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        2 | ♔ | ♗ | ♖ |   |   | ♙ | ♙ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        1 |   |   |   | ♖ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+

     */
    }

    @Test
    public void simpleForcedMate2(){ // White to move
        // White pieces
        pW = new Pieces((short)1, (1L << 48) | (1L << 49)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, (1L << 57)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 56)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 41)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 0)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 58)); // King
        
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board sFM2 = new Board(white, black);
        Game test = new Game(false, sFM2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + sFM2.toString());

        // Actually runs the test
        for(int i = 0; i < 120; i++){
          System.out.println("\nMove " + (i+1));
          Move bestMove = AlphaBeta.computeMove(test, 3);
          if(bestMove == null){
              System.out.println("Game Over");
              break;
          }
          test.doMove(bestMove);
          System.out.println(test.getGameBoard().toString());
      }
        
        // Implement check
        
        // Board Visual
    /*

            a   b   c   d   e   f   g   h
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        8 | ♔ | ♗ | ♚ |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        7 | ♙ | ♙ |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        6 |   | ♟ |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        5 |   |   |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        4 |   |   |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        3 |   |   |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        2 |   |   |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        1 | ♜ |   |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+

     */
    }

    @Test
    public void forcedMate1(){ // Black to move
        // White pieces
        pW = new Pieces((short)1, (1L << 13) | (1L << 14) | (1L << 17)); // Pawns
        nW = new Pieces((short)2, (1L << 16) | (1L << 27)); // Knights
        bW = new Pieces((short)3, (1L << 9) | (1L << 42)); // Bishops
        rW = new Pieces((short)4, (1L << 3) | (1L << 10)); // Rooks
        qW = new Pieces((short)5, (1L << 32)); // Queen
        kW = new Pieces((short)6, (1L << 8)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 40) | (1L << 41) | (1L << 46) | (1L << 39)); // Pawns
        nB = new Pieces((short)-2, (1L << 34)); // Knights
        bB = new Pieces((short)-3, (1L << 35) | (1L << 45)); // Bishops
        rB = new Pieces((short)-4, (1L << 58) | (1L << 51)); // Rooks
        qB = new Pieces((short)-5, (1L << 61)); // Queen
        kB = new Pieces((short)-6, (1L << 57)); // King
        
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board fM1 = new Board(white, black);
        Game test = new Game(false, fM1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + fM1.toString());

        // Actually runs the test
        for(int i = 0; i < 120; i++){
          System.out.println("\nMove " + (i+1));
          Move bestMove = AlphaBeta.computeMove(test, 3);
          if(bestMove == null){
              System.out.println("Game Over");
              break;
          }
          test.doMove(bestMove);
          System.out.println(test.getGameBoard().toString());
      }

        // Implement check

        // Board Visual
    /*

            a   b   c   d   e   f   g   h
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        8 |   | ♚ | ♜ |   |   | ♛ |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        7 |   |   |   | ♜ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        6 | ♟ | ♟ | ♗ |   |   | ♝ | ♟ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        5 | ♕ |   | ♞ | ♝ |   |   |   | ♟ |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        4 |   |   |   | ♘ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        3 | ♘ | ♙ |   |   |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        2 | ♔ | ♗ | ♖ |   |   | ♙ | ♙ |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+
        1 |   |   |   | ♖ |   |   |   |   |
          +–-–+–-–+–-–+–-–+–-–+–-–+–-–+–-–+

     */
    }

    public static void doMoves(Game game, int p1Depth, int p2Depth){
        for(int i = 0; i < 120; i++){
            System.out.println("\nMove " + (i+1));  
            Move bestMove = ((i%2) == 0) ? AlphaBeta.computeMove(game, p1Depth) : AlphaBeta.computeMove(game, p2Depth);
            if(bestMove == null){
                System.out.println("Game Over");
                break;
            }
            game.doMove(bestMove);
            System.out.println(game.getGameBoard().toString());
        }
    }
}