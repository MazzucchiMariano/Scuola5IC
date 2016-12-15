package client;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Mariano Mazzucchi
 */
public class Server 
{
    public static void main(String[] args) throws IOException
    {
        if(args.length != 1)
        {
            System.err.println("Uso: java Server <numero porta>");
            System.exit(1);
        }
        int numeroPorta = Integer.parseInt(args[0]);
        ServerSocket socket = new ServerSocket(numeroPorta);
	while (true) 
        {
		final Socket connection = socket.accept();
		Runnable task = new Runnable() {
		public void run() 
                {
                    try (
                            PrintWriter out= new PrintWriter(connection.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));) 
                            {
                                String inputLine, outputLine;
				BotProtocol bp = new BotProtocol();
				outputLine = bp.processInput(""); 
				out.println(outputLine);
                                while ((inputLine = in.readLine()) != null) 
                                {
                                    outputLine = bp.processInput(inputLine);
                                    out.println(outputLine);
                                    if (outputLine.equals("Bye.")) 
                                    {
					break;
                                    }
				}
                            } catch (IOException e) 
                            {
				System.out.println("Eccezione intercettata durante l'ascolto sulla porta "+ numeroPorta+ " o ascolto una connessione");
				System.out.println(e.getMessage());
                            }
		}
		};
		new Thread(task).start();
	}
    }
}
