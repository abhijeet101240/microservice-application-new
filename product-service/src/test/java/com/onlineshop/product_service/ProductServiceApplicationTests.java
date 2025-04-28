package com.onlineshop.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.product_service.dto.ProductRequest;
import com.onlineshop.product_service.model.Product;
import com.onlineshop.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProductServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MockMvc mockMvc;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry properties){

		properties.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();

		String productRequestAsString = objectMapper.writeValueAsString(productRequest);


		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestAsString))
				.andExpect(status().isCreated());

	Assertions.assertEquals(4,productRepository.findAll().size());
	}

	@Test
	void shouldGetAllProduct() throws Exception {

		Product build1 = Product.builder()
				.name("iphone 13")
				.description("iphone 13 , 256 gb")
				.price(BigDecimal.valueOf(1222))
				.build();

	Product build2 =	Product.builder()
				.name("iphone 11")
				.description("iphone 11 , 256 gb")
				.price(BigDecimal.valueOf(122))
				.build();

		Product build3	= Product.builder()
				.name("iphone 11")
				.description("iphone 11 , 256 gb")
				.price(BigDecimal.valueOf(122))
				.build();

		productRepository.save(build1);
		productRepository.save(build2);
		productRepository.save(build3);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].name").value("iphone 13"))
				.andExpect(jsonPath("$[1].name").value("iphone 11"))
				.andExpect(jsonPath("$[2].name").value("iphone 11"));
	}

	private ProductRequest getProductRequest() {

		ProductRequest build1 = ProductRequest.builder()
				.name("iphone 13")
				.description("iphone 13 , 256 gb")
				.price(BigDecimal.valueOf(1222))
				.build();




		return build1;

	}

}
