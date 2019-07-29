package game.competition;

import game.arena.IArena;
import game.arena.WinterArena;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.ValidationUtils;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian WinterArena extends {@link Competition}
 */
public class WinterCompetition extends Competition {

    private Discipline discipline;
    private League league;
    private Gender gender;

    /**
     * A parameters ctor
     * @param arena send to {@link Competition} to initialize the arena
     * @param maxCompetitors send to {@link Competition} to initialize the maxCompetitors
     * @param discipline initialize the discipline
     * @param league initialize the league
     * @param gender initialize the gender
     */
    public WinterCompetition(IArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender) {
        super(arena, maxCompetitors);
        this.setDiscipline(discipline);
        this.setGender(gender);
        this.setLeague(league);
    }

    /**
     *
     * @param competitor check the validation of the competitor
     * @return true if the competitor stands in all parameters of the competition
     */
    protected boolean isValidCompetitor(Competitor competitor){
        return (competitor instanceof WinterSportsman && league.isInLeague(((WinterSportsman) competitor).getAge()) && ((WinterSportsman) competitor).getGender() == this.gender && ((WinterSportsman) competitor).getDiscipline() == discipline);
    }

    /**
     * check if discipline is not null
     * @param discipline set the discipline
     */
    private void setDiscipline(Discipline discipline) {
        ValidationUtils.assertNotNull(discipline);
        this.discipline = discipline;
    }

    /**
     * check if league is not null
     * @param league set the league
     */
    private void setLeague(League league) {
        ValidationUtils.assertNotNull(league);
        this.league = league;
    }

    /**
     * check if the gender is not null
     * @param gender set the gender
     */
    private void setGender(Gender gender) {
        ValidationUtils.assertNotNull(gender);
        this.gender = gender;
    }


}
