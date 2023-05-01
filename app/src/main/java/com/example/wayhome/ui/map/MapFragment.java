package com.example.wayhome.ui.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wayhome.MainActivity;
import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentMapBinding;
import com.example.wayhome.ui.camera.CameraViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectDragListener;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.AnimatedImageProvider;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class MapFragment extends Fragment {
    FragmentMapBinding mBinding;
    MapViewModel viewModel;
    private final Point TARGET_LOCATION = new Point(59.845933, 30.320045);
    private final Point TARGET_LOCATION2 = new Point(59.805933, 30.320045);
    private final Point TARGET_LOCATION3 = new Point(59.815933, 30.320045);
    private final Point TARGET_LOCATION4 = new Point(59.825933, 30.320045);





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding =  FragmentMapBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mBinding.setViewModel(viewModel);
        viewModel.setMapObjects(mBinding.mapview.getMap().getMapObjects().addCollection());

        return mBinding.getRoot();
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.mapview.getMap().addInputListener(inputListener);
        mBinding.mapview.getMap().addCameraListener(cameraListener);
        if (!viewModel.isActive()){

            mBinding.mapview.getMap().move(
                    new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 5),
                    null);
            ArrayList<Point> pts = new ArrayList<>();
            pts.add(TARGET_LOCATION);
            pts.add(TARGET_LOCATION2);
            pts.add(TARGET_LOCATION3);
            pts.add(TARGET_LOCATION4);
            for (Point p: pts){
                PlacemarkMapObject mark = viewModel.getMapObjects().addPlacemark(p);
                mark.setOpacity(0.7f);
                mark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.dog1));
                viewModel.setActive(true);
                mark.setDraggable(true);
                mark.setDragListener(dragListener);
                mark.addTapListener(tapListener);
            }

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
    @Override
    public void onStop() {
        // Fragment onStop call must be passed to both MapView and MapKit instance.
        mBinding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        // Fragment onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mBinding.mapview.onStart();
    }






//    // Strong reference to the listener.
//    private MapObjectTapListener circleMapObjectTapListener = (mapObject, point) -> {
//        if (mapObject instanceof CircleMapObject) {
//            CircleMapObject circle = (CircleMapObject)mapObject;
//
//            float randomRadius = 100.0f + 50.0f * new Random().nextFloat();
//
//            Circle curGeometry = circle.getGeometry();
//            Circle newGeometry = new Circle(curGeometry.getCenter(), randomRadius);
//            circle.setGeometry(newGeometry);
//
//            Object userData = circle.getUserData();
//            if (userData instanceof CircleMapObjectUserData) {
//                CircleMapObjectUserData circleUserData = (CircleMapObjectUserData)userData;
//
//                Toast toast = Toast.makeText(
//                        getContext(),
//                        "Circle with id " + circleUserData.id + " and description '"
//                                + circleUserData.description + "' tapped",
//                        Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        }
//        return true;
//    };

//    private class CircleMapObjectUserData {
//        final int id;
//        final String description;
//
//        CircleMapObjectUserData(int id, String description) {
//            this.id = id;
//            this.description = description;
//        }
//    }

//    private void createTappableCircle() {
//        CircleMapObject circle = mapObjects.addCircle(
//                new Circle(TARGET_LOCATION, 100), Color.GREEN, 2, Color.RED);
//        circle.setZIndex(100.0f);
//        circle.setUserData(new CircleMapObjectUserData(42, "Tappable circle"));
//        circle.addTapListener(circleMapObjectTapListener);
//    }



    InputListener inputListener = new InputListener() {
        @Override
        public void onMapTap(@NonNull Map map, @NonNull Point point) {
            Toast.makeText(requireContext(), point.getLatitude()+" "+point.getLongitude(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMapLongTap(@NonNull Map map, @NonNull Point point) {
            Snackbar.make(mBinding.mapview, "Давай, ещё подольше подержи, может что получится", Toast.LENGTH_SHORT).show();

        }
    };

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

    MapObjectTapListener tapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
            Toast.makeText(requireContext(), "Пёсель пропал", Toast.LENGTH_SHORT).show();
            mapObject.setVisible(false);
            return false;
        }
    };

    CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
            Snackbar.make(mBinding.mapview, "Перемещай ещё", Toast.LENGTH_SHORT).show();

        }
    };




}