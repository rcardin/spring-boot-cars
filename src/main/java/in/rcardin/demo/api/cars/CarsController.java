package in.rcardin.demo.api.cars;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarsController {
  
  @GetMapping
  public List<Car> findAll() {
    return null;
  }
  
  @PostMapping
  public ResponseEntity<Car> create(@RequestBody NewCar newCar) {
    return null;
  }
  
  
}
