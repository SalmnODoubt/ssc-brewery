package guru.sfg.brewery.web.controllers.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by jt on 6/13/20.
 */
@SpringBootTest
public class BreweryRestControllerIT extends BaseIT {
  private static final String LIST_BREWERIES_URL = "/brewery/breweries";
  private static final String GET_BREWERIES_URL = "/brewery/api/v1/breweries";

  @Test
  void listBreweries() throws Exception {
    mockMvc.perform(get(LIST_BREWERIES_URL)
                    .with(httpBasic("scott", "tiger")))
            .andExpect(status().is2xxSuccessful());
  }

  @Test
  void listBreweriesForbidden() throws Exception {
    mockMvc.perform(get(LIST_BREWERIES_URL)
                    .with(httpBasic("user", "password")))
            .andExpect(status().isForbidden());
  }

  @Test
  void listBreweriesNoAuth() throws Exception {
    mockMvc.perform(get(LIST_BREWERIES_URL))
            .andExpect(status().isUnauthorized());
  }

  @Test
  void getBreweriesJson() throws Exception {
    mockMvc.perform(get(GET_BREWERIES_URL)
                    .with(httpBasic("scott", "tiger")))
            .andExpect(status().is2xxSuccessful());
  }

  @Test
  void getBreweriesJsonForbidden() throws Exception {
    mockMvc.perform(get(GET_BREWERIES_URL)
                    .with(httpBasic("user", "password")))
            .andExpect(status().isForbidden());
  }

  @Test
  void getBreweriesJsonNoAuth() throws Exception {
    mockMvc.perform(get(GET_BREWERIES_URL))
            .andExpect(status().isUnauthorized());
  }

//    @Test
//    void deleteBeerHttpBasic() throws Exception{
//        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
//                .with(httpBasic("spring", "guru")))
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    void deleteBeerHttpBasicUserRole() throws Exception{
//        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
//                .with(httpBasic("user", "password")))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    void deleteBeerHttpBasicCustomerRole() throws Exception{
//        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
//                .with(httpBasic("scott", "tiger")))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    void deleteBeerNoAuth() throws Exception{
//        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    void findBeers() throws Exception{
//        mockMvc.perform(get("/api/v1/beer/"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void findBeerById() throws Exception{
//        mockMvc.perform(get("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void findBeerByUpc() throws Exception{
//        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
//                .andExpect(status().isOk());
//    }
}
