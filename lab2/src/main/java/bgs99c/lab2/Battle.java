package bgs99c.lab2;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
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

    private final boolean cheater;

    /**
     * Creates a battle between given players
     * @param a First player
     * @param b Second player
     */
    public Battle(Player a, Player b){

        cheater = b.getName().equals("Cheater");
        System.out.println(a + " vs " + b);

        teamA = new Team();
        teamA.player = a;
        teamB = new Team();
        teamB.player = b;
        log = new ArrayList<>();
        turnLog  = new ArrayList<>();
        teamA.winState = GameState.AWON;
        teamB.winState = GameState.BWON;
    }

    private Object pass = new Object();
    private MySecurityManager sm = new MySecurityManager(pass);

    private Player fail = null;

    private void runUntrusted(Callable<Boolean> task, Player fail) throws Exception {
        FutureTask<Boolean> futureTask = new FutureTask<>(task);
        futureTask.run();
        try {
            if (!futureTask.get(1, TimeUnit.SECONDS))
                throw new Exception("Player failed to initialize");
        }catch (Exception e){
            e.printStackTrace();
            this.fail = fail;
            throw new Exception();
        }
    }

    private Player init() {
        try {
            runUntrusted(() -> teamA.init(), teamB.player);
            runUntrusted(() -> teamB.init(), teamA.player);
            runUntrusted(() -> {
                teamA.player.getStrategy().init(this);
                return true;
            }, teamB.player);
            runUntrusted(() -> {
                teamB.player.getStrategy().init(this);
                return true;
            }, teamA.player);
            return null;
        } catch (Exception | StackOverflowError | OutOfMemoryError e) {
            return this.fail;
        }
    }

    private boolean healed = false;
    /**
     * Runs battle simulation and returns the winner
     */
    public Player start(){

        SecurityManager old = System.getSecurityManager();
        System.setSecurityManager(sm);

    	Player inp = init();
    	if(inp != null)
    		return inp;
    	
        GameState current;

        Player winner = null;


        while(true){
            FutureTask<GameState> turn = new FutureTask<>(this::makeTurn);
            try {
                turn.run();
                current = turn.get(1, TimeUnit.SECONDS);
            }
            catch(Exception | StackOverflowError| OutOfMemoryError e){
                e.printStackTrace();
                OutputLogger.log("Exception in async");
                winner = opponentInfo().player;
                break;
            }

            if(current == GameState.AWON) {
                winner = teamA.player;
                break;
            }
            else if(current == GameState.BWON) {
                winner = teamB.player;
                break;
            }
        }

        sm.disable(pass);
        System.setSecurityManager(old);

        return winner;
    }

    private GameState makeTurn() throws Exception{
        GameState a;
        try {
            a = makeTurn(teamA);
            if(cheater && healed){
                OutputLogger.log("Cheated!");
            }
        } catch (Exception e) {
            if(e.getMessage().equals("Cheater!") && cheater && healed){
                OutputLogger.log("You've catched the Cheater!");
                markTurn();
                return GameState.AWON;
            }
            throw e;
        }
        markTurn();
        if(a == GameState.BWON) {
            return a;
        }

        healed = false;

        GameState res = makeTurn(teamB);
        markTurn();
        return res;
    }
    private GameState makeTurn(Team team) throws Exception{

        currentPlayer = team.player;

        Log periodicDamages = team.current.applyPeriodicDamages(currentPlayer);
        record(periodicDamages);


        if(team.current.getHealth()<=0){
            OutputLogger.message(team.current.dyingMessage(opponentFighter()), team.current, team.player);
        	record(new DeathLog(team.current, currentPlayer));

        	OutputLogger.log(team.current + " is dead");

        	team.alive--;

            opponentInfo().player.getStrategy().applyLevelUp(opponentFighter());

            if(team.alive <= 0){
                OutputLogger.log(opponentInfo().player + " won!");
                return opponentInfo().winState;
            }

            while(team.current.getHealth() <= 0){
            	FighterInfo last = team.current;
                team.current = team.player.getStrategy().replaceDead();
                OutputLogger.message(team.current.enterFightMessage(), team.current, team.player);
                ReplacementLog rl = new ReplacementLog(last, team.current, currentPlayer);
                record(rl);
            }

            OutputLogger.log(team.current + " replaced his fallen ally.");
        }
        if(!team.current.applyStuns()) {
            Action a = team.player.getStrategy().makeTurn();
            Log result = a.apply(this);
            record(result);
            healed = result instanceof AttackLog && ((AttackLog) result).getResult().heal > 0;
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
                for(int i = FIGHTERS; i > 0; i--) {
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
            OutputLogger.message(current.enterFightMessage(), current, player);
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
