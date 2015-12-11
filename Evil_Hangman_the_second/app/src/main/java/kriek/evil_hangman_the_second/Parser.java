package kriek.evil_hangman_the_second;

/**
 * Created by Kriek on 12/10/2015.
 */
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;;
import java.util.ArrayList;
import java.util.Hashtable;
import android.content.Context;


public class Parser{

    // constructs variable's
    static final String KEY_Item = "item";
    private Hashtable<Integer,ArrayList<String>> wordtable;
    private ArrayList<String> wordsread;
    private ArrayList<String> wordlist;
    private int ctr =0;
    private String curText;


    public Hashtable ParseXml() {

        wordtable = new Hashtable<>(10);
        //gets context from GameboardActivity
        Context context = GameboardActivity.getContext();
        wordsread = new ArrayList<String>();
        curText = "";

        try {
            // sets up parserfactory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            InputStream fis = (context.getResources().openRawResource(R.raw.words));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            xpp.setInput(reader);

            int eventType = xpp.getEventType();

            // Loop through pull events until END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xpp.getName();


                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(KEY_Item)) {

                        }
                        break;

                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (tagname.equalsIgnoreCase(KEY_Item)) {

                            // adds text to arraylist
                            wordsread.add(ctr,curText);
                            curText ="";
                            ctr+=1;

                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // loops through arraylist and put words in hashtable sorted by length

        for(int i =0;i < wordsread.size();i++){
            int wordlength = wordsread.get(i).length();

            if(wordtable.get(wordlength) != null){
                wordlist = new ArrayList<String>();
                wordlist = wordtable.get(wordlength);
                wordtable.remove(wordlength);
                wordlist.add(wordlist.size(), wordsread.get(i));
                wordtable.put(wordlength,wordlist);
            }
            else{
                wordlist = new ArrayList<String>();
                wordlist.add(0,wordsread.get(i));
                wordtable.put(wordlength,wordlist);
            }
        }
        //returns hashtable
        return wordtable;
    }
}
