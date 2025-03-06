package eng.storefunction;

import  java.io.PrintWriter;

import  java.util.Iterator;

public  class   ActionScript extends Action
{
  public ActionScript()
  {
    super.addCommand("<script>");
    super.addCommand("</script>");
  }

  public ActionScript(String command)
  {
    super.addCommand("<script>");
    super.addCommand(command);
    super.addCommand("</script>");
  }

  public ActionScript(String[] commands)
  {
    super.addCommand("<script>");
    super.addCommandAll(commands);
    super.addCommand("</script>");
  }

  public  void    execute(PrintWriter out)
  {
    Iterator commands = super.getCommands();

    while(commands.hasNext())
    {
      out.print(commands.next());
    }
  }

  public  void    executeSingleCommand(PrintWriter out, String command)
  {
    out.print("<script>");
    out.print(normalizeCommand(command));
    out.print("</script>");
  }

  public  String  normalizeCommand(String command)
  {
    if (command == null)
    {
      command = "";
    }
    else
    {
      if (command.length() >= 6)
      {
        if (command.substring(0,6).equals("alert(")
         || command.substring(0,6).equals("alert "))
        {
          String  s1    = command.substring(6);
          char    delimiter = '"';
          int     begin     = s1.indexOf(delimiter);
          if (begin == -1)
          {
            delimiter = '\'';
            begin     = s1.indexOf(delimiter);
          }
          if (begin != -1)
          {
            String  s2   = s1.substring(begin);
            int     end = s2.lastIndexOf(delimiter);
            if (end != -1)
            {
              String s3  = s2.substring(0, end + 1);
              if (s3.length() > 2)
              {
                String s4 = s3.substring(1, s3.length() - 1);
                String s5 = escapedQuote(s4);
                String s6 = s5.replace('\n', ' ');
                String s7 = "alert(\"" + s6 + "\");";
                command = s7;
              }
            }
          }
        }
      }
    }

    return  command;
  }

  public  String  escapedQuote(String s)
  {
    StringBuffer    sb = new StringBuffer();

    for(int i=0; i<s.length(); i++)
    {
        if (s.charAt(i) == '"')
        {
           sb.append(new Character('\\').toString() + '"');
        }
        else
        {
           sb.append(s.charAt(i));
        }
    }

     return  sb.toString();
  }
}
