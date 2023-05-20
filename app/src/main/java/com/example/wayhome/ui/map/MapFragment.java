package com.example.wayhome.ui.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentMapBinding;
import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectDragListener;
import com.yandex.mapkit.map.MapObjectTapListener;

import java.io.IOException;
import java.util.ArrayList;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment{
    FragmentMapBinding binding;
    private MapViewModel viewModel;
    DatabaseReference petRefs;
    MapObjectCollection mapObjects;
    static String TAG ="LOCATION";

    int delay = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  FragmentMapBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        binding.setViewModel(viewModel);
        petRefs = FirebaseDatabase.getInstance().getReference("Pets");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapObjects = binding.mapview.getMap().getMapObjects().addCollection();
        petRefs.addValueEventListener(new ValueEventListener() {
                        @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<MyMy> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyMy m = dataSnapshot.getValue(MyMy.class);
                    arrayList.add(m);
                }
                viewModel.updateLocationData(arrayList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        subscribeToLocationDataList();
        restoreMapState();

    }




    private void subscribeToLocationDataList() {
        binding.getViewModel().getLocationDataList().observe(getViewLifecycleOwner(), new Observer<List<MyMy>>() {
            @Override
            public void onChanged(List<MyMy> locationDataList) {
                // Очищаем карту от предыдущих маркеров
                mapObjects.clear();

                // Добавляем новые маркеры на карту
                for (MyMy m : locationDataList) {
                    Point point = new Point(m.getLatitude(), m.getLongitude());
                    PlacemarkMapObject mark = mapObjects.addPlacemark(point);
                    mark.setOpacity(0.7f);
                    mark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.dog1));
                    mark.addTapListener(tapListener);
                    mark.setUserData(m);
                }

            }
        });
    }

    private void restoreMapState() {
        if (binding.mapview.getMap() != null) {
            viewModel.getCameraPosition().observe(getViewLifecycleOwner(), new Observer<CameraPosition>() {
                @Override
                public void onChanged(CameraPosition cameraPosition) {
                    if (cameraPosition != null) {
//                         cameraUpdate = new CameraPosition(cameraPosition);
                        binding.mapview.getMap().move(cameraPosition);
                    }
                }
            });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        binding.mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding.mapview.getMap() != null) {
            CameraPosition cameraPosition = binding.mapview.getMap().getCameraPosition();
            viewModel.setCameraPosition(cameraPosition);
        }
    }
    MapObjectTapListener tapListener = (mapObject, point) -> {
        if (delay==0){
            MyMy m = (MyMy)mapObject.getUserData();

            Bundle args = new Bundle();
            args.putString("petId", m.getId());
            delay = 1000;
            new Thread(() -> {
                try {
                    Thread.sleep(delay);
                    delay = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            Navigation.findNavController(requireView()).navigate(R.id.action_mapFragment_to_cardFragment, args);
        }



        return false;
    };


}

