package in.rcardin.demo.api.cars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Car {
  private final String id;
  private final String model;
  private final String brand;
  
  @JsonCreator
  public Car(
      @JsonProperty("id") String id,
      @JsonProperty("brand") String brand,
      @JsonProperty("model") String model) {
    this.id = id;
    this.model = model;
    this.brand = brand;
  }
  
  public String getId() {
    return id;
  }
  
  public String getModel() {
    return model;
  }
  
  public String getBrand() {
    return brand;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Car car = (Car) o;
    return Objects.equals(id, car.id) && Objects.equals(model, car.model)
               && Objects.equals(brand, car.brand);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, model, brand);
  }
}
