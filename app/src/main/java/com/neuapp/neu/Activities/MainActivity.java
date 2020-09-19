package com.neuapp.neu.Activities;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neuapp.neu.Adapters.RecyclerViewAdapter;
import com.neuapp.neu.Models.Repositorios;
import com.neuapp.neu.R;
import com.neuapp.neu.Util.DownloadJsonTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**Implementação da api de repositorios do GitHub, url: https://developer.github.com/v3/repos/#list-public-repositories
 * @author Irineu A. Silva
 * @version 1.0
 * @since Debug 01 da aplicação
 */

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private DownloadJsonTask downloadJsonTask;
    private DownloadJsonTask.OnFinish onFinish;
    private AlertDialog errorAlertDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.lista_repositorios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        configDialogBuilder();
        configAsyncTask();

        //start download
        downloadJson();
    }

    private void showErrorDialog() {
        if(errorAlertDialog==null)
            configErrorDialog();
        errorAlertDialog.show();
    }

    private void configErrorDialog() {

        errorAlertDialog = new AlertDialog.Builder(MainActivity.this).create();

        errorAlertDialog.setCancelable(false);
        errorAlertDialog.setTitle(getString(R.string.error_json));
        errorAlertDialog.setMessage(getString(R.string.error_internet));
        errorAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                getString(R.string.btn_tentar_novamente),
                new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                configAsyncTask();
                downloadJsonTask.setOnFinish(onFinish);
                downloadJson();
            }
        });
        errorAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
                getString(R.string.fechar_aplicacao),
                new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
    }

    private void configAsyncTask() {
        downloadJsonTask = new DownloadJsonTask();
        AlertDialog alertDialog = dialogBuilder.create();
        downloadJsonTask.setDialog(alertDialog);
        onFinish = new DownloadJsonTask.OnFinish() {
            @Override
            public void onFinishResult(final JSONObject jsonObjectResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonObjectResult == null) {
                            showErrorDialog();
                        } else {
                            try {
                                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(new Repositorios(jsonObjectResult), MainActivity.this);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
        downloadJsonTask.setOnFinish(onFinish);
    }

    private void downloadJson() {
        try {
            String url = "https://api.github.com/search/repositories?q=language:swift&sort=stars";
            downloadJsonTask.execute(new URL(url));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void configDialogBuilder() {
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.carregando);
        dialogBuilder.setView(R.layout.dialog_progressbar);
        dialogBuilder.setCancelable(false);
    }
}