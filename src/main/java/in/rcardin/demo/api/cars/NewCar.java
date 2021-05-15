package in.rcardin.demo.api.cars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewCar {
  private final String model;
  private final String brand;
  
  @JsonCreator
  public NewCar(
      @JsonProperty("model") String model,
      @JsonProperty("brand") String brand) {
    this.model = model;
    this.brand = brand;
  }
  
  public String getModel() {
    return model;
  }
  
  public String getBrand() {
    return brand;
  }
}
