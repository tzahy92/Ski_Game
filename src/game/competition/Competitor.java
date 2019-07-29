package game.competition;

import game.entities.IMobileEntity;
import utilities.Point;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An interface of Competitor
 */
public interface Competitor extends IMobileEntity {
    /**
     * set the location of the competitor to (x,0)
     * @param x the gap between the competitor
     * @param finish the length of finish line of the arena
     * @param froction the friction of the arena
     */
    void initRace(int x, double finish,double froction);
}
