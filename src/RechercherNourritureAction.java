import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RechercherNourritureAction extends Action {

	public RechercherNourritureAction(Game game, Set<Ant> fourmis) {
		super(game, fourmis);
	}

	@Override
	public boolean activer() {
		// find close food
		Map<Tile, Tile> foodTargets = new HashMap<Tile, Tile>();
		List<Route> foodRoutes = new ArrayList<Route>();
		Ants ants = game.getConnexion();
		TreeSet<Tile> sortedFood = new TreeSet<Tile>(game.getNourritures());
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
					&& game.doMoveLocation(route.getStart(), route.getEnd())) {
				foodTargets.put(route.getEnd(), route.getStart());
			} else {
				activer = false;
			}
		}
		return activer;
	}

}
