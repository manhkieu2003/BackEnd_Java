package com.example.backend_java;

import com.example.backend_java.controller.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackEndJavaApplicationTests {
    @InjectMocks
   private UserController userController;
    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
    }

}
