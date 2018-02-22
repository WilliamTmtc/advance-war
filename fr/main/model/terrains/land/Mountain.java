package fr.main.model.terrains.land;

import java.util.Map;
import java.util.HashMap;

import fr.main.model.units.MoveType;

import fr.main.model.units.AbstractUnit;
import fr.main.model.terrains.Terrain;

public class Mountain extends Terrain implements LandTerrain {

    private static Mountain instance;
    protected static final Map<MoveType,Integer> sunnyWeatherMovementCosts=new HashMap<MoveType,Integer>();
    public static final String name="Montagne";

    static{
        sunnyWeatherMovementCosts.put(MoveType.AIRY,1);
        sunnyWeatherMovementCosts.put(MoveType.INFANTRY,2);
        sunnyWeatherMovementCosts.put(MoveType.MECH,1);
    }

    protected Mountain () {
        super(name, 4, 3, 2, sunnyWeatherMovementCosts);
    }

    public static Mountain get () {
        if (instance == null)
            instance = new Mountain();
        return instance;
    }

    public boolean blockVision(AbstractUnit u){
        return true;
    }

}
