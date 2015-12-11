package kriek.evil_hangman_the_second;

import java.util.*;

/**
 * Created by Kriek on 11/25/2015.
 */
public class Dictionary {

    // constructs variable's
    public char[] word;
    private ArrayList<String> wordlist;
    private ArrayList<Integer> removeindex;
    private int x,key,naughty;
    public String safeword;
    private Parser wordsxml;
    private Random rand;
    private Hashtable<Integer,ArrayList<String>> wordtable;

    private static Dictionary instance;
    private Dictionary() {}

    // gets instance of dictionary
    public static Dictionary getInstance() {
        if (instance == null)
            instance = new Dictionary();
        return instance;
    }

    {
        rand = new Random();
    }

    // initializes dictionary
    public void initializeDict(){

        wordsxml = new Parser();
        wordtable = wordsxml.ParseXml();
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setNaughty(int naughty) {
        this.naughty = naughty;
    }

    public ArrayList<String> getWordlist() {
        return wordlist;
    }

    public String getSafeword() {
        return safeword;
    }

    public char[] getWord() {

        if(wordtable == null){
            initializeDict();
        }

        wordlist = wordtable.get(key);



        // if last word in list return word
        if(wordlist.size() == 1){
            word = wordlist.get(0).toCharArray();

            return word;

        }

        // fetches word
        x = rand.nextInt(wordlist.size());
        word = wordlist.get(x).toCharArray();

        // if good gameplay remove word from list
        if( naughty ==1){
            wordlist.remove(x);
            wordlist.trimToSize();
            wordtable.remove(key);
            wordtable.put(key, wordlist);
        }
        return word;
    }

    // function that remove's words if letter is in them
    public void removeWords(char[] letter){
        removeindex = new ArrayList<>();

        wordlist = wordtable.get(key);
        int ctr = 0;
        for(int i=0; i < wordlist.size(); i++){
            char[] wordremove = wordlist.get(i).toCharArray();
            for(int j =0; j < wordremove.length; j++){
                if(letter[0] == wordremove[j]){

                    removeindex.add(ctr,i);
                    ctr+=1;
                    break;
                }
            }
        }

        int removectr = 0;

        // gets a safe word incase last removal removes entire list
        x = rand.nextInt(wordlist.size());
        safeword = wordlist.get(x);

        for(int i = 0; i < removeindex.size(); i++){
            int a = removeindex.get(i) - removectr;
            wordlist.remove(a);
            removectr +=1;
        }
        wordlist.trimToSize();
        wordtable.put(key, wordlist);
    }
}

