package fr.main.model.players;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.function.Predicate;

import fr.main.model.units.AbstractUnit;

public class UnitControlAI implements ArtificialIntelligence {

	public final AIPlayer player;
	private final HashMap<AbstractUnit, UnitActionChooser> units;

	public UnitControlAI(AIPlayer p){
		this.player = p;
		this.units  = new HashMap<AbstractUnit, UnitActionChooser>();
	}

	public void add(AbstractUnit u){
		units.put(u, new UnitActionChooser(u));
	}

	public void remove(AbstractUnit u){
		units.remove(u);
	}

	private UnitActionChooser get(AbstractUnit u){
		return units.get(u);
	}

	public void run(){
        // first ranged units play
        checkUnits(units.values().iterator(), u -> u.getPrimaryWeapon() != null && !u.getPrimaryWeapon().isContactWeapon());

        // then contact units play
        checkUnits(units.values().iterator(), u -> u.getPrimaryWeapon() != null ||  u.getSecondaryWeapon() != null);

        // eventually other units play
        checkUnits(units.values().iterator(), u -> u.isEnabled());

        // if one unit still has move points, we give it a try to do something
        // because the situation may have changed since the first calculus of the thing to do
        checkUnits(units.values().iterator(), u -> u.isEnabled());
	}

    /**
     * @param iterator is an iterator which contains the units that may move
     * @param test is the condition for the units to do something
     * this method checks all units of the iterator that pass the test, finds the good action and run it
     */
    private void checkUnits(Iterator<UnitActionChooser> iterator, Predicate<AbstractUnit> test){
        while (iterator.hasNext()){
            UnitActionChooser actionChooser = iterator.next();
            if (test.test(actionChooser.unit))
                actionChooser.findAction().run();
        }
    }

}