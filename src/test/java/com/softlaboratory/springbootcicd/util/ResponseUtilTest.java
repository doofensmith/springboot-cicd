package com.softlaboratory.springbootcicd.util;

import com.softlaboratory.springbootcicd.constant.AppConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ResponseUtil.class)
class ResponseUtilTest {

    @Test
    void build_ResponseSuccess_Test() {

        ResponseEntity<Object> response = ResponseUtil.build(HttpStatus.OK, AppConstant.KEY_SUCCESS, null);

        //expected 200
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

    }
}