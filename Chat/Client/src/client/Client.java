package client;
import java.io.*;
import java.net.*;
/**
 * @author Mariano Mazzucchi
 */
public class Client 
{
    public static void main(String[] args) throws IOException
    {
        if (args.length != 2)
        {
            System.err.println("Uso: java EchoClient <nome host> <numero porta>");
            System.exit(1);
        }
        String nomeHost = args[0];
        int numeroPorta = Integer.parseInt(args[1]);
        try
        (
            Socket socket = new Socket(nomeHost, numeroPorta);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String daServer;
                String daUtente;
                while((daServer = in.readLine()) != null)
                {
                    System.out.println("Server: " + daServer);
                    if(daServer.equals("Fine"));
                        break;
                    daUtente = stdIn.readLine();
                    if(daUtente != null)
                    {
                        System.out.println("Client: " + daUtente);
                        System.out.println(daUtente);
                    }
                }
        }
        catch(UnknowHostException e)
        {
            System.err.println("Non conosco l'host");
            System.exit(1);
        }
        catch(IOException e)
        {
            System.err.println("Non riesce a connettersi a: " + nomeHost);
            System.exit(1);
        }
    }

    private static class UnknowHostException {

        public UnknowHostException() {
        }
    }
}
