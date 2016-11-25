package chatconthread;
/*
 *Author Mazzucchi Mariano
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerConThreadDentico 
{
	public static void main(String[] args) throws IOException 
	{
        final int porta = 54444;
        System.out.println("Server in attesa di connessione sulla porta " + porta);
        ServerSocket serversocket = new ServerSocket(porta);
        Socket clientSocket;
        int i = 1;
        while (true) {
            clientSocket = serversocket.accept();
            System.out.println("Il client " + i + " collegato sul indirizzo " + clientSocket.getInetAddress() + " e sulla porta " + clientSocket.getPort());
            ServerThread server = new ServerThread(i, clientSocket);
            Thread thread = new Thread(server);
            i++;
            thread.start();
        }
    }
}
class ServerThread implements Runnable 
{
    Socket clientSocket;
    int contatore;
	public ServerThread(int contatore, Socket socket) 
	{
        this.clientSocket = socket;
        this.contatore = contatore;
    }
    @Override
    public void run() 
	{
        RecieveFromClientThread recieve = new RecieveFromClientThread(this.clientSocket, this.contatore);
        Thread riceviMessaggio = new Thread(recieve);
        riceviMessaggio.start();
    }
}
class RecieveFromClientThread implements Runnable 
{
    Socket clientSocket = null;
    BufferedReader input = null;
    ServerConThreadDentico s;
    int contatore;
    PrintWriter pwPrintWriter;

    public RecieveFromClientThread(Socket Socket, int contatore) {
        this.contatore = contatore;
        this.clientSocket = Socket;
        s = new ServerConThreadDentico();
    }
    @Override
    public void run() 
	{
        try 
		{
            input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            pwPrintWriter = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            String messaggioStringa;
            String msgToClientString = "messaggio ricevuto";
            while (true) 
			{
                while ((messaggioStringa = input.readLine()) != null) 
				{
                    if (messaggioStringa.equals("ESCI") || messaggioStringa.equals("Esci") || messaggioStringa.equals("esci")) 
					{
                        break;
                    }
                    System.out.println("Messaggio dal client " + this.contatore + ": " + messaggioStringa);
                    pwPrintWriter.println(msgToClientString);
                    pwPrintWriter.flush();
                }
                this.clientSocket.shutdownOutput();
                this.clientSocket.shutdownInput();
            }
        } catch (IOException ex) 
		{
            System.out.println(ex.getMessage());
        }
    }
}
