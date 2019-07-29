package game.State;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Injured implements {@link CompetitorConditionState}
 */
public class Injured implements CompetitorConditionState {

    /**
     *
     * @return the string of Injured
     */
    @Override
    public String getStat() {
        return "Injured";
    }
}
