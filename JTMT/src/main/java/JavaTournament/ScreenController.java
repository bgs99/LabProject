package JavaTournament;

import bgs99c.client.*;
import bgs99c.shared.UserStats;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.awt.GridBagConstraints.*;


public class ScreenController {
	private Client client;
	private JFrame frame;
	/*private final ObservableList<PlayerModel> data =
	        FXCollections.observableArrayList();*/
	ScreenController(Client client, JFrame frame) {
		this.client = client;
		this.frame = frame;
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = WEST;
        c.fill = BOTH;
        c.gridx = 0;
        c.gridy = RELATIVE;


        JTable table = new JTable(data);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0) {
                    battleBtn.setEnabled(false);
                    return;
                }
                int i = selectedRows[0];
                selectedPlayer = data.getRow(i).getName();
                battleBtn.setEnabled(!selectedPlayer.equals(client.name));
                teamName.setText("Team " + selectedPlayer);
                //TODO fill detailsList
            }
        });

        UserStats[] stats = new UserStats[0];
        try {
            stats = client.getStats();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(UserStats stat : stats) {
            data.addRow(new PlayerModel(stat.name, stat.lastpos, stat.score, stat.avgpos, stat.fighters));
        }


        panel1.add(table, c);

        teamName = new Label("");
        panel1.add(teamName, c);

        battleBtn = new Button("Battle");
        battleBtn.setEnabled(false);
        battleBtn.addActionListener(this::startBattle);
        panel1.add(battleBtn, c);

        tour = new Button("Tournament");
        tour.addActionListener(this::startTournament);
        panel1.add(tour, c);

        connectionStatus = new Label("Connected");
        panel1.add(connectionStatus, c);

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateConnectionStatus, 2, 2, TimeUnit.SECONDS);
	}
	private void updateConnectionStatus() {
		connectionStatus.setText(client.checkConnection() ? "Connected" : "Disconnected");
	}

	private String selectedPlayer = null;
	private Label connectionStatus;

	private Label teamName;
	private Button tour;
	private Button battleBtn;
	private PlayerTableModel data = new PlayerTableModel();
	
	public void selectFile() throws IOException {
		/*FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Java libraries", "*.jar"));
		File file = fileChooser.showOpenDialog(window);
		if(file==null)
			return;
		client.sendFile(file);*/
	}
	
	private void startTournament(ActionEvent event){
        try {
            client.declareTournament();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new BattleController(client, frame);
	}
	
	private void startBattle(ActionEvent event) {
        try {
            client.declareBattle(selectedPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new BattleController(client, frame);
	}
	
}
