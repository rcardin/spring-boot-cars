package in.rcardin.demo.api.cars;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/cars")
public class CarController {
  
  private final CarService service;
  
  @Autowired
  public CarController(CarService service) {
    this.service = service;
  }
  
  @GetMapping
  public List<Car> find(
      @RequestParam(required = false) String model,
      @RequestParam(required = false) String brand) {
    return service.find(brand, model);
  }
  
  @PostMapping
  public ResponseEntity<Car> create(@Valid @RequestBody NewCar newCar) {
    final Car car = service.create(newCar.getBrand(), newCar.getModel());
    final URI location = buildLocation(car.getId());
    return ResponseEntity.created(location).body(car);
  }
  
  private URI buildLocation(String id) {
    return ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/{id}")
                       .buildAndExpand(id)
                       .toUri();
  }
  
  @GetMapping("/{id}")
  public Car findById(@PathVariable String id) {
    return service.findById(id);
  }
  
  @PutMapping("/{id}")
  public Car update(
      @PathVariable String id,
      @Valid @RequestBody CarUpdates carUpdates) {
    return service.update(new Car(id, carUpdates.getModel(), carUpdates.getBrand()));
  }
  
  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    service.delete(id);
  }
}
