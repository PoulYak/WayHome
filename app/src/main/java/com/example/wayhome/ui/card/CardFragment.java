package com.example.wayhome.ui.card;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentCardBinding;
import com.example.wayhome.data.room.MyMy;
import com.example.wayhome.ui.home.HomeViewModel;
import com.example.wayhome.ui.utils.Address;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CardFragment extends Fragment {
    FragmentCardBinding binding;
    DatabaseReference petsRef;
    String shareText = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentCardBinding.inflate(inflater, container, false);
        petsRef = FirebaseDatabase.getInstance().getReference("Pets");
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapview.getMap().setZoomGesturesEnabled(false);
        binding.mapview.getMap().setScrollGesturesEnabled(false);
        binding.mapview.getMap().setRotateGesturesEnabled(false);
        binding.mapview.getMap().setTiltGesturesEnabled(false);
        String petId = getArguments().getString("petId");
        setUpViews(petId);



        binding.layoutShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,shareText); //todo text for sharing
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent,"Share via");
            startActivity(sendIntent);
        });
        }

    @Override
    public void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapview.onStart();
    }


    private void setUpViews(String petId){
        petsRef.child(petId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    MyMy m = dataSnapshot.getValue(MyMy.class);
                    binding.tvNickname.setText(m.getNickname());
                    binding.tvBreed.setText(m.getBreed());
                    binding.tvCommentVal.setText(m.getComment());
                    binding.tvSex.setText(m.getSex());
                    binding.tvChipVal.setText(m.getChip_number());
                    binding.tvColorVal.setText(m.getColor());
                    binding.tvStigmaVal.setText(m.getStigma_number());
                    binding.tvFeaturesVal.setText(m.getFeatures());
                    binding.tvOllar.setText(m.getCollar());
                    binding.tvPhoneNumber.setText("Звонить "+m.getPhone_number());
                    binding.tvDate.setText(m.getBirthday());
                    binding.tvPlaceVal.setText(Address.getAddress(requireContext(), m.getLatitude(), m.getLongitude()));
                    if (m.getSex().equals("Девочка"))
                        binding.ivSex.setImageDrawable(getResources().getDrawable(R.drawable.female));
                    binding.tvStatus.setText(m.getStatus());
                    if (m.getStatus().equals("Потерян"))
                        binding.tvStatus.setTextColor(getResources().getColor(R.color.red));
                    else
                        binding.tvStatus.setTextColor(getResources().getColor(R.color.green));

                    setUpMap(m.getLongitude(), m.getLatitude());
                    setUpPhoto(m.getImage_path());
                    shareText = m.getNickname()+" пропал, если вдруг увидешь, звони "+m.getPhone_number();
                } else {
                    // Object with the specified ID does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    private void setUpMap(double longitude, double latitude){
        Point TARGET_LOCATION = new Point(latitude, longitude);
        binding.mapview.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        PlacemarkMapObject mark1= binding.mapview.getMap().getMapObjects().addPlacemark(TARGET_LOCATION);
        mark1.setOpacity(1.0f);
        mark1.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.dog1));
    }

    private void setUpPhoto(String path){
        if (Objects.equals(path, ""))
            return;
        StorageReference imageRef = FirebaseStorage.getInstance().getReference(path);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(requireContext()).load(uri.toString()).into(binding.dogPhoto);
            }
        });
    }


}