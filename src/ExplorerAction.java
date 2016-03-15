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
				for (Tile unseenLoc : game.getBrouillardTiles()) {
					int distance = game.getConnexion().getDistance(antLoc.getTile(), unseenLoc);
					/*Tile postTile = new Tile(antLoc.getTile().getRow(),antLoc.getTile().getCol()+1);
					if(postTile.getCol() > 25){
						postTile.setCol(postTile.getRow()-2);
					}*/
					Ants myAnts = game.getConnexion();
					Tile hillTile = myAnts.getMyHills().iterator().next();
					hillTile.setRow(hillTile.getRow()+1);
					hillTile.setCol(hillTile.getCol()-1);
					Route route = new Route(antLoc.getTile(), hillTile, distance);
					unseenRoutes.add(route);
				}
				Collections.sort(unseenRoutes);
				for (Route route : unseenRoutes) {
					if (game.doMoveLocation(route.getStart(), route.getEnd())) {
						break;
					}
				}
			}
		}
		return true;
	}

}
