package factory;

import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.WinterCompetition;
import game.entities.sportsman.Skier;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * An representation of a cartesian ArenaFactory
 */
public class SkiCompetitionBuilder {

    private static SkiCompetitionBuilder instance;

    /**
     * @return the object that we build
     */
    public static SkiCompetitionBuilder getInstance() {
        if (instance == null) {
            instance = new SkiCompetitionBuilder();
        }
        return instance;
    }

    private ClassLoader classLoader;
    private Class<?> classObject;
    private Constructor<?> constructor;

    /**
     * ctor for building only one object
     */
    private SkiCompetitionBuilder() {
        classLoader = ClassLoader.getSystemClassLoader();
    }

    /**
     * build winter competition
     * @param arena the arena of the competition
     * @return object of ski or snowboarder competition
     * @throws ClassNotFoundException if the class dose not exists
     * @throws NoSuchMethodException if the ctor method dose not exists in the class of ski or snowboarder competition
     * @throws SecurityException if we get security violation
     * @throws InstantiationException if that dose not have instance of a class ski or snowboard competition
     * @throws IllegalAccessException if executing method does not have access to the definition of the specified class, field, method or constructor
     * @throws IllegalArgumentException if that indicate that a method has been passed an illegal or inappropriate argument of the ski or snowboarder competition
     * @throws InvocationTargetException if the ctor throw an exception
     */
    public WinterCompetition buildCompetition( IArena arena)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.classObject = classLoader.loadClass("game.competition.SkiCompetition");
        this.constructor = classObject.getConstructor(IArena.class, int.class,Discipline.class,League.class,Gender.class);
        WinterCompetition comp = (WinterCompetition) this.constructor.newInstance(arena, 10, Discipline.SLALOM, League.JUNIOR, Gender.FEMALE);
        this.buildCompetitor(comp);
        return comp;
    }

    /**
     * build 10 competitor and add it to the competition
     * @param comp competition to add the competitor
     */
    private void buildCompetitor(WinterCompetition comp){
        Random rand = new Random();

        double age = 15;

        Gender sportsmanGeneder = Gender.FEMALE;
        for(int i=0;i < 10;i++){
            char[] charName = {(char)(rand.nextInt(26) + 'A'),(char)(rand.nextInt(26) + 'A')};
            String name = new String(charName);
            int maxSpeed = rand.nextInt(60) +10;
            double acceleration = rand.nextInt(3) + 1.5;
            int sportsmanNumber = rand.nextInt(99) + 1;
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            Color color = new Color(r, g, b);
            comp.addCompetitor(new Skier(name,15,Gender.FEMALE,acceleration,sportsmanNumber,color,maxSpeed,Discipline.SLALOM));
        }
    }
}
