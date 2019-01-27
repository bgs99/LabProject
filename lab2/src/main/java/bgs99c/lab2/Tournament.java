package bgs99c.lab2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Class for simulating tournaments with many contestants
 */
public final class Tournament {
    /**
     * Creates tournament with given contestants
     * @param c Contestants. Should not be empty
     */
	
    public Tournament(Player[] c){
    	OutputLogger.log("Tournament started!");
        contestants = new LinkedList<>();
        for(Player p : c)
            contestants.add(p);
    }
    Queue<Player> contestants;

    /**
     * Runs tournament simulation
     * @return Winner
     */
    public List<Player> start(){
    	List<Player> res = new ArrayList<>();
        while(true){
            Player a = contestants.poll();
            if(contestants.isEmpty()) {
                res.add(a);
            	return res;
            }
            Player b = contestants.poll();
            Battle f = new Battle(a, b);
            Player winner = f.start();
            FutureTask<Boolean> evolve = new FutureTask<Boolean>(() -> {
                winner.getStrategy().applyEvolve(winner);
                return true;
            });
            evolve.run();
            try{
                evolve.get(2, TimeUnit.SECONDS);
            }catch(Exception e){
                e.printStackTrace();
            }
            res.add(a == winner ? b : a);
            contestants.add(winner);
        }
    }
}
