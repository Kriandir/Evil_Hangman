package kriek.evil_hangman_the_second;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Kriek on 12/11/2015.
 */

public class CustomAdapter extends BaseAdapter {

    //constructs variable's
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    ListModel tempValues=null;

    //constructs customadapter
    public CustomAdapter(Activity a, ArrayList d) {

        activity = a;
        data= d;

        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

   // gets size of passed arraylist
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // creates holder class to containt inflated xml
    public static class ViewHolder{

        public TextView text;
        public TextView text1;
    }

// creates each listview row
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

          // inflate row.xml for each row
            vi = inflater.inflate(R.layout.row, null);

           // viewholder object that holds the xml
            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.name);
            holder.text1=(TextView)vi.findViewById(R.id.score);

            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.text.setText("No Data");

        }
        else
        {
            // gets each object from arraylist
            tempValues=null;
            tempValues = ( ListModel ) data.get( position );


            // sets values in holder elements
            holder.text.setText( tempValues.getName() );
            holder.text1.setText(tempValues.getScore());
        }
        return vi;
    }
}