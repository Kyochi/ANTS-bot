import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RechercherNourritureAction extends Action {

	Map<Tile, Tile> foodTargets = new HashMap<Tile, Tile>();
	public RechercherNourritureAction(Set<Ant> fourmis) {
		super(fourmis);
	}

	@Override
	public boolean activer() {
		// find close food
		List<Route> foodRoutes = new ArrayList<Route>();
		Ants ants = Game.getSingleton().getConnexion();
		TreeSet<Tile> sortedFood = new TreeSet<Tile>(Game.getSingleton().getNourritures());
		TreeSet<Ant> sortedAnts = new TreeSet<Ant>(super.fourmis);
		for (Tile foodLoc : sortedFood) {
			for (Ant antLoc : sortedAnts) {
				int distance = ants.getDistance(antLoc.getTile(), foodLoc);
				Route route = new Route(antLoc.getTile(), foodLoc, distance);
				foodRoutes.add(route);
			}
		}
		boolean activer = true;
		Collections.sort(foodRoutes);
		for (Route route : foodRoutes) {
			if (!foodTargets.containsKey(route.getEnd()) && !foodTargets.containsValue(route.getStart())
					&& Game.getSingleton().doMoveLocation(route.getStart(), route.getEnd())) {
				foodTargets.put(route.getEnd(), route.getStart());
			} else {
				activer = false;
			}
		}
		return activer;
	}

}
