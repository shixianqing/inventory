package com.inventory.inventory;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryApplicationTests {

    @Value("${spring.datasource.url}")
    private String jdbc;

    @Test
    public void test(){

        BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
        stringEncryptor.setPassword("0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*~");
        String result = stringEncryptor.encrypt("Pw123456!");
        System.out.println(result);//

//        System.out.println(jdbc);
    }
    @Test
    public void test1(){
        BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
        stringEncryptor.setPassword("0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*~");
        String result = stringEncryptor.decrypt("7YS9goVhCjIY3G/99ebOboQkOOPVghDd4+WpLJLO/L+POVj6Y9IYcQG8gbYd3dxb8BbVzHP0BWGVGEd14vuMciIH2DFxqwUsSPYEPRKh9R+kq7ivzBvC9cyR2fgp6G34gMWfn3muUoHW2AFoqhh0+QFgL3r+u45tn3BuLUBN/Y6VyLy0tUSliwx28r/dwrBN/po9ULis8rGWJKXACZ2KyA==");
        System.out.println(result);//cAmfuiuRmieqaZxigapzQw==
    }



}

