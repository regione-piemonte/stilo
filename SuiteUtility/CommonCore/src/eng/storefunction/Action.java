package eng.storefunction;

import  java.util.*;

public abstract class Action
{
  /*ATTENZIONE!
    Il contratto pubblico di Action prevede che i comandi possono essere aggiunti solamente
    medianti i metodi di Action e NON direttamente nella lista dei comandi (commands).
    Infatti la lista dei comandi NON deve essere resa disponibile agli utenti.
    Questo perchè si vuole essere certi che ogni comando aggiunto sia normalizzato.
    In pratica il dato privato "commands" NON deve essere mai visto dall'utente.
  */
  private final List  commands;

  private       boolean isMultiple = false;
  private       int     multipleInput;
  private       int     multipleOutputNames;
  private       int     multipleOutputValues;

  public  Action()
  {
    commands = new ArrayList();
  }

  public  abstract  String  normalizeCommand(String command);

  public  void    addCommand(int index, String command)
  {
    commands.add(index, normalizeCommand(command));
  }

  public  void    addCommand(String command)
  {
    commands.add(normalizeCommand(command));
  }

  public  void    addCommandAll(Collection commands)
  {
    Iterator it = commands.iterator();

    while(it.hasNext())
    {
      addCommand((String)it.next());
    }
  }

  public  void    addCommandAll(String[] commands)
  {
    addCommandAll(Arrays.asList(commands));
  }

  public  String    getFirstCommand()
  {
    if (commands.size() > 0)
    {
      return  (String)commands.get(0);
    }
    else
    {
      return  null;
    }
  }

  public  String    getCommand(int index)
  {
      if (index < commands.size())
      {
        return  (String)commands.get(index);
      }
      else
      {
        return  "";
      }
  }

  public  Iterator  getCommands()
  {
    return commands.iterator();
  }

  public  int             getMultipleInput()
  {
    return  multipleInput;
  }

  public  int             getMultipleOutputNames()
  {
    return  multipleOutputNames;
  }

  public  int             getMultipleOutputValues()
  {
    return  multipleOutputValues;
  }

  public  boolean isMultiple()
  {
    return  isMultiple;
  }

  public  Action  setMultiple(int multipleInput, int multipleOutputNames, int multipleOutputValues)
  {
    this.multipleInput        = multipleInput;
    this.multipleOutputNames   = multipleOutputNames;
    this.multipleOutputValues = multipleOutputValues;
    this.isMultiple     = true;
    return  this;
  }

  public  void      substituteCommand(int index, String command)
  {
    try
    {
      commands.remove(index);
    }
    catch(UnsupportedOperationException ex)
    {
    }
    catch(IndexOutOfBoundsException ex)
    {
    }
    addCommand(index, command);
  }

  public  String    toString()
  {
    StringBuffer  sb = new StringBuffer("(Action:\n");
    Iterator      it = commands.iterator();

    while(it.hasNext())
    {
      sb.append(it.next() + ",\n");
    }

    sb.append(")");

    return  sb.toString();
  }
}
