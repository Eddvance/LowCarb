package io.eddvance.production.low_carb.exception;

public class GetRateException extends RuntimeException {
  public GetRateException(String message) {
    super(message);
  }
  public GetRateException(String message, Throwable cause) {}
}
