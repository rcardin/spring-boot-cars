package in.rcardin.demo.api.cars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
  private final String id;
  private final String model;
  private final String vendor;
  
  @JsonCreator
  public Car(
      @JsonProperty("id") String id,
      @JsonProperty("model") String model,
      @JsonProperty("vendor") String vendor) {
    this.id = id;
    this.model = model;
    this.vendor = vendor;
  }
  
  public String getId() {
    return id;
  }
  
  public String getModel() {
    return model;
  }
  
  public String getVendor() {
    return vendor;
  }
}
