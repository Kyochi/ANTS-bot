import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Game {
	private static Game singleton;
	
	private Ants connexion;

	private int lignes = 0;
	private int colonnes = 0;
	private int tours = 0;
	private Map<Tile, Tile> orders = new HashMap<Tile, Tile>();

	public int getLignes() {
		return lignes;
	}

	public void setLignes(int lignes) {
		this.lignes = lignes;
	}

	public int getColonnes() {
		return colonnes;
	}

	public void setColonnes(int colonnes) {
		this.colonnes = colonnes;
	}

	public int getTours() {
		return tours;
	}

	public void setTours(int tours) {
		this.tours = tours;
	}

	public Map<Tile, Tile> getOrders() {
		return orders;
	}

	public void setOrders(Map<Tile, Tile> orders) {
		this.orders = orders;
	}

	public Set<Tile> getNourritures() {
		return nourritures;
	}

	public void setNourritures(Set<Tile> nourritures) {
		this.nourritures = nourritures;
	}

	public Set<Tile> getFourmillieres() {
		return fourmillieres;
	}

	public void setFourmillieres(Set<Tile> fourmillieres) {
		this.fourmillieres = fourmillieres;
	}

	public Set<Tile> getMesFourmillieres() {
		return mesFourmillieres;
	}

	public void setMesFourmillieres(Set<Tile> mesFourmillieres) {
		this.mesFourmillieres = mesFourmillieres;
	}

	public Set<Tile> getEnnemiesFourmillieres() {
		return ennemiesFourmillieres;
	}

	public void setEnnemiesFourmillieres(Set<Tile> ennemiesFourmillieres) {
		this.ennemiesFourmillieres = ennemiesFourmillieres;
	}

	public Set<Ant> getMesFourmis() {
		return mesFourmis;
	}

	public void setMesFourmis(Set<Ant> mesFourmis) {
		this.mesFourmis = mesFourmis;
	}

	public Set<Ant> getEnnemiesFourmis() {
		return ennemiesFourmis;
	}

	public void setEnnemiesFourmis(Set<Ant> ennemiesFourmis) {
		this.ennemiesFourmis = ennemiesFourmis;
	}

	public Set<Tile> getBrouillardTiles() {
		return brouillardTiles;
	}

	public void setBrouillardTiles(Set<Tile> brouillardTiles) {
		this.brouillardTiles = brouillardTiles;
	}

	public Ants getConnexion() {
		return connexion;
	}

	public static void setSingleton(Game singleton) {
		Game.singleton = singleton;
	}
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
			ants.add(new Ant(tile));
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
		
		Set<Ant> fourmiSeekBouffe = new HashSet<Ant>();
		for (Ant fourmi : mesFourmis) {
			if(!fourmi.isInFormation() && !fourmi.canKillHill()) {
				fourmiSeekBouffe.add(fourmi);
			}
		}
		
		actionSet(fourmiSeekBouffe, new RechercherNourritureAction());
		activerSet(fourmiSeekBouffe);
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
	
	public boolean doMoveLocation(Tile antLoc, Tile destLoc) {
		// Track targets to prevent 2 ants to the same location
		List<Aim> directions = this.connexion.getDirections(antLoc, destLoc);
		for (Aim direction : directions) {
			if (doMoveDirection(antLoc, direction)) {
				return true;
			}
		}
		return false;
	}

	public boolean doMoveDirection(Tile antLoc, Aim direction) {
		// Track all moves, prevent collisions
		Tile newLoc = this.connexion.getTile(antLoc, direction);
		if (this.connexion.getIlk(newLoc).isUnoccupied() && !orders.containsKey(newLoc)) {
			this.connexion.issueOrder(antLoc, direction);
			orders.put(newLoc, antLoc);
			return true;
		} else {
			return false;
		}
	}
}
