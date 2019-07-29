package game.entities;

import game.State.*;
import game.entities.sportsman.WinterSportsman;
import utilities.Point;
import utilities.ValidationUtils;

import java.util.Random;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian MobileEntity extends {@link Entity} and implements {@link IMobileEntity}
 */
public class MobileEntity extends Entity implements IMobileEntity {

    private double maxSpeed;
    private double acceleration;
    private double speed;
    private ConditionStateContext stat;

    protected void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * ctor with parameters
     * set the speed to 0
     * @param maxSpeed set the max Speed
     * @param acceleration set the acceleration
     */
    public MobileEntity(double maxSpeed, double acceleration){
        super();
        setMaxSpeed(maxSpeed);
        this.acceleration = acceleration;
        this.speed = 0;
        stat = new ConditionStateContext();
    }
    /**
     * set the Location of entity to a new Location
     * update the @param speed to the new speed
     * and update the state of the competitor
     * @param friction of the surface to update the speed
     */
    @Override
    public synchronized void move(double friction) {
        if(this.speed + this.getAcceleration()*(1-friction) >= this.maxSpeed)
            this.speed = this.maxSpeed;
        else
            this.speed += this.getAcceleration()*(1-friction);
        if(this.getLocation().getY() + this.speed >= ((WinterSportsman)this).getLengthArena())
            this.setLocation(new Point(this.getLocation().getX(),((WinterSportsman)this).getLengthArena()));
        else
            this.setLocation(new Point(this.getLocation().getX(),this.getLocation().getY() + this.speed));
        changeState();
        setChanged();
        this.notifyObservers(stat.getState());
    }

    /**
     * call to get Location of entity
     * @return the location
     */
    @Override
    public Point getLocation() {
        return super.getLocation();
    }

    /**
     * check validation of maxSpeed
     *@param maxSpeed the max speed of mobile entity
     *
     */
    private void setMaxSpeed(double maxSpeed) {
        ValidationUtils.assertPositive(maxSpeed);
        this.maxSpeed = maxSpeed;
    }

    /**
     *
     * @return acceleration of mobile entity
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     *
     * @return the max speed of the competitor
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     *
     * @return the speed of the competitor
     */
    public double getSpeed() {
        return speed;
    }

    /**
     *
     * @return true if the competitor cross the finish line of the arena
     */
    private boolean isFinish(){
        return this.getLocation().getY() >= ((WinterSportsman)this).getLengthArena();
    }

    /**
     * change the stat of the competitor by random between 0-10
     * 0 and old stat is active - stat move to injured
     * 1 and old stat is injured - stat move to disable
     * isFinish = true - stat move to finish
     * default - stat move to active
     */
    private synchronized void changeState(){
        Random rand = new Random();
        int wicthState = rand.nextInt(10);
        if(wicthState == 0 && stat.getState() == "Active")
            stat.setState(new Injured());
        else if(wicthState == 1 && stat.getState() == "Injured")
            stat.setState(new Disabled());
        else if(isFinish())
            stat.setState(new Completed());
        else
            stat.setState(new Active());
    }

    /**
     * set the new stat of the competitor
     * @param stat - the new stat condition
     */
    protected void setStat(ConditionStateContext stat) {
        this.stat = stat;
    }

    /**
     *
     * @return the current stat of the competitor
     */
    public ConditionStateContext getStat() {
        return stat;
    }

    /**
     * thread.
     * implement the function of Runnable.
     * As long as the competitor dose not finish the race, the competitor still moving
     * if the competitor cross the finish line of the arena he notify the competition
     */
    public void run(){
        while (!(isFinish()) && !stat.getState().equals("Disabled")){
            this.move(((WinterSportsman)this).getFriction());

            try {
                Thread.sleep(100);
                 }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setChanged();
        this.notifyObservers("");
        Thread.currentThread().interrupt();
    }

}