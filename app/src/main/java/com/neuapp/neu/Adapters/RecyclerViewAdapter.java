package com.neuapp.neu.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.neuapp.neu.Models.Repositorio;
import com.neuapp.neu.Models.Repositorios;
import com.neuapp.neu.R;
import com.neuapp.neu.Util.GlideApp;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RepositorioHolder> {

    private Repositorios repositorios;
    private Context context;

    public RecyclerViewAdapter(Repositorios repositorios, Context context) {

        this.repositorios = repositorios;
        this.context = context;
    }

    @NonNull
    @Override
    public RepositorioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new RepositorioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositorioHolder holder, int position) {
        Repositorio repositorio = repositorios.get(position);

        String name = repositorio.getName();
        String login = repositorio.getLogin();
        int stars = repositorio.getStars();

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.user_image);
        GlideApp.with(context).load(Uri.parse(repositorio.getAvatarUrl())).apply(requestOptions).into(holder.getAvatarReference());

        holder.vincula(name, login, stars);
    }

    @Override
    public int getItemCount() {

        if (repositorios == null)
            return 0;
        else
            return repositorios.getCount();
    }

    public static class RepositorioHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView login;
        private TextView stars;
        private ImageView avatar;

        public RepositorioHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            login = itemView.findViewById(R.id.login);
            stars = itemView.findViewById(R.id.stars);
            avatar = itemView.findViewById(R.id.avatar);
        }

        public void vincula(String name, String login, int stars) {
            this.name.setText(name);
            this.login.setText(login);
            this.stars.setText(String.valueOf(stars));
        }

        public ImageView getAvatarReference()
        {
            return avatar;
        }
    }
}