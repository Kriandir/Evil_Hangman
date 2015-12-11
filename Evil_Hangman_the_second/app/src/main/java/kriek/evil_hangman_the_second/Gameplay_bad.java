package kriek.evil_hangman_the_second;

/**
 * Created by Kriek on 12/10/2015.
 */

import java.util.ArrayList;

public class Gameplay_bad {

    // constructs variable's
    private char[] letter;
    private ArrayList<Integer> indexword;
    private boolean isinword;
    public char[] printword;


    public void setLetter(char[] letter) {
        this.letter = letter;

    }

    public void setPrintword(char[] printword){
        this.printword = printword;
    }

    // check if letter in word
    public String checkinWord(ArrayList<String> wordlist) {
        indexword = new ArrayList<>();
        isinword = false;

        // iterate and index letterlocation if in word
        if(wordlist.size() == 1){
            char[] word = wordlist.get(0).toCharArray();
            for(int j = 0; j < word.length; j++) {
                if (letter[0] == word[j]) {
                    indexword.add(j);
                    isinword = true;
                    updateGuessedWord(indexword, letter);
                }
            }
        }
        else {
            // if wordlist != 1 but letter is in word return 2
            for (int i = 0; i < wordlist.size(); i++) {
                char[] word = wordlist.get(i).toCharArray();
                for (int j = 0; j < word.length; j++) {
                    if (letter[0] == word[j]) {

                        return "2";
                    }
                }
            }
        }
        if (!isinword) {

            return "3";
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
