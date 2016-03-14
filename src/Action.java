import java.util.Set;

public abstract class Action {
	protected Set<Ant> fourmis;
	protected Game game;
	
	public abstract boolean activer();

	public Action(Game game, Set<Ant> fourmis) {
		this.game = game;
		this.fourmis = fourmis;
	}
}
