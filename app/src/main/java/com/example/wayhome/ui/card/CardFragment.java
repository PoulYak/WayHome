package com.example.wayhome.ui.card;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.wayhome.data.room.User;
import com.example.wayhome.databinding.FragmentCardBinding;
import com.example.wayhome.ui.profile.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.InertiaMoveListener;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;

public class CardFragment extends Fragment {
    FragmentCardBinding binding;
    private Point TARGET_LOCATION = new Point(59.845933, 30.320045);
    DatabaseReference petsRef;


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
        assert getArguments() != null;
        String petId = getArguments().getString("petId");
        setUpViews(petId);






//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.mapview.setVisibility(View.VISIBLE);
//            }
//        });




        binding.layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Пропал султан"); //todo text for sharing
                sendIntent.setType("text/plain");
//                sendIntent.setType("")
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
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
                    binding.tvOllar.setText(m.isHave_collar()?"В ошейнике":"Без ошейника");
                    binding.tvPhoneNumber.setText("Звонить "+m.getPhone_number());





                    setUpMap(m.getLongitude(), m.getLatitude());
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

    private void setUpMap(float longitude, float latitude){
        TARGET_LOCATION = new Point(latitude, longitude);
        binding.mapview.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }
}