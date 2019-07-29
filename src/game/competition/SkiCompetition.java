package game.competition;

import game.arena.IArena;
import game.arena.WinterArena;
import game.entities.sportsman.Skier;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian SkiCompetition extends {@link WinterCompetition}
 */
public class SkiCompetition extends WinterCompetition {

    /**
     * parameters ctor
     * @param arena send to {@link WinterCompetition} to initialize the arena
     * @param maxCompetitors send to {@link WinterCompetition} to initialize the maxCompetitors
     * @param discipline send to {{@link WinterCompetition} to initialize the discipline
     * @param league send to {@link WinterCompetition} to initialize the league
     * @param gender send to {@link WinterCompetition} to initialize the gender
     */
    public SkiCompetition(IArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender) {
        super(arena, maxCompetitors, discipline, league, gender);
    }

    /**
     * check if the competitor is instanceof Skier and call the isValidCompetitor of WinterCompetition
     * @param competitor check the validation of the competitor
     * @return true if the competitor is valid to the competition
     */
    protected boolean isValidCompetitor(Competitor competitor){
        return competitor instanceof Skier && super.isValidCompetitor(competitor);
    }
}
