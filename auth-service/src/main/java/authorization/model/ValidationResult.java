package authorization.model;

public class ValidationResult {
    private boolean isValid;

    public ValidationResult(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }
}
