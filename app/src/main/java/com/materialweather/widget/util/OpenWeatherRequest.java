package com.materialweather.widget.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.materialweather.widget.model.OpenWeatherData;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by
 * Gson.
 */
public class OpenWeatherRequest extends Request<OpenWeatherData> {
    private final Map<String, String> headers;
    private final Listener<OpenWeatherData> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param headers Map of request headers
     */
    public OpenWeatherRequest(String url, Map<String, String> headers, Listener<OpenWeatherData> listener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.setRetryPolicy(new MaterialWeatherPolicy());
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(OpenWeatherData response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<OpenWeatherData> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(App.getInstance().getGson().fromJson(json, OpenWeatherData.class), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
