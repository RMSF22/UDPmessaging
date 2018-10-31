import java.net.InetAddress;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainWindow extends Application{
	
	Stage window;
	int portEnteredInField; 
	String ipEnteredInField;
	int curPort = 10000;
	
	public static void main(String [] args){
		launch(args);
	}
	
	 public void start(Stage primaryStage){
		window = primaryStage;
		primaryStage.setTitle("Main");
		
		/*TEXTFIELD WHERE USER ENTERS IP*/
		TextField ipTextField = new TextField();
		/*____________________________________________*/
		/*LABEL FOR IP*/
		Label ipLabel = new Label();
		ipLabel.setText("IP Address");
		/*_______________________________________________*/
		/*TEXTFIELD WHERE USER ENTERS PORT NUMBER*/
		TextField portTextField = new TextField();
		/*________________________________________________*/
		/*LABEL FOR PORT*/
		Label portLabel = new Label();
		portLabel.setText("Port");
		/*__________________________________________________*/
		/*BUTTON THAT TRIGGERS MESSAGING WINDOW*/
		Button startChat = new Button();
		startChat.setText("START CHAT");
		startChat.setOnAction(e -> {
			
		/*Taking input from IP text field*/
		ipEnteredInField = ipTextField.getText();
		/*______________________________________________________*/
		/*Clear IP Text Field Area*/
		ipTextField.clear();
		/*______________________________________________________*/
		/*Taking input from Port text field*/
		portEnteredInField = Integer.valueOf(portTextField.getText());
		/*______________________________________________________*/
		/*Clear Port Text Field Area*/
		portTextField.clear();
		/*______________________________________________________*/
		
		/*TRIGGERS MESSAGING WINDOW AND ADDS USER IP AND PORT TO THE TITLE
		 * -TAKES THE PORT AND IP ENTERED BY USER AS WELL AS THE CURRENT PORT  
		 * */
		try{
			textingWindow texting_window = new textingWindow(curPort++,InetAddress.getByName(ipEnteredInField),portEnteredInField);
			texting_window.display(" IP: " + ipEnteredInField + " and Port: " + portEnteredInField);
		}catch(Exception a){
			a.printStackTrace();
		}
		/*__________________________________________________*/		
			});
		
		/*LAYOUT FOR THE MAIN WINDOW*/
		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(5));
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.getChildren().addAll(ipLabel,ipTextField,portLabel,portTextField,startChat);
		/*___________________________________________________________________________________*/
		
		Scene scene = new Scene(layout, 200, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//________________GETTERS FOR IP ENTERED BY USER____________________
	public int getPortEnteredInField() {
		return portEnteredInField;
	}
	public String getIpEnteredInField() {
		return ipEnteredInField;
	}
	//________________SETTERS FOR PORT ENTERED BY USER___________________
	public void setIpEnteredInField(String ip) {
		ipEnteredInField = ip;
	}
	public void setPortEnteredInField(int port) {
		portEnteredInField = port;
	}
	
	}
	
	

