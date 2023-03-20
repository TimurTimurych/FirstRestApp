package springcourse.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import springcourse.util.exceptions.SensorNotCreatedException;

import java.util.List;

/**
 * @author Neil Alishev
 */
public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }

        throw new SensorNotCreatedException(errorMsg.toString());
    }
}
