import org.junit.*;
import static org.junit.Assert.assertEquals;

public class MoveGenerationTests {

    long wStarting = 0b1111111100000000L;
    long bStarting = 0b11111111L << 48;

    @Test
    public void whiteDouble1(){
        long opponents = 0b1111110100000000000000000L;
        MoveSet moves = Pieces.generatePawnMoveTwoSquares(true, wStarting, opponents);
        assertEquals(1024L, moves.getBitboard());
    }

    @Test
    public void whiteCR1(){
        long opponents = 0b111001110L << 16;
        MoveSet moves = Pieces.generatePawnCapturingRight(true, wStarting, opponents);
        assertEquals(0b01100111L << 8, moves.getBitboard());
    }

    @Test
    public void whiteCL1(){
        long opponents = 0b101100101L << 16;
        MoveSet moves = Pieces.generatePawnCapturingLeft(true, wStarting, opponents);
        assertEquals(0b11001010L << 8, moves.getBitboard());
    }

    @Test
    public void whiteSingle1(){
        long pawns = 0b110100111001L << 8;
        long opponents = 0b10011011011101L << 16;
        MoveSet moves = Pieces.generatePawnMoveOneSquare(true, pawns, opponents);
        assertEquals(0b100100100000L << 8, moves.getBitboard());
    }

    @Test
    public void blackDouble1(){
        long opponents = 0b1011010100011101L << 32;
        MoveSet moves = Pieces.generatePawnMoveTwoSquares(false, bStarting, opponents);
        assertEquals(0b01000010L << 48, moves.getBitboard());
    }

    @Test
    public void blackCR1(){
        long opponents = 0b111001110L << 40;
        MoveSet moves = Pieces.generatePawnCapturingRight(false, bStarting, opponents);
        assertEquals(0b01100111L << 48, moves.getBitboard());
    }

    @Test
    public void blackCL1(){
        long opponents = 0b101100101L << 40;
        MoveSet moves = Pieces.generatePawnCapturingLeft(false, bStarting, opponents);
        assertEquals(0b11001010L << 48, moves.getBitboard());
    }

    @Test
    public void blackSingle1(){
        long pawns = 0b110100111001L << 48;
        long opponents = 0b10011011011101L << 40;
        MoveSet moves = Pieces.generatePawnMoveOneSquare(false, pawns, opponents);
        assertEquals(0b100100100000L << 48, moves.getBitboard());
    }

}
