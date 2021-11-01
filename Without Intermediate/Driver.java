import java.net.*;
import java.io.*;


public class Driver {

	public static void main(String[] args) throws Exception {
		 
		Socket DriverNode = new Socket("LocalHost", 5858);
			
		 DataInputStream Driver_In = new DataInputStream(DriverNode.getInputStream());
         DataOutputStream Driver_Out = new DataOutputStream(DriverNode.getOutputStream());
        
	     System.out.println("Client attempting to connect to the Server Node... \n");
       
	     Driver_Out.writeUTF( "Hello, Connect Driver to the Server" );
	     
	     String Message = new String (Driver_In.readUTF());  	     
	     System.out.println(Message);
	     
	     if ( Message.equals("Connected") )
	     {
		     System.out.println("Server : "+ Message);
		     System.out.println("Driver wants Best Route ... \n");
		     Driver_Out.writeUTF("Get the Best Route" );     
		 }
		
	     Message = new String (Driver_In.readUTF());
	     
	     if (Message.equals( "Send Recommended Best Route" ) )
	     {
		     System.out.println("Server : "+ Message);
		     System.out.println("Driver now has the best route! \n");
	     }
	     
	     
	     System.out.println("Driver : Closing Connection \n" );	
	     Driver_In.close();
	     Driver_Out.close();
	     DriverNode.close();

	}

}
