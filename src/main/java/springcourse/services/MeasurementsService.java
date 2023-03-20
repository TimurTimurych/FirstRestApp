package springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Measurement;
import springcourse.models.Sensor;
import springcourse.repositories.MeasurementsRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;


    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrich(measurement);

        setOwner(measurement);

        measurementsRepository.save(measurement);
    }

    public boolean checkOwner(Measurement measurement){
        return sensorsService.isAvailable(measurement.getSensor());
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }



    private void enrich(Measurement measurement) {
        measurement.setCreatedAt(LocalDate.now());
    }

    private void setOwner(Measurement measurement) {
        Sensor sensor = sensorsService.findByName(measurement.getSensor().getName()).get();
        measurement.setSensor(sensor);
    }
}
