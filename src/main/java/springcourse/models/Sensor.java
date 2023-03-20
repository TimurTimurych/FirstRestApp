package springcourse.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should be empty")
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurementList;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonManagedReference
    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    @JsonManagedReference
    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }
}


