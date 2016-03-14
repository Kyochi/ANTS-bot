import java.util.Set;

public abstract class Action {
	protected Set<Ant> fourmis;
	
	public abstract boolean activer();

	public Action(Set<Ant> fourmis) {
		this.fourmis = fourmis;
	}
}
