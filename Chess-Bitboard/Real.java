import java.util.Scanner;
import java.util.regex.*;

public class Real extends Player{
    public Move getMove(){
        Move move;

        int attempts = 0;
        int maxAttempts = 3;

        // Regular expression to parse move entry
        String moveRegex = "[PNRBQK][A-H][1-8]x?[A-H][1-8]";

        Scanner input = new Scanner(System.in);
        String userChoice = "";

        System.out.println("What move would you like to make?\n");
        System.out.println("Please enter the move in the form:");
        System.out.println("<piece type letter><file-rank of origin square>[x if captured]<file-rank of destination>, e.g. 'ra1xa8'");
        System.out.println("Type 'quit' at any time to exit the program");

        while(attempts < maxAttempts){
            userChoice = input.nextLine();
            userChoice = userChoice.toUpperCase();

            if (userChoice.equals("QUIT")) {
                System.out.println("\nExiting the program...");
                System.exit(0);
            }

            if(Pattern.matches(moveRegex, userChoice)){
                // Process the entry into a Move
                // TODO: this is translating the char into the piece "index", not sure if there's a better way
                short piece = Utility.getPieceIndex(userChoice.charAt(0));
                int prevColumn = userChoice.charAt(1)-65;
                int prevRow = userChoice.charAt(2)-49;
                int endRow, endColumn;
                boolean captured;

                if(userChoice.charAt(3)=='X'){
                    captured = true;
                    endColumn = userChoice.charAt(4);
                    endRow = userChoice.charAt(5);
                }

                else{
                    captured = false;
                    endColumn = userChoice.charAt(3)-65;
                    endRow = userChoice.charAt(4)-49;
                }

                int prevMove = prevRow*8+prevColumn;
                long endMove = Utility.setBit(0L, endRow*8+endColumn);
                move = new Move(endMove, piece, prevMove, captured);
                // TODO: Confirm that move is valid move (also move this outside of the loop?)
                return move;
            }
            attempts++;
            System.out.println("\nPlease input your move in the correct format.");
            System.out.println("\nFor example: rook at a1 to a8 capturing a piece is: ra1xa8. \nWithout a capture is: ra1a8.");
            System.out.println("\nFiles are a-h, ranks are 1-8. \nAttempts: " + attempts + "/" + maxAttempts);
        }
        return null;
    }
}
