public final class Utility{

    //https://www.chessprogramming.org/Square_Mapping_Considerations

    public static final long left_edge_mask = ~0xFEFEFEFEFEFEFEFEL;
    public static final long right_edge_mask = ~0x7F7F7F7F7F7F7F7FL;
    public static final long a_file = 0x00000000000000FFL;
    public static final long h_file = 0xFF00000000000000L;
    public static final long first_rank = 0x0101010101010101L;
    public static final long eighth_rank = 0x8080808080808080L;
    public static final long  a_h_diagonal = 0x8040201008040201L;
    public static final long  h_a_antidiagonal = 0x0102040810204080L;

    public static int getIndex(long bitboard){
        return Long.numberOfTrailingZeros(bitboard);
    }

    
    public static long maskGetBit(long bits, long mask){
        return (bits & mask);
    }

    public static long maskSetBit(long bits, long mask){
        return (bits | mask);
    }

    public static long maskZeroBit(long bits, long mask){
        return maskGetBit(bits, mask) != 0 ? (bits ^ mask) : bits;
    }

    public static long getBit(long bits, int index){
        return (bits & (1L << index));
    }

    public static long setBit(long bits, int index){
        return (bits | (1L << index));
    }

    public static long zeroBit(long bits, int index){
        return getBit(bits, index) != 0 ? (bits ^ (1L << index)) : bits;
    }

    public static short getPieceIndex(char pieceChar){
        switch(pieceChar){
            case 'P':
                return 1;
            case 'N':
                return 2;
            case 'B':
                return 3;
            case 'R':
                return 4;
            case 'Q':
                return 5;
            case 'K':
                return 6;
            default:
                return 0;
        }
    }

    public static long getLSB(long bitboard) {
        return bitboard & (~bitboard + 1);
    } 

    public static int[] onBoardShifts(long curPosition, int[] shifts){
        int[] newShifts = new int[shifts.length];
        int index = 0;
        for(int i = 0; i < shifts.length; i++){
            if (onBoard(curPosition, shifts[i])){
                newShifts[index] = shifts[i];
                index++;
            }
        }
        return newShifts;
    }

    public static boolean onBoard(long curPosition, int shift){
        long shiftedBits = shift > 0 ? curPosition << shift : curPosition >>> -shift;
        if (shiftedBits == 0){
            return false;
        }

        boolean ret = true;

        if ((curPosition & right_edge_mask) != 0){
            ret = (shift == 9) | (shift == 1) | (shift == -7) | (shift == 17) | (shift == -15) | (shift == 10) | (shift == -6) ? false : true;
        }else if((curPosition & left_edge_mask) != 0){ 
            ret = (shift == 7) | (shift == -1) | (shift == -9) | (shift == -17) | (shift == 15) | (shift == -10) | (shift == 6) ? false : true;
        }else if((curPosition & (right_edge_mask >>> 1)) != 0){
            ret = (shift == 10) | (shift == -6)? false : true;
        }else if((curPosition & (left_edge_mask << 1)) != 0){
            ret = (shift == 6) | (shift == -10) ? false : true;
        }
        return ret;
    }
}