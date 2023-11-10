package com.eshop.av.bean;

import com.eshop.av.entity.ShopUser;
import com.eshop.av.repository.ShopUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class DatabaseBackupEncrypted {

    private final ShopUserRepository shopUserRepository;

    public void DatabaseBackup(Exchange ex) throws Exception {

        String secretKey = "7e0b3f8a6d2c1f9e";
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");

        String iv = "a2f4e1d9c8b7a605";
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String jsonBody = null;

        Optional<ShopUser> user;
        List<ShopUser> body = new ArrayList<>();
        long counter = 1;

        do{
             user = shopUserRepository.findById(counter);
             counter++;
            if (user.isEmpty()){
                break;
            } else {
                body.add(user.get());
            }
        }while(true);

        try{

           jsonBody = objectMapper.writeValueAsString(body);

        } catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
        }

        if (jsonBody.isEmpty()){

            throw new Exception("Empty List");

        }

        jsonBody = encrypt(jsonBody, key, ivSpec);

        ex.getIn().setBody(jsonBody);

    }

    private String encrypt(String jsonBody, SecretKey key, IvParameterSpec ivSpec) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(jsonBody.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);

    }
}
