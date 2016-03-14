import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Game {
	private static Game singleton;
	
	private Ants connexion;

	private int lignes = 0;
	private int colonnes = 0;
	private int tours = 0;
	private Map<Tile, Tile> orders = new HashMap<Tile, Tile>();

	private Set<Tile> nourritures;
	private Set<Tile> fourmillieres;
	private Set<Tile> mesFourmillieres = new HashSet<Tile>();
	private Set<Tile> ennemiesFourmillieres = new HashSet<Tile>();

	private Set<Ant> mesFourmis = new HashSet<Ant>();
	private Set<Ant> ennemiesFourmis = new HashSet<Ant>();

	private Set<Tile> brouillardTiles;
	
	public static Game getSingleton() {
		if(singleton == null) {
			singleton = new Game();
		}
		return singleton;
	}
	
	public Game() {
		
	}
	
	public void setConnexion(Ants ants) {
		this.connexion = ants;
		initialiser();
	}

	private void initialiser() {
		tours = this.connexion.getTurnTime();
		lignes = this.connexion.getRows();
		colonnes = this.connexion.getCols();
		nourritures = this.connexion.getFoodTiles();
		mesFourmillieres = this.connexion.getMyHills();
		ennemiesFourmillieres = this.connexion.getEnemyHills();
		defineFourmis();
	}

	private void defineFourmis() {
		fourmis(mesFourmis, this.connexion.getMyAnts());
		fourmis(ennemiesFourmis, this.connexion.getEnemyAnts());
	}

	private void fourmis(Set<Ant> ants, Set<Tile> tiles) {
		for (Tile tile : tiles) {
			ants.add(new Ant(this, tile));
		}
	}

	public void play() {
		orders.clear();
		
		for (Iterator<Tile> locIter = brouillardTiles.iterator(); locIter.hasNext();) {
			Tile next = locIter.next();
			if (connexion.isVisible(next)) {
				locIter.remove();
			}
		}
		
		actionSet(this.mesFourmis, new RechercherNourritureAction());
		activerSet(this.mesFourmis);
	}

	private void activerSet(Set<Ant> setFourmis) {
		for (Ant fourmi : setFourmis) {
			fourmi.activer();
		}
	}
	private void actionSet(Set<Ant> setFourmis, Action action) {
		for (Ant fourmi : setFourmis) {
			fourmi.setAction(action);
		}
	}
}
