import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Ant implements Comparable<Ant> {

	private Game game;
	private Tile tile;
	private Action action;

	private boolean inFormation = false;

	public Ant(Game game, Tile tile) {
		this.game = game;
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
		Set<Tile> ennemiesFourmillieres = game.getEnnemiesFourmillieres();
		for (Tile tile : ennemiesFourmillieres) {
			if (game.getConnexion().getDistance(this.tile, tile) < 5) {
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

	public void setTile(Tile newLoc) {
		this.tile = newLoc;
	}

	public int haveFriends() {
		List<Tile> arounds = new ArrayList<Tile>();
		arounds.add(game.getConnexion().getTile(tile, Aim.EAST));
		arounds.add(game.getConnexion().getTile(tile, Aim.WEST));
		
		arounds.add(game.getConnexion().getTile(tile, Aim.NORTH));
		arounds.add(game.getConnexion().getTile(game.getConnexion().getTile(tile, Aim.NORTH), Aim.EAST));
		arounds.add(game.getConnexion().getTile(game.getConnexion().getTile(tile, Aim.NORTH), Aim.WEST));
		
		arounds.add(game.getConnexion().getTile(tile, Aim.SOUTH));
		arounds.add(game.getConnexion().getTile(game.getConnexion().getTile(tile, Aim.SOUTH), Aim.EAST));
		arounds.add(game.getConnexion().getTile(game.getConnexion().getTile(tile, Aim.SOUTH), Aim.WEST));
		
		int nb = 0;
		for (Tile around : arounds) {
			for (Ant ant : game.getMesFourmis()) {
				if (ant.getTile().equals(around)) {
					nb++;
					break;
				}
			}
		}
		System.out.println("NB - " + nb);
		return nb;
	}
}
