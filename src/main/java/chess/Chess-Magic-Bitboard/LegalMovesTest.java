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

    // Testing White Pawns #1
    @Test
    public void whitePawns1(){
        // White pieces
        pW = new Pieces((short)1, (1L << 45) | (1L << 52)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 44)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 39)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 61)); // King
        
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

        Board wP1 = new Board(white, black);
        Game test = new Game(true, wP1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wP1.toString());
        

        LinkedList<Move> pawnLegal = white[0].generateMoves(whiteBoard, blackBoard);
        assertEquals(3, pawnLegal.size());

        for(Move move : pawnLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    // Testing White Pawn #2

    @Test
    public void whitePawns2(){
        // White pieces
        pW = new Pieces((short)1, (1L << 45) | (1L << 52)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 44)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 39)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 61)); // King
        
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

        Board wP2 = new Board(white, black);
        Game test = new Game(true, wP2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wP2.toString());
        

        /* 
        LinkedList<Move> pawnLegal = white[0].generateMoves(whiteBoard, blackBoard);
        assertEquals(3, pawnLegal.size());

        for(Move move : pawnLegal){
            test.doMove(move);
            System.out.println(test.getGamBoard().toString());
            test.undoMove();
        }
        */
    }

    // Testing Black Pawn #1
    @Test
    public void blackPawns1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, (1L << 40)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 0)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 18)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, (1L << 33)); // Queen
        kB = new Pieces((short)-6, (1L << 26)); // King
        
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

        Board bP1 = new Board(white, black);
        Game test = new Game(false, bP1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bP1.toString());
        

        LinkedList<Move> pawnLegal = black[0].generateMoves(whiteBoard, blackBoard);
        assertEquals(1, pawnLegal.size());

        for(Move move : pawnLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    
    }

    @Test
    public void blackPawns2(){
        // White pieces
        pW = new Pieces((short)1, (1L << 11)); // Pawns
        nW = new Pieces((short)2, (1L << 50)); // Knights
        bW = new Pieces((short)3, (1L << 40)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 0)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 18)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, (1L << 33)); // Queen
        kB = new Pieces((short)-6, (1L << 26)); // King
        
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

        Board bP1 = new Board(white, black);
        Game test = new Game(false, bP1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println(bP1.toString());


        LinkedList<Move> pawnLegal = black[0].generateMoves(whiteBoard, blackBoard);
        assertEquals(2, pawnLegal.size());

        for(Move move : pawnLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void whiteKnight1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, (1L << 30)); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 5)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 15) | (1L << 38)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 7)); // King
        
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

        Board wN1 = new Board(white, black);
        Game test = new Game(true, wN1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wN1.toString());
        

        LinkedList<Move> knightLegal = white[1].generateMoves(whiteBoard, blackBoard);
        //assertEquals(6, knightLegal.size());

        for(Move move : knightLegal){
            System.out.println(move.toString());
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void whiteKnight2(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, (1L << 30)); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 5)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 15) | (1L << 38)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 7)); // King
        
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

        Board wN2 = new Board(white, black);
        Game test = new Game(true, wN2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wN2.toString());
        

        LinkedList<Move> knightLegal = white[1].generateMoves(whiteBoard, blackBoard);
        assertEquals(6, knightLegal.size());

        for(Move move : knightLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    // Testing Black Knight 1
    @Test
    public void blackKnight1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 4)); // Queen
        kW = new Pieces((short)6, (1L << 63)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, (1L << 45) | (1L << 47)); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 46)); // King
        
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

        Board bN1 = new Board(white, black);
        Game test = new Game(false, bN1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println(bN1.toString());


        LinkedList<Move> knightLegal = black[1].generateMoves(whiteBoard, blackBoard);
        assertEquals(12, knightLegal.size());

        for(Move move : knightLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void blackKnight2(){
        // White pieces
        pW = new Pieces((short)1, (1L << 51)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, (1L << 27)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 4)); // Queen
        kW = new Pieces((short)6, (1L << 63)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, (1L << 45) | (1L << 47)); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 46)); // King
        
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

        Board bN2 = new Board(white, black);
        Game test = new Game(false, bN2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println(bN2.toString());


        LinkedList<Move> knightLegal = black[1].generateMoves(whiteBoard, blackBoard);
        assertEquals(12, knightLegal.size());

        for(Move move : knightLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void whiteBishop1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, (1L << 29) | (1L << 37)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 41)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, 1L << 56); // King
        
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

        Board wB1 = new Board(white, black);
        Game test = new Game(true, wB1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wB1.toString());
        

        LinkedList<Move> bishopLegal = white[2].generateMoves(whiteBoard, blackBoard);
        assertEquals(22, bishopLegal.size());

        for(Move move : bishopLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void whiteBishop2(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, (1L << 29) | (1L << 37)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 41)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 28)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 15)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, 1L << 56); // King
        
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

        Board wB2 = new Board(white, black);
        Game test = new Game(true, wB2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wB2.toString());
        

        LinkedList<Move> bishopLegal = white[2].generateMoves(whiteBoard, blackBoard);
        assertEquals(19, bishopLegal.size());

        for(Move move : bishopLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void blackBishop1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 0)); // Queen
        kW = new Pieces((short)6, (1L << 7)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, (1L << 15)); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, (1L << 23)); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 13)); // King
        
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

        Board bB1 = new Board(white, black);
        Game test = new Game(false, bB1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bB1.toString());
        

        LinkedList<Move> bishopLegal = black[2].generateMoves(whiteBoard, blackBoard);
        assertEquals(7, bishopLegal.size());

        for(Move move : bishopLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void blackBishop2(){
        // White pieces
        pW = new Pieces((short)1, (1L << 15) | (1L << 21)); // Pawns
        nW = new Pieces((short)2, (1L << 37)); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 0)); // Queen
        kW = new Pieces((short)6, (1L << 7)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, (1L << 23)); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 13)); // King
        
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

        Board bB2 = new Board(white, black);
        Game test = new Game(false, bB2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bB2.toString());
        

        LinkedList<Move> bishopLegal = black[2].generateMoves(whiteBoard, blackBoard);
        assertEquals(4, bishopLegal.size());

        for(Move move : bishopLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void whiteRook1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, (1L << 50)); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 41)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, 1L << 56); // King
        
        // Assign white pieces
        white[0] = pW;
        white[1] = nW;
        white[2] = bW;
        white[3] = rW;
        white[4] = qW;
        white[5] = kW;
        
        // Assign black pieces
        black[0] = pB;
        black[1] = nB;
        black[2] = bB;
        black[3] = rB;
        black[4] = qB;
        black[5] = kB;

        Board wR1 = new Board(white, black);
        Game test = new Game(true, wR1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wR1.toString());
        

        LinkedList<Move> rookLegal = white[3].generateMoves(whiteBoard, blackBoard);
        assertEquals(14, rookLegal.size());

        for(Move move : rookLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void whiteRook2(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, (1L << 50)); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 41)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, (1L << 58)); // Knights
        bB = new Pieces((short)-3, (1L << 10)); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, 1L << 56); // King
        
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

        Board wR2 = new Board(white, black);
        Game test = new Game(true, wR2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wR2.toString());
        

        LinkedList<Move> rookLegal = white[3].generateMoves(whiteBoard, blackBoard);
        assertEquals(13, rookLegal.size());

        for(Move move : rookLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void blackRook1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, (1L << 7)); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 57)); // Queen
        kW = new Pieces((short)6, (1L << 2)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 63)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 18)); // King
        
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

        Board bR1 = new Board(white, black);
        Game test = new Game(false, bR1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bR1.toString());
        

        LinkedList<Move> rookLegal = black[3].generateMoves(whiteBoard, blackBoard);
        assertEquals(13, rookLegal.size());

        for(Move move : rookLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void blackRook2(){
        white = new Pieces[6];
        black = new Pieces[6];

        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 2)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 63)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 18)); // King
        
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

        Board bR2 = new Board(white, black);
        Game test = new Game(false, bR2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bR2.toString());
        

        LinkedList<Move> rookLegal = black[3].generateMoves(whiteBoard, blackBoard);
        assertEquals(14, rookLegal.size());

        for(Move move : rookLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void whiteQueen1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 8)); // Queen
        kW = new Pieces((short)6, (1L << 41)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, 1L << 57); // King
        
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

        Board wQ1 = new Board(white, black);
        Game test = new Game(true, wQ1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wQ1.toString());
        

        LinkedList<Move> queenlegal = white[4].generateMoves(whiteBoard, blackBoard);
        assertEquals(21, queenlegal.size());

        for(Move move : queenlegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void whiteQueen2(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 8)); // Queen
        kW = new Pieces((short)6, (1L << 41)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, (1L << 62)); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, (1L << 15)); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, 1L << 57); // King
        
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

        Board wQ2 = new Board(white, black);
        Game test = new Game(true, wQ2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wQ2.toString());
        

        LinkedList<Move> queenlegal = white[4].generateMoves(whiteBoard, blackBoard);
        assertEquals(21, queenlegal.size());

        for(Move move : queenlegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void blackQueen1(){
        // White pieces
        pW = new Pieces((short)1, 0); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 6)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, (1L << 12)); // Queen
        kB = new Pieces((short)-6, (1L << 21)); // King
        
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

        Board bQ1 = new Board(white, black);
        Game test = new Game(false, bQ1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bQ1.toString());
        

        LinkedList<Move> queenLegal = black[4].generateMoves(whiteBoard, blackBoard);
        assertEquals(20, queenLegal.size());

        for(Move move : queenLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }

    }

    @Test
    public void blackQueen2(){
        // White pieces
        pW = new Pieces((short)1, (1L << 52)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, (1L << 40)); // Queen
        kW = new Pieces((short)6, (1L << 6)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, (1L << 12)); // Queen
        kB = new Pieces((short)-6, (1L << 21)); // King
        
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

        Board bQ2 = new Board(white, black);
        Game test = new Game(false, bQ2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bQ2.toString());
        

        LinkedList<Move> queenLegal = black[4].generateMoves(whiteBoard, blackBoard);
        assertEquals(19, queenLegal.size());

        for(Move move : queenLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void whiteKing1(){
        // White Pieces
        pW = new Pieces((short)1, 0);
        nW = new Pieces((short)2, 0);
        bW = new Pieces((short)3, 0);
        rW = new Pieces((short)4, 0);
        qW = new Pieces((short)5, 0);
        kW = new Pieces((short)6, 1L << 23);

        // Black Pieces
        pB = new Pieces((short)-1, (1L << 31) | (1L << 30) | (1L << 22));
        nB = new Pieces((short)-2, 0);
        bB = new Pieces((short)-3, 0);
        rB = new Pieces((short)-4, 0);
        qB = new Pieces((short)-5, 0);
        kB = new Pieces((short)-6, 0);

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

        Board wK1 = new Board(white, black);
        Game test = new Game(true, wK1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wK1.toString());
        

        LinkedList<Move> kingLegal = white[5].generateMoves(whiteBoard, blackBoard);
        assertEquals(5, kingLegal.size());

        for(Move move : kingLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void whiteKing2(){
        // White Pieces
        pW = new Pieces((short)1, 0);
        nW = new Pieces((short)2, 0);
        bW = new Pieces((short)3, 0);
        rW = new Pieces((short)4, 0);
        qW = new Pieces((short)5, 0);
        kW = new Pieces((short)6, 1L << 23);
        pB = new Pieces((short)-1, (1L << 31) | (1L << 30) | (1L << 22));
        nB = new Pieces((short)-2, 1L << 46);
        bB = new Pieces((short)-3, 0);
        rB = new Pieces((short)-4, 0);
        qB = new Pieces((short)-5, 0);
        kB = new Pieces((short)-6, 0);
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

        Board wK2 = new Board(white, black);
        Game test = new Game(true, wK2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + wK2.toString());
        

        LinkedList<Move> kingLegal = white[5].generateMoves(whiteBoard, blackBoard);
        assertEquals(5, kingLegal.size());

        for(Move move : kingLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void blackKing1(){
        // White pieces
        pW = new Pieces((short)1, (1L << 41) | (1L << 42) | (1L << 50)); // Pawns
        nW = new Pieces((short)2, 0); // Knights
        bW = new Pieces((short)3, 0); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 4)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 49)); // King
        
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

        Board bK1 = new Board(white, black);
        Game test = new Game(false, bK1);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bK1.toString());
        

        LinkedList<Move> kingLegal = black[5].generateMoves(whiteBoard, blackBoard);
        assertEquals(8, kingLegal.size());

        for(Move move : kingLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    @Test
    public void blackKing2(){
        // White pieces
        pW = new Pieces((short)1, (1L << 41) | (1L << 42) | (1L << 50)); // Pawns
        nW = new Pieces((short)2, (1L << 25)); // Knights
        bW = new Pieces((short)3, (1L << 23)); // Bishops
        rW = new Pieces((short)4, 0); // Rooks
        qW = new Pieces((short)5, 0); // Queen
        kW = new Pieces((short)6, (1L << 4)); // King
        
        // Black pieces
        pB = new Pieces((short)-1, 0); // Pawns
        nB = new Pieces((short)-2, 0); // Knights
        bB = new Pieces((short)-3, 0); // Bishops
        rB = new Pieces((short)-4, 0); // Rooks
        qB = new Pieces((short)-5, 0); // Queen
        kB = new Pieces((short)-6, (1L << 49)); // King
        
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

        Board bK2 = new Board(white, black);
        Game test = new Game(false, bK2);
        whiteBoard = test.getGameBoard().getFullWhite();
        blackBoard = test.getGameBoard().getFullBlack();
        System.out.println("Original Board "  + "\n" + bK2.toString());
        

        LinkedList<Move> kingLegal = black[5].generateMoves(whiteBoard, blackBoard);
        assertEquals(8, kingLegal.size());

        for(Move move : kingLegal){
            test.doMove(move);
            System.out.println(test.getGameBoard().toString());
            test.undoMove();
        }
    }

    public static void main(String args[]){
        // https://www.baeldung.com/junit-tests-run-programmatically-from-java
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.run(LegalMovesTest.class);

    }
}