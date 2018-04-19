package fr.main.model;

public class State implements java.io.Serializable{

    /**
	 * Add state UID
	 */
	private static final long serialVersionUID = 5899790995626210357L;
	public final int numberOfUnit,
                     numberOfBuilding,
                     numberOfFunds;

    public State (int unit, int building, int funds) {
        numberOfUnit     = unit;
        numberOfBuilding = building;
        numberOfFunds    = funds;
    }
}