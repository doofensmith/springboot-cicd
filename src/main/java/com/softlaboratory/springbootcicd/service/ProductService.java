package com.softlaboratory.springbootcicd.service;

import com.softlaboratory.springbootcicd.constant.AppConstant;
import com.softlaboratory.springbootcicd.domain.dao.ProductDao;
import com.softlaboratory.springbootcicd.domain.dto.ProductDto;
import com.softlaboratory.springbootcicd.repository.ProductRepository;
import com.softlaboratory.springbootcicd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Object> getAllProduct() {
        List<ProductDao> productDaoList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDao productDao : productDaoList) {
            productDtoList.add(ProductDto.builder()
                    .id(productDao.getId())
                    .name(productDao.getName())
                    .description(productDao.getDescription())
                    .stock(productDao.getStock())
                    .price(productDao.getPrice())
                    .build()
            );
        }

        return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, productDtoList);

    }

    public ResponseEntity<Object> getProductById(Long id) {
        Optional<ProductDao> productDao = productRepository.findById(id);

        if (productDao.isPresent()) {
            ProductDto productDto = ProductDto.builder()
                    .id(productDao.get().getId())
                    .name(productDao.get().getName())
                    .description(productDao.get().getDescription())
                    .stock(productDao.get().getStock())
                    .price(productDao.get().getPrice())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, productDto);

        }else {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
        }
    }

    public ResponseEntity<Object> saveNewProduct(ProductDto payload) {
        ProductDao productDao = ProductDao.builder()
                .name(payload.getName())
                .description(payload.getDescription())
                .stock(payload.getStock())
                .price(payload.getPrice())
                .build();
        productRepository.save(productDao);

        ProductDto productDto = ProductDto.builder()
                .id(productDao.getId())
                .name(productDao.getName())
                .description(productDao.getDescription())
                .stock(productDao.getStock())
                .price(productDao.getPrice())
                .build();

        return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_CREATED, productDto);
    }

    public ResponseEntity<Object> updateProduct(Long id, ProductDto payload) {
        Optional<ProductDao> productDaoOld = productRepository.findById(id);

        if (productDaoOld.isPresent()) {
            ProductDao productDaoNew = productDaoOld.get();
            productDaoNew.setName(payload.getName());
            productDaoNew.setDescription(payload.getDescription());
            productDaoNew.setStock(payload.getStock());
            productDaoNew.setPrice(payload.getPrice());
            productRepository.save(productDaoNew);

            ProductDto productDto = ProductDto.builder()
                    .id(productDaoNew.getId())
                    .name(productDaoNew.getName())
                    .description(productDaoNew.getDescription())
                    .stock(productDaoNew.getStock())
                    .price(productDaoNew.getPrice())
                    .build();

            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_UPDATED, productDto);

        }else {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
        }
    }

    public ResponseEntity<Object> deleteProduct(Long id) {
        Optional<ProductDao> productDao = productRepository.findById(id);

        if (productDao.isPresent()) {
            productRepository.deleteById(id);
            return ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_DELETED, null);
        }else {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, AppConstant.KEY_DATA_NOT_FOUND, null);
        }
    }

}
