import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AttaquerAction extends Action {

	public AttaquerAction(Game game, Set<Ant> fourmis) {
		super(game, fourmis);
	}

	@Override
	public boolean activer() {
		List<Route> hillRoutes = new ArrayList<Route>();
        for (Tile hillLoc : game.getEnnemiesFourmillieres()) {
        	System.out.println("des ennemies hills");
            for (Ant ant : super.fourmis) {
                    int distance = game.getConnexion().getDistance(ant.getTile(), hillLoc);
                    Route route = new Route(ant.getTile(), hillLoc, distance);
                    hillRoutes.add(route);
            }
        }
        Collections.sort(hillRoutes);
        for (Route route : hillRoutes) {
        	game.doMoveLocation(route.getStart(), route.getEnd());
        }
		return true; 
	}

}
