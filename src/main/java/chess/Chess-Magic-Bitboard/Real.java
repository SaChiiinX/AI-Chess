import java.util.Scanner;
import java.util.regex.*;

public class Real extends Player{
    
    public Real(Game game){
        super(game);
    }
    
    public Move getMove(Scanner input){
        Move move;
        short piece, valid;
        int prevColumn, prevRow, endRow, endColumn, prevMove;
        long endMove;
        boolean captured;
    
        int attempts = 0;
        int maxAttempts = 3;

        // Regular expression to parse move entry
        String moveRegex = "[PNRBQK][A-H][1-8]X?[A-H][1-8]";

        String userChoice = "";

        System.out.println("What move would you like to make?");
        System.out.println("Please enter the move in the form:");
        System.out.println("<piece type letter><file-rank of origin square><file-rank of destination>, e.g. 'ra1a8'");

        while(attempts < maxAttempts){
            userChoice = input.nextLine();
            userChoice = userChoice.toUpperCase();
            if(Pattern.matches(moveRegex, userChoice)){
                // Process the entry into a Move
                piece = game.getTurn() ? (short)-Utility.getPieceIndex(userChoice.charAt(0)) : Utility.getPieceIndex(userChoice.charAt(0));
                prevColumn = userChoice.charAt(1)-65;
                prevRow = userChoice.charAt(2)-49;
                endColumn = userChoice.charAt(3)-65;
                endRow = userChoice.charAt(4)-49;
                prevMove = prevRow*8+prevColumn;
                endMove = Utility.setBit(0L, endRow*8+endColumn);
                valid = game.validMove(piece, prevMove, endMove);
                if(valid == 0){
                    continue;
                }
                captured = (valid < 2) ? false : true;
                move = new Move(endMove, piece, prevMove, captured);
                return move;
            }
            else if(userChoice.equals("QUIT")){
                return null;
            }
            attempts++;
            System.out.println("Please input your move in the correct format.");
            System.out.println("For example: rook at a1 to a8 is: ra1a8.");
            System.out.println("Files are a-h, ranks are 1-8. To quit, type \"quit\"");
            System.out.println("\nAttempts: " + attempts + "/" + maxAttempts);
        }
        return null;
    }
}
