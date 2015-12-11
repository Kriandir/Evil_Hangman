package kriek.evil_hangman_the_second;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;


public class HighscoreActivity extends AppCompatActivity {

    // constructs variable's
    ListView list;
    CustomAdapter adapter;
    public  HighscoreActivity Highscore = null;
    public  ArrayList<ListModel> Highscorevalues = new ArrayList<ListModel>();
    private TextView greeting;
    private EditText nameinput;
    private Button namepush,menuback;
    private String name ="";
    private int score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        greeting = (TextView)findViewById(R.id.textViewName);
        nameinput = (EditText)findViewById(R.id.editName);
        namepush = (Button)findViewById(R.id.pushName);
        menuback = (Button)findViewById(R.id.menubutton);
        score =0;
        Bundle extras = getIntent().getExtras();

        // if score put in extra fetch it
        try {
            score = extras.getInt("score");
        }catch(Exception inihigh){}


        // read in previous stored Highscore list
        try{
            read();

        }catch(Exception nothing){}

        // resetting highscorelist if more then 10 entries
        if(Highscorevalues.size() > 10){
            Highscorevalues.clear();
        }
        // allows user to enter name to the highscorelist if score is found
        if(score > 0){
            greeting.setText(getString(R.string.inputname));
            nameinput.setVisibility(View.VISIBLE);
            namepush.setVisibility(View.VISIBLE);
        }
        else{
            menuback.setVisibility(View.VISIBLE);
        }

        // create custom adapter
        Highscore = this;
        list= ( ListView )findViewById( R.id.list );
        adapter = new CustomAdapter( Highscore, Highscorevalues );
        list.setAdapter( adapter );



    }
    // reads in highscore list from file
    public void read(){
        ObjectInputStream input;
        String filename = "testFilemost.srl";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getFilesDir(),"")+File.separator+filename)));
            StoredListModel myListmodel = (StoredListModel) input.readObject();
            Highscorevalues = myListmodel.getHighscoresvalue();
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    // writes highscorelist out to file
    public void write(){
        StoredListModel myListmodel = new StoredListModel();
        myListmodel.setHighscoresvalue(Highscorevalues);
        String filename = "testFilemost.srl";
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+ File.separator+filename));
            out.writeObject(myListmodel);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method for adding name and score to highscore list
    public void onPushButton(View v){
        name = String.valueOf(nameinput.getText());
        final ListModel sched = new ListModel();

        sched.setName(name);
        sched.setScore(String.valueOf(score));
        Highscorevalues.add(sched);
        write();
        Intent highscore = new Intent(this,HighscoreActivity.class);
        startActivity(highscore);

    }
    public void onBackMenuButton(View v){
        Intent menu = new Intent(this,MenuActivity.class);
        startActivity(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
