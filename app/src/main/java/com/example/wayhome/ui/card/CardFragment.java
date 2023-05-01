package com.example.wayhome.ui.card;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.databinding.FragmentCardBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

public class CardFragment extends Fragment {
    FragmentCardBinding binding;
    private final Point TARGET_LOCATION = new Point(59.845933, 30.320045);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentCardBinding.inflate(inflater, container, false);
//        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
//        mBinding.setViewModel(viewModel);
//        viewModel.setMapObjects(mBinding.mapview.getMap().getMapObjects().addCollection());

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

            binding.mapview.getMap().move(
                    new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f));

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
}