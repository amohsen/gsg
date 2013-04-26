package sg.general;

import claim.Predicate;
import sg.Assignment;

public interface Translator {
	Assignment translateClaimParameters(String id, Assignment sa);
	String translateValueToSource(String id, String val);
	String translateValueToTarget(String id, String val);
	String translateTypeToTarget(String type);
	/** TODO: in general returns a formula that may have a let */
	Predicate translateSrcPredicate(Predicate p);
}
