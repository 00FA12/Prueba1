package model.lists;

import model.GameCandidate;

import java.io.Serializable;
import java.util.ArrayList;

//Seva & Hugo
public class VotingList implements Serializable
{
  private ArrayList<GameCandidate> votes;

  public VotingList()
  {
    this.votes = new ArrayList<>();
  }

  public void addVote(GameCandidate vote)
  {
    this.votes.add(vote);
  }


  public void clear()
  {
    votes.clear();
  }

  public GameCandidate getVoteByGameTitle(String title)
  {
    for (int i = 0; i < votes.size(); i++)
    {
      if (votes.get(i).getTitleOfGame().equals(title))
      {
        return votes.get(i);
      }
    }
    return null;
  }

  public GameCandidate[] getVotingListToArray()
  {
    return this.votes.toArray(new GameCandidate[votes.size()]);
  }

  public ArrayList<GameCandidate> getVotes()
  {
    return this.votes;
  }

  public String[] getGameTitlesToArray()
  {
    ArrayList<String> titles = new ArrayList<>();

    for (int i = 0; i < votes.size(); i++)
    {
      titles.add(votes.get(i).getTitleOfGame());
    }
    return titles.toArray(new String[titles.size()]);
  }

  public int getSize()
  {
    if(votes != null)
      return votes.size();
    else return -1;
  }

  public GameCandidate getVote(int index)
  {
    return votes.get(index);
  }

  public String toString()
  {
    String temp = "";
    for (int i = 0; i < votes.size(); i++)
    {
      temp += votes.get(i) + "\n";
    }
    return temp;
  }
}
