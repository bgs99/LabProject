package JavaTournament;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bgs99c.client.*;
import bgs99c.lab2.AttackLog;
import bgs99c.lab2.DeathLog;
import bgs99c.lab2.Log;
import bgs99c.lab2.PeriodicDamageLog;
import bgs99c.lab2.Record;
import bgs99c.lab2.ReplacementLog;
import bgs99c.lab2.TeamLog;
import bgs99c.lab2.shared.FighterStats;
import bgs99c.lab2.shared.LogId;

import javax.swing.*;


public class BattleController {
	private Client client;
	private JFrame frame;
	public LogId apid = null;
	public Map<LogId, FighterStats> fightersA = new LinkedHashMap<>(), fightersB = new LinkedHashMap<>();
	public FighterStats curA, curB;
	public int turn = -1;
	public int battle = 0;
	List<Record> res;
	public BattleController() {}
	List<String> players;
	List<String> ranks;
	/*private ListView<HBox> teamAList;
	private ListView<HBox> teamBList;
	private List<HBox> playersList;
	private List<String> losersList;*/
	private Label logDisplay;
	private Label aName, bName;
	private Label aStats, bStats;
	private Label aDamage, bDamage;
	private Image aImage, bImage;
	private Panel aPane, bPane;
	private Button turnButton, battleButton;
    List<String> losers = new ArrayList<>();
	private void turn() throws CloneNotSupportedException {
		turn++;
		//System.out.println(teamAList.getItems().size());
		List<Log> logs = res.get(turn).logs;
		
		for(Log l : logs) {
			if(apid == null)
				apid = l.getPlayer();
			
			Map<LogId, FighterStats> team, oteam;
			if(l.getPlayer().equals(apid)) {
				team = fightersA;
				oteam = fightersB;
			} else {
				team = fightersB;
				oteam = fightersA;
			}
			switch(l.getType()) {
				case TEAM:
					TeamLog tl = (TeamLog)l;
					tl.fs.health = tl.fs.maxhealth;
					team.put(tl.getSubject(), tl.fs);
					if(team.size() == 1) {
						if(l.getPlayer().equals(apid)) curA = tl.fs;
						else curB = tl.fs;
					}
					updateFighters(team);
					break;
				case LASTING:
					PeriodicDamageLog pd = (PeriodicDamageLog)l;
					if(pd.getDamage() == 0)
						break;
					team.get(pd.getSubject()).health -= pd.getDamage();
					

					damageAnimation(pd.getDamage(), l.getPlayer().equals(apid) ? aDamage : bDamage);
					
					updateFighters(team);
					break;
				case ATTACK:
					AttackLog al = (AttackLog)l;
					if(al.getResult().getDamage() == 0)
						break;
					oteam.get(al.getSubject()).health -= al.getResult().getDamage();
					
					damageAnimation(al.getResult().getDamage(), l.getPlayer().equals(apid) ? bDamage : aDamage);
					updateFighters(oteam);
					break;
				case DEATH:
					DeathLog dl = (DeathLog)l;
					team.remove(dl.getSubject());
					updateFighters(team);
					break;
				case REPLACEMENT:
					ReplacementLog rl = (ReplacementLog)l;
					if(l.getPlayer().equals(apid)) curA = team.get(rl.getReplacement());
					else curB = team.get(rl.getReplacement());
					updateFighters(team);
					break;
				default:
					System.out.println(logs.get(0).getType());
			}
		}
		LogId p = logs.get(0).getPlayer();
		/*TranslateTransition tt = new TranslateTransition();
		tt.setByX(p.equals(apid)? 20 : -20);
		tt.setNode(p.equals(apid)? aPane : bPane);
		tt.setDuration(new Duration(200));
		TranslateTransition tb = new TranslateTransition();
		tb.setByX(p.equals(apid)? -20 : 20);
		tb.setNode(p.equals(apid)? aPane : bPane);
		tb.setDuration(new Duration(200));
		SequentialTransition st = new SequentialTransition();
		st.getChildren().addAll(tt, tb);
		st.playFromStart();*/
		logDisplay.setText(res.get(turn).msgs.toString());
		if(turn == res.size() - 1) {
			turnButton.setEnabled(false);
			String winner;
			if(players.get(0).equals(ranks.get(battle))) {
				players.remove(0);
				winner = players.get(0);
				players.remove(0);
				players.add(winner);
			} else {
				players.remove(1);
				winner = players.get(0);
				players.remove(0);
				players.add(winner);
			}
			losers.add(ranks.get(battle++));
			updatePlayers();
		}
	}
	
	
	private void damageAnimation(int amount, Label target) {
		int dir = target == aDamage? 1 : -1;
		/*SequentialTransition st = new SequentialTransition();
		ParallelTransition pt = new ParallelTransition();
		FadeTransition ft = new FadeTransition();
		ft.setByValue(-1);
		ft.setAutoReverse(false);
		ft.setDuration(new Duration(500));
		TranslateTransition tt = new TranslateTransition();
		tt.setDuration(new Duration(500));
		TranslateTransition tb = new TranslateTransition();
		tb.setDuration(new Duration(0));
		st.getChildren().addAll(tt,tb);
		pt.getChildren().addAll(ft,st);*/
		target.setText("-" + amount);
		target.setVisible(true);
		/*target.setOpacity(1);
		tt.setByX(60*dir);
		tb.setByX(-60*dir);
		ft.setNode(target);
		tt.setNode(target);
		tb.setNode(target);
		pt.play();*/
	}
	
