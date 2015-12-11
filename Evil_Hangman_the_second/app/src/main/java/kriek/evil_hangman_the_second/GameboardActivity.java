package kriek.evil_hangman_the_second;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class GameboardActivity extends AppCompatActivity {

    // construct variables
    private char[] word,guessedletters,printword;
    private int countercheck,naughty,score,guessnum,wordlen,dead;
    private boolean lost,istheword;
    private String goodword,devilword;
    private EditText inputletter;
    private TextView welcome,wrong,wordshower,guessesleft;
    public Gameplay_good angelplay;
    public Gameplay_bad devilplay;
    public ArrayList<String>wordlist;

    // construct dictionary so that it can be passed
    private final Dictionary dictionary = Dictionary.getInstance();
    protected Dictionary dict() { return dictionary; }

    // constructs the instance of this activity
    private static GameboardActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);

        // variables initialization
        Bundle extras = getIntent().getExtras();
        wordlist = new ArrayList<>();
        naughty = 1;
        score =0;
        guessnum = 6;
        wordlen = 4;

        // tries to fetch extra's if there
        try{
            naughty = extras.getInt("naughty");
            score = extras.getInt("score");
            wordlen = extras.getInt("wordlen");
            guessnum = extras.getInt("guessnum");
        }catch(Exception ini){}


        inputletter = (EditText)findViewById(R.id.letter);
        welcome = (TextView)findViewById(R.id.gamewelcome);
        wordshower = (TextView)findViewById(R.id.wordshower);
        wrong = (TextView)findViewById(R.id.wrong);
        guessesleft = (TextView)findViewById(R.id.guessesleft);

        angelplay = new Gameplay_good();
        devilplay = new Gameplay_bad();

        guessedletters = new char[50];
        printword = new char[50];

        welcome.setText("Hey you! guess a letter!");
        guessesleft.setText("Number of guesses left: " + String.valueOf(guessnum));

        dead = guessnum;
        dict().setKey(wordlen);
        dict().setNaughty(naughty);
        word = dict().getWord();
        wordlist =dict().getWordlist();


        // initializes printword with '_'
        for(int i = 0; i < word.length * 2; i++){
            if(i%2 == 0){
                printword[i] = '_';
            }
            else{
                printword[i] = ' ';
            }
        }
        wordshower.setText(String.valueOf(printword));
    }

    // gets instance of this activity
    public static GameboardActivity getInstance() {
        return instance;
    }

    // returns context of activity
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    // popup window
    public void lostPopup(){

        DialogFragment dialog = new popuplost();
        Bundle args = new Bundle();
        String message;

        // if naughty == 2 reset dictionary
        if(naughty ==2){
            dict().initializeDict();
        }

        if(lost){

            message = "Mwahp mwahp mwaaah you lost try again?";
            score = 0;

        }
        else{

            message = "Congratulations wanna go for another word?";
            score+=1;
        }

        //puts variables into the popupwindow
        args.putInt("score", score);
        args.putString("message", message);
        args.putInt("naughty", naughty);
        args.putInt("wordlen", wordlen);
        args.putInt("guessnum",guessnum);

        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "tag");
    }


// when button pressed execute this method

    public void pushWord(View view){

        istheword = true;
        String getletter = inputletter.getText().toString();
        inputletter.setText("");
        countercheck +=1;

        // makes sure that only 1 letter can be added
        if (getletter.matches("[a-zA-Z\\s]+") && getletter.length() == 1 ) {
            getletter = getletter.toUpperCase();
            char[] letter = getletter.toCharArray();

            // checks if letter was used before
            if(!checkinList(letter, countercheck)){

                // code for good gameplay
                if(naughty == 1){
                    angelplay.setLetter(letter);
                    angelplay.setPrintword(printword);
                    goodword = angelplay.checkinWord(word);

                    // if goodword is 1 execute operationKill
                    if(goodword.equals("1")){
                        operationKill(letter);
                    }

                    // else check if the word is found.
                    else {
                        wordshower.setText(goodword);
                        char[] wordgood = goodword.toCharArray();
                        for(int i = 0; i < wordgood.length;i++){
                            if(wordgood[i] == '_'){
                                istheword = false;
                            }
                        }
                        // if the word is found open popupwindow
                        if(istheword){
                            lost = false;
                            lostPopup();
                        }
                    }
                }
                // code for bad gameplay
                if(naughty == 2){
                    devilplay.setLetter(letter);
                    devilplay.setPrintword(printword);
                    devilword = devilplay.checkinWord(wordlist);

                    // if devilword returns 2 remove word and try to get a new one
                    if (devilword.equals("2")){
                        dict().removeWords(letter);
                        try {
                            word = dict().getWord();
                            operationKill(letter);
                        }catch(Exception safe){

                        // get safeword and check if letter in word
                            word = dict().getSafeword().toCharArray();
                            wordlist.add(0,dict().getSafeword());
                            devilword = devilplay.checkinWord(wordlist);
                            wordshower.setText(devilword);
                        }

                    }
                    // if devilword returns 3 remove words containing letter
                    else if(devilword.equals("3")){
                        if(wordlist.size() >1){
                            dict().removeWords(letter);
                        }
                        operationKill(letter);
                    }
                    //else check if word is fonund
                    else {
                        wordshower.setText(devilword);
                        char[] wordgood = devilword.toCharArray();
                        for(int i = 0; i < wordgood.length;i++){
                            if(wordgood[i] == '_'){
                                istheword = false;
                            }
                        }
                        //opens popup window
                        if(istheword){
                            lost = false;
                            lostPopup();
                        }
                    }
                }
            }
            else{
                Toast.makeText(GameboardActivity.this, "allready guessed brah", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(GameboardActivity.this, " Please enter a single letter", Toast.LENGTH_SHORT).show();
        }
    }
    //method used to check if a letter in allready guessed
    public boolean checkinList(char[] letter,int countercheck){

        for(int i = 0; i < guessedletters.length;i++){
            if(guessedletters[i] == letter[0]){
                return true;
            }
        }
        guessedletters[countercheck] = letter[0];
        return false;
    }
    //method used to show wrong letters and num of guesses left
    public void operationKill(char[] letter){
        String woopsie = wrong.getText().toString();
        dead -=1;
        if(dead == 0){
            lost = true;
            lostPopup();
        }
        wrong.setText(woopsie + " " + letter[0]);
        guessesleft.setText("Remaining guesses: " + String.valueOf(dead));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gameboard, menu);
        return true;
    }

    public void onBackPressed(){
        Intent menu = new Intent(this,MenuActivity.class);
        startActivity(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
