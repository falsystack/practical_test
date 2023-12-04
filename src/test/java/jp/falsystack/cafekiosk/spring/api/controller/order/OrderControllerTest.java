package jp.falsystack.cafekiosk.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {OrderController.class})
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private OrderService orderService;

  @Test
  @DisplayName("신규 주문을 등록한다.")
  void createOrder() throws Exception {
    // given
    var request = OrderCreateRequest.builder()
        .productNumbers(List.of("001"))
        .build();
    var json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
        .andDo(MockMvcResultHandlers.print());
  }

}