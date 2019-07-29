package game.State;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Completed implements {@link CompetitorConditionState}
 */
public class Completed implements CompetitorConditionState {

    /**
     *
     * @return the string of Completed
     */
    @Override
    public String getStat() {
        return "Completed";
    }
}
