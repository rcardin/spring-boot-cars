package in.rcardin.demo.api.cars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class CarUpdates {
  private final String model;
  private final String brand;

  @JsonCreator
  public CarUpdates(
      @JsonProperty("brand") String brand,
      @JsonProperty("model") String model) {
    this.model = model;
    this.brand = brand;
  }

  @NotBlank
  public String getModel() {
    return model;
  }

  @NotBlank
  public String getBrand() {
    return brand;
  }
}
