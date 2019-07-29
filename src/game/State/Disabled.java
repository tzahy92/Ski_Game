package game.State;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Disabled implements {@link CompetitorConditionState}
 */
public class Disabled implements CompetitorConditionState {

    /**
     *
     * @return the string of Disabled
     */
    @Override
    public String getStat() {
        return "Disabled";
    }
}
