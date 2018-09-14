package com.example.vitalyyurenya.appetizer.utils;

import com.example.vitalyyurenya.appetizer.api.ProfileApi;

public class UrlConverter {
    public static String convert(String url) {
        if (url != null) {
            return url.startsWith("uploads") ? ProfileApi.BASE_URL + "/" + url : url;
        }
        return "placeholder";
    }

}
