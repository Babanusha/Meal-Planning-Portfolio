
package controller;

public enum Validator {
  NOT_EMPTY {
    @Override
    public boolean validate(String value) {
      return value != null && !value.trim().isEmpty();
    }
  },
  POSITIVE_NUMBER {
    @Override
    public boolean validate(String value) {
      try {
        return Integer.parseInt(value) > 0;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  };

  public abstract boolean validate(String value);
}