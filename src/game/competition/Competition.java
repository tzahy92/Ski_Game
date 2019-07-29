package game.competition;

import game.arena.IArena;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import game.arena.WinterArena;
import game.entities.sportsman.WinterSportsman;
import utilities.ValidationUtils;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian Competition implement {@link Observer}
 */
public abstract class Competition implements Observer {

    private IArena arena;
    private int maxCompetitors;
    private ArrayList<Competitor> activeCompetitors;
    private ArrayList<Competitor> finishedCompetitors;
    private ArrayList<Competitor> injuredCompetitors;
    private ArrayList<Competitor> disabledCompetitors;
    private final static int MIN_X_GAP = 80;

    /**
     * parameters ctor
     * @param arena initialize the arena
     * @param maxCompetitors initialize the maxCompetitors
     */
    public Competition(IArena arena, int maxCompetitors){
        this.setArena(arena);
        this.setMaxCompetitors(maxCompetitors);
        activeCompetitors = new ArrayList<Competitor>();
        finishedCompetitors = new ArrayList<Competitor>();
        injuredCompetitors = new ArrayList<Competitor>();
        disabledCompetitors = new ArrayList<Competitor>();
    }

    /**
     * abstract method
     * @param competitor
     * @return
     */
    protected abstract boolean isValidCompetitor(Competitor competitor);

    /**
     * check if the competitor is valid and if the arena is not full
     * init the competitor to the race
     * @param competitor add to the activeCompetitors
     */
    public void addCompetitor(Competitor competitor){
        synchronized (activeCompetitors) {
            ValidationUtils.assertNotNull(competitor);
            if (activeCompetitors.size() == this.getMaxCompetitors())
                throw new IllegalStateException(arena + "is full max = " + this.maxCompetitors);
            else if (!this.isValidCompetitor(competitor))
                throw new IllegalArgumentException("Invalid competitor " + competitor);
            else {
                competitor.initRace(MIN_X_GAP * activeCompetitors.size(),(arena).getLength(),this.arena.getFriction());
                activeCompetitors.add(competitor);
            }

        }
    }

    /**
     * Make a move for all the competitor how dose not finish the race
     */

    /**
     *
     * @return true if there are more active competitor in the race
     */
    public boolean hasActiveCompetitors(){
        synchronized (activeCompetitors) {
            return !activeCompetitors.isEmpty();
        }
    }

    /**
     * check if the arena is null
     * @param arena set the arena
     */
    private void setArena(IArena arena) {
        ValidationUtils.assertNotNull(arena);
        this.arena = arena;
    }

    /**
     *
     * @return the maximum of competitor that can race in the competition
     */
    public int getMaxCompetitors() {
        return maxCompetitors;
    }

    /**
     * check if the maxCompetitors is bigger then 0
     * @param maxCompetitors set the maxCompetitors
     */
    private void setMaxCompetitors(int maxCompetitors) {
        ValidationUtils.assertPositive(maxCompetitors);
        this.maxCompetitors = maxCompetitors;
    }

    /**
     *
     * @return the finishedCompetitors
     */
    public ArrayList<Competitor> getFinishedCompetitors() {
        synchronized (finishedCompetitors) {
            return finishedCompetitors;
        }
    }

    /**
     *
     * @return the activeCompetitors
     */
    public ArrayList<Competitor> getActiveCompetitors() {
        synchronized (activeCompetitors) {
            return activeCompetitors;
        }
    }

    /**
     * execute the observabel competitor for all the active competitor.
     * finish and shutdown the competitors if there are no active competitor, all the competitor are finish
     * @throws InterruptedException if the competitor thread are interrupted before or during the activity
     */
    public void startRace() throws InterruptedException {
        ExecutorService e;
        synchronized (activeCompetitors) {
            e = Executors.newFixedThreadPool(this.activeCompetitors.size());
            synchronized (this) {
                for (Competitor racer : activeCompetitors) {
                    e.execute((WinterSportsman)racer);
                }
            }
        }
        e.shutdown();
    }

    /**
     * override the method of observer.
     * move the competitor frome the activeCompetitor to the finishCompetitor
     * @param o the competitor
     * @param arg the type of the message, get only one message: finish
     */
    @Override
    public void update(Observable o, Object arg) {
        Competitor comp = (Competitor) o;
        synchronized (this.activeCompetitors) {
            if (arg.equals("Completed") && activeCompetitors.contains(comp)) {
                this.finishedCompetitors.add(comp);
                this.activeCompetitors.remove(comp);
            }

            else if (arg.equals("Injured") && activeCompetitors.contains(comp)) {
                injuredCompetitors.add(comp);
                activeCompetitors.remove(comp);
            }

            else if (arg.equals("Disabled") && injuredCompetitors.contains(comp)) {
                disabledCompetitors.add(comp);
                injuredCompetitors.remove(comp);
            }

            else if (arg.equals("Active") && injuredCompetitors.contains(comp)) {
                activeCompetitors.add(comp);
                injuredCompetitors.remove(comp);
            }
        }

    }

    /**
     *
     * @return the list of the injured competitor
     */
    public ArrayList<Competitor> getInjuredCompetitors() {
        return injuredCompetitors;
    }

    /**
     *
     * @return the list of the disabled competitor
     */
    public ArrayList<Competitor> getDisabledCompetitors() {
        return disabledCompetitors;
    }
}
