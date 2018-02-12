package fr.main.model.units.land;

import java.awt.Point;
import java.util.Map;
import java.util.HashMap;

import fr.main.model.Player;

import fr.main.model.units.Unit;
import fr.main.model.units.AbstractUnit;
import fr.main.model.units.weapons.SecondaryWeapon;
import fr.main.model.units.MoveType;
import fr.main.model.units.air.BCopter;
import fr.main.model.units.air.TCopter;

public class Recon extends Unit implements LandVehicleUnit{

    public static final String NAME = "Recon";
    public static final int PRICE   = 4000;

    public static final String SECONDARYWEAPON_NAME = "Mitrailleuse";

    private static final Map<Class<? extends AbstractUnit>, Integer> SECONDARYWEAPON_DAMAGES  = new HashMap<Class<? extends AbstractUnit>, Integer>();

    static{
        SECONDARYWEAPON_DAMAGES.put(Infantry.class,70);
        SECONDARYWEAPON_DAMAGES.put(Mech.class,65);
        SECONDARYWEAPON_DAMAGES.put(Recon.class,35);
        SECONDARYWEAPON_DAMAGES.put(Tank.class,6);
        SECONDARYWEAPON_DAMAGES.put(MTank.class,1);
        SECONDARYWEAPON_DAMAGES.put(Neotank.class,1);
        SECONDARYWEAPON_DAMAGES.put(Megatank.class,1);
        SECONDARYWEAPON_DAMAGES.put(AntiAir.class,4);
        SECONDARYWEAPON_DAMAGES.put(Artillery.class,45);
        SECONDARYWEAPON_DAMAGES.put(Rockets.class,55);
        SECONDARYWEAPON_DAMAGES.put(Missiles.class,28);
        SECONDARYWEAPON_DAMAGES.put(APC.class,45);
        SECONDARYWEAPON_DAMAGES.put(BCopter.class,10);
        SECONDARYWEAPON_DAMAGES.put(TCopter.class,35);
    }

    public Recon(Player player, Point point){
        super(player, point, 80, MoveType.WHEEL, 8, 5, null, new SecondaryWeapon(SECONDARYWEAPON_NAME,SECONDARYWEAPON_DAMAGES),NAME);
    }

    public Recon(Player player, int x, int y){
        this(player, new Point(x,y));
    }
}
