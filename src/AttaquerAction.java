import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AttaquerAction extends Action {

	public AttaquerAction(Set<Ant> fourmis) {
		super(fourmis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean activer() {
		List<Route> hillRoutes = new ArrayList<Route>();
        for (Tile hillLoc : Game.getSingleton().getEnnemiesFourmillieres()) {
            for (Ant antLoc : super.fourmis) {
                    int distance = Game.getSingleton().getConnexion().getDistance(antLoc.getTile(), hillLoc);
                    Route route = new Route(antLoc.getTile(), hillLoc, distance);
                    hillRoutes.add(route);
            }
        }
        Collections.sort(hillRoutes);
        for (Route route : hillRoutes) {
            Game.getSingleton().doMoveLocation(route.getStart(), route.getEnd());
        }
		return true;
	}

}
