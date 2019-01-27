package JavaTournament;

import bgs99c.client.*;
import bgs99c.shared.UserStats;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScreenController {
	static Client client;
	/*private final ObservableList<PlayerModel> data =
	        FXCollections.observableArrayList();*/
	public ScreenController() {
	}
	private void updateConnectionStatus() {
		//connectionStatus.setText((client.checkConnection())? Main.rb.getString("Connected") : Main.rb.getString("Disconnected"));
	}
	private boolean inited = false;
	private String selectedPlayer;
	private Label connectionStatus;
	private Label teamName;
	private Button tour;
	private Button battleBtn;
	/*private TableView<PlayerModel> table;
	private TableColumn<PlayerModel,String> nameColumn;
	private TableColumn<PlayerModel, Integer> rankColumn, scoreColumn;
	private TableColumn<PlayerModel, Double> aRankColumn;
	private ListView<VBox> detailsList;*/
	public void init() {
		if(inited) return;
	}
	
	public void selectFile() throws IOException {
		/*FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Java libraries", "*.jar"));
		File file = fileChooser.showOpenDialog(window);
		if(file==null)
			return;
		client.sendFile(file);*/
	}
	
	private void startTournament() throws IOException, ClassNotFoundException {
		client.declareTournament();
		//Main.createScene((AnchorPane)null, "BattleScreen.fxml");
	}
	
	private void startBattle() throws IOException, ClassNotFoundException {
		client.declareBattle(selectedPlayer);
		//Main.createScene((AnchorPane)null, "BattleScreen.fxml");
	}
	
	public void initialize() throws IOException, ClassNotFoundException {
		/*nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		aRankColumn.setCellValueFactory(new PropertyValueFactory<>("aRank"));
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	selectedPlayer = newSelection.getName();
		    	battleBtn.setDisable(selectedPlayer.equals(client.name));
		    	teamName.setText(Main.rb.getString("Team") + " " + newSelection.getName());
		    	detailsList.setItems(newSelection.showFighters());
		    } else {
		    	battleBtn.setDisable(true);
		    }
		});
		
		UserStats[] stats = client.getStats();
		for(UserStats stat : stats) {
			data.add(new PlayerModel(stat.name, stat.lastpos, stat.score, stat.avgpos, stat.fighters));
		}
		table.setItems(data);

		ScheduledExecutorService scheduler =
			     Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> updateConnectionStatus(), 2, 2, TimeUnit.SECONDS);
		BattleController.client = client;*/
	}
	
}
