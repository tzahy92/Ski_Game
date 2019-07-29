package game.arena;

import game.entities.IMobileEntity;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An interface of IArena
 */
public interface IArena {
    /**
     *
     * @return the Friction
     */
    double getFriction();

    /**
     *
     * @param me is competitor
     * @return true if the competitor finish the race
     */
    boolean isFinished(IMobileEntity me);

    /**
     *
     * @return the length of the arena
     */
    double getLength();
}
