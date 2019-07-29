package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;

import java.awt.*;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Snowboarder and extends {@link WinterSportsman}
 */
public class Snowboarder extends WinterSportsman {

    /**
     * A parameters ctor
     * @param name send to {@link WinterSportsman} to initialize the name
     * @param age send to {@link WinterSportsman} to initialize the age
     * @param gender send to {@link WinterSportsman} to initialize the gender
     * @param acceleration send to {@link WinterSportsman} to initialize the acceleration
     * @param maxSpeed send to {@link WinterSportsman} to initialize the maxSpeed
     * @param discipline send to {@link WinterSportsman} to initialize the discipline
     * @param sportsmanNumber send to {@link WinterSportsman} to initialize the discipline
     * @param color send to {@link WinterSportsman} to initialize the discipline
     */
    public Snowboarder(String name, double age, Gender gender, double acceleration, int sportsmanNumber, Color color, double maxSpeed, Discipline discipline) {
        super(name, age, gender, maxSpeed, acceleration,sportsmanNumber,color, discipline);
    }

    /**
     *
     * @return toString of the snowboarder
     */
    @Override
    public String toString() {
        return "Snowboarder " + this.getName();
    }
}