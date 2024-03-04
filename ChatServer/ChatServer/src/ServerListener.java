import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServersListener implements Runnable{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;

    // Stores the which player this listener is for
    private char chatter;

    // static data that is shared between both listeners
    private static char turn = 'Y';
    private static GameData gameData = new GameData();
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    //do i need the arrayList for Connect4 too?
    private static boolean restar[]= {false, false};



    public ServersListener(ObjectInputStream is, ObjectOutputStream os, char chatter) {
        this.is = is;
        this.os = os;
        this.chatter = chatter;
        outs.add(os);
    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                CommandFromClient cfc = (CommandFromClient) is.readObject();

                //quit

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }




    public void sendCommand(CommandFromServer cfs)
    {
        // Sends command to both players
        for (ObjectOutputStream o : outs) {
            try {
                o.writeObject(cfs);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



