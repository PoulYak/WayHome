package com.example.wayhome.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectDragListener;
import com.yandex.mapkit.map.MapObjectTapListener;

import java.util.ArrayList;


public class MapFragment extends Fragment {
    FragmentMapBinding mBinding;
    MapViewModel viewModel;
    private ArrayList<Point> pointsList = new ArrayList<>();
    DatabaseReference petRefs;
    int delay = 0;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding =  FragmentMapBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mBinding.setViewModel(viewModel);
        petRefs = FirebaseDatabase.getInstance().getReference("Pets");




        return mBinding.getRoot();
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.setMapObjects(mBinding.mapview.getMap().getMapObjects().addCollection());
        petRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyMy m = dataSnapshot.getValue(MyMy.class);

                    assert m != null;
                    viewModel.addPoint(m, tapListener, dragListener, requireContext());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (!viewModel.isActive()){

            mBinding.mapview.getMap().move(
                    new CameraPosition(new Point(55.75254900905604, 37.62317657062987), 14.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 5),
                    null);
        }
        else{
//            mBinding.mapview.getMap().move(
//                    new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
//                    new Animation(Animation.Type.SMOOTH, 0),
//                    null);
//            PlacemarkMapObject mark = viewModel.getMapObjects().addPlacemark(TARGET_LOCATION);
//            mark.setOpacity(0.7f);
//            mark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.dog1));
//            mark.setDraggable(true);


        }







    }





//    InputListener inputListener = new InputListener() {
//        @Override
//        public void onMapTap(@NonNull Map map, @NonNull Point point) {
//            Toast.makeText(requireContext(), point.getLatitude()+" "+point.getLongitude(), Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onMapLongTap(@NonNull Map map, @NonNull Point point) {
//            Snackbar.make(mBinding.mapview, "Давай, ещё подольше подержи, может что получится", Toast.LENGTH_SHORT).show();
//
//        }
//    };

    MapObjectDragListener dragListener = new MapObjectDragListener() {
        @Override
        public void onMapObjectDragStart(@NonNull MapObject mapObject) {
        }
        @Override
        public void onMapObjectDrag(@NonNull MapObject mapObject, @NonNull Point point) {
        }
        @Override
        public void onMapObjectDragEnd(@NonNull MapObject mapObject) {
            Toast.makeText(requireContext(), "Перетащен", Toast.LENGTH_SHORT).show();
        }
    };

    MapObjectTapListener tapListener = (mapObject, point) -> {
        if (delay==0){
            MyMy m = (MyMy)mapObject.getUserData();

//            Toast.makeText(requireContext(), m.getId(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStart() {
        super.onStart();
        mBinding.mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        mBinding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }
}