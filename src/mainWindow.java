import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainWindow extends Application{
	
	Stage window;
	
	static int portEnteredInField; 
	static String ipEnteredInField;
	 
	
	public static void main(String [] args){
		launch(args);
	}
	@Override public void start(Stage primaryStage){
		window = primaryStage;
		primaryStage.setTitle("Main");
		
		TextArea activity = new TextArea();
		activity.setPrefSize(350, 350);
		
		
		TextField ipTextField = new TextField();
		Label ipLabel = new Label();
		ipLabel.setText("IP Address");
		
		TextField portTextField = new TextField();
		Label portLabel = new Label();
		portLabel.setText("Port");
		
		Button startChat = new Button();
		startChat.setText("START CHAT");
		startChat.setOnAction(e -> {
			
		//Taking input for IP text field____________________
		ipEnteredInField = ipTextField.getText();
		//Clear IP Text Field Area__________________________
		ipTextField.clear();
		//Taking input for port text field__________________
		portEnteredInField = Integer.valueOf(portTextField.getText());
		//Clear Port Text Field Area__________________________________
		portTextField.clear();
		//textingWindow.display(" IP: " + ipEnteredInField + " and Port: " + portEnteredInField);

		
	    
		textingWindow texting_window = new textingWindow();
		texting_window.display(" IP: " + ipEnteredInField + " and Port: " + portEnteredInField);
		
						
			});
		

		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(5));
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.getChildren().addAll(activity,ipLabel,ipTextField,portLabel,portTextField,startChat);
		
		
		Scene scene = new Scene(layout, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//________________GETTERS____________________
	public int getPortEnteredInField() {
		return portEnteredInField;
	}
	public String getIpEnteredInField() {
		return ipEnteredInField;
	}
	//________________SETTERS__________________________________
	public void setIpEnteredInField(String ip) {
		ipEnteredInField = ip;
	}
	public void setPortEnteredInField(int port) {
		portEnteredInField = port;
	}
	
	}
	
	

