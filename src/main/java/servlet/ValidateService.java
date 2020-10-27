package servlet;

public class ValidateService {

    private ValidateService() {
    }

    private static class ValidateServiceExecute{
    private static final IValidate INST = new ValidateStub();

}

    public static IValidate getInstance() {
        return ValidateServiceExecute.INST;
    }
}
