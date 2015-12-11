package kriek.evil_hangman_the_second;


import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Kriek on 12/1/2015.
 */
public class Gameplay_good {

    //constructs variable's
    public char[] letter;
    public ArrayList<Integer> indexword;
    private boolean isinword;
    public char[] printword;


    public void setLetter(char[] letter) {
        this.letter = letter;

    }

    public void setPrintword(char[] printword){
        this.printword = printword;
    }

    // check if letter in word
    public String checkinWord(char[] word) {
        indexword = new ArrayList<>();
        isinword = false;
        // iterate and index letterlocation if in word
        for (int i = 0; i < word.length; i++) {

            if (letter[0] == word[i]) {
                indexword.add(i);
                isinword = true;
                updateGuessedWord(indexword, letter);
            }


        }
        if (!isinword) {

            return "1";
        }



        return String.valueOf(printword);



    }
    //update printword
    public char[] updateGuessedWord(ArrayList<Integer> indexword, char[] letter) {
        for (int i = 0; i < indexword.size(); i++) {

            printword[indexword.get(i) * 2] = letter[0];


        }
        return printword;
    }
}