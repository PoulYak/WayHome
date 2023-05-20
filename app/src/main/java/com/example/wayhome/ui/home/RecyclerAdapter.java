package com.example.wayhome.ui.home;

import android.content.Context;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.wayhome.R;
import com.example.wayhome.data.room.MyMy;
import com.example.wayhome.ui.utils.Address;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ImageView pm;
    Context context;
    private ArrayList<MyMy> arrayList;

    public RecyclerAdapter(Context context) {
        this.arrayList = new ArrayList<>();
        this.context = context;
    }

    public void setData(ArrayList<MyMy> arrayList){
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

        pm = holder.postImage;


        String path = post.getImage_path();
        if (Objects.equals(path, ""))
            return;
        StorageReference imageRef = FirebaseStorage.getInstance().getReference(path);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .apply(new RequestOptions().placeholder(R.drawable.pets)).fitCenter()
                        .into(holder.postImage);
            }
        });

        holder.status.setText(post.getStatus());
        if (post.getStatus().equals("Потерян"))
            holder.status.setTextColor(context.getColor(R.color.red));
        else
            holder.status.setTextColor(context.getColor(R.color.green));

        String address = Address.getAddress(context, post.getLatitude(), post.getLongitude());
        int limit = 30;
        if (address.length()>limit)
            address = address.substring(0, limit)+"...";
        holder.address.setText(address);
        holder.title.setText(post.getNickname());

        holder.phoneNumber.setText(post.getPhone_number());
        holder.timeLost.setText(post.getBirthday());
        holder.itemView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("petId", post.getId());
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_cardFragment, args);
        });


    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView title;
        TextView address;
        TextView timeLost;
        TextView status;
        TextView phoneNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.ivPost);
            title = itemView.findViewById(R.id.tvTitle);
            status = itemView.findViewById(R.id.tvStatusOfPet);
            timeLost = itemView.findViewById(R.id.ivBookmark);
            phoneNumber = itemView.findViewById(R.id.tvPhone);
            address = itemView.findViewById(R.id.tvPlaceSaw);

        }

    }



}
