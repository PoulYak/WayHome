package com.example.wayhome.ui.camera.getlocation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentGetLocationBinding;
import com.example.wayhome.databinding.FragmentMapBinding;
import com.example.wayhome.ui.map.MapViewModel;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;


public class GetLocationFragment extends Fragment {
    FragmentGetLocationBinding binding;
    PlacemarkMapObject mark1;
    LocationViewModel viewModel;
    private final Point TARGET_LOCATION = new Point(59.845933, 30.320045);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentGetLocationBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.setMapObjects(binding.mapview.getMap().getMapObjects().addCollection());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mark1= viewModel.getMapObjects().addPlacemark(TARGET_LOCATION);
        mark1.setOpacity(0.7f);
        mark1.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.dog1));
        binding.mapview.getMap().addCameraListener(cameraListener);
    }

    @Override
    public void onStop() {
        // Fragment onStop call must be passed to both MapView and MapKit instance.
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        // Fragment onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapview.onStart();
    }


    CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {


            mark1.setGeometry(cameraPosition.getTarget());

//            Snackbar.make(mBinding.mapview, "Перемещай ещё", Toast.LENGTH_SHORT).show();

        }
    };
}