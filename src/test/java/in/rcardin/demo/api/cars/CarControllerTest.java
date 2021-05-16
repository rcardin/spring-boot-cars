package in.rcardin.demo.api.cars;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {CarController.class, CarControllerAdvice.class})
class CarControllerTest {

  @MockBean
  private CarService service;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void findShouldReturnAListOfCars() throws Exception {
    given(service.find("Ford", "Mondeo"))
        .willReturn(List.of(new Car("id", "Ford", "Mondeo")));
    mockMvc
        .perform(
            get("/cars")
                .param("brand", "Ford")
                .param("model", "Mondeo"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            content().json("[{\"id\": \"id\", \"brand\": \"Ford\", \"model\": \"Mondeo\"}]"));
  }

  @Test
  void createShouldCreateANewCarIfInputsAreFine() throws Exception {
    given(service.create("Ford", "Mondeo")).willReturn(new Car("id", "Ford", "Mondeo"));
    mockMvc
        .perform(
            post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"Ford\", \"model\": \"Mondeo\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\"id\": \"id\", \"brand\": \"Ford\", \"model\": \"Mondeo\"}"));
  }

  @Test
  void createShouldReturnA400StatusIfBrandIsEmpty() throws Exception {
    mockMvc
        .perform(
            post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"\", \"model\": \"Mondeo\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("[brand: must not be blank]"));
  }

  @Test
  void createShouldReturnA400StatusIfModelIsEmpty() throws Exception {
    mockMvc
        .perform(
            post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"Ford\", \"model\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("[model: must not be blank]"));
  }

  @Test
  void findByIdShouldReturn404IfNoCarHasTheGivenId() throws Exception {
    given(service.findById("id1"))
        .willThrow(new NoSuchElementException("No car with id id1 found"));
    mockMvc
        .perform(
            get("/cars/id1"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("No car with id id1 found"));
  }
  
  @Test
  void findByIdShouldReturnTheCarWithTheGivenId() throws Exception {
    given(service.findById("id"))
        .willReturn(new Car("id", "Ford", "Mondeo"));
    mockMvc
        .perform(
            get("/cars/id"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\"id\": \"id\", \"brand\": \"Ford\", \"model\": \"Mondeo\"}"));
  }

  @Test
  void updateShouldReturn404IfNoCarHasTheGivenId() throws Exception {
    given(service.update(new Car("id1", "Ford", "Fiesta")))
        .willThrow(new NoSuchElementException("No car with id id1 found"));
    mockMvc
        .perform(
            put("/cars/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"Ford\", \"model\": \"Fiesta\"}"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("No car with id id1 found"));
  }
  
  @Test
  void updateShouldReturn400IfTheBrandIsEmpty() throws Exception {
    mockMvc
        .perform(
            put("/cars/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"\", \"model\": \"Fiesta\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("[brand: must not be blank]"));
  }
  
  @Test
  void updateShouldReturn400IfTheModelIsEmpty() throws Exception {
    mockMvc
        .perform(
            put("/cars/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"Ford\", \"model\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("[model: must not be blank]"));
  }
  
  @Test
  void updateShouldReturnTheUpdatedCar() throws Exception {
    final Car fiesta = new Car("id1", "Ford", "Fiesta");
    given(service.update(fiesta)).willReturn(fiesta);
    mockMvc
        .perform(
            put("/cars/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":  \"Ford\", \"model\": \"Fiesta\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\"id\": \"id1\", \"brand\": \"Ford\", \"model\": \"Fiesta\"}"));
  }

  @Test
  void deleteShouldReturn404IfNoCarHasTheGivenId() throws Exception {
    BDDMockito.willThrow(new NoSuchElementException("No car with id id1 found"))
        .given(service).delete("id1");
    mockMvc
        .perform(
            delete("/cars/id1"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.description").value("No car with id id1 found"));
  }
  
  @Test
  void deleteShouldDeleteTheCarWithTheGivenId() throws Exception {
    mockMvc
        .perform(
            delete("/cars/id"))
        .andExpect(status().isOk());
  }
}
