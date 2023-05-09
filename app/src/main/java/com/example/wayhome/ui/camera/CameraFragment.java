package com.example.wayhome.ui.camera;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.databinding.FragmentCameraBinding;
import com.example.wayhome.ui.camera.getlocation.GetLocationFragment;
import com.example.wayhome.ui.profile.MyMy;
import com.example.wayhome.ui.profile.RecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class CameraFragment extends Fragment {

    private static final int CAMERA_PERMISSION_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    FragmentCameraBinding binding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    CameraViewModel viewModel;






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CameraViewModel.class);

        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapView.getMap().addCameraListener(cameraListener);
        PhotoRecyclerAdapter recyclerAdapter = new PhotoRecyclerAdapter(viewModel.getPhotos());
        binding.recyclerView.setAdapter(recyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        if (viewModel.isActive()){
            if (viewModel.isPhoto())
                binding.ivSuccess.setVisibility(View.VISIBLE);
            if (viewModel.isMap())
                binding.ivSuccess2.setVisibility(View.VISIBLE);

            //checkImagesAndSetThem
//            binding.ivUser.setImageURI(viewModel.getImageUri());
        }
        else{
            viewModel.setActive(true);
//            viewModel.clearImages();
        }

//        viewModel.addPhoto(new Photo(createUri(0)));
        binding.locationBtn.setOnClickListener(v -> {
            binding.closeBtn.setVisibility(View.VISIBLE);
            binding.mapView.setVisibility(View.VISIBLE);
            binding.haveSelectedBtn.setVisibility(View.VISIBLE);
            binding.pointer.setVisibility(View.VISIBLE);
        });
        binding.closeBtn.setOnClickListener(v -> {
            binding.closeBtn.setVisibility(View.INVISIBLE);
            binding.mapView.setVisibility(View.INVISIBLE);
            binding.haveSelectedBtn.setVisibility(View.INVISIBLE);
            binding.pointer.setVisibility(View.INVISIBLE);
        });
        binding.haveSelectedBtn.setOnClickListener(v -> {
            binding.closeBtn.setVisibility(View.INVISIBLE);
            binding.mapView.setVisibility(View.INVISIBLE);
            binding.haveSelectedBtn.setVisibility(View.INVISIBLE);
            binding.pointer.setVisibility(View.INVISIBLE);
            viewModel.setSelected(true);
            viewModel.setMap(true);
            binding.ivSuccess2.setVisibility(View.VISIBLE);

        });


        binding.btnTakePicture.setOnClickListener(v -> {
            openFileChooser();
//            viewModel.addPhoto(new Photo(createUri(0)));
//            recyclerAdapter.notifyItemInserted(viewModel.getSize()-1);
//            checkCameraPermissi.onAndOpenCamera();
        });

        binding.nextBtn.setOnClickListener(v -> {
            String name_s = Objects.requireNonNull(binding.edit1.getText()).toString();

//            String sex_s = Objects.requireNonNull(binding.edit2.getText()).toString();
            String sex_s = "Сука";
            String birthday_s = Objects.requireNonNull(binding.edit3.getText()).toString();
            String breed_s = Objects.requireNonNull(binding.edit4.getText()).toString();
            String color_s = Objects.requireNonNull(binding.edit5.getText()).toString();
            MyMy m = new MyMy(R.drawable.pets, name_s, "Потерян", breed_s);
            m.setBirthday(birthday_s);
            m.setColor(color_s);
            //todo добавить всё
            m.setSex(sex_s);
            m.setLatitude(viewModel.getLatitude());
            m.setLongitude(viewModel.getLongitude());
            viewModel.uploadFile(requireContext(), requireActivity());
            viewModel.insertPet(m);
        });





    }



    private Uri createUri(int numberUri) {
        File imageFile = new File(requireContext().getFilesDir(), "camera_phot"+numberUri+".jpg");
        return FileProvider.getUriForFile(
                requireContext(),
                "com.example.wayhome.fileProvider",
                imageFile
        );
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
            viewModel.setLatitude(cameraPosition.getTarget().getLatitude());
            viewModel.setLongitude(cameraPosition.getTarget().getLongitude());


        }
    };

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            viewModel.setImageUri(data.getData());
            binding.ivSuccess.setVisibility(View.VISIBLE);
            viewModel.setPhoto(true);

        }
    }






    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }





    //    private void registerPictureLauncher() {
//        takePictureLauncher = registerForActivityResult(
//                new ActivityResultContracts.TakePicture(),
//                result -> {
//                    try {
//                        if (result) {
//                            Photo p = new Photo(viewModel.getImageUriLast());
//                            arrayList.add(p);
//                            viewModel.setActive(true);
//
//                        }
//                    } catch (Exception exception) {
//                        exception.getStackTrace();
//                    }
//                }
//        );
//    }

//    private void checkCameraPermissionAndOpenCamera() {
//        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
//        } else {
//            takePictureLauncher.launch(viewModel.getImageUriLast());
//            viewModel.setActive(true);
//
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                takePictureLauncher.launch(viewModel.getImageUriLast());
//                viewModel.setActive(true);
//            }
//            else
//                Toast.makeText(requireContext(), "Camera permission denied, please allow permission to take a photo", Toast.LENGTH_SHORT).show();
//        }
//    }
}