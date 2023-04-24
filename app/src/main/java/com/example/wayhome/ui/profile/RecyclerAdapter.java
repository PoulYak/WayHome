package com.example.wayhome.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wayhome.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<MyMy> arrayList;
    public RecyclerAdapter(ArrayList<MyMy> arrayList){
        this.arrayList=arrayList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view_profile, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyMy post = arrayList.get(position);

//        holder.title.setText(post.getTitle());
//        holder.message.setText(post.getMessage());
        holder.postImage.setImageResource(post.getPostImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView nickname;
        TextView breed;
        TextView status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            postImage = itemView.findViewById(R.id.ivPost);
            nickname = itemView.findViewById(R.id.name);
            breed = itemView.findViewById(R.id.breed);
        }
    }

}
