package jp.falsystack.cafekiosk.spring.api.controller.product;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import jp.falsystack.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import jp.falsystack.cafekiosk.spring.api.service.product.ProductService;
import jp.falsystack.cafekiosk.spring.domain.product.ProductSellingStatus;
import jp.falsystack.cafekiosk.spring.domain.product.ProductType;
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

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("新規商品を登録する")
  void createProduct() throws Exception {
    // given
    var request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("Americano")
        .price(400)
        .build();
    var json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
  void createProductWithoutType() throws Exception {
    // given
    var request = ProductCreateRequest.builder()
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("Americano")
        .price(400)
        .build();
    var json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 타입은 필수입니다."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("신규 상품을 등록할 때 상품 판매상태는 필수값이다.")
  void createProductWithoutSellingStatus() throws Exception {
    // given
    var request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .name("Americano")
        .price(400)
        .build();
    var json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 판매상태는 필수입니다."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("신규 상품을 등록할 때 상품 이름은 필수값이다.")
  void createProductWithoutProductName() throws Exception {
    // given
    var request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .price(400)
        .build();
    var json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 이름은 필수입니다."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("신규 상품을 등록할 때 상품 가격은 양수이어야.")
  void createProductWithZeroPrice() throws Exception {
    // given
    var request = ProductCreateRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("Americano")
        .price(0)
        .build();
    var json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("판매상품을 조회한다.")
  void getSellingProducts() throws Exception {
    // given
    when(productService.getSellingProducts()).thenReturn(List.of());

    // expected
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/selling"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andDo(MockMvcResultHandlers.print());
  }
}