package com.studiobethejustice.mustardcoupon.AndroidRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by econo106 on 2017-07-07.
 */

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://168.131.35.106/main_user/store.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userNum, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Seq", userNum);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
