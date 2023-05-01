package com.example.wayhome.ui.camera;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wayhome.R;

import java.util.ArrayList;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {

    private ArrayList<Photo> arrayList;
    public PhotoRecyclerAdapter(ArrayList<Photo> arrayList){
        this.arrayList=arrayList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo post = arrayList.get(position);


        holder.postImage.setImageURI(post.getImageUri());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.ivPet);
        }
    }

}
