package com.mp.myfilms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mp.myfilms.R;
import com.mp.myfilms.helpers.BitmapHelp;
import com.mp.myfilms.models.Film;

import java.util.ArrayList;
import java.util.List;

public class ListFilmsAdapter extends RecyclerView.Adapter<ListFilmsAdapter.ViewHolder> {

    private List<Film> filmList = new ArrayList<>();

    public ListFilmsAdapter(List<Film> filmList) {
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film film = filmList.get(position);

        holder.date.setText(film.getDateLaunch());
        holder.title.setText(film.getTitle());
        holder.thumblenail.setImageBitmap(BitmapHelp.BytesToBitmap(film.getThumbnail()));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumblenail;
        private TextView title, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumblenail = itemView.findViewById(R.id.img_thumbnail);
            title = itemView.findViewById(R.id.txt_name_films);
            date = itemView.findViewById(R.id.txt_date_item_list);
        }
    }
}
