package io.eddvance.production.lowcarb.rating;

public class RateException extends RuntimeException {
  public RateException(String message) {
    super(message);
  }
  public RateException(String message, Throwable cause) {}
}
