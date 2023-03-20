package springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springcourse.models.Sensor;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findSensorByName(String name);
}
