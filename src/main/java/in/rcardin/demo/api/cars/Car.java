package in.rcardin.demo.api.cars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
  private final String id;
  private final String model;
  private final String brand;
  
  @JsonCreator
  public Car(
      @JsonProperty("id") String id,
      @JsonProperty("model") String model,
      @JsonProperty("brand") String brand) {
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
}
