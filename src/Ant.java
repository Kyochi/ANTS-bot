
public class Ant {

	private Tile tile;
	private Action action;
	
	public Ant(Play play, Tile tile) {
		this.tile = tile;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void activer() {
		this.action.activer();		
	}

}
