package chatconthread;
/*
 *Author Mazzucchi Mariano
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ClientConThreadDentico
{
	Socket conn;
    BufferedReader ingresso;
    PrintStream uscita;
    OutputStream out;
    BufferedReader tastiera;
    public ClientConThreadDentico() 
	{
        try 
		{
            conn = new Socket("localhost", 54444);
            ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = conn.getOutputStream();
            uscita = new PrintStream(conn.getOutputStream(), true);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) 
		{
            System.out.println("IO Exception");
        }
    }
    public void Spedisci() 
	{
        String messaggio = "";
        System.out.println("Inserisci il messaggio da inviare al server, per interrompere le comunicazioni scrivere la parola 'end' \n\t");
        while (true) 
		{
            try 
			{
                messaggio = tastiera.readLine();
                System.out.println(messaggio);
            } catch (IOException ex) 
			{
                Logger.getLogger(ClientConThreadDentico.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (messaggio.equals("Esci")||messaggio.equals("ESCI")||messaggio.equals("esci")) 
			{
                System.out.println("Chiusura connessione...");
                try 
				{
                    conn.close();
                    break;
                } catch (IOException ex) 
				{
                    System.out.println("Errore nella chisura delle comunicazioni");
                    Logger.getLogger(ClientConThreadDentico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            uscita.println(messaggio);
            try 
			{
                System.out.println("DAL SERVER: " + ingresso.readLine());
                uscita.flush();
            } catch (IOException ex) 
			{
                Logger.getLogger(ClientConThreadDentico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void main(String[] args) throws IOException 
	{
        ClientConThreadDentico c = new ClientConThreadDentico();
        c.Spedisci();
    }
}
