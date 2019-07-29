package game.entities.sportsman;

import game.State.Active;
import game.State.ConditionStateContext;
import game.competition.Competitor;
import game.entities.decorator.IWinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.Point;
import utilities.ValidationUtils;

import java.awt.*;
import java.util.Random;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian WinterSportsman extends {@link Sportsman} and implements {@link Competitor}
 */
public class WinterSportsman extends Sportsman implements IWinterSportsman, Cloneable {

    private Discipline discipline;
    private double lengthArena;
    private double friction;

    /**
     * A parameters ctor
     * @param name send to {@link Sportsman} to initialize the name
     * @param age send to {@link Sportsman} to initialize the age
     * @param gender send to {@link Sportsman} to initialize the gender
     * @param maxSpeed send to {@link Sportsman} to initialize the maxSpeed
     * @param acceleration send to {@link Sportsman} to initialize the acceleration
     * @param discipline initialize the discipline
     */
    public WinterSportsman(String name, double age, Gender gender, double maxSpeed, double acceleration, int sportsmanNumber, Color color, Discipline discipline) {
        super(name, age, gender, maxSpeed, acceleration, sportsmanNumber, color);
        this.setDiscipline(discipline);

    }

    /**
     * set the Location to (x,0)
     * @param x the gap between the competitor
     * @param length the length of the arena
     * @param friction the friction of the arena
     */
    @Override
    public void initRace(int x,double length,double friction) {
        lengthArena = length;
        this.setLocation(new Point(x,0));
        this.friction = friction;
        this.setAcceleration(this.getAcceleration() + League.calcAccelerationBonus(getAge()));
    }

    /**
     *
     * @return discipline of the winter sportsman
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * check validation of discipline
     * @param discipline set the discipline of the winter sportsman
     *
     */
    private void setDiscipline(Discipline discipline) {
        ValidationUtils.assertNotNull(discipline);
        this.discipline = discipline;
    }

    /**
     *
     * @return the acceleration + the bonus acceleration of the league
     */
    public double getAcceleration() {
        return super.getAcceleration();
    }

    /**
     *
     * @return the length of the arena
     */
    public double getLengthArena() {
        return lengthArena;
    }

    /**
     *
     * @return the friction of the arena
     */
    public double getFriction() {
        return friction;
    }

    public WinterSportsman clone() throws CloneNotSupportedException {
        WinterSportsman cloned;
        if(this instanceof Skier)
            cloned = (Skier)super.clone();
        else
            cloned = (Snowboarder)super.clone();
        cloned.setLocation(((WinterSportsman) super.clone()).getLocation());
        //cloned.setLocation(new Point(getLocation().getX(),0));
        cloned.setStat(new ConditionStateContext());

        return cloned;
       }

    /**
     * add new acceleration to the old acceleration
     * @param accelerat add to the acceleration
     */
    @Override
    public void addAccel(double accelerat) {
        this.setAcceleration(this.getAcceleration() + accelerat);
    }
}
