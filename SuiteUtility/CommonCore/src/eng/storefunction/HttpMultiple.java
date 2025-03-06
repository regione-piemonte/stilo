package eng.storefunction;

import  java.sql.Types;

public class HttpMultiple extends HttpPrimitive
{
  public HttpMultiple(int position, String name)
  {
    super(position, Types.VARCHAR, name);
  }
  
  public Parameter cloneMe()  throws eng.database.exception.EngException {
        
      
        HttpMultiple p = new HttpMultiple(this.getPosition(), this.getName());
        if (this.isModeIn()) p.setModeIn();
        else if (this.isModeOut()) p.setModeOut();
        else if (this.isModeInOut()) p.setModeInOut();
        
        return p;
      
    }
}
