import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.io.*;

public class RookAttacks {
    //public long[] rankOccupancy = new long[256];

    //public void allPossibleOccupancies() {
        // loop through all 256 numbers since there are 2^8 possible configurations for a rank
      //  for (int i = 0; i < 256; i++) {
            // no occupancy
        //    long rankOcc = 0L;
          //  for (int j = 0; j < 8; j++) {
                // get the jth bit from number i
            //    int jthBit = i >> j;
                // if jth bit is 1
              //  if ((jthBit & 1) == 1) {
                    // shift 1L by j, which puts a 1 in the occupied bit position in rankOcc
                //    rankOcc |= (1L << j);
                //}
            //}
            //rankOccupancy[i] = rankOcc;
        //}
    //}

    /*     // for each square
    for(int index = 0; index < 64; index++) {
        long ret = 0L;
        // set row to all 1s
        long setRow = 0xffL << (8 * (index/8));

        // a bitboard with a 1 in the place of the index
        long bit = 1L << index;

        // put 1s in the up direction until you encounter a blocker
        do {
            bit <<= 8;
            ret |= bit;
        } while (bit > 0 && (bit & occ) == 0);

        // a bitboard with a 1 in the place of the index
        long bit = 1L << index;

        // down direction
        do {
            bit >>>= 8;
            ret |= bit;
        } while (bit != 0 && (bit & occ) == 0);

        // a bitboard with a 1 in the place of the index
        long bit = 1L << index;

        // Left
        do {
            bit <<= 1;
            if ((bit & rowbits) != 0)
                ret |= bit; 
            else
                break;
        } while ((bit & occ) == 0);

        // a bitboard with a 1 in the place of the index
        long bit = 1L << index;

        // right    
        do {
            bit >>>= 1;
            if ((bit & rowbits) != 0)
                ret |= bit;
            else
                break;
        } while ((bit & occ) == 0);

        return ret;
    }
    */

    public static void rookAttackSet(){
        long rookLocation;
        long attackSet;
        HashMap<Long, Long> rookAttacks = new HashMap<Long, Long>();

        for(int i = 0; i < 64; i++){
            rookLocation = (1L << i);
            long blockers = 0;
            for(int north = -1; north < 7-((int)i/8); north++){

                blockers = north > 0 ? blockers | (rookLocation << (north*8)) : blockers;
                for(int east = -1; east < 7-(i%8); east++){

                    blockers = east > 0 ? blockers | (rookLocation << east) : blockers;

                    for(int south = -1; south < 7-((int)i/8); south++){

                        blockers = south > 0 ? blockers | (rookLocation >>> (south*8)) : blockers;

                        for(int west = -1; west < 7-(i%8); west++){

                            blockers = west > 0 ? blockers | (rookLocation >>> west) : blockers;
                            attackSet = MagicBitboards.getRookAttacks(rookLocation, blockers);
                            List<Long> permutations = getPermutations(blockers, north, east, south, west, i);
                            for(Long perm : permutations){
                                int magicIndex = MagicIndex(i);
                                long key = (perm * MagicBitboards.RookMagicNum[magicIndex]) >> (64 - MagicBitboards.RBits[magicIndex]);
                                rookAttacks.put(key, attackSet);
                            }
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

    public static List<Long> getPermutations(long blockers, int north, int east, int south, int west, int rookIndex){
        List<Long> retlist = new ArrayList<Long>();
        long copy = blockers;
        for(int i = north; i < 8; i++){

            if (north > 0){
                copy = copy | (1L << (i*8));
            }
            

            for(int j = east; j < 8; j++){

                if (east > 0){
                    copy = copy | (1L << j);
                }

               

                for(int k = south; k < 8; k++){

                    if (south > 0){
                        copy = copy | (1L >>> k);
                    }

                    

                    for(int l = west; l < 8; l++){

                        if (west > 0){
                            copy = copy | (1L >> (l*8));
                        }
                        retlist.add(copy);
                    }
                }
            }
        }

        return retlist;
    }

    public static int MagicIndex(int start){
        return (56-8*(((int)start/8))+(start%8));
    }
    
    public static void main(String[] args) {
        rookAttackSet();
        /*HashMap<Long, Long> rookMap = null;
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
        }*/
    }
}
