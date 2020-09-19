package com.neuapp.neu.Util;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadHttp {

    private OkHttpClient client;

    public DownloadHttp() {
        client = new OkHttpClient();
    }

    @Nullable
    public JSONObject init(URL url) throws IOException, JSONException {

        JSONObject jsonObject = null;

        Log.d("okhttp", "Builder request");
        Request request = new Request.Builder().url(url).build();
        Log.d("okhttp", "Call request");
        Call call = client.newCall(request);
        Response response = call.execute();
        Log.d("okhttp", response.toString());

        String string = null;

        if (response.body() != null)
            string = Objects.requireNonNull(response.body()).string();

        if (string != null)
            jsonObject = new JSONObject(string);

        return jsonObject;
    }
}
