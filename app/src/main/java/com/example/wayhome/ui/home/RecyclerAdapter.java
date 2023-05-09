package com.example.wayhome.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wayhome.R;
import com.example.wayhome.ui.profile.MyMy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ImageView pm;
    private ArrayList<MyMy> arrayList;
    public RecyclerAdapter(ArrayList<MyMy> arrayList){
        this.arrayList=arrayList;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyMy post = arrayList.get(position);
//
        pm = holder.postImage;
//        setUpPhoto(post.getImage_path());
        holder.message.setText(post.getStatus());
        holder.title.setText(post.getNickname());
        holder.itemView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("petId", post.getId());
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_cardFragment, args);
        });


    }
    private void setUpPhoto(String path){
//        if (pm.getIm)
//            return;
        if (Objects.equals(path, ""))
            return;
        StorageReference imageRef = FirebaseStorage.getInstance().getReference(path);

// Create a temporary file to save the downloaded image
        File localFile = null;
        try {
            localFile = File.createTempFile("image", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (localFile != null) {
            // Download the file to the local device
            File finalLocalFile = localFile;
            imageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Picasso.get()
                                .load(finalLocalFile)
                                .into(pm);
                    })
                    .addOnFailureListener(exception -> {
                        // An error occurred while downloading the file
                        // Handle the error
                    });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView title;
        TextView message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.ivPost);
            title = itemView.findViewById(R.id.tvTitle);
            message = itemView.findViewById(R.id.tvPlaceSaw);
        }

    }



}
