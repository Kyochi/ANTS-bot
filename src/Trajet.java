import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trajet {

	private Tile origin;
	private Tile end;
	private Ants connexion;
	private int rowDelta = 0;
	private int colDelta = 0;
	private Tile[][] maps;
	private List<Aim> tilesTrajet = new ArrayList<Aim>();
	
	public Tile current;

	private final int NEAREST = 1;
	private int compteur = 0;

	public Trajet(Tile t1, Tile t2, Ants ants) {
		this.origin = t1;
		this.end = t2;
		this.connexion = ants;
	}

	public List<Aim> calculer() {
		if(!subcalculer(origin, end)) {
			return connexion.getDirections(origin, end);
		}
		Collections.reverse(tilesTrajet);
		return tilesTrajet;
	}
	
	private boolean subcalculer(Tile debut, Tile fin) {
		List<Aim> aims = connexion.getDirections(debut, fin);
		for (Aim aim : aims) {
			Tile sub = connexion.getTile(debut, aim);
			if(!connexion.getIlk(sub).isPassable()) return false;
			if(compteur++ > 6) return false;
			if(sub.equals(end)) {
				tilesTrajet.add(aim);
				return true;
			}
			if(subcalculer(sub, fin)) {
				tilesTrajet.add(aim);
				return true;
			}
		}
		return false;
	}
}