	private void updatePlayers() {
		//ObservableList<HBox> res = FXCollections.observableArrayList();
		for(int i = 0; i < players.size()/2;i++) {
			Label a = new Label(players.get(i*2));
			Label b = new Label(players.get(i*2+1));
			Label vs = new Label("vs");
			/*HBox hb = new HBox();
			hb.setSpacing(20d);
			hb.getChildren().addAll(a, vs, b);
			res.add(hb);*/
		}
		if(players.size() % 2 > 0) {
			Label a = new Label(players.get(players.size()-1));
			/*HBox hb = new HBox();
			hb.setSpacing(20d);
			hb.getChildren().addAll(a);
			res.add(hb);Text*/
		}
		//playersList.setItems(res);
	}
	private void updateFighters(Map<LogId, FighterStats> team) {
		//ObservableList<HBox> res = FXCollections.observableArrayList();
		for(LogId li : team.keySet()) {
			Label name = new Label(li.name);
			Label health = new Label("" + team.get(li).health + "/" + team.get(li).maxhealth);
			/*HBox hb = new HBox();
			hb.setSpacing(10d);
			hb.getChildren().addAll(name, health);
			res.add(hb);*/
		}
		if(team == fightersA) {
			aName.setText(curA.name);
			aStats.setText(curA.toString());
			try {
				//aImage.setImage(new Image(curA.img));
			} catch(Exception e) {
				System.out.println(e.getMessage());
				//aImage.setImage(null);
			}
			//teamAList.setItems(res);
		} else {
			bName.setText(curB.name);
			bStats.setText(curB.toString());
			try {
				System.out.println(curB.img);
				
				//bImage.setImage(new Image(curB.img));
			} catch(Exception e) {
				System.out.println(e.getMessage());
				//bImage.setImage(null);
			}
			//teamBList.setItems(res);
		}
	}

	public BattleController(Client client, JFrame frame){
        this.client = client;
        this.frame = frame;
    }
	public void initialize() throws ClassNotFoundException, IOException, CloneNotSupportedException{
		if(logDisplay == null)
			return;
		fightersA.clear();
		fightersB.clear();
		battleButton.setEnabled(false);
		players = client.getPlayers();
		ranks = client.getRanks();
		res = client.getResults();
		losersList.setItems(losers);
		updatePlayers();
		turn();
	}
	
}
