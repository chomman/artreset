package com.artreset.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Taehyun Jung
 */
public class TestUtil {

    private static final String CHARACTER = "a";

    public static byte[] convertObjectToFormUrlEncodedBytes(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, Object> propertyValues = mapper.convertValue(object, Map.class);

        Set<String> propertyNames = propertyValues.keySet();
        Iterator<String> nameIter = propertyNames.iterator();

        StringBuilder formUrlEncoded = new StringBuilder();

        for (int index=0; index < propertyNames.size(); index++) {
            String currentKey = nameIter.next();
            Object currentValue = propertyValues.get(currentKey);

            formUrlEncoded.append(currentKey);
            formUrlEncoded.append("=");
            formUrlEncoded.append(currentValue);

            if (nameIter.hasNext()) {
                formUrlEncoded.append("&");
            }
        }

        return formUrlEncoded.toString().getBytes();
    }

    /**
     * 주어진 URL로 Redirect 경로를 생성해 반환한다
     * @param path
     * @return
     */
    public static String createRedirectViewPath(String path) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(path);
        return redirectViewPath.toString();
    }

    /**
     * 임의의 문자열을 주어진 길이만큼 생성해 반환한다
     * @param length
     * @return
     */
    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }
}
