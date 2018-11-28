package com.studiobethejustice.mustardcoupon.AndroidRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by econo106 on 2017-06-28.
 */

public class LoginRequest extends StringRequest {

    final static private String URL = "http://168.131.35.106/login2.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
