package kriek.evil_hangman_the_second;


import java.io.Serializable;

/**
 * Created by Kriek on 12/11/2015.
 * Object that stores Name and Score
 */
public class ListModel implements Serializable{

    private  String Name="";
    private  String Score="";


    public void setName(String Name)
    {
        this.Name = Name;
    }

    public void setScore(String Score)
    {
        this.Score = Score;
    }


    public String getName()
    {
        return this.Name;
    }

    public String getScore()
    {
        return this.Score;
    }
}
