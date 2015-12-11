package kriek.evil_hangman_the_second;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by Kriek on 12/1/2015.
 */
public class popuplost extends DialogFragment
{
    //constructs variables
    private int score,naughty,guessnum,wordlen;
    private String message;


    public popuplost()
    {

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // get arguments from activity
        Bundle args = getArguments();
        score = args.getInt("score");
        message = args.getString("message", "");
        naughty = args.getInt("naughty");
        guessnum = args.getInt("guessnum");
        wordlen = args.getInt("wordlen");


        // builds popupwindow
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent gameboard = new Intent(getActivity(), GameboardActivity.class);
                        gameboard.putExtra("naughty", naughty);
                        gameboard.putExtra("score", score);
                        gameboard.putExtra("guessnum", guessnum);
                        gameboard.putExtra("wordlen", wordlen);
                        startActivity(gameboard);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //goes to highscore if score > 0 else to menu
                        if (score > 0) {
                            Intent highscore = new Intent(getActivity(), HighscoreActivity.class);
                            highscore.putExtra("score", score);
                            startActivity(highscore);
                        }
                        else {
                            Intent menu = new Intent(getActivity(), MenuActivity.class);
                            startActivity(menu);
                        }
                    }
                })
                .create();
    }
    // if dismissed rebuild popupwindow
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        DialogFragment vialog = new popuplost();
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putString("message", message);
        args.putInt("naughty", naughty);
        args.putInt("wordlen", wordlen);
        args.putInt("guessnum", guessnum);
        vialog.setArguments(args);
        vialog.show(getFragmentManager(), "tag");
    }
}

