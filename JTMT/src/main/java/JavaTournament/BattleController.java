package JavaTournament;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import bgs99c.client.*;
import bgs99c.lab2.*;
import bgs99c.lab2.shared.AttackResult;
import bgs99c.lab2.shared.FighterStats;
import bgs99c.lab2.shared.LogId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



class BattleController {


    BattleController(BattleInfo battleInfo, JFrame frame) {

        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.BATTLE_PAGE_SIZE));
        frame.setContentPane(new BattlePanel(battleInfo, frame));
        frame.pack();


    }



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
    private List<String> losers = new ArrayList<>();
    private BattleInfo battleInfo;
    private JFrame frame;
    private JLabel  jLabel2  ;
    private JLabel  jLabel3  ;
    private JLabel  jLabel4 ;
    private  DefaultTableModel logTableModel ;
    private   DefaultTableModel pokemon1TableModel ;
    private  DefaultTableModel pokemon2TableModel;

    private   JLabel  jLabel5 ;
    private JLabel  jLabel6  ;
    private JLabel  jLabel7  ;
    private JLabel  jLabel8  ;
    private JLabel  jLabel9  ;
    private JLabel  jLabel10 ;
    private JLabel  jLabel12;
    private JLabel  jLabel13;
    private JLabel jLabel18;
    private JLabel jLabel19;

    private JButton jButton1 ;
    private JButton jButton2 ;
    private   JLabel jLabel14;
    private   JLabel jLabel15;
    private   JLabel  jLabel16;
    private JPanel oldPanel;
    BattlePanel(BattleInfo battleInfo, JFrame frame) {
        this.battleInfo = battleInfo;
        this.frame = frame;
        oldPanel = (JPanel) frame.getContentPane();
        Font CustomFont;
        Font customFont18 = null;
        Font customFont13 = null;

        try {

            URL resource = ScreenController.class.getResource("/beermoney.ttf");
            String TTFpath = Paths.get(resource.toURI()).toString();
            CustomFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(TTFpath));
            customFont18 = CustomFont.deriveFont(19.0F);
            customFont13 = CustomFont.deriveFont(13.0F);


        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
        }
        JScrollPane jScrollPane2 = new JScrollPane();
        JTable jTable2 = new JTable();
        JScrollPane jScrollPane4 = new JScrollPane();
        JTable jTable4 = new JTable();
        JLabel jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        JLabel jLabel11 = new JLabel();
        jLabel12 = new JLabel();
        jLabel13 = new JLabel();
        jLabel14 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jLabel15 = new JLabel();
        jLabel16 = new JLabel();
         jLabel18 = new JLabel();
         jLabel19 = new JLabel();
        JScrollPane jScrollPane3 = new JScrollPane();
        JTable jTable3 = new JTable();

        setBackground(new Color(128, 12, 12));
        setPreferredSize(new Dimension(850, 500));

        jScrollPane2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));


        Vector<String> pokemon1TableHeadVector = new Vector<>();
        pokemon1TableHeadVector.add("pokemons");
        jTable2.setAutoCreateRowSorter(true);
        jTable2.setBackground(new Color(128, 12, 12));
       // jTable2.setBorder(new border.MatteBorder(null));
        jTable2.setFont(customFont13);
        jTable2.setForeground(new Color(209, 202, 202));
        pokemon1TableModel = new DefaultTableModel(pokemon1TableHeadVector, 0);
        jTable2.setModel(pokemon1TableModel);
        jTable2.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        jTable2.setName("");
        jTable2.setRowHeight(23);
        jTable2.setRowSelectionAllowed(false);
        jTable2.setSelectionForeground(new Color(128, 12, 12));
        jScrollPane2.setViewportView(jTable2);

        jScrollPane4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));


        Vector<String> pokemon2TableHeadVector = new Vector<>();
        pokemon2TableHeadVector.add("pokemons");
        jTable4.setAutoCreateRowSorter(true);
        jTable4.setBackground(new Color(128, 12, 12));
        //jTable4.setBorder(new border.MatteBorder(null));
        jTable4.setForeground(new Color(209, 202, 202));
        pokemon2TableModel = new DefaultTableModel(pokemon2TableHeadVector, 0);
        jTable4.setModel(pokemon2TableModel);
        jTable4.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        jTable4.setName("");
        jTable4.setFont(customFont13);

        jTable4.setRowHeight(23);
        jTable4.setRowSelectionAllowed(false);
        jScrollPane4.setViewportView(jTable4);

        jLabel1.setBackground(new Color(128, 12, 12));
        jLabel1.setFont(customFont18);
        jLabel1.setForeground(new Color(209, 202, 202));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Stats");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setPreferredSize(new Dimension(27, 23));

        jLabel2.setBackground(new Color(128, 12, 12));
        jLabel2.setFont(customFont18);
        jLabel2.setForeground(new Color(209, 202, 202));
        jLabel2.setText("Health: ");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setPreferredSize(new Dimension(27, 23));

        jLabel3.setBackground(new Color(128, 12, 12));
        jLabel3.setFont(customFont18);
        jLabel3.setForeground(new Color(209, 202, 202));
        jLabel3.setText("Defence: ");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setPreferredSize(new Dimension(27, 23));

        jLabel4.setBackground(new Color(128, 12, 12));
        jLabel4.setFont(customFont18);
        jLabel4.setForeground(new Color(209, 202, 202));
        jLabel4.setText("Accuracy: ");
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setPreferredSize(new Dimension(27, 23));

        jLabel5.setBackground(new Color(128, 12, 12));
        jLabel5.setFont(customFont18);
        jLabel5.setForeground(new Color(209, 202, 202));
        jLabel5.setText("Evasion: ");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setPreferredSize(new Dimension(27, 23));

        jLabel6.setBackground(new Color(128, 12, 12));
        jLabel6.setFont(customFont18);
        jLabel6.setForeground(new Color(209, 202, 202));
        jLabel6.setText("Power: ");
        jLabel6.setAlignmentX(0.5F);
        jLabel6.setPreferredSize(new Dimension(27, 23));


        jLabel11.setBackground(new Color(128, 12, 12));
        jLabel11.setFont(customFont18);
        jLabel11.setForeground(new Color(209, 202, 202));
        jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel11.setText("Stats");
        jLabel11.setAlignmentX(0.5F);
        jLabel11.setPreferredSize(new Dimension(27, 23));

        jLabel12.setBackground(new Color(128, 12, 12));
        jLabel12.setFont(customFont18);
        jLabel12.setForeground(new Color(209, 202, 202));
        jLabel12.setText("Health: ");
        jLabel12.setAlignmentX(0.5F);
        jLabel12.setPreferredSize(new Dimension(27, 23));

        jLabel7.setBackground(new Color(128, 12, 12));
        jLabel7.setFont(customFont18);
        jLabel7.setForeground(new Color(209, 202, 202));
        jLabel7.setText("Defence: ");
        jLabel7.setAlignmentX(0.5F);
        jLabel7.setPreferredSize(new Dimension(27, 23));

        jLabel8.setBackground(new Color(128, 12, 12));
        jLabel8.setFont(customFont18);
        jLabel8.setForeground(new Color(209, 202, 202));
        jLabel8.setText("Accuracy: ");
        jLabel8.setAlignmentX(0.5F);
        jLabel8.setPreferredSize(new Dimension(27, 23));

        jLabel9.setBackground(new Color(128, 12, 12));
        jLabel9.setFont(customFont18);
        jLabel9.setForeground(new Color(209, 202, 202));
        jLabel9.setText("Evasion: ");
        jLabel9.setAlignmentX(0.5F);
        jLabel9.setPreferredSize(new Dimension(27, 23));

        jLabel10.setBackground(new Color(128, 12, 12));
        jLabel10.setFont(customFont18);
        jLabel10.setForeground(new Color(209, 202, 202));
        jLabel10.setText("Power: ");
        jLabel10.setAlignmentX(0.5F);
        jLabel10.setPreferredSize(new Dimension(27, 23));


        jLabel13.setFont(customFont18);
        jLabel13.setForeground(new Color(209, 202, 202));
        jLabel13.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel13.setText("Player1");

        jLabel14.setFont(customFont18);
        jLabel14.setForeground(new Color(209, 202, 202));
        jLabel14.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel14.setText("Player2");

        jButton1.setBackground(new Color(128, 12, 12));
        jButton1.setFont(customFont18);
        jButton1.setForeground(new Color(209, 202, 202));
        jButton1.setText("Step");

        jButton2.setBackground(new Color(128, 12, 12));
        jButton2.setFont(customFont18);
        jButton2.setForeground(new Color(209, 202, 202));
        jButton2.setText("Skip");


        jLabel15.setBackground(new Color(128, 12, 12));
        jLabel15.setFont(customFont13);
        jLabel15.setForeground(new Color(209, 202, 202));
        jLabel15.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel15.setText("Pokemon");

        jLabel16.setBackground(new Color(128, 12, 12));
        jLabel16.setFont(customFont13);
        jLabel16.setForeground(new Color(209, 202, 202));
        jLabel16.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel16.setText("Pokemon");

        jLabel18.setBackground(new Color(0, 0, 0));
        jLabel18.setText("");

        jLabel19.setBackground(new Color(0, 0, 0));
        jLabel19.setText("");


        jButton1.addActionListener(this::turn);
        jButton2.addActionListener(this::skip);

        jScrollPane3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        jTable3.setAutoCreateRowSorter(true);
        jTable3.setBackground(new Color(128, 12, 12));
       // jTable3.setBorder(new border.MatteBorder(null));
        jTable3.setFont(customFont18);
        jTable3.setForeground(new Color(209, 202, 202));
        Vector<String> headerVect = new Vector<>();
        headerVect.add("Log");

        logTableModel = new DefaultTableModel(headerVect, 0);
        jTable3.setModel(logTableModel);
        jTable3.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        jTable3.setName("");
        jTable3.setRowHeight(20);
        jTable3.setRowSelectionAllowed(false);
        jTable3.setSelectionForeground(new Color(128, 12, 12));
        jScrollPane3.setViewportView(jTable3);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel14, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel19, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel18, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(jScrollPane3))
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(13, 13, 13)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(jLabel11, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                                                .addComponent(jLabel12, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jLabel7, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(jLabel10, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jScrollPane4, GroupLayout.Alignment.TRAILING,

                                                                                GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel16, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(27, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addGap(40, 40, 40)
                                                .addComponent(jLabel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(jLabel15)
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel19, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel18, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane3,

                                                        GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)))
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
                    AttackResult ar = al.getResult();

                    oteam.get(al.getSubject()).health -= ar.damage;
                    team.get(al.getUser()).health += ar.heal;

                    if (ar.damage > 0) {
                        //damageAnimation(al.getResult().damage, l.getPlayer().equals(apid) ? bDamage : aDamage);
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
                    if(dl.getPlayer().equals(apid)) {
                        pokemon1TableModel.removeRow(0);
                    }
                    else
                        pokemon2TableModel.removeRow(0);

                    updateFighters(team);
                    break;
                case REPLACEMENT:
                    ReplacementLog rl = (ReplacementLog)l;
                    if(l.getPlayer().equals(apid)) {
                        curA = team.get(rl.getReplacement());

                    }
                    else {
                        curB = team.get(rl.getReplacement());
                    }

                    updateFighters(team);
                    break;
                case MESSAGE:
                    MessageLog ml = (MessageLog) l;
                    System.out.println(ml.getSubject().name + " said: \""+ml.message + "\"");
                default:
                    System.out.println();
            }
        }


        int logSize = res.get(turn).msgs.toString().split("\n").length;
        for(int i = 0 ; i < logSize ; i++) {

            logTableModel.insertRow(0,new Object[]{res.get(turn).msgs.toString().split("\n")[i]});
            System.out.println(res.get(turn).msgs.toString().split("\n")[i]);
        }

        if(pokemon1TableModel.getRowCount() == 0)
            for (FighterStats pokemon : fightersA.values()) {
                System.out.println(pokemon.name);
                pokemon1TableModel.addRow(new Object[]{
                        pokemon.name
                });
            }
        if(pokemon2TableModel.getRowCount() == 0)
            for (FighterStats pokemon : fightersB.values()) {
                pokemon2TableModel.addRow(new Object[]{
                        pokemon.name
                });
            }


        if(turn == res.size() - 1) {
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
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
            JOptionPane.showMessageDialog(null, "Battle finished. Winner is " + winner);

            frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.SELECT_OPPONENT_PAGE_SIZE));
            frame.setContentPane(oldPanel);
            frame.pack();
            AudioController.stopAudio();
            System.out.println("Battle finished. Winner is " + winner) ;
            updatePlayers();
        }
    }

    void skip(ActionEvent event){
        while(turn != res.size() - 1){
            turn(event);
        }

    }

    private void updatePlayers() {
        if(battleInfo.getPlayers().size() >0 ) {

            jLabel13.setText(battleInfo.getPlayers().get(0));
            if(battleInfo.getPlayers().size() >1)
                jLabel14.setText(battleInfo.getPlayers().get(1));
        }
    }


    private void updateFighters(Map<LogId, FighterStats> team) {
        List<LogId> stats = new LinkedList<>();

        stats.addAll(team.keySet());
        if(stats.size()>0) {
            jLabel15.setText(stats.get(0).name);
            if (stats.size() > 1)
                jLabel16.setText(stats.get(1).name);
        }

        if(team == fightersA) {
            try {
                jLabel18.setIcon(new ImageIcon(new ImageIcon(new URL(curA.img)).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            jLabel2.setText("Health: " + curA.health + "/" + curA.maxhealth);
            jLabel3.setText("Defence : " + curA.defence);
            jLabel4.setText("Accuracy: " + curA.accuracy);
            jLabel5.setText("Evasion: " + curA.evasion);
            jLabel6.setText("Power :" + curA.power);

        } else {
            try {
                jLabel19.setIcon(new ImageIcon(new ImageIcon(new URL(curB.img)).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }            jLabel12.setText("Health: " + curB.health  + "/" + curB.maxhealth);
            jLabel7.setText("Defence : " + curB.defence);
            jLabel8.setText("Accuracy: " + curB.accuracy);
            jLabel9.setText("Evasion: " + curB.evasion);
            jLabel10.setText("Power :" + curB.power);
        }
    }
}