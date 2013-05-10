package lab.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lab.ScholarI;
import lab.impl.ScoreBoard;

public class History {
	private final Collection<GameHistoryRecord> ghrs = new ArrayList<GameHistoryRecord>();
	
	public void add(GameHistoryRecord ghr) {
		ghrs.add(ghr);
	}
	
	public ScoreBoard getScoreBoard(){
		return new ScoreBoard(this);
	}
	
	public Collection<ScholarI> getScholars(){
		Set<ScholarI> scholars = new HashSet<ScholarI>();
		for (GameHistoryRecord ghr : ghrs) {
			scholars.add(ghr.getVerifier());
			scholars.add(ghr.getFalsifier());
		}
		return scholars;
	}
	
	public Collection<GameHistoryRecord> getRecords(){
		return ghrs;
	}
}
