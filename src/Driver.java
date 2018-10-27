import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Driver {
	
	static mainWindow a = new mainWindow();
	static textingWindow b = new textingWindow();
	
	public static void main(String [] args){
		Socket socket = new Socket(64000);
		//InetAddress myAddress = null;
		InetAddress professor = null;
		
		
		try{
			//myAddress = InetAddress.getLocalHost(); 
			//System.out.print(myAddress);
			professor = InetAddress.getByName("172.20.10.2");
			//System.out.print(" Port Entered by me: " + a.port +" Entered IP by me: " + professor);
			
			
		} catch (Exception e){
			e.printStackTrace();
			System.exit(-1);
			
		}
		socket.send("hi", professor, 64000);
		
		
		
//		socket.send("Hi, this is a message", myAddress, 64000);
//		socket.send("Hello!", myAddress, 64000);
//		socket.send("How Are You?", myAddress, 64000);
//		socket.send("I Am having fun writing code", myAddress, 64000);
//		socket.send("What are you doing?", myAddress, 64000);
//		socket.send("Are you having fun?", myAddress, 64000);
		
		try{
			System.out.println(" Main is sleeping ");
			TimeUnit.SECONDS.sleep(5);
			System.out.println("Main woke up");
		}catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
		
	
	DatagramPacket packet;
	
	do{
		packet = socket.receive();
		if(packet != null){
			String message = new String(packet.getData());
			System.out.println("Message From Port: "+ 64000 +" IP: " + professor +" "+ message);
		}
		
	}while(packet != null);
		System.out.println("That's it folks!");

}}
