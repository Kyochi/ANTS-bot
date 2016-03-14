import java.util.Set;

public class Ant implements Comparable<Ant>{

	private Tile tile;
	private Action action;
	
	private boolean inFormation = false;
	
	public Ant(Tile tile) {
		this.tile = tile;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean activer() {
		return this.action.activer();		
	}

	public boolean isInFormation() {
		return inFormation;
	}

	public boolean canKillHill() {
		Set<Tile> ennemiesFourmillieres = Game.getSingleton().getEnnemiesFourmillieres();
		for (Tile tile : ennemiesFourmillieres) {
			if(Game.getSingleton().getConnexion().getDistance(this.tile, tile) < 5) {
				return true;
			}
		}
		return false;
	}

	public Tile getTile() {
		return this.tile;
	}
	
	@Override
    public int hashCode() {
        return tile.getRow() * Ants.MAX_MAP_SIZE + tile.getCol();
    }

	@Override
	public int compareTo(Ant o) {
        return hashCode() - o.hashCode();
	}
}
