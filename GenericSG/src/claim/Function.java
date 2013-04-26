package claim;

import java.util.Map;

public interface Function<RT> extends Formula{
	RT execute(Map<Var<?>, Object> env);
}
