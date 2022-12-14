package model;

import model.lists.StudentList;
import model.lists.VotingList;
import utils.MyFileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class AssociationModelManager implements Serializable
{
  private static final String fileName = "association.bin";

  public static void saveAssociation(Association association)
  {
    try
    {
      MyFileHandler.writeToBinaryFile(fileName, association);
    }
    catch (FileNotFoundException e)
    {
      System.err.println(String.format("File %s is not found", fileName));
    }
    catch (IOException e)
    {
      System.err.println(
          String.format("End of file %s, or it is empty", fileName));
    }
  }

  public static Association getAssociation()
  {
    Association association = null;
    try
    {
      association = (Association) MyFileHandler.readFromBinaryFile(fileName);
    }
    catch (FileNotFoundException e)
    {
      System.err.println(String.format("File %s is not found", fileName));
    }
    catch (IOException e)
    {
      System.err.println(
          String.format("End of file %s, or it is empty", fileName));
    }
    catch (ClassNotFoundException e)
    {
      System.err.println(
          String.format("Class is not found in %s file", fileName));
    }
    catch (Exception e){
      System.err.println("some other mistakes");
    }
    return association;
  }

  public static VotingList getVotingList()
  {
    return getAssociation().getVotingList();
  }

  public static StudentList getStudentList()
  {
    return getAssociation().getStudentList();
  }
}
