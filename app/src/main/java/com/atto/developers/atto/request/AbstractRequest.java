package com.atto.developers.atto.request;

import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.userdata.NetworkResultTemp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public abstract class AbstractRequest<T> extends NetworkRequest<T> {

    private final static String HOST = "ec2-52-78-136-23.ap-northeast-2.compute.amazonaws.com";
    private final static int HTTPS_PORT = 443;
    private final static int HTTP_PORT = 80;

    protected HttpUrl.Builder getBaseUrlHttpsBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("https");
        builder.host(HOST);
        builder.port(HTTPS_PORT);
        return builder;
    }

    protected HttpUrl.Builder getBaseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("http");
        builder.host(HOST);
        builder.port(HTTP_PORT);
        return builder;
    }

    @Override
    protected T parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        NetworkResultTemp temp = gson.fromJson(text, NetworkResultTemp.class);
        if (temp.getCode() == 1) {
            T result = gson.fromJson(text, getType());
            return result;
        } else if (temp.getCode() == 0) {
            Type type = new TypeToken<ResultMessage>(){}.getType();
            ResultMessage result = gson.fromJson(text, type);
            throw new IOException(result.getMessage());
        } else {
            T result = gson.fromJson(text, getType(temp.getCode()));
            return result;
        }
    }
    protected Type getType(int code) {
        return getType();
    }

    protected abstract Type getType();
}
