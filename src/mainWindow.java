import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainWindow extends Application {

	/* ______________________________ */
	Stage window;
	int portEnteredInField;
	String ipEnteredInField;
	int curPort = 10000;
	/* ______________________________ */

	Socket socket = new Socket(32000);
	ConcurrentHashMap<String, textingWindow> hashMap = new ConcurrentHashMap<String, textingWindow>();
	textingWindow incoming;

	public static void main(String[] args) {
		launch(args);
	}

	public mainWindow() {

		listeningForIncoming();
	}

	public void start(Stage primaryStage) {
		window = primaryStage;
		primaryStage.setTitle("Main");

		/* TEXTFIELD WHERE USER ENTERS IP */
		TextField ipTextField = new TextField();
		/* ____________________________________________ */
		/* LABEL FOR IP */
		Label ipLabel = new Label();
		ipLabel.setText("IP Address");
		/* _______________________________________________ */
		/* TEXTFIELD WHERE USER ENTERS PORT NUMBER */
		TextField portTextField = new TextField();
		/* ________________________________________________ */
		/* LABEL FOR PORT */
		Label portLabel = new Label();
		portLabel.setText("Port");
		/* __________________________________________________ */
		/* BUTTON THAT TRIGGERS MESSAGING WINDOW */
		Button startChat = new Button();
		startChat.setText("START CHAT");
		startChat.setOnAction(e -> {

			/* Taking input from IP text field */
			ipEnteredInField = ipTextField.getText();
			/* ______________________________________________________ */
			/* Clear IP Text Field Area */
			ipTextField.clear();
			/* ______________________________________________________ */
			/* Taking input from Port text field */
			portEnteredInField = Integer.valueOf(portTextField.getText());
			/* ______________________________________________________ */
			/* Clear Port Text Field Area */
			portTextField.clear();
			/* ______________________________________________________ */

			/*
			 * TRIGGERS MESSAGING WINDOW AND ADDS USER IP AND PORT TO THE TITLE
			 * -TAKES THE PORT AND IP ENTERED BY USER AS WELL AS THE CURRENT
			 * PORT
			 */
			try {
				textingWindow texting_window = new textingWindow(curPort++, InetAddress.getByName(ipEnteredInField),
						portEnteredInField);
				
				texting_window.display(" IP: " + ipEnteredInField + " and Port: " + portEnteredInField);

			} catch (Exception a) {
				a.printStackTrace();
			}

			/* __________________________________________________ */
		});

		/* LAYOUT FOR THE MAIN WINDOW */
		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(5));
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.getChildren().addAll(ipLabel, ipTextField, portLabel, portTextField, startChat);
		/* ___________________________________________________________________________________ */

		Scene scene = new Scene(layout, 250, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// ________________GETTERS FOR IP ENTERED BY USER____________________
	public int getPortEnteredInField() {
		return portEnteredInField;
	}

	public String getIpEnteredInField() {
		return ipEnteredInField;
	}

	// ________________SETTERS FOR PORT ENTERED BY USER___________________
	public void setIpEnteredInField(String ip) {
		ipEnteredInField = ip;
	}

	public void setPortEnteredInField(int port) {
		portEnteredInField = port;
	}

	/*
	 * THIS METHOD ALLOWS THE MAIN WINDOW CLASS TO CONSTANTLY RECEIVE INCOMING
	 * MESSAGES IF THE PACKET IS NOT EMPTY, THEN THE PORT AND THE IP OF THE
	 * SENDER WILL BE STORE AS A KEY VALUE, TO BE SAVED IN A HASHMAP AND AVOID
	 * CREATING THE SAME WINDOW EVERY TIME THE RECIPIENT REPLIES BACK.
	 * THIS METHOD IS CALLED INSIDE THE MAIN WINDOW CONSTRUCTOR.
	 */
	public void listeningForIncoming() {
		Thread newMessage = new Thread(() -> {

			do {
				DatagramPacket packet = socket.receive();

				if (packet != null) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							/*CHECKS IF INCOMING MESSAGE IP AND PORT DOES NOT EXIST INSIDE HASHMAP, IF TRUE, 
							 * OPEN NEW WINDOW TO START CHATTIN*/
							if (!hashMap.containsKey(packet.getPort() + packet.getAddress().toString())) {

								String message = new String(packet.getData());
								incoming = new textingWindow(socket, packet.getAddress(), packet.getPort());
								incoming.displayIncome(" PORT: " + packet.getPort() + " IP: " + packet.getAddress());
								incoming.area.appendText(" PORT: " + packet.getPort() + " AND IP: "
										+ packet.getAddress() + " SAID: " + message + "\n");
								
								/*ADD THE IP AND PORT INTO THE HASHMAP TO AVOID RE-OPENING AN EXISTING CHAT*/
								hashMap.put(packet.getPort() + packet.getAddress().toString(), incoming);
								System.out.println(" MY KEY FOR THE HASHMAP: " + packet.getPort()
										+ packet.getAddress().toString() + "\n");

							} else {
								/*GET THE SAVED CHAT, AND APPEND MESSAGE TO THAT CHAT*/
								textingWindow insideHashMap = hashMap.get(packet.getPort() + packet.getAddress().toString());
								String message = new String(packet.getData());
								System.out.println("ALREADY OPENED");
								insideHashMap.area.appendText(" PORT: " + packet.getPort() + " AND IP: " + packet.getAddress()
										+ " SAID: " + message + "\n");

							}
						}
					});
				}
			} while (true);

		});

		newMessage.start();

	}
}
