import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class textingWindow {

	
	mainWindow main_window = new mainWindow();
	Socket socket = new Socket(main_window.getPortEnteredInField());
	
	public void display(String title){
		Stage window = new Stage();
		window.setTitle(title);
		
		//Code for TextArea where users messages should appear___________
		TextArea area = new TextArea();
		area.setPrefSize(350, 350);
		//_______________________________________________________________
		
		//Code for the TextField where user type message_________________
		TextField messageInput = new TextField();
				
		//_______________________________________________________________
		
		
		//Code for button that closes window_____________________________
		Button btnClose =  new Button();
			btnClose.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9;");
			btnClose.setText("CLOSE");
			btnClose.setOnAction(e -> window.close());
		//_______________________________________________________________
			

		//Code for the button that send messages______________________________
		Button btnSend = new Button();
			btnSend.setText("SEND");
			btnSend.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9;");
			btnSend.setOnAction(e -> {
			String message = messageInput.getText().toString();
			System.out.println(main_window.getIpEnteredInField() + " and " 
			+ main_window.getPortEnteredInField());

			
		InetAddress address = null;
			try{
				address = InetAddress.getByName(main_window.getIpEnteredInField());
				
			} catch (Exception a){
				a.printStackTrace();
				System.exit(-1);
				
			}
			
			socket.send(message, address, main_window.getPortEnteredInField());
			try{
				System.out.println(" Main is sleeping ");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Main woke up");
			}catch(Exception a){
				a.printStackTrace();
				System.exit(-1);
			}
			
	
		DatagramPacket packet;
		do{
			packet = socket.receive();
			if(packet != null){
				String message_ = new String(packet.getData());
				area.appendText("Message From Port: "+ main_window.getPortEnteredInField() +" IP: " + address +" "+ message_ + "\n");
			}
			
			
		}while(packet != null);
			System.out.println("That's it folks!");

			});
			
		//_______________________________________________________________

		//Layout utilized to place components together______________________
		VBox layout = new VBox();
			layout.setSpacing(10);
			layout.setPadding(new Insets(5));
			layout.setAlignment(Pos.CENTER_LEFT);
			layout.getChildren().addAll(area,messageInput,btnSend,btnClose);
		//__________________________________________________________________
		
		//_____This makes the scene visible_________________________________
		Scene scene = new Scene(layout, 500, 500);
		window.setScene(scene);
		window.show();
	}
}
