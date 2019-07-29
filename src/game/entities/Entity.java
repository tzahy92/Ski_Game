package game.entities;
import utilities.Point;
import utilities.ValidationUtils;

import java.util.Observable;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Entity extends {@link Observable} implements {@link Runnable}
 */
public abstract class Entity extends Observable implements Runnable {

    private Point location = null;

    /**
     * Default Ctor for a entity sets x location.
     */
    public Entity(){
        location = new Point();
    }

    /**
     * Ctor for a entity
     * @param other location position
     */
    public Entity(Point other){
        location = new Point(other);
    }

    /**
     * check validation of location
     * @param location set the location
     *
     */
    protected void setLocation(Point location) {
        ValidationUtils.assertNotNull(location);
        this.location = new Point(location);
    }

    /**
     *
     * @return the point of location
     */
    public Point getLocation() {
        return location;
    }
}
