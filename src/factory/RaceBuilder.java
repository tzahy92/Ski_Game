package factory;
import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.WinterCompetition;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A singelton class of RaceBuilder
 */

public class RaceBuilder {

    private static RaceBuilder instance;

    /**
     * @return the object that we build
     */
    public static RaceBuilder getInstance() {
        if (instance == null) {
            instance = new RaceBuilder();
        }
        return instance;
    }

    private ClassLoader classLoader;
    private Class<?> classObject;
    private Constructor<?> constructor;

    /**
     * ctor for building only one object
     */
    private RaceBuilder() {
        classLoader = ClassLoader.getSystemClassLoader();
    }

    /**
     * build winter competition
     * @param competitonType the type of the competition ski or snowboarder to build the type of the competition
     * @param arena the arena of the competition
     * @param maxCompetitor maximum of competitor that can race
     * @param discipline of the competition
     * @param league type of the competition
     * @param gender the type of the gender that can race, female or male
     * @return object of ski or snowboarder competition
     * @throws ClassNotFoundException if the class dose not exists
     * @throws NoSuchMethodException if the ctor method dose not exists in the class of ski or snowboarder competition
     * @throws SecurityException if we get security violation
     * @throws InstantiationException if that dose not have instance of a class ski or snowboard competition
     * @throws IllegalAccessException if executing method does not have access to the definition of the specified class, field, method or constructor
     * @throws IllegalArgumentException if that indicate that a method has been passed an illegal or inappropriate argument of the ski or snowboarder competition
     * @throws InvocationTargetException if the ctor throw an exception
     */
    public WinterCompetition buildCompetition(String competitonType, IArena arena, int maxCompetitor, Discipline discipline, League league, Gender gender)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.classObject = classLoader.loadClass(competitonType);
        this.constructor = classObject.getConstructor(IArena.class, int.class,Discipline.class,League.class,Gender.class);
        return (WinterCompetition) this.constructor.newInstance(arena, maxCompetitor, discipline, league, gender);

    }

    /**
     *
     * @param SportsmanType the type of the competitor ski or snowboarder to build the type of the competitor
     * @param name the name of the competitor
     * @param age the age of the competitor
     * @param gender the gender of the competitor, female or male
     * @param maxSpeed the maximum speed that the competitor can come
     * @param acceleration the acceleration of the competitor
     * @param discipline that have the competitor
     * @return object of ski or snowboarder competitor
     * @throws ClassNotFoundException if the class dose not exists
     * @throws NoSuchMethodException if the ctor method dose not exists in the class of ski or snowboarder competitor
     * @throws SecurityException if we get security violation
     * @throws InstantiationException if that dose not have instance of a class ski or snowboard competitor
     * @throws IllegalAccessException if executing method does not have access to the definition of the specified class, field, method or constructor
     * @throws IllegalArgumentException if that indicate that a method has been passed an illegal or inappropriate argument of the ski or snowboarder competitor
     * @throws InvocationTargetException if the ctor throw an exception
     */
    public WinterSportsman buildSportman(String SportsmanType, String name, double age, Gender gender,double maxSpeed,double acceleration, int sportsmansNumber, Color color, Discipline discipline)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.classObject = classLoader.loadClass(SportsmanType);
        this.constructor = classObject.getConstructor(String.class, double.class, Gender.class, double.class, int.class, Color.class,
            double.class, Discipline.class);
        return (WinterSportsman) this.constructor.newInstance(name, age, gender, acceleration, sportsmansNumber, color, maxSpeed , discipline);
    }

}
