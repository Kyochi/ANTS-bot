import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Game {
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

	// private void defineFourmilliere() {
	// fourmilliere(ennemiesFourmillieres, this.connexion.getEnemyHills());
	// }
	//
	// private void fourmilliere(Set<Tile> fourmilieres, Set<Tile>
	// nouvellesFourmillieres) {
	// for (Tile tile : nouvellesFourmillieres) {
	// if (!fourmilieres.contains(tile)) {
	// ennemiesFourmillieres.add(tile);
	// }
	// }
	// }

	private void defineFourmis() {
		mesFourmis.clear();
		ennemiesFourmis.clear();
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

		if (brouillardTiles == null) {
			brouillardTiles = new HashSet<Tile>();
			for (int row = 0; row < connexion.getRows(); row++) {
				for (int col = 0; col < connexion.getCols(); col++) {
					brouillardTiles.add(new Tile(row, col));
				}
			}
		}

		for (Iterator<Tile> locIter = brouillardTiles.iterator(); locIter.hasNext();) {
			Tile next = locIter.next();
		//	if (connexion.isVisible(next)) {
			//	locIter.remove();
			//}
		}

		// prevent stepping on own hill
		for (Tile myHill : getMesFourmillieres()) {
			orders.put(myHill, null);
		}

		// Recherche de nourriture
		Set<Ant> fourmiSeekBouffe = new HashSet<Ant>();
		TreeSet<Ant> sortedAnts = new TreeSet<Ant>(getMesFourmis());

		for (Ant fourmi : sortedAnts) {
			if (!fourmi.isInFormation() && !fourmi.canKillHill()) {
				fourmiSeekBouffe.add(fourmi);
			}
		}
		RechercherNourritureAction rna = new RechercherNourritureAction(this, fourmiSeekBouffe);
		rna.activer();

		Set<Ant> fourmiAttaquer = new HashSet<Ant>();
		for (Ant fourmi : sortedAnts) {
			if (!orders.containsValue(fourmi.getTile())) {
				fourmiAttaquer.add(fourmi);
			}
		}
		AttaquerAction aa = new AttaquerAction(this, fourmiAttaquer);
		aa.activer();

		Set<Ant> fourmiExplorer = new HashSet<Ant>();
		for (Ant fourmi : sortedAnts) {
			if (!orders.containsValue(fourmi.getTile())) {
				fourmiExplorer.add(fourmi);
			}
		}
		ExplorerAction ea = new ExplorerAction(this, fourmiExplorer);
		ea.activer();

		// unblock hills
		for (Tile myHill : mesFourmillieres) {
			for (Ant ant : sortedAnts) {
				if (ant.getTile().equals(myHill) && !orders.containsValue(myHill)) {
					for (Aim direction : Aim.values()) {
						if (doMoveDirection(myHill, direction)) {
							break;
						}
					}
				}
			}
		}
	}

	public boolean doMoveLocation(Tile antLoc, Tile destLoc) {
		List<Aim> directions = this.connexion.getDirections(antLoc, destLoc);
		for (Aim direction : directions) {
			if (doMoveDirection(antLoc, direction)) {
				return true;
			}
		}
		return false;
	}

	public boolean doMoveDirection(Tile antLoc, Aim direction) {
		Tile newLoc = this.connexion.getTile(antLoc, direction);
		if (this.connexion.getIlk(newLoc).isUnoccupied() && !orders.containsKey(newLoc)) {
			this.connexion.issueOrder(antLoc, direction);
			orders.put(newLoc, antLoc);
			return true;
		} else {
			return false;
		}
	}

	public boolean estObstacle(Tile nodeTeste) {
		return !this.connexion.getIlk(nodeTeste).isPassable();
	}
}
