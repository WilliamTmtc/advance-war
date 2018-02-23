package fr.main.model.units;

import java.awt.Point;
import java.io.Serializable;

import fr.main.model.Player;
import fr.main.model.Weather;
import fr.main.model.units.weapons.PrimaryWeapon;
import fr.main.model.units.weapons.SecondaryWeapon;
import fr.main.model.terrains.AbstractTerrain;
import fr.main.model.terrains.Buildable;
import fr.main.model.buildings.AbstractBuilding;
import fr.main.model.buildings.RepairBuilding;
import fr.main.model.Universe;
import fr.main.model.Direction;

/**
 * Represents a unit on the board
 */
public abstract class Unit implements AbstractUnit {

    /**
     * Life in percentage
     */
    private Point location;
    private Player player;
    private int life, moveQuantity;

    public final Fuel fuel;
    public final MoveType moveType;

    private PrimaryWeapon primaryWeapon;
    private SecondaryWeapon secondaryWeapon;

    public final int vision, maxMoveQuantity, cost;
    public final String name;

    public class Fuel implements java.io.Serializable{
        public final String name; // l'infanterie n'a pas de 'carburant' mais des 'rations' (c'est un détail mais bon)
        public final int maximumQuantity;
        private int quantity;
        public final boolean diesIfNoFuel;

        public Fuel(String name, int maximumQuantity, boolean diesIfNoFuel){
            this.name            = name;
            this.maximumQuantity = maximumQuantity;
            this.quantity        = maximumQuantity;
            this.diesIfNoFuel    = diesIfNoFuel;
        }

        /*
         *  @return true if the unit has still has fuel.
         */
        public boolean consume(int quantity){
            quantity*=Universe.get().getWeather().malusFuel;
            this.quantity=Math.max(0,this.quantity-quantity);
            if (fuel.quantity==0 && diesIfNoFuel){
                Universe.get().setUnit(location.x,location.y,null);
                player.remove(Unit.this);
            }
            return quantity!=0;
        }

        public void replenish(){
            this.quantity=this.maximumQuantity;
        }
        public int getQuantity() {
            return quantity;
        }
    }

    public Unit (Point location) {
        this (null, location);
    }

    public Unit (Player player, Point location) {
        this (player, location, "fuel", 0, false, MoveType.WHEEL, 5, 2, null, null, "unit",1);
    }

    public Unit (Player player, Point location, String fuelName, int maxFuel, boolean diesIfNoFuel, MoveType moveType, int moveQuantity, int vision, PrimaryWeapon primaryWeapon, SecondaryWeapon secondaryWeapon, String name, int cost) {
        this.life            = 100;
        this.player          = player;
        this.location        = location;
        this.fuel            = new Fuel(fuelName,maxFuel,diesIfNoFuel);
        this.moveType        = moveType;
        this.maxMoveQuantity = moveQuantity;
        this.moveQuantity    = moveQuantity;
        this.vision          = vision;
        this.primaryWeapon   = primaryWeapon;
        this.secondaryWeapon = secondaryWeapon;
        this.name            = name;
        this.cost            = cost;
        move(location.x, location.y);
    }

    public int getMoveQuantity(){
        return moveQuantity;
    }

    public int getMaxMoveQuantity(){
        return maxMoveQuantity;
    }

    public void setMoveQuantity(int m){
        this.moveQuantity=Math.min(m,getMaxMoveQuantity());
    }

    public Fuel getFuel(){
        return fuel;
    }

    public final boolean removeLife(int life){
        return setLife(this.life-life);
    }

    public final void addLife(int life){
        setLife(this.life+life);
    }

    /*
    * @return true if and only if the unit is still alive
    */
    public final boolean setLife (int life) {
        this.life = Math.max(0, Math.min(100, life));
        return this.life!=0;
    }

    public final int getLife () {
        return life;
    }

    public final boolean setPlayer (Player p) {
        if (player == null) {
            this.player = p;
            return true;
        } else return false;
    }

    public final Player getPlayer(){
        return this.player;
    }

    public final int getX () {
        return location.x;
    }

    public final int getY () {
        return location.y;
    }

    public boolean canAttackAfterMove(){
        return true;
    }

    /*
    * @return true if and only if the move was done.
    */
    public final boolean move(int x, int y) {
        Universe u = Universe.get();
        if (u != null && u.isValidPosition(x,y) && u.getUnit(x, y) == null) {
            int q=u.getTerrain(x,y).moveCost(this);
            if (q>=getMoveQuantity()){
                return false;
            }
            removeMoveQuantity(q);
            u.setUnit(location.x, location.y, null);
            location.x = x;
            location.y = y;
            u.setUnit(x, y, this);
            u.updateVision();
            fuel.consume(1);
            return true;
        }
        else
            return false;
    }

    /*
    * @return true if and only if the move was done.
    */
    public final boolean move (Direction d) {
        Point t = (Point)location.clone();
        d.move(t);
        return move(t.x, t.y);
    }

