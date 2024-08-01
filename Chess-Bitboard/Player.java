public class Player {
    protected Game game;

    public Player(){
        this.game = null;
    }

    public Player(Game game){
        this.game = game;
    }

    public Move getMove(){return null;}
}
