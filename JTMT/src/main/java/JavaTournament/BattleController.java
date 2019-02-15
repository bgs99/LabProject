package JavaTournament;

import java.awt.*;
import java.awt.event.ActionEvent;
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
import bgs99c.lab2.shared.AttackResult;
import bgs99c.lab2.shared.FighterStats;
import bgs99c.lab2.shared.LogId;

import javax.imageio.ImageIO;
import javax.swing.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.WEST;


public class BattleController {

    private class MultiLabel extends JLabel {
        public MultiLabel() {
            super();
        }
        public MultiLabel(String text){
            super("<html>" + text.replace("\n", "<br>"));
        }

        @Override
        public void setText(String text){
            super.setText("<html>" + text.replace("\n", "<br>"));
        }
    }

	private JFrame frame;
	private LogId apid = null;
	private Map<LogId, FighterStats> fightersA = new LinkedHashMap<>(), fightersB = new LinkedHashMap<>();
	private FighterStats curA, curB;
	private int turn = -1;
	private int battle = 0;
	private List<Record> res;

	public BattleController(BattleInfo battleInfo, JFrame frame) {
	    this.frame = frame;

        /*fightersA.clear();
        fightersB.clear();*/
        players = battleInfo.getPlayers();
        ranks = battleInfo.getRanks();
        res = battleInfo.getResults();
        /*losersList.clear();
        losersList.addAll(losers);*/

        playersList = new JList<>();

        updatePlayers();


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = WEST;
        c.fill = BOTH;
        c.gridx = 0;
        c.gridy = RELATIVE;

        playersList = new JList<>();
        panel.add(playersList, c);
        losersList = new JList<>();
        panel.add(losersList, c);

        logDisplay = new MultiLabel();
        panel.add(logDisplay, c);

        c.gridx = 1;
        c.gridy = 0;
        aName = new MultiLabel();
        panel.add(aName, c);
        c.gridy = RELATIVE;
        aStats = new MultiLabel();
        panel.add(aStats, c);
        aDamage = new MultiLabel();
        panel.add(aDamage, c);
        teamAList = new JList<>();
        panel.add(teamAList, c);
        turnButton = new Button("Step");
        panel.add(turnButton, c);

        c.gridx = 2;
        c.gridy = 0;
        bName = new MultiLabel();
        panel.add(bName, c);
        c.gridy = RELATIVE;
        bStats = new MultiLabel();
        panel.add(bStats, c);
        bDamage = new MultiLabel();
        panel.add(bDamage, c);
        teamBList = new JList<>();
        panel.add(teamBList, c);
        battleButton = new Button("Skip");
        panel.add(battleButton, c);


        battleButton.setEnabled(false);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setVisible(true);

        turnButton.addActionListener(this::turn);

        turn(null);
    }
	private List<String> players;
	private List<String> ranks;
	private JList<Box> teamAList;
	private JList<Box> teamBList;
	private JList<MultiLabel> playersList;
	private JList<MultiLabel> losersList;
	private MultiLabel logDisplay;
	private MultiLabel aName, bName;
	private MultiLabel aStats, bStats;
	private MultiLabel aDamage, bDamage;
	private Image aImage, bImage;
	private Button turnButton, battleButton;
    private List<String> losers = new ArrayList<>();

	private void turn(ActionEvent event) {
		turn++;
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
					AttackResult ar = al.getResult();

					oteam.get(al.getSubject()).health -= ar.damage;
					team.get(al.getUser()).health += ar.heal;

					if (ar.damage > 0) {
						damageAnimation(al.getResult().damage, l.getPlayer().equals(apid) ? bDamage : aDamage);
						updateFighters(oteam);
					}
					if (ar.heal > 0) {
						//TODO: heal animation
						updateFighters(team);
					}
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
					System.out.println(l.getType());
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
	
	
	private void damageAnimation(int amount, MultiLabel target) {
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
        playersList.removeAll();
		for(int i = 0; i < players.size()/2;i++) {
            playersList.add(new MultiLabel(players.get(i*2) + " vs " + players.get(i*2+1)));
		}
		if(players.size() % 2 > 0) {
            playersList.add(new MultiLabel(players.get(players.size()-1)));
		}
	}
	private void updateFighters(Map<LogId, FighterStats> team) {
        JList<Box> res = team == fightersA ? teamAList : teamBList;
        res.removeAll();
		for(LogId li : team.keySet()) {
			MultiLabel name = new MultiLabel(li.name);
			MultiLabel health = new MultiLabel("" + team.get(li).health + "/" + team.get(li).maxhealth);
			Box hb = Box.createHorizontalBox();
			hb.add(name);
			hb.add(Box.createHorizontalStrut(20));
			hb.add(health);
			res.add(hb);
		}
		if(team == fightersA) {
			aName.setText(curA.name);
			aStats.setText(curA.toString());
			try {
				aImage = ImageIO.read(new URL(curA.img));
			} catch(Exception e) {
				System.out.println(e.getMessage());
				aImage = null;
			}
		} else {
			bName.setText(curB.name);
			bStats.setText(curB.toString());
			try {
				//System.out.println(curB.img);
				bImage = ImageIO.read(new URL(curB.img));
			} catch(Exception e) {
				System.out.println(e.getMessage());
				bImage = null;
			}
		}
	}
}
