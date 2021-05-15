package in.rcardin.demo.api.cars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CarService {

  private final Map<String, Car> cars = new HashMap<>();

  /**
   * Creates a new car with the given information.
   *
   * @return The created car containing its id
   */
  public Car create(String brand, String model) {
    final String carId = UUID.randomUUID().toString();
    final Car car = new Car(carId, model, brand);
    cars.put(carId, car);
    return car;
  }

  /**
   * Removes the car with the given {@code id}.
   *
   * @throws NoSuchElementException If there is no car with the given id
   */
  public void delete(String id) {
    if (!cars.containsKey(id)) {
      throwNoCarFoundWithId(id);
    }
    cars.remove(id);
  }

  /**
   * Updates the persisted car with the given information.
   *
   * @throws NoSuchElementException If there is no car with the given id
   */
  public Car update(Car car) {
    final String carId = car.getId();
    if (!cars.containsKey(carId)) {
      throwNoCarFoundWithId(car.getId());
    }
    cars.put(carId, car);
    return car;
  }

  /**
   * Returns the car with the given {@code id}.
   *
   * @throws NoSuchElementException If there is no car with the given id
   */
  public Car findById(String id) {
    final Car car = cars.get(id);
    if (car == null) {
      throwNoCarFoundWithId(id);
    }
    return car;
  }

  private void throwNoCarFoundWithId(String id) {
    throw new NoSuchElementException(String.format("No car with id %s found", id));
  }
  
  /**
   * Returns the list of cars matching the given information. Both {@code brand} and {@code model}
   * can be empty. Empty parameters are not considered during filtering.
   * @return The list of cars matching the given information
   */
  public List<Car> find(String brand, String model) {
    return cars.values().stream()
        .filter(car -> match(car, brand, model))
        .collect(Collectors.toList());
  }

  private boolean match(Car car, String brand, String model) {
    return (!StringUtils.hasText(brand) || car.getBrand().equalsIgnoreCase(brand))
        && (!StringUtils.hasText(model) || car.getModel().equalsIgnoreCase(model));
  }
}
