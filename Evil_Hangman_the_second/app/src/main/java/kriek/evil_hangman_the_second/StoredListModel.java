package kriek.evil_hangman_the_second;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kriek on 12/11/2015.
 * Object that Stores highscorelist so it can be written to file
 */
public class StoredListModel implements Serializable {

    private ArrayList<ListModel> Highscoresvalue;


    public void setHighscoresvalue(ArrayList<ListModel> highscoresvalue) {
        Highscoresvalue = highscoresvalue;
    }

    public ArrayList<ListModel> getHighscoresvalue() {
        return Highscoresvalue;
    }
}
