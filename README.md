# Chess

## How to use
There are two options for how to run the code that has been set up so far.
You can open the appropriate files in Visual Studio Code (VS Code) and run them from there,
or you can run them from the terminal.

### Visual Studio Code
To run the code through VS Code, you must begin in the chess directory in the terminal.
* Open the current project folder in VS Code by typing "code Chess-Magic-Bitboard" into the terminal.
   * You will be asked whether you trust the authors of the file. Click "Yes, I trust the authors".
* If you have never used Java in VS Code before, you will have to do some setup. Follow the instructions in the VS Code window to complete this.
* To run the program itself, open the Game.java file.
   * Scroll to the bottom of the file, where you will find the main function (signature: public static void main(String args[]))
   * Just above the signature of the main function, there is a line that says "Run | Debug". Click "Run".
   * A terminal window will pop up at the bottom of the screen displaying the game. Follow the instructions in that window, entering input as you would normally in the terminal.
   * You can increase the size of that window by pulling the line above the tab names to view more of the game at once.
* To run the tests of legal move generation for each piece, open the LegalMovesTest.java file. It can be found on the left-hand side of the Visual Studio Code window. This tests the number of legal moves generated for a specified piece given a specified board setup. This moves are pseudo-legal moves (moves on the board that are not blocked by a friendly or opponent piece, including captures), not taking into account en passant, castling, moving into check.
   * Scroll down to the bottom of the file, where you will find the main function (signature: public static void main(String args[]))
   * Just above the signature of the main function, there is a line that says "Run | Debug". Click "Run".
   * The results of the tests will be displayed in the "TERMINAL" tab at the bottom of the Visual Studio Code window. You can increase the size of that window by pulling the line above the tab names or scroll up to view the boards printed by the tests, and whether or not the tests passed is printed at the bottom.
* To run the test of our minimax search with alpha/beta pruning, open the BestMoveTest.java file. It can be found on the left-hand side of the Visual Studio Code window. This test calculates the best move for each side for 60 full moves (60 moves per side, or 120 half moves) with the search running at depth 2. Essentially, it plays out a game, and it will stop before reaching the 60 full moves if a checkmate is identified  (stops at the move before a king is taken).
   * Scroll down to the second method, which is the main method (signature: public static void main(String[] args)).
   * Just above the signature of the main function, there is a line that says "Run | Debug". Click "Run".
   * The results of the tests will be displayed in the "TERMINAL" tab at the bottom of the Visual Studio Code window. You will have to wait for some time for the entire process to finish, but you will see each move printed in the terminal as it is calculated.
   * You can increase the size of that window by pulling the line above the tab names or scroll up to view the boards printed by the tests, and whether or not the tests passed is printed at the bottom.


### Terminal
To run the code through the terminal, you must begin in the chess directory in the terminal. Note that this will create .class files for most of the .java files in this program.
* Once in the chess directory, cd into the Chess-Magic-Bitboard directory.
* Type "javac -cp lib/junit-4.13.2.jar *.java" into the terminal to compile the necessary java files.
* To run the program itself, type "java Game" into the terminal.
   * The program will begin running, showing options for the game, displaying the board, and allowing your input.
   * Follow the instructions in the terminal, entering input as you would normally.
   * Depending on the depth, the AI player may take some time to make its move.
   * To terminate the program before the game ends, type ctrl+C.
   * When the game ends, the program will print "Game Over" and allow you to choose whether to play another game.
* To run the tests of legal move generation for each piece, type "java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar LegalMovesTest”. This tests the number of legal moves generated for a specified piece given a specified board setup. This moves are pseudo-legal moves (moves on the board that are not blocked by a friendly or opponent piece, including captures), not taking into account en passant, castling, moving into check.
   * The time it took to generate all the legal moves will be displayed below the last move generated.
   * The results of the tests (how many passed) will be displayed below the time.
* To run the test of our minimax search with alpha/beta pruning, type "java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar BestMoveTest". This test calculates the best move for each side for 60 full moves (60 moves per side, or 120 half moves) with the search running at depth 2. Essentially, it plays out a game, and it will stop before reaching the 60 full moves if a checkmate is identified (stops at the move before a king is taken).
   * You will see each move printed to the terminal as it is calculated, along with the number of half-moves (move by one side) have been made.
   * If the game ends before 60 full moves have been made, the program will print "Game Over" and terminate.
   * The time it took to calculate all the moves will be displayed beneath the last move.