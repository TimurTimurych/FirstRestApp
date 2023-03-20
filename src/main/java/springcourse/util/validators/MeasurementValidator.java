package springcourse.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springcourse.models.Measurement;
import springcourse.services.MeasurementsService;


@Component
public class MeasurementValidator implements Validator {

    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementValidator(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;

        if (measurement.getSensor() == null) {
            return;
        }
        if(!measurementsService.checkOwner(measurement))
            errors.rejectValue("sensor", "", "This sensor not found");
    }
}
