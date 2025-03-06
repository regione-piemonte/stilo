package it.eng.areas.hybrid.module.cryptoLight.tools;

public interface ITrace {
  enum Level {
    TRACE, INFO, ERROR
  }
  
  public void trace(Level level, String message);

}
