package JavaTournament;

import bgs99c.client.*;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainController{
	Client client = new Client();
	public void connect() {
		/*window.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent arg0) {
				try {
					client.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			
		});*/
	}
	
	public MainController(){

	}
	private JComboBox<Locale> comboBox;
	private Button button;
	private TextField passField;
	private TextField loginField;
	private Window window;
	private boolean inited = false;
	public void init() {
		if(inited) return;
		connect();
	}
	public void login() throws IOException {
		init();
		String name = loginField.getText(), pass = passField.getText();
		/*Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Login refused");*/
		try {
			if(!client.init(name, pass)) {
				//alert.setContentText("Connection to helios refused");
				//alert.show();
				return;
			}
		} catch(Exception e) {
			//alert.setContentText("Connection failed");
			//alert.show();
			return;
		}
			
		if(client.login(name, pass)) {
			//Main.switchScene((AnchorPane)null, "MainScreen.fxml");
		} else {
			//alert.setContentText("Wrong login/password!");
			//alert.show();
		}
	}
	public void initialize() {
		ScreenController.client = this.client;
	    //ObservableList<Locale> options = FXCollections.observableArrayList(new Locale("en", "US"), new Locale("ru", "RU"));
	    //comboBox.setItems(options);
	    /*comboBox.setConverter(new StringConverter<Locale>() {
	      @Override
	      public String toString(Locale object) {
	        return object.getDisplayLanguage();
	      }
	 
	      @Override
	      public Locale fromString(String string) {
	        return null;
	      }
	    });
	    comboBox.getSelectionModel().selectFirst();
	 
	    comboBox.setOnAction(event -> updateLocale());*/
	}
	private void updateLocale() {
		/*System.out.println(comboBox.getSelectionModel().getSelectedItem());
		Main.rb = ResourceBundle.getBundle("Bundle", comboBox.getSelectionModel().getSelectedItem());
		System.out.println(Main.rb.getString("lprompt"));
		loginField.setPromptText(Main.rb.getString("lprompt"));
		passField.setPromptText(Main.rb.getString("pprompt"));
		button.setText(Main.rb.getString("login"));*/
	}
}
