package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 21.08.2015.
 */
public class ActionMapping {
    private Game game;
    private Player player;
    private Action action;

    public ActionMapping(Game s, Player sp, Action e){
        this.game = s;
        this.player = sp;
        this.action = e;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
