package client;
import java.net.*;
import java.io.*;
/**
 * @author Mariano Mazzucchi
 */
public class BotProtocol 
{
    private static final int PRESENTAZIONE = 0;
    private static final int DOMANDA = 4;
    private static final int RISPOSTA = 1;
    private static final int ANOTHER = 2;
    private int state = PRESENTAZIONE;
    private int currentJoke = 0;
    private String[] domande = {"Come stai?", "Qual'è il tuo colore preferito?", "Cosa ti piace?", "Che ore sono?"};
    private String[] risposte = {"Io sto bene grazie!", "Mi piace molto il verde.", "Non sono una persona mi dispiace.", "L'estate"};
    public String processInput(String theInput) 
    {
        String theOutput = null;
        if (state == PRESENTAZIONE) 
        {
            theOutput = "Ciao sono Mr. Robots, e sono un genio che risponde alle domande(prefissate prima) XD XD";
            state = DOMANDA;
        } else if (state == DOMANDA)
        {
            if (theInput.equalsIgnoreCase("Ciao Mr.Robots") || theInput.equalsIgnoreCase("Ciao")) 
            {
                theOutput="Domanda qualcosa";
                state = RISPOSTA;
            } else
            {
                theOutput = "Dovresti dire \"buongiorno messere può rispondere a qualche domanda\" cioè permetti";
                state = DOMANDA;
            }

        } else if (state == RISPOSTA) 
        {
            for (int i = 0; i < domande.length; i++) 
            {
                if (domande[i].equalsIgnoreCase(theInput)) 
                {
                    theOutput = risposte[i]+" C'è qualcos'altro? (S/N)";
                    state = ANOTHER;
                }
            }
            if (state != ANOTHER) 
            {
                theOutput = "Scusa non sono abilitato a rispondere, qualcos'altro? (S/N)";
                state = ANOTHER;
            }
        } else if (state == ANOTHER) 
        {
            if (theInput.equalsIgnoreCase("S")) 
            {
                theOutput="Doamda ciò che ti vol more";
                state = RISPOSTA;
            } else 
            {
                theOutput = "Arrivederci";
                state = PRESENTAZIONE;
            }
        }
        return theOutput;
    }
}
