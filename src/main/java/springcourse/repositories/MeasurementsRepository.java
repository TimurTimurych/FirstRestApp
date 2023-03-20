package springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springcourse.models.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

}
