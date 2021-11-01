import java.net.*;
import java.io.*;

public class Server {

	public static void main(String[] args)throws IOException
	{
		try {
		ServerSocket MyServer = new ServerSocket(5858);			
		System.out.println("Server ready...");
		
		while(true)
		{
			/* Wait and accept a connection a connection */
			Socket Client = MyServer.accept();
	        DataOutputStream DataOut = new DataOutputStream(Client.getOutputStream());		
		    DataInputStream DataIn = new DataInputStream(Client.getInputStream());	
            System.out.println("Assigning thread for this client \n");
            ClientHandler thread = new ClientHandler(Client, DataIn, DataOut);
            thread.start();			       
		} 
	
		
		}catch (IOException e) {	e.printStackTrace();	}
		
	}

}




class ClientHandler extends Thread{

	final DataInputStream DataIn;
	final DataOutputStream DataOut;
	final Socket client;
    

    public ClientHandler(Socket Client, DataInputStream DataIn, DataOutputStream DataOut) {
        this.client = Client;
        this.DataIn = DataIn;
        this.DataOut= DataOut;
    }
    
    @Override
    public synchronized void run(){
        
    	String Message;
        
        while(true)
        {
            try{
             
                Message = new String ( DataIn.readUTF() );

    		    /* Surveillance Node Connection */
    		    if ( Message.equals( "Hello, Connect Surveillance to the Server" ))
    		    {
    		    	System.out.println("Surveillance : " + Message);
    		    	DataOut.writeUTF("Connected");
    		    	System.out.println("Server : Connected to the Surveillance node ... \n");
    		    	
    		    	// ASK SURVEILLANCE NODE FOR DATA TO PROCESS
    		    	DataOut.writeUTF("Get Data");
    		    	System.out.println("Server : Get Data from the Surveilliance node ... \n");
    		    }
    		    
    		    /* Driver Node Connection */
    		    else if ( Message.equals( "Hello, Connect Driver to the Server" ))
    		    {
    		    	System.out.println("Driver : " + Message);
    		    	DataOut.writeUTF("Connected");
    		    	System.out.println("Server : Connected to the Driver node ... \n");
    		    }	
    		    
    		    
    		    /* Surveillance Node Data Transfer to Server */
    		    else if ( Message .equals(  "Transfer Data Collected" ))
    		    {
    		    	System.out.println("Surveillance : " + Message);
    		    	System.out.println("Server: Received Data \n");
    		    }
    		    
    		    	    
    		     /* Reccemonding the best route */ 		    
    		    else if( Message .equals( "Get the Best Route"))
    		    {
        		    /* Sending Reccemondation to the driver node */
    		    	System.out.println("Driver : " + Message);
    		    	System.out.println("Server: Send Recommended Best Route \n");
    		    	DataOut.writeUTF("Send Recommended Best Route");
    		    }
    		    
    		    
            } catch(EOFException e) {
                break;
            }
             catch (IOException e) {
             e.printStackTrace();
             }
       }
        
        try
        {    	               
            DataOut.close();
            DataIn.close();
            client.close();
        }
        catch(EOFException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
              
}






