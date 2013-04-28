package claim.structure.impl;

import claim.structure.FormulaI;
import claim.structure.VarI;
import static claim.structure.impl.Utils.*;

public class Formula implements FormulaI{
	private final VarI<?>[] params;
	
	public Formula(VarI<?>...params) {
		this.params = params;
		checkEqualParameters(params);
	}

	@Override
	public VarI<?>[] getParameters() {
		return params;
	}

}
