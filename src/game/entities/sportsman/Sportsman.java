package game.entities.sportsman;
import game.entities.MobileEntity;
import game.enums.Gender;
import utilities.ValidationUtils;

import java.awt.*;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Sportsman and extends {@link MobileEntity}
 */
public class Sportsman extends MobileEntity{
    private String name;
    private double age;
    private Gender gender;
    private int sportsmanNumber;
    private Color color;

    /**
     * A parametrs ctor
     * @param name set the name
     * @param age set the age
     * @param gender set the gender
     * @param maxSpeed send to {@link MobileEntity} to initialize the name
     * @param acceleration send to {@link MobileEntity} to initialize the name
     * @param sportsmanNumber a uniq number for competitor
     * @param color the color of the competitor
     */
    public Sportsman(String name, double age, Gender gender,double maxSpeed, double acceleration,int sportsmanNumber ,Color color){
        super(maxSpeed,acceleration);
        setName(name);
        setAge(age);
        setGender(gender);
        this.sportsmanNumber = sportsmanNumber;
        this.color = color;
    }

    /**
     *
     * @return the name of the sports man
     */
    public String getName() {
        return name;
    }

    /**
     *check validation of name
     * @param name set the name of the sports man
     *
     */
    protected void setName(String name) {
        ValidationUtils.assertNotNullOrEmptyString(name);
        this.name = name;
    }

    /**
     *
     * @return age of the sports man
     */
    public double getAge() {
        return age;
    }

    /**
     *check validation of age
     * @param age set the age of the sports man
     *
     */
    private void setAge(double age) {
        ValidationUtils.assertPositive(age);
        this.age = age;
    }

    /**
     *
     * @return gender of the sports man
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * check validation of gender
     * @param gender set the gender of the sports man
     *
     */
    private void setGender(Gender gender) {
        ValidationUtils.assertNotNull(gender);
        this.gender = gender;
    }

    /**
     *
     * @return the sportsmanNumber
     */
    public int getSportsmanNumber() {
        return sportsmanNumber;
    }

    /**
     * set the sportsmanNumber to the competitor
     * @param sportsmanNumber uniq number for the competitor
     */
    public void setSportsmanNumber(int sportsmanNumber) {
        this.sportsmanNumber = sportsmanNumber;
    }

    /**
     *
     * @return the color of the competitor
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color set the color of the competitor
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
