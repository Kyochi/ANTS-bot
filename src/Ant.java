
public class Ant {

	private Tile tile;
	private Action action;
	
	public Ant(Game play, Tile tile) {
		this.tile = tile;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean activer() {
		return this.action.activer();		
	}

}
