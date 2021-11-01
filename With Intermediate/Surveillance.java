import java.net.*;
import java.io.*;

public class Surveillance {

	public static void main(String[] args) throws Exception {
		
		 Socket SurveillanceNode = new Socket("LocalHost", 5858);		
		 DataInputStream Surveillance_In = new DataInputStream(SurveillanceNode.getInputStream());
         DataOutputStream Surveillance_Out = new DataOutputStream(SurveillanceNode.getOutputStream());
         
	     System.out.println("Client attempting to connect to the Server Node... ");   
	     Surveillance_Out.writeUTF( "Hello, Connect Surveillance to the Server" );
	     
	     String Message = new String (Surveillance_In.readUTF());   
	     if (Message.equals("Connected"))
	     {
		     System.out.println("Server : "+ Message + "\n");	 	     
		 }
		
	     
	     Message = new String (Surveillance_In.readUTF());
	     if (Message.equals("Get Data"))
	     {
		     System.out.println("Server : "+ Message);			     
		     Surveillance_Out.writeUTF("Transfer Data Collected");
		     System.out.println("Surveillance : Transfer Data Collected \n" );	 	
	     }	     
	     
	     System.out.println("Surveillance : Closing Connection \n" );
	     Surveillance_In.close();
	     Surveillance_Out.close();
	     SurveillanceNode.close();
	}

}