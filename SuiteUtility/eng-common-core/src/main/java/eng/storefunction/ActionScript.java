// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import java.util.Iterator;
import java.io.PrintWriter;

public class ActionScript extends Action
{
    public ActionScript() {
        super.addCommand("<script>");
        super.addCommand("</script>");
    }
    
    public ActionScript(final String command) {
        super.addCommand("<script>");
        super.addCommand(command);
        super.addCommand("</script>");
    }
    
    public ActionScript(final String[] commands) {
        super.addCommand("<script>");
        super.addCommandAll(commands);
        super.addCommand("</script>");
    }
    
    public void execute(final PrintWriter out) {
        final Iterator commands = super.getCommands();
        while (commands.hasNext()) {
            out.print(commands.next());
        }
    }
    
    public void executeSingleCommand(final PrintWriter out, final String command) {
        out.print("<script>");
        out.print(this.normalizeCommand(command));
        out.print("</script>");
    }
    
    @Override
    public String normalizeCommand(String command) {
        if (command == null) {
            command = "";
        }
        else if (command.length() >= 6 && (command.substring(0, 6).equals("alert(") || command.substring(0, 6).equals("alert "))) {
            final String s1 = command.substring(6);
            char delimiter = '\"';
            int begin = s1.indexOf(delimiter);
            if (begin == -1) {
                delimiter = '\'';
                begin = s1.indexOf(delimiter);
            }
            if (begin != -1) {
                final String s2 = s1.substring(begin);
                final int end = s2.lastIndexOf(delimiter);
                if (end != -1) {
                    final String s3 = s2.substring(0, end + 1);
                    if (s3.length() > 2) {
                        final String s4 = s3.substring(1, s3.length() - 1);
                        final String s5 = this.escapedQuote(s4);
                        final String s6 = s5.replace('\n', ' ');
                        final String s7 = command = "alert(\"" + s6 + "\");";
                    }
                }
            }
        }
        return command;
    }
    
    public String escapedQuote(final String s) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '\"') {
                sb.append(new Character('\\').toString() + '\"');
            }
            else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
