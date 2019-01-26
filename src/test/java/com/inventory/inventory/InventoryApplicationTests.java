package com.inventory.inventory;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryApplicationTests {



    @Test
    public void test(){
        BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
        stringEncryptor.setPassword("");
        String result = stringEncryptor.encrypt("123456");
        System.out.println(result);
    }

}

