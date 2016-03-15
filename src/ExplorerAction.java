import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ExplorerAction extends Action {

	public ExplorerAction(Game game, Set<Ant> fourmis) {
		super(game, fourmis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean activer() {
		for (Ant antLoc : this.fourmis) {
			if (!game.getOrders().containsValue(antLoc.getTile())) {
				List<Route> unseenRoutes = new ArrayList<Route>();
<<<<<<< HEAD
				List<Tile> angles = new ArrayList<Tile>();
				angles.add(new Tile(0,0));
				angles.add(new Tile(game.getLignes(), game.getColonnes()));
				
				int distanceMax = 0;
				Tile tileEloigne = new Tile(game.getLignes(), game.getColonnes());
				for (Tile tile : game.getMesFourmillieres()) {
					for (Tile angle : angles) {
						int distance = game.getConnexion().getDistance(tile, angle);
						if(distance > distanceMax) {
							distanceMax = distance;
							tileEloigne = angle;
						}
					}
=======
				for (Tile unseenLoc : game.getBrouillardTiles()) {
					int distance = game.getConnexion().getDistance(antLoc.getTile(), unseenLoc);
					Route route = new Route(antLoc.getTile(), unseenLoc, distance);
					unseenRoutes.add(route);
>>>>>>> 70261d86bb2a3adc1927e9229f55e4a4f81fda97
				}
				
				int distance = game.getConnexion().getDistance(antLoc.getTile(), tileEloigne);
				Route route2 = new Route(antLoc.getTile(), tileEloigne, distance);
				unseenRoutes.add(route2);
				
				Collections.sort(unseenRoutes);
				for (Route route : unseenRoutes) {
					if (!game.getOrders().containsKey(route.getEnd())
							&& !game.getOrders().containsValue(route.getStart())
							&& game.doMoveLocation(route.getStart(), route.getEnd())) {
						break;
					}
				}
			}
		}
		return true;
	}

}
