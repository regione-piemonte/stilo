package it.eng.hybrid.module.firmaCertificato.tool;

public interface ITrace {
  enum Level {
    TRACE, INFO, ERROR
  }
  
  public void trace(Level level, String message);

}
