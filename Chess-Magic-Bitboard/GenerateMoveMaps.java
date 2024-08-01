import java.util.*;
import java.io.*;


public class GenerateMoveMaps {

    // KNIGHTS HERE ----------------------------
    // generate knight moves for a given knight position
    public static long generateKnightMoves(long knightPos) {
        long knightMoves = 0L;
        long a_file = Utility.a_file;
        long h_file = Utility.h_file;

        // calc all possible knight moves
        long m1 = (knightPos & ~(a_file | (a_file << 1))) << 6;
        long m2 = (knightPos & ~a_file) << 15;
        long m3 = (knightPos & ~h_file) << 17;
        long m4 = (knightPos & ~(h_file | (h_file >>> 1))) << 10;
        long m5 = (knightPos & ~(h_file | (h_file >>> 1))) >>> 6;
        long m6 = (knightPos & ~h_file) >>> 15;
        long m7 = (knightPos & ~a_file ) >>> 17;
        long m8 = (knightPos & ~(a_file | (a_file << 1))) >>> 10;

        // combine all possible moves
        knightMoves |= (m1 | m2 | m3 | m4 | m5 | m6 | m7 | m8);
    
        return knightMoves;
    }

    public static HashMap<Long, Long> generateAllKnightMoves(){
        HashMap<Long, Long> knightAttacks = new HashMap<Long, Long>();
        long pos = 1L;
        for(int i = 0; i < 64; i++){
            long moves = generateKnightMoves(pos);
            knightAttacks.put(pos, moves);
            pos = pos << 1;
        }
        return knightAttacks;
    }

    // KING HERE ----------------------------
    // generate king moves for a given king position
    private static long generateKingMoves(long kingPos) {
        long kingMoves = 0L;
        long a_file = Utility.a_file;
        long h_file = Utility.h_file;

        // calc all possible king moves (l = left, r = right, u = up, d = down)
        long l = (kingPos & ~a_file) >>> 1;
        long ul = (kingPos & ~a_file) >>> 9;
        long ur = (kingPos & ~a_file) << 7;
        long r = (kingPos & ~h_file) << 1;
        long u = kingPos << 8;
        long d = kingPos >>> 8;
        long dl = (kingPos & ~h_file) >>> 7;
        long dr = (kingPos & ~h_file) << 9;

        // combine all possible moves
        kingMoves |= (l | r | u | d | ul | ur | dl | dr);

        return kingMoves;
    }

    public static HashMap<Long, Long> generateAllKingMoves(){
        HashMap<Long, Long> kingAttacks = new HashMap<Long, Long>();
        long pos = 1L;
        for(int i = 0; i < 64; i++){
            long moves = generateKingMoves(pos);
            kingAttacks.put(pos, moves);
            pos = pos << 1;
        }
        return kingAttacks;
    }

    // SLIDING PIECE MOVES
    // Helper: generate all possible combinations of blockers for a given number of squares
    public static List<Long> allPossibleOccupancies(int numOfSquares) {
        // list to hold all conmbinations
        List<Long> occList = new ArrayList<Long>();
        int possibleCombin = 1 << numOfSquares;
        // loop through all 2^numOfSquares numbers since there are 2^numOfSquares possible configurations
        for (long i = 0; i < possibleCombin; i++) {
                occList.add(i);
            }
        return occList;
    }

    // Helper: get index into arrays for magic numbers, masks, etc to do with magic bitboards
    public static int MagicIndex(int start){
        return (56-8*(((int)start/8))+(start%8));
    }

