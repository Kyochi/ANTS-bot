import java.util.List;

public abstract class Action {
	protected List<Ant> fourmis;
	
	public abstract boolean activer();

	public Action(List<Ant> fourmis) {
		this.fourmis = fourmis;
	}
}
