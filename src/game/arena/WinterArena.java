package game.arena;

import game.entities.IMobileEntity;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
import utilities.ValidationUtils;
/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian WinterArena implements {@link IArena}
 */
public class WinterArena implements IArena {

    private double length;
    private SnowSurface surface;
    private WeatherCondition condition;

    /**
     * parameters ctor
     * @param length initialize the length
     * @param surface initialize the surface
     * @param condition initialize the condition
     */
    public WinterArena(double length,SnowSurface surface,WeatherCondition condition){
        this.setLength(length);
        this.setSurface(surface);
        this.setCondition(condition);
    }

    /**
     *
     * @return the Friction of the surface
     */
    @Override
    public double getFriction() {
        return this.surface.getFriction();
    }

    /**
     *
     * @param me is a competitor
     * @return true if the competitor passed the length of arena false if not
     */
    @Override
    public boolean isFinished(IMobileEntity me) {
        return (me.getLocation().getY() >= this.length);
    }

    /**
     *
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * check validation of the length
     * @param length set the length of the arena
     */
    private void setLength(double length) {
        ValidationUtils.assertPositive(length);
        this.length = length;
    }

    /**
     *
     * @return the surface
     */
    public SnowSurface getSurface() {
        return surface;
    }

    /**
     * check validation of the surface
     * @param surface set the surface of the arena
     */
    private void setSurface(SnowSurface surface) {
        ValidationUtils.assertNotNull(surface);
        this.surface = surface;
    }

    /**
     *
     * @return the condition
     */
    public WeatherCondition getCondition() {
        return condition;
    }

    /**
     * check validation of the condition
     * @param condition set the condition
     */
    private void setCondition(WeatherCondition condition) {
        ValidationUtils.assertNotNull(condition);
        this.condition = condition;
    }

    /**
     *
     * @return the string of the class WinterArena
     */
    @Override
    public String toString() {
        return "WinterArena ";
    }
}
