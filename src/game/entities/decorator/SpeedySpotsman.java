package game.entities.decorator;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An representation of a cartesian SpeedySpotsman extends {@link WSDecorator}
 */
public class SpeedySpotsman extends WSDecorator {

    /**
     * build the WSDecorator class
     * @param decoratWS
     */
    public SpeedySpotsman(IWinterSportsman decoratWS) {
        super(decoratWS);
    }

    /**
     * Add the accelerat to the competitor accelerat
     * @param accelerat
     */
    @Override
    public void addAccel(double accelerat) {
        super.addAccel(accelerat);
    }
}
