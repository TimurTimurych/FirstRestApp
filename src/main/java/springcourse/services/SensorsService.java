package springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcourse.models.Sensor;
import springcourse.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public boolean isAvailable(Sensor sensor) {
        Optional<Sensor> optional = sensorsRepository.findSensorByName(sensor.getName());

        return optional.isPresent();
    }
    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findSensorByName(name);
    }

}