    // ROOK MOVES
    public static HashMap<Integer, HashMap<Long, Long>> generateRookAttacks() {
        long rookLoc;
        long attackSet;
        HashMap<Integer, HashMap<Long, Long>> allAttacks = new HashMap<Integer, HashMap<Long,Long>>();
        for(int rookIndex = 0; rookIndex < 64; rookIndex++) {
            HashMap<Long, Long> rookAttacks = new HashMap<Long, Long>();
            // put the rook in its location in an otherwise empty bitboard
            // I'm shifting it by 64-index and not just by index because of the way our indexing works (since index 0 is in the lower left corner)
            //rookLoc = (1L << (64-rookIndex));
            rookLoc = (1L << rookIndex);
            long blockers = 0L;
            // squares the rook can move to in the north direction
            int north = 7 - (rookIndex/8);
            // squares the rook can move to in the south direction
            int south = rookIndex/8;
            // squares the rook can move to in the west direction
            int west = rookIndex%8;
            // squares the rook can move to in the east direction
            int east = 7 - (rookIndex%8);
            // generate all possible combinations for each direction given the number of squares in each direction
            List<Long> northCombin = allPossibleOccupancies(north);
            List <Long> southCombin = allPossibleOccupancies(south);
            List<Long> westCombin = allPossibleOccupancies(west);
            List<Long> eastCombin = allPossibleOccupancies(east);

            // for each possible combination in the north direction
            for(Long nCombin : northCombin) {
                long nblock = 0L;
                // put nCombin in the vertical (north) direction
                for(int i = 1; i < north+1; i++) {
                    // get the i-th bit in nCombin
                    long ithBit = (nCombin >>> i-1) & 1;
                    if (ithBit == 1) {
                        nblock |= rookLoc << (i*8);
                    }
                    //System.out.printf("These is nCombin: " + Long.toBinaryString(nCombin)+ "\n");
                    //System.out.println("These are the blockers for north: " + Long.toBinaryString(blockers)+ "\n");
                }
                for(Long sCombin : southCombin) {
                    long sblock = 0L;
                    // put sCombin in the vertical (south) direction
                    for(int j = 1; j < south+1; j++) {
                        long jthBit = (sCombin >>> j-1) & 1;
                        if(jthBit == 1) {
                            sblock |= rookLoc >>> j*8;
                        }
                    }
                    for(Long wCombin : westCombin) {
                        long wblock = wCombin << ((rookIndex/8)*8);

                        for (Long eCombin : eastCombin) {
                            long eblock = eCombin << (rookIndex+1);
                            blockers = sblock | wblock | eblock | nblock;
                            attackSet = MagicBitboards.getRookAttacks(rookLoc, blockers);
                            if(north != 0){
                                blockers = blockers & ~Utility.eighth_rank;
                            }
                            if(south != 0){
                                blockers = blockers & ~Utility.first_rank;
                            }
                            if(east != 0){
                                blockers = blockers & Utility.mask_off_right_edge;
                            }
                            if(west != 0){
                                blockers = blockers & Utility.mask_off_left_edge;
                            }
                            int magicIndex = MagicIndex(rookIndex);
                            long key = (blockers * MagicBitboards.RookMagicNum[rookIndex]) >>> (64 - MagicBitboards.RBits[rookIndex]);
                            rookAttacks.put(blockers, attackSet);
                            blockers = 0L;
                        }
                    }
                }
            }
            allAttacks.put(rookIndex, rookAttacks);
        }
            return allAttacks;
    }

    public static HashMap<Integer, HashMap<Long,Long>> generateBishopAttacks() {
        long bishopLoc;
        long attackSet;
        HashMap<Integer, HashMap<Long,Long>> allAttacks = new HashMap<Integer, HashMap<Long,Long>>();
        for(int bishopIndex = 0; bishopIndex < 64; bishopIndex++) {
            HashMap<Long, Long> bishopAttacks = new HashMap<Long, Long>();
            // put the bishop in its location in an otherwise empty bitboard
            bishopLoc = (1L << bishopIndex);
            long blockers = 0L;
            // squares the bishop can move to in the northwest direction
            int northwest = Math.min(((int)bishopIndex%8), 7-((int)bishopIndex/8));
            // squares the bishop can move to in the northeast direction
            int northeast = Math.min(7-((int)bishopIndex%8),7-((int)bishopIndex/8));
            // squares the bishop can move to in the southwest direction
            int southwest = Math.min(((int)bishopIndex/8),(int)bishopIndex%8);
            // squares the bishop can move to in the southeast direction
            int southeast = Math.min(((int)bishopIndex/8), 7-((int)bishopIndex%8));
            // generate all possible combinations for each direction given the number of squares in each direction
            List<Long> northwestCombin = allPossibleOccupancies(northwest);
            List<Long> northeastCombin = allPossibleOccupancies(northeast);
            List<Long> southwestCombin = allPossibleOccupancies(southwest);
            List<Long> southeastCombin = allPossibleOccupancies(southeast);

            // for each possible combination in the north direction
            for(Long nwCombin : northwestCombin) {
                long nwblock = 0L;
                // put nwCombin in the northwest direction
                for(int i = 1; i < northwest+1; i++) {
                    // get the i-th bit in nwCombin
                    long ithBit = (nwCombin >>> i-1) & 1;
                    if (ithBit == 1) {
                        nwblock |= bishopLoc << (i*7);
                    }
                }
                for(Long neCombin : northeastCombin) {
                    long neblock = 0L;
                    // put neCombin in the northeast direction
                    for(int j = 1; j < northeast+1; j++) {
                        long jthBit = (neCombin >>> j-1) & 1;
                        if(jthBit == 1) {
                            neblock |= bishopLoc << (j*9);
                        }
                    }
                    for(Long swCombin : southwestCombin) {
                        long swblock = 0L;
                        // put swCombin in the southwest direction
                        for(int k = 1; k < southwest+1; k++) {
                            long kthBit = (swCombin >>> k-1) & 1;
                            if(kthBit == 1) {
                                swblock |= bishopLoc >>> (k*9);
                            }
                        }

                        for (Long seCombin : southeastCombin) {
                            long seblock = 0L;
                            // put seCombin in the southeast direction
                            for(int l = 1; l < southeast+1; l++) {
                                long lthBit = (seCombin >>> l-1) & 1;
                                if(lthBit == 1) {
                                    seblock |= bishopLoc >>> (l*7);
                                }
                            }
                            blockers = nwblock | neblock | swblock | seblock;
                            blockers = blockers & ~Utility.eighth_rank & ~Utility.first_rank & Utility.mask_off_right_edge & Utility.mask_off_left_edge;
                            attackSet = MagicBitboards.getBishopAttacks(bishopLoc, blockers);
                            /*int magicIndex = MagicIndex(bishopIndex);
                            long key = (blockers * MagicBitboards.BishopMagicNum[magicIndex]) >>> (64 - MagicBitboards.BBits[magicIndex]);*/
                            bishopAttacks.put(blockers, attackSet);
                            blockers = 0L;
                        }
                    }
                }
            }
            allAttacks.put(bishopIndex, bishopAttacks);
        }
        return allAttacks;
    }

