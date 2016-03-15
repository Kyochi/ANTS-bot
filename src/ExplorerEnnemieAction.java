import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ExplorerEnnemieAction extends Action {

	public ExplorerEnnemieAction(Game game, Set<Ant> fourmis) {
		super(game, fourmis);
	}

	@Override
	public boolean activer() {
		List<Route> unseenRoutes = new ArrayList<Route>();
		for (Ant ant : fourmis) {
			Tile tile = new Tile(random(game.getLignes()*-1,game.getLignes()), random(game.getColonnes()*-1, game.getColonnes()));
			int distance = game.getConnexion().getDistance(ant.getTile(), tile);
			Route route = new Route(ant.getTile(), tile, distance );
			unseenRoutes.add(route);
		}
		Collections.sort(unseenRoutes);
		for (Route route : unseenRoutes) {
			if (!game.getOrders().containsKey(route.getEnd())
					&& !game.getOrders().containsValue(route.getStart())
					&& game.doMoveLocation(route.getStart(), route.getEnd())) {
				break;
			}
		}
		return false;
	}

	public int random(int min, int max) {
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max - min + 1) + min;
		return nombreAleatoire;
	}
}
