import org.junit.*;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;
import java.util.LinkedList;

public class LegalMovesTest {

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
    public void pawnMove1(){
        Utility.initializeMaps();
        // White pieces
        pW = new Pieces((short)1, (1L << 33) | (1L << 35) | (1L << 44) | (1L << 21) | (1L << 30) | (1L << 23)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 3)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 48) | (1L << 34) | (1L << 52) | (1L << 37) | (1L << 38) | (1L << 39)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 59)); // King
        
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

        Board pawns = new Board(white, black);
        Game test = new Game(true, pawns);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + pawns.toString());
        
        for(MoveSet moveSet : test.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test.getGameBoard().getWhitePieces(), test.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test.doMove(curMove);
                System.out.println(pawns.toString());
                test.undoMove();
            }
        }

        System.out.println("Black's turn-----------------------------------");

        Game test2 = new Game(false, pawns);
        System.out.println("Original Board "  + "\n" + pawns.toString());
        
        for(MoveSet moveSet : test2.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test2.getGameBoard().getWhitePieces(), test2.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test2.doMove(curMove);
                System.out.println(pawns.toString());
                test2.undoMove();
            }
        }

    }

    @Test
    public void knightMove1(){
        Utility.initializeMaps();
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, (1L << 32) | (1L << 17) | (1L << 6) | (1L << 55)); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 3)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, (1L << 61) | (1L << 40) | (1L << 38) | (1L << 44)); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 59)); // King
        
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

        Board knights = new Board(white, black);
        Game test = new Game(true, knights);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + knights.toString());
        
        for(MoveSet moveSet : test.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test.getGameBoard().getWhitePieces(), test.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test.doMove(curMove);
                System.out.println(knights.toString());
                test.undoMove();
            }
        }

        System.out.println("Black's turn-----------------------------------");

        Game test2 = new Game(false, knights);
        System.out.println("Original Board "  + "\n" + knights.toString());
        
        for(MoveSet moveSet : test2.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test2.getGameBoard().getWhitePieces(), test2.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test2.doMove(curMove);
                System.out.println(knights.toString());
                test2.undoMove();
            }
        }

    }

    @Test
    public void bishopMoves1(){
        Utility.initializeMaps();
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, (1L << 48) | (1L << 15)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 34)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, (1L << 29) | (1L << 35)); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 36)); // King
        
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

        Board bishops = new Board(white, black);
        Game test = new Game(true, bishops);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bishops.toString());
        
        for(MoveSet moveSet : test.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test.getGameBoard().getWhitePieces(), test.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test.doMove(curMove);
                System.out.println(bishops.toString());
                test.undoMove();
            }
        }

        System.out.println("Black's turn-----------------------------------");

        Game test2 = new Game(false, bishops);
        System.out.println("Original Board "  + "\n" + bishops.toString());
        
        for(MoveSet moveSet : test2.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test2.getGameBoard().getWhitePieces(), test2.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test2.doMove(curMove);
                System.out.println(bishops.toString());
                test2.undoMove();
            }
        }
    }

    @Test
    public void rookMove1(){
        Utility.initializeMaps();
        // White pieces
        pW = new Pieces((short)1, (1L << 16) | (1L << 32) | (1L << 31) | (1L << 14) | (1L << 13)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, (1L << 0) | (1L << 63)); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 3)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 50) | (1L << 51) | (1L << 47)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 48) | (1L << 15)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 59)); // King
        
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

        Board rooks = new Board(white, black);
        Game test = new Game(true, rooks);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + rooks.toString());
         
        for(MoveSet moveSet : test.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test.getGameBoard().getWhitePieces(), test.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test.doMove(curMove);
                System.out.println(rooks.toString());
                test.undoMove();
            }
        }

        System.out.println("Black's turn-----------------------------------");

        Game test2 = new Game(false, rooks);
        System.out.println("Original Board "  + "\n" + rooks.toString());
        
        for(MoveSet moveSet : test2.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test2.getGameBoard().getWhitePieces(), test2.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test2.doMove(curMove);
                System.out.println(rooks.toString());
                test2.undoMove();
            }
        }
    }

    @Test
    public void kingMoves1(){
        Utility.initializeMaps();
        // White pieces
        pW = new Pieces((short)1, (1L << 41)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, (1L << 63)); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 3)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, (1L << 40)); // Knights
        bB = new Pieces((short)-3, (1L << 36)); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
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

        Board king = new Board(white, black);
        Game test = new Game(true, king);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + king.toString());
         
        for(MoveSet moveSet : test.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test.getGameBoard().getWhitePieces(), test.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test.doMove(curMove);
                //if(!test.isCheck()){
                    System.out.println(king.toString());
                //}
                
                test.undoMove();
            }
        }

        System.out.println("Black's turn-----------------------------------");

        Game test2 = new Game(false, king);
        System.out.println("Original Board "  + "\n" + king.toString());
        
        for(MoveSet moveSet : test2.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test2.getGameBoard().getWhitePieces(), test2.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test2.doMove(curMove);
                //if(!test2.isCheck()){
                    System.out.println(king.toString());
                //}
                test2.undoMove();
            }
        }
    }

    public static void main(String args[]){
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
        Utility.initializeMaps();
        // White pieces
        pW = new Pieces((short)1, (1L << 16) | (1L << 32) | (1L << 31) | (1L << 14) | (1L << 13)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, (1L << 0) | (1L << 63)); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 3)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 50) | (1L << 51) | (1L << 47)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 48) | (1L << 15)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 59)); // King
        
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

        Board rooks = new Board(white, black);
        Game test = new Game(true, rooks);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + rooks.toString());
         
        for(MoveSet moveSet : test.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test.getGameBoard().getWhitePieces(), test.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test.doMove(curMove);
                System.out.println(rooks.toString());
                test.undoMove();
            }
        }

        System.out.println("Black's turn-----------------------------------");

        Game test2 = new Game(false, rooks);
        System.out.println("Original Board "  + "\n" + rooks.toString());
        
        for(MoveSet moveSet : test2.generateLegalMoveSets()){
            while(moveSet.getBitboard() != 0){
                Move curMove = moveSet.firstMove(test2.getGameBoard().getWhitePieces(), test2.getGameBoard().getBlackPieces());
                moveSet = moveSet.next();
                test2.doMove(curMove);
                System.out.println(rooks.toString());
                test2.undoMove();
            }
        }
        
        /*// https://www.baeldung.com/junit-tests-run-programmatically-from-java
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.run(LegalMovesTest.class);*/

    }
}