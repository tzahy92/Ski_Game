package factory;

import game.arena.IArena;
import game.arena.SummerArena;
import game.arena.WinterArena;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An representation of a cartesian ArenaFactory
 */
public class ArenaFactory {

    /**
     * build the arena and return it.
     * @param className the type of the class
     * @param length the length of the arena
     * @param surface the surface of the arena
     * @param condition the condition of the arena
     * @return the an object of arena type, winter or summer arena.
     */
    public IArena getArena(String className, double length, SnowSurface surface, WeatherCondition condition){
        if (className == "Summer")
            return new SummerArena(length,surface,condition);
        else
            return new WinterArena(length,surface,condition);
    }
}
