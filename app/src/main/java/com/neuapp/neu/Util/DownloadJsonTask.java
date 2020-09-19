package com.neuapp.neu.Util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class DownloadJsonTask extends AsyncTask<URL, Integer, JSONObject> {

    private AlertDialog alertDialog;
    private final Handler handler;
    private OnFinish onFinish;

    @Override
    protected JSONObject doInBackground(final URL... urls) {

        JSONObject jsonObjectResult = null;
        DownloadHttp downloadHttp = new DownloadHttp();

        if(alertDialog!=null)
        handler.post(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });

        try {
            jsonObjectResult = downloadHttp.init(urls[0]);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObjectResult;
    }

    public DownloadJsonTask() {
        super();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPostExecute(@Nullable JSONObject jsonObjectResult) {

        Log.d("AsyncTask", "Terminou");
        super.onPostExecute(jsonObjectResult);

        if(alertDialog!=null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                }
            });
        }

        if(onFinish!=null)
            onFinish.onFinishResult(jsonObjectResult);
    }

    public void setDialog(AlertDialog alertDialog)
    {
        this.alertDialog = alertDialog;
    }
    public void setOnFinish(OnFinish onFinish)
    {
        this.onFinish = onFinish;
    }

    public interface OnFinish
    {
        void onFinishResult(@Nullable JSONObject jsonObjectResult);
    }
}
