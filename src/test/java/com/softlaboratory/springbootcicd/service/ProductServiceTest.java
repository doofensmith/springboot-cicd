package com.softlaboratory.springbootcicd.service;

import com.softlaboratory.springbootcicd.domain.dao.ProductDao;
import com.softlaboratory.springbootcicd.domain.dto.ProductDto;
import com.softlaboratory.springbootcicd.repository.ProductRepository;
import com.softlaboratory.springbootcicd.util.BaseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductService.class)
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @Test
    void getAllProductNotNull_Success_Test() {

        //mock product dao
        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .name("Test product")
                .build();

        when(productRepository.findAll()).thenReturn(List.of(productDao));
        when(mapper.map(any(), eq(ProductDto.class))).thenReturn(
                ProductDto.builder()
                        .id(1L)
                        .name("Test product")
                        .build()
        );

        //test service
        ResponseEntity<Object> responseEntity = productService.getAllProduct();
        BaseResponse baseResponse = (BaseResponse) responseEntity.getBody();
        List<ProductDto> productDtoList = (List<ProductDto>) baseResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, productDtoList.size());

    }

    @Test
    void getAllProductNull_Success_Test() {
        //mock product
        ProductDto productDto = new ProductDto();

        //when(productRepository.findAll()).thenReturn(null);
        when(mapper.map(any(),eq(ProductDto.class))).thenReturn(productDto);

        //test service
        ResponseEntity<Object> responseEntity = productService.getAllProduct();
        BaseResponse baseResponse = (BaseResponse) responseEntity.getBody();
        List<ProductDto> productDtoList = (List<ProductDto>) baseResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0, productDtoList.size());

    }

    @Test
    void getAllProductError_Exception_Test() {

        //mock
        when(productService.getAllProduct()).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = productService.getAllProduct();

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getProductByIdNotNull_Success_Test() {
        //mocking
        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .build();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productDao));
        when(mapper.map(any(), eq(ProductDto.class))).thenReturn(ProductDto.builder()
                .id(1L)
                .build());

        //test service
        ResponseEntity<Object> responseEntity = productService.getProductById(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getProductByIdNotFound_Test() {

        //test service
        ResponseEntity<Object> responseEntity = productService.getProductById(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void getProductById_Error_Test() {

        //mocking
        when(productService.getProductById(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = productService.getProductById(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void saveProductWithPayload_Success_Test() {
        //mocking
        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .build();

        //payload
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("Test Product")
                .build();

        when(mapper.map(any(),eq(ProductDao.class))).thenReturn(productDao);
        when(mapper.map(any(),eq(ProductDto.class))).thenReturn(productDto);
        when(productRepository.save(any())).thenReturn(productDao);

        //test service
        ResponseEntity<Object> responseEntity = productService.saveNewProduct(
                ProductDto.builder()
                        .id(1L)
                        .name("Test Product")
                        .build()
        );
        BaseResponse baseResponse = (BaseResponse) responseEntity.getBody();
        ProductDto resultData = (ProductDto) Objects.requireNonNull(baseResponse).getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(productDto.getName(), resultData.getName());

    }

    @Test
    void saveNewProductException_Error_Test() {
        //mocking
        when(productService.saveNewProduct(mapper.map(any(),eq(ProductDto.class)))).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = productService.saveNewProduct(ProductDto.builder().build());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateProduct_Success_Test() {
        //mocking
        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .name("Test Product")
                .build();

        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("Update Test")
                .build();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productDao));
        when(mapper.map(any(),eq(ProductDao.class))).thenReturn(productDao);
        when(mapper.map(any(),eq(ProductDto.class))).thenReturn(productDto);

        //test service
        ResponseEntity<Object> responseEntity = productService.updateProduct(
                anyLong(),
                ProductDto.builder()
                        .name("Update Test")
                        .build()
        );

        BaseResponse baseResponse = (BaseResponse) responseEntity.getBody();
        ProductDto resultData = (ProductDto) baseResponse.getData();

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(productDto.getName(),resultData.getName());

    }

    @Test
    void updateProductNotFound_Failed_Test() {
        //mocking
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = productService.updateProduct(
                anyLong(),
                ProductDto.builder()
                        .name("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void updateProductException_Error_Test() {
        //mocking
        when(productRepository.save(any())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = productService.updateProduct(
                anyLong(),
                ProductDto.builder()
                        .name("Update Test")
                        .build()
        );

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    void deleteProduct_Success_Test() {
        //mocking
        ProductDao productDao = ProductDao.builder()
                .id(1L)
                .build();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productDao));

        //test service
        ResponseEntity<Object> responseEntity = productService.deleteProduct(anyLong());

        //assertion
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteProductIdNotFound_Failed_Test() {
        //mocking
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        //test service
        ResponseEntity<Object> responseEntity = productService.deleteProduct(anyLong());

        //assertion
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteProductException_Error_Test() {
        //mocking
        //doThrow().when(productRepository).deleteById(anyLong());
        when(productService.deleteProduct(anyLong())).thenThrow();

        //test service
        ResponseEntity<Object> responseEntity = productService.deleteProduct(anyLong());

        //assertion
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
    }


}