    // Main function creates all attack maps needed and writes them to files, to be loaded in upon the program's start
    public static void main(String[] args){
        /*HashMap<Long, Long> knightAttacks = new HashMap<Long, Long>();
        knightAttacks = generateAllKnightMoves();
        File outputFile1 = new File(System.getProperty("user.dir"), "knightAttackMap");
        try{
            FileOutputStream fos1 = new FileOutputStream(outputFile1);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(knightAttacks);
            oos1.close();
            fos1.close();
        }
        catch (IOException e){
            System.out.println("Knight write failed");
        }

        HashMap<Long, Long> kingAttacks = new HashMap<Long, Long>();
        kingAttacks = generateAllKingMoves();
        File outputFile2 = new File(System.getProperty("user.dir"), "kingAttackMap");
        try{
            FileOutputStream fos2 = new FileOutputStream(outputFile2);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(kingAttacks);
            oos2.close();
            fos2.close();
        }
        catch (IOException e){
            System.out.println("King write failed");
        }

        HashMap<Integer, HashMap<Long, Long>> rookAttacks = new HashMap<Integer, HashMap<Long, Long>>();
        rookAttacks = generateRookAttacks();
        File outputFile3 = new File(System.getProperty("user.dir"), "rookAttackMap");
        try{
            FileOutputStream fos3 = new FileOutputStream(outputFile3);
            ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
            oos3.writeObject(rookAttacks);
            oos3.close();
            fos3.close();
        }
        catch (IOException e){
            System.out.println("Rook write failed");
        }*/

        HashMap<Integer, HashMap<Long, Long>> bishopAttacks = new HashMap<Integer, HashMap<Long, Long>>();
        bishopAttacks = generateBishopAttacks();
        File outputFile4 = new File(System.getProperty("user.dir"), "bishopAttackMap");
        try{
            FileOutputStream fos4 = new FileOutputStream(outputFile4);
            ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
            oos4.writeObject(bishopAttacks);
            oos4.close();
            fos4.close();
        }
        catch (IOException e){
            System.out.println("Bishop write failed");
        }

        /*HashMap<Integer, HashMap<Long,Long>> rookMap = null;
        File inputFile = new File(System.getProperty("user.dir"), "rookAttackMap");
        try{
            FileInputStream fis = new FileInputStream(inputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            rookMap = (HashMap<Integer, HashMap<Long,Long>>) ois.readObject();
            fis.close();
            ois.close();
        }
        catch(IOException | ClassNotFoundException | ClassCastException e){
            System.out.println("input failed");
        }
        
        System.out.println("Rook at index 2, blockers at index 1 and 4");
        int rookIndex = 16;
        int magicIndex = MagicIndex(rookIndex);
        long blockers = (1L << 56) | 1L;
        blockers = blockers & MagicBitboards.RookMask[rookIndex];
        long attack = rookMap.get(rookIndex).get(blockers);
        System.out.println(Long.toBinaryString(attack));*/
    }

}
