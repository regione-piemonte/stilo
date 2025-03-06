package it.eng.utility.jobmanager.quartz.plugin.history;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobPersistenceException;
import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.impl.jdbcjobstore.AttributeRestoringConnectionInvocationHandler;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.spi.SchedulerPlugin;
import org.quartz.utils.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTriggerHistoryInDbPlugin implements SchedulerPlugin, TriggerListener {
	
	private String name;
	
	/*
	 0	String	The Trigger's Name.
	 1	String	The Trigger's Group.
	 2	Date	The scheduled fire time.
	 3	Date	The next scheduled fire time.
	 4	Date	The actual fire time.
	 5	String	The Job's name.
	 6	String	The Job's group.
	 7	Integer	The re-fire count from the JobExecutionContext.
	 */
    private String triggerFiredMessage = "Trigger {1}.{0} fired job {6}.{5} at: {4, date, HH:mm:ss MM/dd/yyyy}";

    /*
	 0	String	The Trigger's Name.
	 1	String	The Trigger's Group.
	 2	Date	The scheduled fire time.
	 3	Date	The next scheduled fire time.
	 4	Date	The actual fire time.
	 5	String	The Job's name.
	 6	String	The Job's group.	
	 */
    private String triggerMisfiredMessage = "Trigger {1}.{0} misfired job {6}.{5}  at: {4, date, HH:mm:ss MM/dd/yyyy}.  Should have fired at: {3, date, HH:mm:ss MM/dd/yyyy}";

    /*
	 0	String	The Trigger's Name.
	 1	String	The Trigger's Group.
	 2	Date	The scheduled fire time.
	 3	Date	The next scheduled fire time.	 	 
	 4	Date	The job completion time.
	 5	String	The Job's name.
	 6	String	The Job's group.
	 7	Integer	The re-fire count from the JobExecutionContext.
	 8	Integer	The trigger's resulting instruction code.
	 9	String	A human-readable translation of the trigger's resulting instruction code.
	 */
    private String triggerCompleteMessage = "Trigger {1}.{0} completed firing job {6}.{5} at {4, date, HH:mm:ss MM/dd/yyyy} with resulting trigger instruction code: {9}";
    
    protected String dsName;
    
    private final Logger log = LoggerFactory.getLogger(getClass());       
   
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Constructors.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public LoggingTriggerHistoryInDbPlugin() {
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    
    protected Logger getLog() {
        return log;
    }
      
    /**
     * Wrap the given <code>Connection</code> in a Proxy such that attributes 
     * that might be set will be restored before the connection is closed 
     * (and potentially restored to a pool).
     */
    protected Connection getAttributeRestoringConnection(Connection conn) {
        return (Connection)Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { Connection.class },
                new AttributeRestoringConnectionInvocationHandler(conn));
    }
            
    protected Connection getConnection() throws JobPersistenceException {
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection(getDataSource());
        } catch (SQLException sqle) {
            throw new JobPersistenceException(
                    "Failed to obtain DB connection from data source '"
                    + getDataSource() + "': " + sqle.toString(), sqle);
        } catch (Throwable e) {
            throw new JobPersistenceException(
                    "Failed to obtain DB connection from data source '"
                    + getDataSource() + "': " + e.toString(), e);
        }

        if (conn == null) { 
            throw new JobPersistenceException(
                "Could not get connection from DataSource '"
                + getDataSource() + "'"); 
        }

        // Protect connection attributes we might change.
        conn = getAttributeRestoringConnection(conn);

        // Set any connection connection attributes we are to override.
        try {
            conn.setAutoCommit(false);            
        } catch (SQLException sqle) {
            getLog().warn("Failed to override connection auto commit.", sqle);
        } catch (Throwable e) {
            try { conn.close(); } catch(Throwable tt) {}
            
            throw new JobPersistenceException(
                "Failure setting up connection.", e);
        }
    
        return conn;
    }
        
    /**
     * Get the message that is printed upon the completion of a trigger's
     * firing.
     * 
     * @return String
     */
    public String getTriggerCompleteMessage() {
        return triggerCompleteMessage;
    }

    /**
     * Get the message that is printed upon a trigger's firing.
     * 
     * @return String
     */
    public String getTriggerFiredMessage() {
        return triggerFiredMessage;
    }

    /**
     * Get the message that is printed upon a trigger's mis-firing.
     * 
     * @return String
     */
    public String getTriggerMisfiredMessage() {
        return triggerMisfiredMessage;
    }

    /**
     * <p>
     * Get the name of the <code>DataSource</code> that should be used for
     * performing database functions.
     * </p>
     */
    public String getDataSource() {
        return dsName;
    }
        
    /**
     * Set the message that is printed upon the completion of a trigger's
     * firing.
     * 
     * @param triggerCompleteMessage
     *          String in java.text.MessageFormat syntax.
     */
    public void setTriggerCompleteMessage(String triggerCompleteMessage) {
        this.triggerCompleteMessage = triggerCompleteMessage;
    }

    /**
     * Set the message that is printed upon a trigger's firing.
     * 
     * @param triggerFiredMessage
     *          String in java.text.MessageFormat syntax.
     */
    public void setTriggerFiredMessage(String triggerFiredMessage) {
        this.triggerFiredMessage = triggerFiredMessage;
    }

    /**
     * Set the message that is printed upon a trigger's firing.
     * 
     * @param triggerMisfiredMessage
     *          String in java.text.MessageFormat syntax.
     */
    public void setTriggerMisfiredMessage(String triggerMisfiredMessage) {
        this.triggerMisfiredMessage = triggerMisfiredMessage;
    }
    
    /**
     * <p>
     * Set the name of the <code>DataSource</code> that should be used for
     * performing database functions.
     * </p>
     */
    public void setDataSource(String dsName) {
        this.dsName = dsName;
    }
    
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * SchedulerPlugin Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p>
     * Called during creation of the <code>Scheduler</code> in order to give
     * the <code>SchedulerPlugin</code> a chance to initialize.
     * </p>
     * 
     * @throws SchedulerConfigException
     *           if there is an error initializing.
     */
    public void initialize(String pname, Scheduler scheduler)
        throws SchedulerException {
        this.name = pname;

        scheduler.getListenerManager().addTriggerListener(this,  EverythingMatcher.allTriggers());
    }

    public void start() {
        // do nothing...
    }

    /**
     * <p>
     * Called in order to inform the <code>SchedulerPlugin</code> that it
     * should free up all of it's resources because the scheduler is shutting
     * down.
     * </p>
     */
    public void shutdown() {
        // nothing to do...
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * TriggerListener Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /*
     * Object[] arguments = { new Integer(7), new
     * Date(System.currentTimeMillis()), "a disturbance in the Force" };
     * 
     * String result = MessageFormat.format( "At {1,time} on {1,date}, there
     * was {2} on planet {0,number,integer}.", arguments);
     */

    public String getName() {
        return name;
    }

    public void triggerFired(Trigger trigger, JobExecutionContext context) {
    	if (!getLog().isInfoEnabled()) {
            return;
        } 

    	Object[] args = {
            trigger.getKey().getName(), trigger.getKey().getGroup(),
            trigger.getPreviousFireTime(), trigger.getNextFireTime(),
            new java.util.Date(), context.getJobDetail().getKey().getName(),
            context.getJobDetail().getKey().getGroup(),
            Integer.valueOf(context.getRefireCount())
        };
    	getLog().info(MessageFormat.format(getTriggerFiredMessage(), args));
            	    	
        try {
        	String triggerId = java.util.UUID.randomUUID().toString();
        	trigger.getJobDataMap().put("TRIGGER_ID", triggerId);
        	
        	HistoryTriggerLog lLog = new HistoryTriggerLog();
        	lLog.setTriggerId(triggerId);
        	lLog.setTriggerName(trigger.getKey().getName());
        	lLog.setTriggerGroup(trigger.getKey().getGroup());
        	lLog.setTriggerDescription(trigger.getDescription());
        	lLog.setJobName(context.getJobDetail().getKey().getName());
        	lLog.setJobGroup(context.getJobDetail().getKey().getGroup());
        	lLog.setTsStart(new java.util.Date());
        	lLog.setTsEnd(null);
        	lLog.setState("FIRED");
        	
			storeHistoryTriggerLog(lLog, false);
		} catch (JobPersistenceException e) {
			log.error(e.getMessage(),e);
		}		  
    }

    public void triggerMisfired(Trigger trigger) {
    	if (!getLog().isInfoEnabled()) {
            return;
        } 
        
        Object[] args = {
            trigger.getKey().getName(), trigger.getKey().getGroup(),
            trigger.getPreviousFireTime(), trigger.getNextFireTime(),
            new java.util.Date(), trigger.getJobKey().getName(),
            trigger.getJobKey().getGroup()
        };
        
        getLog().info(MessageFormat.format(getTriggerMisfiredMessage(), args));             
    	
    	try {
    		String triggerId = (String) trigger.getJobDataMap().get("TRIGGER_ID");                 
    		HistoryTriggerLog lLog = getHistoryTriggerLog(triggerId);
    		lLog.setTsEnd(new java.util.Date());
    		lLog.setState("MISFIRED");
        	
			storeHistoryTriggerLog(lLog, true);
		} catch (JobPersistenceException e) {
			log.error("",e);
		}
    }

    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
    	if (!getLog().isInfoEnabled()) {
            return;
        } 
        
        String instrCode = "UNKNOWN";
        if (triggerInstructionCode == CompletedExecutionInstruction.DELETE_TRIGGER) {
            instrCode = "DELETE TRIGGER";
        } else if (triggerInstructionCode == CompletedExecutionInstruction.NOOP) {
            instrCode = "DO NOTHING";
        } else if (triggerInstructionCode == CompletedExecutionInstruction.RE_EXECUTE_JOB) {
            instrCode = "RE-EXECUTE JOB";
        } else if (triggerInstructionCode == CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
            instrCode = "SET ALL OF JOB'S TRIGGERS COMPLETE";
        } else if (triggerInstructionCode == CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
            instrCode = "SET THIS TRIGGER COMPLETE";
        }

        Object[] args = {
            trigger.getKey().getName(), trigger.getKey().getGroup(),
            trigger.getPreviousFireTime(), trigger.getNextFireTime(),
            new java.util.Date(), context.getJobDetail().getKey().getName(),
            context.getJobDetail().getKey().getGroup(),
            Integer.valueOf(context.getRefireCount()),
            triggerInstructionCode.toString(), instrCode
        };
        
        getLog().info(MessageFormat.format(getTriggerCompleteMessage(), args));
        
    	try {
    		String triggerId = (String) trigger.getJobDataMap().get("TRIGGER_ID");                 
    		HistoryTriggerLog lLog = getHistoryTriggerLog(triggerId);
    		lLog.setTsEnd(new java.util.Date());
    		lLog.setState("COMPLETED");
        	
			storeHistoryTriggerLog(lLog, true);
		} catch (JobPersistenceException e) {
			log.error("",e);
		}    	    
    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }
    
    public HistoryTriggerLog getHistoryTriggerLog(String triggerId)
    	throws JobPersistenceException {
    	
    	Connection conn = null;   
    	PreparedStatement ps = null;
        ResultSet rs = null;   
       
        try {
            conn = getConnection();     
            
            HistoryTriggerLog lLog = null;            
            String sql = "SELECT TRIGGER_ID, TRIGGER_NAME, TRIGGER_GROUP, " +
            			 "TRIGGER_DESCRIPTION, JOB_NAME, JOB_GROUP, TS_START, " +
            			 "TS_END, STATE FROM DMT_HISTORY_TRIGGER_LOG WHERE TRIGGER_ID = ?";        
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, triggerId);
            rs = ps.executeQuery();
            if (rs.next()) {            	
            	lLog = new HistoryTriggerLog();
            	lLog.setTriggerId(rs.getString(1));
                lLog.setTriggerName(rs.getString(2));
                lLog.setTriggerGroup(rs.getString(3));
                lLog.setTriggerDescription(rs.getString(4));
                lLog.setJobName(rs.getString(5));
                lLog.setJobGroup(rs.getString(6));
                Date tsStart = (rs.getTimestamp(7) != null) ? new Date(rs.getTimestamp(7).getTime()) : null;    		
                Date tsEnd = (rs.getTimestamp(8) != null) ? new Date(rs.getTimestamp(8).getTime()) : null; 
                lLog.setTsStart(tsStart);
                lLog.setTsEnd(tsEnd);
                lLog.setState(rs.getString(9));               	
            } 
                                    
            return lLog;
        } catch(SQLException e) {
        	throw new JobPersistenceException("Couldn't find log: "
                    + e.getMessage(), e);
        } finally {
        	closeResultSet(rs);
            closeStatement(ps);
            cleanupConnection(conn);            
        } 
    }
    
    protected void storeHistoryTriggerLog(HistoryTriggerLog log, boolean existingLog)
        throws JobPersistenceException {
    	
    	Connection conn = null;
        try {
            conn = getConnection();     
            
            try {
            	if (existingLog) {                
                    updateHistoryTriggerLog(conn, log);
                } else {
                    insertHistoryTriggerLog(conn, log);
                }
            } catch (SQLException e) {
                throw new JobPersistenceException("Couldn't store log: "
                        + e.getMessage(), e);
            }
            
            commitConnection(conn);
        } catch (JobPersistenceException e) {
            rollbackConnection(conn);
            throw e;
        } catch (RuntimeException e) {
            rollbackConnection(conn);
            throw new JobPersistenceException("Unexpected runtime exception: "
                    + e.getMessage(), e);
        } finally {
            cleanupConnection(conn);            
        }
   
    }           
    
    /**
     * <p>
     * Insert the log detail record.
     * </p>
     * 
     * @param conn
     *          the DB Connection
     * @param log
     *          the log to insert
     * @return number of rows inserted
     * @throws SQLException     
     */
    public int insertHistoryTriggerLog(Connection conn, HistoryTriggerLog log)
        throws SQLException {
        
        PreparedStatement ps = null;

        int insertResult = 0;
        
        String sql = "INSERT INTO DMT_HISTORY_TRIGGER_LOG ( " +
        					"TRIGGER_ID, TRIGGER_NAME, TRIGGER_GROUP, " +
        					"TRIGGER_DESCRIPTION, JOB_NAME, JOB_GROUP, " +
        					"TS_START, TS_END, STATE ) " + 
        					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, log.getTriggerId());
            ps.setString(2, log.getTriggerName());
            ps.setString(3, log.getTriggerGroup());
            ps.setString(4, log.getTriggerDescription());
            ps.setString(5, log.getJobName());
            ps.setString(6, log.getJobGroup());          
    		Timestamp tsStart = (log.getTsStart() != null) ? new Timestamp(log.getTsStart().getTime()) : null;    		
    		Timestamp tsEnd = (log.getTsEnd() != null) ? new Timestamp(log.getTsEnd().getTime()) : null;           
            ps.setTimestamp(7, tsStart);
            ps.setTimestamp(8, tsEnd);
            ps.setString(9, log.getState());                                   
    		            
            insertResult = ps.executeUpdate();                       
        } finally {
            closeStatement(ps);
        }

        return insertResult;
    }

    /**
     * <p>
     * Update the log record.
     * </p>
     * 
     * @param conn
     *          the DB Connection
     * @param log
     *          the log to update
     * @return number of rows updated
     * @throws SQLException    
     */
    public int updateHistoryTriggerLog(Connection conn, HistoryTriggerLog log)
        throws SQLException {
        
        PreparedStatement ps = null;

        int updateResult = 0;
        
        String sql = "UPDATE DMT_HISTORY_TRIGGER_LOG SET " +
        		     "TRIGGER_NAME = ?, " +
        		     "TRIGGER_GROUP = ?, " +
        			 "TRIGGER_DESCRIPTION = ?, " +
        			 "JOB_NAME = ?, " +
        			 "JOB_GROUP = ?, " +
        			 "TS_START = ?, " +
        			 "TS_END = ?, " +
        			 "STATE = ? " +
        			 "WHERE TRIGGER_ID = ?"; 
        	
        try {
            ps = conn.prepareStatement(sql);
                        
            ps.setString(1, log.getTriggerName());
            ps.setString(2, log.getTriggerGroup());
            ps.setString(3, log.getTriggerDescription());
            ps.setString(4, log.getJobName());
            ps.setString(5, log.getJobGroup());        
            Timestamp tsStart = (log.getTsStart() != null) ? new Timestamp(log.getTsStart().getTime()) : null;    		
    		Timestamp tsEnd = (log.getTsEnd() != null) ? new Timestamp(log.getTsEnd().getTime()) : null;           
            ps.setTimestamp(6, tsStart);
            ps.setTimestamp(7, tsEnd);            
            ps.setString(8, log.getState());    
            ps.setString(9, log.getTriggerId());
                        
            updateResult = ps.executeUpdate();                       
        } finally {
            closeStatement(ps);
        }

        return updateResult;
    }
       
    /**
     * <p>
     * Cleanup the given database connection.  This means restoring
     * any modified auto commit or transaction isolation connection
     * attributes, and then closing the underlying connection.
     * </p>
     * 
     * <p>
     * This is separate from closeConnection() because the Spring 
     * integration relies on being able to overload closeConnection() and
     * expects the same connection back that it originally returned
     * from the datasource. 
     * </p>
     * 
     * @see #closeConnection(Connection)
     */
    protected void cleanupConnection(Connection conn) {
        if (conn != null) {
            if (conn instanceof Proxy) {
                Proxy connProxy = (Proxy)conn;
                
                InvocationHandler invocationHandler = 
                    Proxy.getInvocationHandler(connProxy);
                if (invocationHandler instanceof AttributeRestoringConnectionInvocationHandler) {
                    AttributeRestoringConnectionInvocationHandler connHandler =
                        (AttributeRestoringConnectionInvocationHandler)invocationHandler;
                        
                    connHandler.restoreOriginalAtributes();
                    closeConnection(connHandler.getWrappedConnection());
                    return;
                }
            }
            
            // Wan't a Proxy, or was a Proxy, but wasn't ours.
            closeConnection(conn);
        }
    }
    
    
    /**
     * Closes the supplied <code>Connection</code>.
     * <p>
     * Ignores a <code>null Connection</code>.  
     * Any exception thrown trying to close the <code>Connection</code> is
     * logged and ignored.  
     * </p>
     * 
     * @param conn The <code>Connection</code> to close (Optional).
     */
    protected void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                getLog().error("Failed to close Connection", e);
            } catch (Throwable e) {
                getLog().error(
                    "Unexpected exception closing Connection." +
                    "  This is often due to a Connection being returned after or during shutdown.", e);
            }
        }
    }

    /**
     * Rollback the supplied connection.
     * 
     * <p>  
     * Logs any SQLException it gets trying to rollback, but will not propogate
     * the exception lest it mask the exception that caused the caller to 
     * need to rollback in the first place.
     * </p>
     *
     * @param conn (Optional)
     */
    protected void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                getLog().error(
                    "Couldn't rollback jdbc connection. "+e.getMessage(), e);
            }
        }
    }
    
    /**
     * Commit the supplied connection
     *
     * @param conn (Optional)
     * @throws JobPersistenceException thrown if a SQLException occurs when the
     * connection is committed
     */
    protected void commitConnection(Connection conn)
        throws JobPersistenceException {

        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new JobPersistenceException(
                    "Couldn't commit jdbc connection. "+e.getMessage(), e);
            }
        }
    }    
    
    /**
     * Cleanup helper method that closes the given <code>Statement</code>
     * while ignoring any errors.
     */
    protected static void closeStatement(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException ignore) {
            }
        }
    }
    
    /**
     * Cleanup helper method that closes the given <code>ResultSet</code>
     * while ignoring any errors.
     */
    protected static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
    }

}
