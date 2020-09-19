package com.neuapp.neu.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repositorios {

    public int getCount() {
        return respositories.size();
    }

    private int total_count;
    private List<Repositorio> respositories = new ArrayList<>();

    public Repositorios(JSONObject jsonObjectResult) throws JSONException {

        total_count = jsonObjectResult.getInt("total_count");
        Log.d("Repositories", "total_count: " + total_count);

        JSONArray items = jsonObjectResult.getJSONArray("items");

        for(int i=0; i<items.length() ; i++)
        {
            //Objetos
            JSONObject jsonObject = (JSONObject) items.get(i);
            JSONObject owner = jsonObject.getJSONObject("owner");

            //Essencial
            int id = jsonObject.getInt("id");
            Log.d("Repositories", "id: " + id);

            String name = jsonObject.getString("name");
            Log.d("Repositories", "name: " + name);

            String login = owner.getString("login");
            Log.d("Repositories", "login: " + login);

            String url = jsonObject.getString("url");

            //Outros
            int stars = jsonObject.getInt("stargazers_count");

            String avatarUrl = owner.getString("avatar_url");
            Log.d("Repositories", "avatar_url: " + avatarUrl);

            String description = jsonObject.getString("description");

            //Principais
            Repositorio repositorio = new Repositorio(name, login, id, url);

            //Outros
            repositorio.setStars(stars);
            repositorio.setAvatarUrl(avatarUrl);
            repositorio.setDescription(description);

            respositories.add(repositorio);
        }
    }

    public Repositorio get(int position)
    {
        if(respositories==null)
            return null;

        if(position<=respositories.size()&&position!=-1)
            return respositories.get(position);
        else
            return null;
    }
}
