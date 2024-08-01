import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.io.*;

public class BishopAttacks {

    public static void bishopAttackSet(){
        long bishopLocation;
        long attackSet;
        HashMap<Long, Long> bishopAttacks = new HashMap<Long, Long>();

        for(int i = 0; i < 64; i++){
            bishopLocation = (1L << i);
            long blockers = 0;
            for(int northwest = -1; northwest < Math.min(((int)i%8), 7-((int)i/8); northwest++){

                blockers = northwest > 0 ? blockers | (bishopLocation << (northwest*9)) : blockers;
                for(int northeast = -1; northeast < Math.min(7-((int)i%8),7-((int)i/8)); northeast++){

                    blockers = northeast > 0 ? blockers | (bishopLocation << (northeast*7)) : blockers;

                    for(int southwest = -1; southwest < Math.min(((int)i/8),(int)i%8)); southwest++){

                        blockers = southwest > 0 ? blockers | (bishopLocation >>> (southwest*7)) : blockers;

                        for(int southeast = -1; southeast < Math.min(((int)i/8), 7-((int)i%8)); southeast++){

                            blockers = southeast > 0 ? blockers | (bishopLocation >>> (southeast*9)) : blockers;
                            attackSet = MagicBitboards.getBishopAttacks(bishopLocation, blockers);
                            List<Long> permutations = getPermutations(blockers, northwest, northeast, southwest, southeast, i);
                            for(Long perm : permutations){
                                int magicIndex = MagicIndex(i);
                                long key = (perm * MagicBitboards.BishopMagicNum[magicIndex]) >> (64 - MagicBitboards.BBits[magicIndex]);
                                bishopAttacks.put(key, attackSet);
                            }
                        }
                    }
                }
            }
        }
        File outputFile = new File(System.getProperty("user.dir"), "bishopAttackMap");
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

    public static List<Long> getPermutations(long blockers, int northwest, int northeast, int southwest, int southeast, int bishopIndex){
        List<Long> retlist = new ArrayList<Long>();
        long copy = blockers;
        for(int i = northwest; i < 8; i++){

            if (northwest > 0){
                copy = copy | (1L << (i*9));
            }
            

            for(int j = northeast; j < 8; j++){

                if (northeast > 0){
                    copy = copy | (1L << (j*7);
                }

               

                for(int k = southwest; k < 8; k++){

                    if (southwest > 0){
                        copy = copy | (1L >>> (k*7));
                    }

                    

                    for(int l = southeast; l < 8; l++){

                        if (southeast > 0){
                            copy = copy | (1L >> (l*9));
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
        bishopAttackSet();
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
