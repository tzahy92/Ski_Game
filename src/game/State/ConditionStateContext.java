package game.State;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian ConditionStateContext implements {@link Cloneable}
 */
public class ConditionStateContext implements Cloneable {
    private CompetitorConditionState currentState;

    /**
     * ctor of the class
     * build the stat first with Active
     */
    public ConditionStateContext(){
        currentState = new Active();
    }

    /**
     * change the stat of the competitor by the parameter state
     * @param state the new stat of the competitor
     */
    public void setState(CompetitorConditionState state){
        currentState = state;
    }

    /**
     *
     * @return the current stat of the competitor
     */
    public String getState(){
        return currentState.getStat();
    }

    /**
     * build new condition stat - it build with Active stat
     * @return condition stat
     * @throws CloneNotSupportedException if the object dose not support in clone
     */
    public ConditionStateContext cloun() throws CloneNotSupportedException {
        return new ConditionStateContext();
    }
}
