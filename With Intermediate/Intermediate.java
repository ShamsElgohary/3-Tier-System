import java.net.*;
import java.io.*;

public class Intermediate {

	public static void main(String[] args) throws Exception {        
		
		try {
			ServerSocket IntermediateServer = new ServerSocket(5858);
			Socket ClientSocket = new Socket("LocalHost", 2222);
			System.out.println("Intermediate Server Ready");
	  		
		while(true)
		{
			/* Wait and accept a connection a connection */
			Socket Client = IntermediateServer.accept();
	        DataOutputStream DataOut = new DataOutputStream(Client.getOutputStream());		
		    DataInputStream DataIn = new DataInputStream(Client.getInputStream());	
            System.out.println("Assigning thread for this client \n");
            IntermediateHandler thread = new IntermediateHandler(Client, ClientSocket, DataIn, DataOut);
            thread.start();			       
		} 
		
		}catch (IOException e) {	e.printStackTrace();	}		

	}

}


class IntermediateHandler extends Thread{

	DataInputStream  ClientDataIn, ServerDataIn;
	DataOutputStream ClientDataOut, ServerDataOut;
	private Socket client;
    private Socket server;

    public IntermediateHandler(Socket server, Socket client, DataInputStream DataIn, DataOutputStream DataOut) {
        this.server = server;
    	this.client = client;
        this.ClientDataIn = DataIn;
        this.ClientDataOut= DataOut;
    }
    

    @Override
    public synchronized void run(){
        String ServerMessage = null;
        String ClientMessage = null;
        try{
         ServerDataIn = new DataInputStream(client.getInputStream());
         ServerDataOut = new DataOutputStream(client.getOutputStream());
        }
         catch(Exception e){}
        while(true)
        {
            try {
                try{
                    sleep(1500); 
                    }
                    catch(Exception e){}
                
                if ( ClientDataIn.available() > 0)
                	ClientMessage = ClientDataIn.readUTF();                
                else                
                	ClientMessage = null;
                

                if (ClientMessage != null)
                {
                	ServerDataOut.writeUTF(ClientMessage);
                    System.out.println(ClientMessage);
                }
                    
                
                if ( ServerDataIn.available() > 0)
                	ServerMessage = ServerDataIn.readUTF();
                else                
                	ServerMessage = null;
                
                
                if (ServerMessage != null)
                {
                	ClientDataOut.writeUTF(ServerMessage);
                    System.out.println( ServerMessage );
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try
        {
            this.ClientDataIn.close();
            this.ClientDataOut.close();
        }
        catch(EOFException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        } 
    }
    
              
}