package game.entities.decorator;

import game.competition.Competitor;
/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An interface of a cartesian IWinterSportsman extends {@link Competitor}
 */
public interface IWinterSportsman extends Competitor {
    /**
     * Add the accelerat to the competitor accelerat
     * @param accelerat
     */
    void addAccel(double accelerat);
}
