package com.example.PassBox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PassBox.Database.PassBoxDb;

import java.util.List;

public class PassListAdapter extends RecyclerView.Adapter<PassListAdapter.PassViewHolder> {
    private List<PassList> passList;
    private OnItemClickListener listener;  // list item click event listener

    public PassListAdapter(List<PassList> passList,OnItemClickListener listener) { //contractor, it needs  list  and event listener argument
        this.passList = passList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(PassList list);
    }

    public static class PassViewHolder extends RecyclerView.ViewHolder { // inner class for holding recycler view
        TextView nameText;
        ImageView img;
        public PassViewHolder(View itemView) { //inner class constructor
            super(itemView);
            nameText = itemView.findViewById(R.id.passwordDomain);
            img = itemView.findViewById(R.id.passwordIcon);
        }

    }

    @NonNull
    @Override
    public PassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_item, parent, false);
        return new PassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassViewHolder holder, int position) {
        holder.nameText.setText(passList.get(position).getSite());
        holder.img.setImageResource(R.drawable.browser);
        PassList list = passList.get(position);

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(list);
        });

    }

    @Override
    public int getItemCount() {
        return passList.size();
    }
}

