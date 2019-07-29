package game.entities;
import utilities.Point;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An interface of IMobileEntity
 */
public interface IMobileEntity {
    /**
     * move the competitor to the next location
     * @param friction the friction of the surface
     */
    void move(double friction);

    /**
     *
     * @return the location of the competitor
     */
    Point getLocation();
}
