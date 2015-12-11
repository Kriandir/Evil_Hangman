package kriek.evil_hangman_the_second;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MenuActivity extends GameboardActivity {

    //constructs variables
    private int naughty,wordlen,guessnum,score;
    private SeekBar numguesses,wordlength;
    private TextView showguess,showlength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton angel = (ImageButton)findViewById(R.id.angel);
        ImageButton devil = (ImageButton)findViewById(R.id.devil);
        // resets highlighting of devil or angel picture
        devil.getBackground().clearColorFilter();
        angel.getBackground().clearColorFilter();
        showguess = (TextView)findViewById(R.id.guessnumber);
        showlength = (TextView)findViewById(R.id.lengthnumber);
        // initializes variables
        naughty =0;
        wordlen = 1;
        guessnum = 1;
        wordlength = (SeekBar)findViewById(R.id.wordlength);
        numguesses = (SeekBar)findViewById(R.id.guesses);

        // implements seekbar for wordlen variable
        wordlength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wordlen = 1 + progress;
                showlength.setText("Length of word: "+ wordlen);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // implements seekbar for guessnum variable
        numguesses.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                guessnum = 1 + progress;
                showguess.setText("Number of guesses: " + guessnum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

// method which graphically shows which gamemode is selected
    public void onClick(View v) {

        ImageButton angel = (ImageButton)findViewById(R.id.angel);
        ImageButton devil = (ImageButton)findViewById(R.id.devil);;

        switch (v.getId()) {

            case R.id.angel:
                naughty = 1;
                devil.getBackground().clearColorFilter();
                angel.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);

                break;

            case R.id.devil:
                naughty = 2;
                angel.getBackground().clearColorFilter();
                devil.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                break;


            default:
                break;
        }
    }

// on button push passes variables to gameboardactivity
    public void onStartbutton(View view){

            if(naughty != 0) {
                score = 0;

                // initializes Dictionary for good gameplay
                if(naughty == 1){
                    dict().initializeDict();
                }
                Intent gameboard = new Intent(this, GameboardActivity.class);
                gameboard.putExtra("naughty", naughty);
                gameboard.putExtra("score",score);
                gameboard.putExtra("guessnum",guessnum);
                gameboard.putExtra("wordlen",wordlen);
                startActivity(gameboard);
            }
            else {
                Toast.makeText(MenuActivity.this, "Please select Game mode", Toast.LENGTH_SHORT).show();
            }

    }
    public void onBackPressed(){
        Intent menu = new Intent(this,MenuActivity.class);
        startActivity(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
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

