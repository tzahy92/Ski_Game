package game.entities.decorator;

import utilities.Point;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An representation of a cartesian WSDecorator implements {@link IWinterSportsman}
 */
public abstract class WSDecorator implements IWinterSportsman {
    private  IWinterSportsman decoratWS;

    /**
     * Ctor of the class
     * @param decoratWS
     */
    public WSDecorator(IWinterSportsman decoratWS){
        this.decoratWS = decoratWS;
    }

    /**
     * Add the accelerat to the competitor accelerat
     * @param accelerat
     */
    @Override
    public void addAccel(double accelerat) {
        decoratWS.addAccel(accelerat);
    }

    /**
     * set the location of the competitor to (x,0)
     * @param x the gap between the competitor
     * @param finish the length of finish line of the arena
     * @param froction the friction of the arena
     */
    @Override
    public void initRace(int x, double finish, double froction) {
        decoratWS.initRace( x, finish, froction);

    }

    /**
     * set the Location of entity to a new Location
     * update the @param speed to the new speed
     * @param friction of the surface to update the speed
     */
    @Override
    public void move(double friction) {
        decoratWS.move(friction);
    }

    /**
     *
     * @return the point of location
     */
    @Override
    public Point getLocation() {
        return decoratWS.getLocation();
    }
}
