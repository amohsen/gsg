package claim;

/* a compound formula */
public interface Compound extends Formula{
	Formula getLeft();
	Formula getRight();
}
