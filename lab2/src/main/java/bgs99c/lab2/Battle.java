package bgs99c.lab2;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public final class Battle {
    private Team teamA, teamB;
    private List<Log> log, turnLog;
    private Player currentPlayer;
    private final static int FIGHTERS = 3;
    private void markTurn() {
    	OutputLogger.markTurn(turnLog);
        turnLog = new ArrayList<>();
    }
    Player getCurrentPlayer(){
        return currentPlayer;
    }
    private void record(Log l) {
    	log.add(l);
    	turnLog.add(l);
    }
    Team getTeam(Player p) {
    	if(p == teamA.player)
    		return teamA;
    	if(p == teamB.player)
    		return teamB;
    	return null;
    }
    /**
     * Creates a battle between given players
     * @param a First player
     * @param b Second player
     */
    public Battle(Player a, Player b){
        teamA = new Team();
        teamA.player = a;
        teamB = new Team();
        teamB.player = b;
        log = new ArrayList<>();
        turnLog  = new ArrayList<>();
        teamA.winState = GameState.AWON;
        teamB.winState = GameState.BWON;
    }
    
    private Player init() {
    	FutureTask<Boolean> initA = new FutureTask<>(() -> teamA.init());
        FutureTask<Boolean> initB = new FutureTask<>(() -> teamB.init());
        initA.run();
        initB.run();
        try {
            if (!initA.get()) return teamB.player;
        }catch (Exception e){
            e.printStackTrace();
            return teamB.player;
        }
        try {
            if (!initB.get()) return teamA.player;
        }catch (Exception e){
            e.printStackTrace();
            return teamA.player;
        }
        teamA.player.getStrategy().init(this);
        teamB.player.getStrategy().init(this);
    	
    	return null;
    }
    
    /**
     * Runs battle simulation and returns the winner
     */
    public Player start(){

    	Player inp = init();
    	if(inp != null)
    		return inp;
    	
        GameState current;

        while(true){
            FutureTask<GameState> turn = new FutureTask<>(this::makeTurn);
            try {
                turn.run();
                current = turn.get(2, TimeUnit.SECONDS);
            }
            catch(Exception e){
                e.printStackTrace();
                OutputLogger.log("Exception in async");
                return opponentInfo().player;
            }

            if(current == GameState.AWON) return teamA.player;
            else if(current == GameState.BWON) return teamB.player;
        }
    }

    private GameState makeTurn(){
        GameState a = makeTurn(teamA);
        markTurn();
        if(a == GameState.BWON) {
            return a;
        }
        GameState res = makeTurn(teamB);
        markTurn();
        return res;
    }
    private GameState makeTurn(Team team){
        currentPlayer = team.player;
        Log pd = team.current.applyPeriodicDamages(currentPlayer);
        record(pd);
        
        if(team.current.getHealth()<=0){
        	record(new DeathLog(team.current, currentPlayer));
            OutputLogger.log(team.current + " is dead");
            team.alive--;
            opponentInfo().player.getStrategy().applyLevelUp(opponentFighter());
            if(team.alive <=0){
                OutputLogger.log(opponentInfo().player + " won!");
                return opponentInfo().winState;
            }
            while(team.current.getHealth()<=0){
            	FighterInfo last = team.current;
                team.current = team.player.getStrategy().replaceDead();
                ReplacementLog rl = new ReplacementLog(last, team.current, currentPlayer);
                record(rl);
            }
            OutputLogger.log(team.current + " replaced his fallen ally.");
        }
        if(!team.current.applyStuns()) {
            Action a = team.player.getStrategy().makeTurn();
            record(a.apply(this));
        }
        return GameState.NORMAL;
    }

    private enum GameState{
        NORMAL, AWON, BWON
    }

    void changeFighter(Fighter b){
        allyInfo().current = b;
    }

    /**
     * Returns current player's opponent's squad
     */
    public FighterInfo[] enemySquad(){
        return opponentInfo().squad;
    }

    /**
     * Returns current player's opponent's current fighter
     */
    public FighterInfo currentOpponent() {
        return opponentInfo().current;
    }

    /**
     * Returns current player's squad
     */
    public Fighter[] allySquad() {
        return allyInfo().squad;
    }
    /**
     * Returns current player's current fighter
     **/
    public Fighter currentFighter() {
        return allyInfo().current;
    }
    private Fighter opponentFighter(){return opponentInfo().current;}

    private Team allyInfo(){
        if(currentPlayer == teamA.player)
            return teamA;
        return teamB;
    }
    private Team opponentInfo(){
        if(currentPlayer == teamA.player)
            return teamB;
        return teamA;
    }

    /**
     * Returns battle logs
     */
    public Log[] getLog(){
        return log.toArray(new Log[0]);
    }
    class Team {
        int alive = FIGHTERS;
        Player player;
        Fighter[] squad = new Fighter[FIGHTERS];
        Fighter current;
        GameState winState;
        boolean init(){
            if(player.fought){
                squad = player.fighters;
            }
            else{
                for(int i = FIGHTERS; i>0;i--) {
                    Fighter x = player.getStrategy().selectFighterTournament(i);
                    if(x == null)
                        return false;
                    OutputLogger.log(player + " selected " + x);
                    squad[FIGHTERS-i] = x;
                }
            }
            player.fighters = squad;
            player.fought = true;
            alive = FIGHTERS;
            current = squad[0];
            List<Log> logs = new ArrayList<>();
            for(Fighter f : squad){
                f.reset();
                logs.add(new TeamLog(f, player));
            }
            OutputLogger.markTurn(logs);
            return true;
        }
    }
	public FighterInfo getFighter(int hash, String name) {
		for(Fighter f : teamA.squad)
			if(f.toString().equals(name) && f.hashCode() == hash)
				return f;
		for(Fighter f : teamB.squad)
			if(f.toString().equals(name) && f.hashCode() == hash)
				return f;
		return null;
	}
	public Player getPlayer(int hash, String name) {
		if(teamA.player.getName().equals(name) && teamA.player.hashCode() == hash)
			return teamA.player;
		if(teamB.player.getName().equals(name) && teamB.player.hashCode() == hash)
			return teamB.player;
		return null;
	}
}
