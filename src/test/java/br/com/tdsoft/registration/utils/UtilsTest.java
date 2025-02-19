package br.com.tdsoft.registration.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsTest {

    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "{}"; // Retorna um JSON vazio em caso de erro
        }
    }
}
