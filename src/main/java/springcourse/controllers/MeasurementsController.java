package springcourse.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dto.MeasurementDTO;
import springcourse.models.Measurement;
import springcourse.services.MeasurementsService;
import springcourse.util.responses.MeasurementErrorResponse;
import springcourse.util.validators.MeasurementValidator;
import springcourse.util.exceptions.SensorNotCreatedException;
import springcourse.util.ErrorsUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;


    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementDTOValidator) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementDTOValidator;
    }


    @GetMapping()
    public List<Measurement> getAllMeasurements() {
        return measurementsService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public Long rainyDays() {
        return measurementsService.findAll().stream().filter(Measurement::getRain).count();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                      BindingResult bindingResult) {
        measurementValidator.validate(convertToMeasurement(measurementDTO), bindingResult);
        if (bindingResult.hasErrors())
            ErrorsUtil.returnErrorsToClient(bindingResult);

        measurementsService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(SensorNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
