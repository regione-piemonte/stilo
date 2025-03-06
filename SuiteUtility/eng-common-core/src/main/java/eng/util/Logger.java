// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import org.apache.log4j.PropertyConfigurator;
import java.util.Properties;

public class Logger
{
    static org.apache.log4j.Logger logger;
    
    public static String getlogMode() {
        return null;
    }
    
    public static void setlogMode(final String newlogMode) {
    }
    
    public static void PrintOut(final String message) {
        getLogger().info((Object)message);
    }
    
    public static org.apache.log4j.Logger getLogger() {
        if (Logger.logger == null) {
            Logger.logger = org.apache.log4j.Logger.getLogger(Logger.class.getName());
            final Properties p = new Properties();
            try {
                p.load(Logger.class.getClassLoader().getResourceAsStream("log4j.properties"));
            }
            catch (Exception ex) {
                p.setProperty("log4j.rootLogger", "debug, mystdout");
                p.setProperty("log4j." + Logger.class.getName(), "debug, mystdout");
                p.setProperty("log4j.appender.mystdout", "org.apache.log4j.ConsoleAppender");
                p.setProperty("log4j.appender.mystdout.layout", "org.apache.log4j.PatternLayout");
                p.setProperty("log4j.appender.mystdout.layout.ConversionPattern", "[ENG Common Core] %5p (%F:%L) - %m%n");
            }
            PropertyConfigurator.configure(p);
        }
        return Logger.logger;
    }
}
