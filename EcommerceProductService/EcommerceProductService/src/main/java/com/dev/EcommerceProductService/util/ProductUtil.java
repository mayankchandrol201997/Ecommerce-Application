package com.dev.EcommerceProductService.util;

import java.util.HashMap;
import java.util.UUID;

public class ProductUtil {
    public static boolean isNull(Object object)
    {
        if (object==null)
            return true;
        return false;
    }

    public static UUID toUUID(String id)
    {
        return UUID.fromString(id);
    }

    public static HashMap<String,Object> buildResponseEntity(Object response)
    {
        HashMap<String,Object> responseMap = new HashMap<>();
        responseMap.put("response",response);
        responseMap.put("message","Success");
        return responseMap;
    }
}