    /*
    * @return true if and only if the move was done entirely.
    */
    public final boolean move (Path path) {
        for (Direction d: path){
            if (!move(d)){
                setMoveQuantity(0);
                return false;
            }
        }
        return true;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public final void renderVision (boolean[][] fog) {
        //TODO : can see beyond a moutain or a forest if the unit is on a mountain
        //TODO : can see beyond a forest if the unit is on a hill
        if (fog==null || fog.length==0 || fog[0]==null || fog[0].length==0)
            return;

        int x=location.x;
        int y=location.y;

        fog[y][x]=true;

        if (vision!=0){
            if (y!=0){
                fog[y-1][x]=true;
                renderVision (fog,x,y-1,vision);
            }
            if (y!=fog.length-1){
                fog[y+1][x]=true;
                renderVision (fog,x,y+1,vision);
            }
            if (x!=0){
                fog[y][x-1]=true;
                renderVision (fog,x-1,y,vision);
            }
            if (x!=fog[0].length-1){
                fog[y][x+1]=true;
                renderVision (fog,x+1,y,vision);
            }
        }
    }

    private void renderVision (boolean[][] fog, int x, int y, int vision){
        vision--;
        AbstractTerrain t = Universe.get().getTerrain(x,y);
        if (!fog[y][x] && !t.hideFrom(this))
            fog[y][x]=true;
        if (vision!=0 && !t.blockVision(this)){
            if (y!=0)
                renderVision(fog,x,y-1,vision);
            if (y!=fog.length-1)
                renderVision(fog,x,y+1,vision);
            if (x!=0)
                renderVision(fog,x-1,y,vision);
            if (x!=fog[0].length-1)
                renderVision(fog,x+1,y,vision);
        }
    }

    public void reachableLocation (boolean[][] map) {
        if (map!=null && map.length!=0 && map[0]!=null && map[0].length!=0)
            reachableLocation (map,location.x,location.y,moveQuantity+Universe.get().getTerrain(location.x,location.y).moveCost(this));
    }

    private void reachableLocation (boolean[][] map, int x, int y, int movePoint){
        Integer mvP=Universe.get().getTerrain(x,y).moveCost(this);
        if (mvP!=null && movePoint>=mvP)
            movePoint-=mvP;
        else
            return;

        map[y][x]=true;

        if (y!=0)
            reachableLocation(map,x,y-1,movePoint);
        if (y!=map.length-1)
            reachableLocation(map,x,y+1,movePoint);
        if (x!=0)
            reachableLocation(map,x-1,y,movePoint);
        if (x!=map[0].length-1)
            reachableLocation(map,x+1,y,movePoint);
    }

    public void canTarget (boolean[][] map) {
        if (map!=null && map.length!=0 && map[0]!=null && map[0].length!=0 && (primaryWeapon!=null || secondaryWeapon!=null)){
            if (canAttackAfterMove())
                canTarget (map,location.x,location.y,moveQuantity+Universe.get().getTerrain(location.x,location.y).moveCost(this));
            else{
                if (primaryWeapon!=null)
                    primaryWeapon.canTarget(map,location.x,location.y);
                if (secondaryWeapon!=null)
                    secondaryWeapon.canTarget(map,location.x,location.y);
            }
        }
    }

    private void canTarget (boolean[][] map, int x, int y, int movePoint){
        Integer mvP=Universe.get().getTerrain(x,y).moveCost(this);
        if (mvP!=null && movePoint>=mvP)
            movePoint-=mvP;
        else
            return;

        if (movePoint>0){
            if (primaryWeapon!=null)
                primaryWeapon.canTarget(map,location.x,location.y);
            if (secondaryWeapon!=null)
                secondaryWeapon.canTarget(map,location.x,location.y);
        }

        if (y!=0)
            reachableLocation(map,x,y-1,movePoint);
        if (y!=map.length-1)
            reachableLocation(map,x,y+1,movePoint);
        if (x!=0)
            reachableLocation(map,x-1,y,movePoint);
        if (x!=map[0].length-1)
            reachableLocation(map,x+1,y,movePoint);
    }

    public String getName (){
        return name;
    }

    @Override
    public String toString() {
        String out = getName() +
                "\nHP : " + Integer.toString(life);
        if(player != null)
            out += "\nPlayer : " + player.name;
            out += "\nVision : " + Integer.toString(vision);
        if(fuel != null)
            out += "\nFuel : " + Integer.toString(fuel.getQuantity());
        if(primaryWeapon != null)
            out += "\nPrimary : " + primaryWeapon.name;
        if(secondaryWeapon != null)
            out += "\nSecondary : " + secondaryWeapon.name;
        return out;
    }

    public boolean canAttack(AbstractUnit u){
        return true;
    }

    public int getFuelTurnCost(){
        return 0;
    }

    public void turnBegins(){
        AbstractTerrain t = Universe.get().getTerrain(location.x,location.y);
        boolean heal = false;
        if (t instanceof Buildable){
            AbstractBuilding b=((Buildable)t).getBuilding();
            if (b instanceof RepairBuilding){
                RepairBuilding rep = (RepairBuilding)b;
                if (rep.canRepair(this)){
                    rep.repair(this);
                    heal = true;
                }
            }
        }
        if (!heal)
            fuel.consume(getFuelTurnCost());
        setMoveQuantity(getMaxMoveQuantity());
    }

    public int getCost(){
        return cost;
    }
}
