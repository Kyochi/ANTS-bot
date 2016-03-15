import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttaquerAction extends Action {

	public AttaquerAction(Game game, Set<Ant> fourmis) {
		super(game, fourmis);
	}

	@Override
	public boolean activer() {
		List<Route> routes = new ArrayList<Route>();
		
		for(Tile hill : game.getEnnemiesFourmillieres()) {
			for (Ant ant : super.fourmis) {
				int distance = game.getConnexion().getDistance(ant.getTile(), hill);
				Route route = new Route(ant.getTile(), hill, distance);
				routes.add(route);
			}
		}
		
		for (Ant ennemiesAnt : game.getEnnemiesFourmis()) {
			for (Ant ant : super.fourmis) {
				boolean distanceFaible = false;
				for (Tile tile : game.getMesFourmillieres()) {
					int distance = game.getConnexion().getDistance(ant.getTile(), tile);
					if(distance < 5) {
						distanceFaible = true;
						break;
					}
				}
				if(distanceFaible || ant.haveFriends() > 1) {
					int distance = game.getConnexion().getDistance(ant.getTile(), ennemiesAnt.getTile());
					Route route = new Route(ant.getTile(), ennemiesAnt.getTile(), distance);
					routes.add(route);
				}
			}
		}
		Set<Ant> capitaines = new HashSet<Ant>();
		int nbCapitaine = 0;
		for (Ant ant : capitaines) {
			capitaines.add(ant);
			if(++nbCapitaine > super.fourmis.size() /2) break;
		}
		
		
		for(Ant ant : super.fourmis) {
			for(Ant ant2 : capitaines) {
				Tile tile = (Tile) game.getConnexion().getTile(ant2.getTile(), Aim.SOUTH);
				Tile tileBis = (Tile) game.getConnexion().getTile(ant2.getTile(), Aim.EAST);
				int distance = game.getConnexion().getDistance(ant.getTile(), tile);
				Route route = new Route(ant.getTile(), tile, distance);
				routes.add(route);
				distance = game.getConnexion().getDistance(ant.getTile(), tileBis);
				route = new Route(ant.getTile(), tile, distance);
				routes.add(route);
			}
		}
		Collections.sort(routes);
		for (Route route : routes) {
			if (!game.getOrders().containsKey(route.getEnd()) 
					&& !game.getOrders().containsValue(route.getStart())) {
			game.doMoveLocation(route.getStart(), route.getEnd());
			}
		}
		return true;
	}

}
