import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ExplorerAction extends Action {

	public ExplorerAction(Set<Ant> fourmis) {
		super(fourmis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean activer() {
		for (Ant antLoc : super.fourmis) {
		List<Route> unseenRoutes = new ArrayList<Route>();
		for (Tile unseenLoc : Game.getSingleton().getBrouillardTiles()) {
			int distance = Game.getSingleton().getConnexion().getDistance(antLoc.getTile(), unseenLoc);
			Route route = new Route(antLoc.getTile(), unseenLoc, distance);
			unseenRoutes.add(route);
		}
		Collections.sort(unseenRoutes);
		for (Route route : unseenRoutes) {
			if (Game.getSingleton().doMoveLocation(route.getStart(), route.getEnd())) {
				break;
			}
		}
		}
		return true;
	}

}
