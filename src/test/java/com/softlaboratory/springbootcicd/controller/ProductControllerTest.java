package com.softlaboratory.springbootcicd.controller;

import com.softlaboratory.springbootcicd.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductController.class)
@AutoConfigureMockMvc
@EnableWebMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllProduct_Success_Test() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult response = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());

    }

    @Test
    void getAllProduct_Exception_Test() {

        when(productController.getAllProduct()).thenThrow(BadRequest.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

//        MvcResult mvcResult = mockMvc
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequest))
//                .andReturn();

        assertThrows(Exception.class, () -> mockMvc.perform(requestBuilder).andReturn());

    }

    @Test
    void getProductById_Success_Test() throws Exception {

        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .get("/api/product/{id}",1);

        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void getProductById_Exception_Test() throws Exception {

        when(productController.getProductById(anyLong())).thenThrow(BadRequest.class);

        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .get("/api/product/{id}",1);

        assertThrows(Exception.class,()->mockMvc.perform(requestBuilder).andReturn());

    }

    @Test
    void saveNewProduct_Success_Test() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void saveNewProduct_Exception_Test() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/product")
                .content("{}");

        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

    }

    @Test
    void updateProduct_Success_Test() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/api/product/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void updateProduct_Exception_Test() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/api/product/{id}",1)
                .content("{}");

        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    @Test
    void deleteProduct_Success_Test() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/product/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void deleteProduct_Exception_Test() throws Exception {

        when(productController.deleteProduct(anyLong())).thenThrow(BadRequest.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/product/{id}",1)
                .content("{}");

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(requestBuilder)
                        .andReturn();
            }
        });

    }

}