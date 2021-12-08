package com.restaurant.acquerello.util;

import java.util.LinkedHashMap;
import java.util.Map;

//CREATED BY GABRIEL

public class Util {
    public static Map<String,Object> makeDTO(String key, Object value){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put(key, value);
        return dto;
    }

    public static String cardNumber(){
        String cardNumber = "";
        for (int i =0; i < 4; i++){
            int randomNumber= (int) ((Math.random()*(9999-1000)+1000));
            cardNumber= cardNumber.concat(Integer.toString(randomNumber));
            if (i!=3){
                cardNumber = cardNumber.concat("-");
            }
        }
        return cardNumber;
    }

    public static int cvvCard(){
        return (int)(Math.random()*(999-100)+100);
    }
}
