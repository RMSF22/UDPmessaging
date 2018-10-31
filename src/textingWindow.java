import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class textingWindow {
	
	Socket socket;
	TextArea area = new TextArea();
	int usePort;
	InetAddress destinationIP;
	int destPort;
	
	
	public textingWindow(int usePort, InetAddress destIP, int destPort) {
		this.usePort = usePort;
		socket = new Socket(this.usePort, this);
		this.destinationIP = destIP;
		this.destPort = destPort;
		
		Thread newMessage = new Thread( () -> { 
			DatagramPacket packet;
			do{
				packet = socket.receive();
				if(packet != null){
					String message = new String(packet.getData());
					System.out.println("TEXTING WINDOW NEW MESSAGE" + message);
					Platform.runLater(() -> area.appendText(" PORT = " + destPort + 
							" IP = " + destinationIP + " SAID: " + message + "\n"));	
				}
				
			}while(true);
			
		});
		
		newMessage.start();
		
	}
	
	public void display(String title){
		Stage window = new Stage();
		window.setTitle(title);
		
		/*Code for TextArea where users messages should appear*/
		area = new TextArea();
		area.setPrefSize(350, 350);
		area.setEditable(false);
		/*_______________________________________________________________*/
		
		/*Code for the TextField where user type message*/
		TextField messageInput = new TextField();
		
		/*_______________________________________________________________*/
		
		/*Code for button that closes window*/
		Button btnClose =  new Button();
			btnClose.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9;");
			btnClose.setText("CLOSE");
			btnClose.setOnAction(e -> {
				window.close();
			});
		/*_______________________________________________________________*/
			
		/*LABEL TO PROVIDE SENDER'S PORT NUMBER*/		
		Label portNotification = new Label();
		portNotification.setText(" Sender's Port is : " + this.usePort 
				+" Destination port -----> "+ destPort);
		/*________________________________________________________________*/
		
		/*Code for the button that send messages
		 * When the send button is clicked, it will take the input and save it into a String.
		 * The send method from the Socket class will take that message.
		 * */
		Button btnSend = new Button();
		btnSend.setDefaultButton(true);
			btnSend.setText("SEND");
			btnSend.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9;");
			btnSend.setOnAction(e -> {
			String message = messageInput.getText().toString();
			messageInput.clear();
			area.appendText(" ME: " + message + "\n");

			socket.send(message, destinationIP, destPort);
			
			try{
				TimeUnit.SECONDS.sleep(1);
			}catch(Exception a){
				a.printStackTrace();
				System.exit(-1);
			}});
	
		/*Layout utilized to place components together*/
		VBox layout = new VBox();
			layout.setSpacing(10);
			layout.setPadding(new Insets(5));
			layout.setAlignment(Pos.CENTER_LEFT);
			layout.getChildren().addAll(area,messageInput,btnSend,btnClose,portNotification);
		/*__________________________________________________________________*/
		
		/*This makes the scene visible*/
		Scene scene = new Scene(layout, 500, 500);
		window.setScene(scene);
		window.show();
		}
		/*METHOD TO APPEND MESSAGES ON THE TEXT AREA FROM A METHOD IN THE SOCKET CLASS*/
		public void addToTextA(String msg) {
		area.appendText(msg);
		}}
