package JavaTournament;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

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
        frame.setContentPane(new BattlePanel(battleInfo));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(810, 500);
        frame.setVisible(true);
//
//        turnButton.addActionListener(this::turn);
//
//        turn(null);
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


}


class BattlePanel extends JPanel {
	private LogId apid = null;
	private Map<LogId, FighterStats> fightersA = new LinkedHashMap<>(), fightersB = new LinkedHashMap<>();
	private FighterStats curA, curB;
	private int turn = -1;
	private int battle = 0;
	private List<Record> res;
	private List<String> players;
	private List<String> ranks;
	private JList<Box> teamAList;
	private JList<Box> teamBList;
//	private JList<MultiLabel> playersList;
//	private JList<MultiLabel> losersList;
//	private MultiLabel logDisplay;
//	private MultiLabel aName, bName;
//	private MultiLabel aStats, bStats;
//	private MultiLabel aDamage, bDamage;
	private Image aImage, bImage;
	private Button turnButton, battleButton;
	private List<String> losers = new ArrayList<>();
	private BattleInfo battleInfo;

	JScrollPane jScrollPane2 ;
	JTable jTable2 ;
	JScrollPane 	jScrollPane4  ;
	JTable	jTable4;
	JLabel jLabel1;
	JLabel	health1 ;
	JLabel	stat1_2 ;
	JLabel	stat1_3 ;
	JLabel stat1_4 ;
	JLabel stat1_5 ;
	JLabel	stat2_2 ;
	JLabel stat2_3 ;
	JLabel stat2_4;
	JLabel	stat2_5 ;
	JLabel jLabel11;
	JLabel health2 ;
	JLabel player1 ;
	JLabel player2 ;
	JPanel jPanel2 ;
	JPanel jPanel3;
	JButton stepButton ;
	JButton skipButton;
	JLabel pokemon1 ;
	JLabel pokemon2 ;
	String winner;
	BattlePanel(BattleInfo battleInfo) {
		this.battleInfo = battleInfo;

	jScrollPane2 = new javax.swing.JScrollPane();
		 jTable2 = new javax.swing.JTable();
		JScrollPane 	jScrollPane4 = new javax.swing.JScrollPane();
		jTable4 = new javax.swing.JTable();
jLabel1 = new javax.swing.JLabel();
health1 = new javax.swing.JLabel();
stat1_2 = new javax.swing.JLabel();
stat1_3 = new javax.swing.JLabel();
		stat1_4 = new javax.swing.JLabel();
		stat1_5 = new javax.swing.JLabel();
		stat2_2 = new javax.swing.JLabel();
		stat2_3 = new javax.swing.JLabel();
		stat2_4 = new javax.swing.JLabel();
		stat2_5 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		health2 = new javax.swing.JLabel();
		player1 = new javax.swing.JLabel();
		player2 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		stepButton = new javax.swing.JButton();
		skipButton = new javax.swing.JButton();
		pokemon1 = new javax.swing.JLabel();
		pokemon2 = new javax.swing.JLabel();

		setBackground(new java.awt.Color(171, 53, 53));
		setPreferredSize(new java.awt.Dimension(805, 500));

		jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jTable2.setAutoCreateRowSorter(true);
		jTable2.setBackground(new java.awt.Color(171, 53, 53));
		jTable2.setBorder(new javax.swing.border.MatteBorder(null));
		jTable2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jTable2.setForeground(new java.awt.Color(183, 175, 175));
		jTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
						{null},
						{null},
						{null},
						{null},
						{null}
				},
				new String [] {
						"Pokemons"
				}
		) {
			Class[] types = new Class [] {
					java.lang.String.class
			};

			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}
		});
		jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR));
		jTable2.setName(""); // NOI18N
		jTable2.setRowHeight(23);
		jTable2.setRowSelectionAllowed(false);
		jTable2.setSelectionForeground(new java.awt.Color(171, 53, 53));
		jScrollPane2.setViewportView(jTable2);

		jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jTable4.setAutoCreateRowSorter(true);
		jTable4.setBackground(new java.awt.Color(171, 53, 53));
		jTable4.setBorder(new javax.swing.border.MatteBorder(null));
		jTable4.setForeground(new java.awt.Color(183, 175, 175));
		jTable4.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
						{null},
						{null},
						{null},
						{null},
						{null}
				},
				new String [] {
						"Pokemons"
				}
		) {
			Class[] types = new Class [] {
					java.lang.String.class
			};

			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}
		});
		jTable4.setCursor(new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR));
		jTable4.setName(""); // NOI18N
		jTable4.setRowHeight(23);
		jTable4.setRowSelectionAllowed(false);
		jScrollPane4.setViewportView(jTable4);

		jLabel1.setBackground(new java.awt.Color(171, 53, 53));
		jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(183, 175, 175));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("Stats");
		jLabel1.setAlignmentX(0.5F);
		jLabel1.setPreferredSize(new java.awt.Dimension(27, 23));

		health1.setBackground(new java.awt.Color(171, 53, 53));
		health1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		health1.setForeground(new java.awt.Color(183, 175, 175));
		health1.setText("Health: ");
		health1.setAlignmentX(0.5F);
		health1.setPreferredSize(new java.awt.Dimension(27, 23));

		stat1_2.setBackground(new java.awt.Color(171, 53, 53));
		stat1_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat1_2.setForeground(new java.awt.Color(183, 175, 175));
		stat1_2.setText("2");
		stat1_2.setAlignmentX(0.5F);
		stat1_2.setPreferredSize(new java.awt.Dimension(27, 23));

		stat1_3.setBackground(new java.awt.Color(171, 53, 53));
		stat1_3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat1_3.setForeground(new java.awt.Color(183, 175, 175));
		stat1_3.setText("3");
		stat1_3.setAlignmentX(0.5F);
		stat1_3.setPreferredSize(new java.awt.Dimension(27, 23));

		stat1_4.setBackground(new java.awt.Color(171, 53, 53));
		stat1_4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat1_4.setForeground(new java.awt.Color(183, 175, 175));
		stat1_4.setText("4");
		stat1_4.setAlignmentX(0.5F);
		stat1_4.setPreferredSize(new java.awt.Dimension(27, 23));

		stat1_5.setBackground(new java.awt.Color(171, 53, 53));
		stat1_5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat1_5.setForeground(new java.awt.Color(183, 175, 175));
		stat1_5.setText("6");
		stat1_5.setAlignmentX(0.5F);
		stat1_5.setPreferredSize(new java.awt.Dimension(27, 23));

		stat2_2.setBackground(new java.awt.Color(171, 53, 53));
		stat2_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat2_2.setForeground(new java.awt.Color(183, 175, 175));
		stat2_2.setText("2");
		stat2_2.setAlignmentX(0.5F);
		stat2_2.setPreferredSize(new java.awt.Dimension(27, 23));

		stat2_3.setBackground(new java.awt.Color(171, 53, 53));
		stat2_3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat2_3.setForeground(new java.awt.Color(183, 175, 175));
		stat2_3.setText("3");
		stat2_3.setAlignmentX(0.5F);
		stat2_3.setPreferredSize(new java.awt.Dimension(27, 23));

		stat2_4.setBackground(new java.awt.Color(171, 53, 53));
		stat2_4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat2_4.setForeground(new java.awt.Color(183, 175, 175));
		stat2_4.setText("4");
		stat2_4.setAlignmentX(0.5F);
		stat2_4.setPreferredSize(new java.awt.Dimension(27, 23));

		stat2_5.setBackground(new java.awt.Color(171, 53, 53));
		stat2_5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stat2_5.setForeground(new java.awt.Color(183, 175, 175));
		stat2_5.setText("6");
		stat2_5.setAlignmentX(0.5F);
		stat2_5.setPreferredSize(new java.awt.Dimension(27, 23));

		jLabel11.setBackground(new java.awt.Color(171, 53, 53));
		jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel11.setForeground(new java.awt.Color(183, 175, 175));
		jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel11.setText("Stats");
		jLabel11.setAlignmentX(0.5F);
		jLabel11.setPreferredSize(new java.awt.Dimension(27, 23));

		health2.setBackground(new java.awt.Color(171, 53, 53));
		health2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		health2.setForeground(new java.awt.Color(183, 175, 175));
		health2.setText("Health: ");
		health2.setAlignmentX(0.5F);
		health2.setPreferredSize(new java.awt.Dimension(27, 23));

		player1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		player1.setForeground(new java.awt.Color(183, 175, 175));
		player1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		player1.setText("player1");

		player2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		player2.setForeground(new java.awt.Color(183, 175, 175));
		player2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		player2.setText("player2");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 556, Short.MAX_VALUE)
		);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 201, Short.MAX_VALUE)
		);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE)
		);
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 69, Short.MAX_VALUE)
		);

		stepButton.setBackground(new java.awt.Color(171, 53, 53));
		stepButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		stepButton.setForeground(new java.awt.Color(183, 175, 175));
		stepButton.setText("Step");

		skipButton.setBackground(new java.awt.Color(171, 53, 53));
		skipButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		skipButton.setForeground(new java.awt.Color(183, 175, 175));
		skipButton.setText("Skip");

		pokemon1.setBackground(new java.awt.Color(171, 53, 53));
		pokemon1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		pokemon1.setForeground(new java.awt.Color(183, 175, 175));
		pokemon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		pokemon1.setText("Pokemon");

		pokemon2.setBackground(new java.awt.Color(171, 53, 53));
		pokemon2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		pokemon2.setForeground(new java.awt.Color(183, 175, 175));
		pokemon2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		pokemon2.setText("Pokemon");


		stepButton.addActionListener(this::turn);
		skipButton.addActionListener(this::skip);


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(health1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(stat1_2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
														.addComponent(stat1_3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
														.addComponent(stat1_4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
														.addComponent(stat1_5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
														.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
														.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(pokemon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(skipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																.addGap(0, 0, Short.MAX_VALUE)
																.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(stat2_3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(health2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(stat2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(stat2_4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(stat2_5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(layout.createSequentialGroup()
																.addGap(2, 2, 2)
																.addComponent(pokemon2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addGap(0, 0, Short.MAX_VALUE)
												.addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(69, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(player1)
										.addComponent(player2))
								.addGap(16, 16, 16)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(pokemon2)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(health2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(stat2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(stat2_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(stat2_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(stat2_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(pokemon1)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(health1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(stat1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(stat1_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(stat1_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(stat1_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(skipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(39, 39, 39))
		);

        /*fightersA.clear();
        fightersB.clear();*/
        players = battleInfo.getPlayers();
        ranks = battleInfo.getRanks();
        res = battleInfo.getResults();
        /*losersList.clear();
        losersList.addAll(losers);*/


        updatePlayers();

        
	}
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


					//damageAnimation(pd.getDamage(), l.getPlayer().equals(apid) ? aDamage : bDamage);

					updateFighters(team);
					break;
				case ATTACK:
					AttackLog al = (AttackLog)l;
					if(al.getResult().getDamage() == 0)
						break;
					oteam.get(al.getSubject()).health -= al.getResult().getDamage();

					//damageAnimation(al.getResult().getDamage(), l.getPlayer().equals(apid) ? bDamage : aDamage);
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
		//logDisplay.setText(res.get(turn).msgs.toString());
		if(turn == res.size() - 1) {
			skipButton.setEnabled(false);
			stepButton.setEnabled(false);
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
			System.out.println("Battle finished. Winner is " + winner) ;
			updatePlayers();
		}
	}
	
	void skip(ActionEvent event){
		while(turn != res.size() - 1){
			turn(event);
		}
			
	}
	
//
//
//	private void damageAnimation(int amount, MultiLabel target) {
//		int dir = target == aDamage? 1 : -1;
//		/*SequentialTransition st = new SequentialTransition();
//		ParallelTransition pt = new ParallelTransition();
//		FadeTransition ft = new FadeTransition();
//		ft.setByValue(-1);
//		ft.setAutoReverse(false);
//		ft.setDuration(new Duration(500));
//		TranslateTransition tt = new TranslateTransition();
//		tt.setDuration(new Duration(500));
//		TranslateTransition tb = new TranslateTransition();
//		tb.setDuration(new Duration(0));
//		st.getChildren().addAll(tt,tb);
//		pt.getChildren().addAll(ft,st);*/
//		target.setText("-" + amount);
//		target.setVisible(true);
//		/*target.setOpacity(1);
//		tt.setByX(60*dir);
//		tb.setByX(-60*dir);
//		ft.setNode(target);
//		tt.setNode(target);
//		tb.setNode(target);
//		pt.play();*/
//	}
//
	private void updatePlayers() {
		if(battleInfo.getPlayers().size() >0 ) {
			player1.setText(battleInfo.getPlayers().get(0));
			if(battleInfo.getPlayers().size() >1)
			player2.setText(battleInfo.getPlayers().get(1));
		}
	}


	private void updateFighters(Map<LogId, FighterStats> team) {
		List<LogId> stats = new LinkedList<>();
//		JList<Box> res = team == fightersA ? teamAList : teamBList;
//		res.removeAll();

		for(LogId li : team.keySet()) {
			stats.add(li);

//						MultiLabel name = new MultiLabel(li.name);
//			MultiLabel health = new MultiLabel("" + team.get(li).health + "/" + team.get(li).maxhealth);
//			Box hb = Box.createHorizontalBox();
//			hb.add(name);
//			hb.add(Box.createHorizontalStrut(20));vs
//			hb.add(health);
//			res.add(hb);
		}
		if(stats.size()>0) {
		for(int i = 0 ; i < stats.size(); i++){
			System.out.println(stats.get(i).name);
		}

			pokemon1.setText(stats.get(0).name);
			if (stats.size() > 1)
				pokemon2.setText(stats.get(1).name);
		}
//		health1.setText("Health: " + team.get(stats.get(0)).health + "/" + team.get(stats.get(0)).maxhealth);
//		if(stats.size() >1)
//		health2.setText("Health: " + team.get(stats.get(1)).health + "/" + team.get(stats.get(1)).maxhealth);



		if(team == fightersA) {
//			aName.setText(curA.name);
			health1.setText("Health: " + curA.health + "/" + curA.maxhealth);
			stat1_2.setText("Defence : " + curA.defence);
			stat1_3.setText("Accuracy: " + curA.accuracy);
			stat1_4.setText("Evasion: " + curA.evasion);
			stat1_5.setText("Power :" + curA.power);
			//aStats.setText(curA.toString());
//			try {
//				aImage = ImageIO.read(new URL(curA.img));
//			} catch(Exception e) {
//				System.out.println(e.getMessage());
//				aImage = null;
//			}
		} else {
//			bName.setText(curB.name);
			health2.setText("Health: " + curB.health  + "/" + curB.maxhealth);
			stat2_2.setText("Defence : " + curB.defence);
			stat2_3.setText("Accuracy: " + curB.accuracy);
			stat2_4.setText("Evasion: " + curB.evasion);
			stat2_5.setText("Power :" + curB.power);
//			try {
//				System.out.println(curB.img);
//				bImage = ImageIO.read(new URL(curB.img));
//			} catch(Exception e) {
//				System.out.println(e.getMessage());
//				bImage = null;
//			}
		}
	}
		}