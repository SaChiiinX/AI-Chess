import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.io.*;

public class RookAttacks {
    //public long[] rankOccupancy = new long[256];

    // generate all possible combinations of blockers for a given number of squares
    public static List<Long> allPossibleOccupancies(int numOfSquares) {
        // list to hold all conmbinations
        List<Long> occList = new ArrayList<Long>();
        int possibleCombin = 1 << numOfSquares;
        // loop through all 2^numOfSquares numbers since there are 2^numOfSquares possible configurations
        for (long i = 0; i < possibleCombin; i++) {
            // no occupancy
            //long occup = 0L;
            //for (int j = 0; j < numOfSquares; j++) {
                // the following is done in order to convert the number to its binary representation
                // get the j-th bit from number i
                //int jthBit = (i >> j) & 1;
                // if jth bit is 1
                //if (jthBit == 1) {
                    // shift 1L by j, which puts a 1 in the occupied bit position in occup
                    //occup |= (1L << j);
                    // in the end, occup should just be the binary representation of number i
                //}
                occList.add(i);
            }
        return occList;
    }

    public static void rookAttackSet() {
        long rookLoc;
        long attackSet;
        HashMap<Long, Long> rookAttacks = new HashMap<Long, Long>();
        for(int rookIndex = 0; rookIndex < 3; rookIndex++) {
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
                            long key = (blockers * MagicBitboards.RookMagicNum[magicIndex]) >>> (64 - MagicBitboards.RBits[magicIndex]);
                            rookAttacks.put(key, attackSet);
                            // print statements for testing
                                System.out.printf("These are the blockers: " + Long.toBinaryString(blockers)+ "\n");
                                System.out.printf("This is the attackset: " + Long.toBinaryString(attackSet)+ "\n");
                                //System.out.printf("This is the key: " + Long.toBinaryString(key)+ "\n");
                                //System.out.printf("This is the magic number: " + Long.toBinaryString(MagicBitboards.RookMagicNum[magicIndex])+ "\n");
                                System.out.printf("This is the rook index: " + rookIndex+ "\n");
                                System.out.printf("This is the north combination: " + Long.toBinaryString(nCombin) + "\n");
                                System.out.printf("This is the south combination: " + Long.toBinaryString(sCombin) + "\n");
                                System.out.printf("This is the west combination: " + Long.toBinaryString(wCombin) + "\n");
                                System.out.println("This is the east combination: " + Long.toBinaryString(eCombin) + "\n");
                            blockers = 0L;
                        }
                    }
                }
            }
        }
            File outputFile = new File(System.getProperty("user.dir"), "rookAttackMap");
            try{
                FileOutputStream fos = new FileOutputStream(outputFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(rookAttacks);
                oos.close();
                fos.close();
            }
            catch (IOException e){
                System.out.println("File write failed");
            }
    }

    public static int MagicIndex(int start){
        return (56-8*(((int)start/8))+(start%8));
    }
    
    public static void main(String[] args) {
        //rookAttackSet();
        //System.out.printf("Our boards index: " + 56 + "\n");
        //System.out.printf("Calculated magic index: " + MagicIndex(56) + "\n");
        //long example = 0b
        HashMap<Long, Long> rookMap = null;
        File inputFile = new File(System.getProperty("user.dir"), "rookAttackMap");
        try{
            FileInputStream fis = new FileInputStream(inputFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            rookMap = (HashMap<Long,Long>) ois.readObject();
            fis.close();
            ois.close();
        }
        catch(IOException | ClassNotFoundException | ClassCastException e){
            System.out.println("input failed");
        }
        Set<Long> keys = rookMap.keySet();

        for(Long key : keys){
            System.out.println(Long.toBinaryString(key) + " " + Long.toBinaryString(rookMap.get(key)) + "\n");
        } 

        System.out.println("Rook at index 2, blockers at index 1 and 4");
        int rookIndex = 2;
        int magicIndex = MagicIndex(rookIndex);
        long blockers = 0b010010L;
        long key = (blockers * MagicBitboards.RookMagicNum[magicIndex]) >>> (64 - MagicBitboards.RBits[magicIndex]);
        long attack = rookMap.get(key);
        System.out.println(Long.toBinaryString(attack));
    }
}