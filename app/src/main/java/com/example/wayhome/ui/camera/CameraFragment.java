package com.example.wayhome.ui.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class CameraFragment extends Fragment {

    private static final int CAMERA_PERMISSION_CODE = 1;
    FragmentCameraBinding binding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    CameraViewModel viewModel;
    int REQUEST_CODE=1;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CameraViewModel.class);

        binding.setViewModel(viewModel);



//
//        Person joe = new Person("Joe", "Swedish");
//        Person joe2 = new Person("Joe", "Dura");
//        viewModel.insertPerson(joe);
//
//        viewModel.getAllPersons().observe(requireActivity(), personList->{
//            if (personList==null)
//                return;
//            Log.d("persons", ": "+personList.size());
//            Log.d("persons", personList.get(0).firstName);
//            for (Person lists: personList){
//                Log.d("persons", lists.firstName+" "+lists.lastName);
//            }
//        });






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

            //checkImagesAndSetThem
//            binding.ivUser.setImageURI(viewModel.getImageUri());
        }
        else{

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
        });


        binding.btnTakePicture.setOnClickListener(v -> {
            viewModel.addPhoto(new Photo(createUri(0)));
            recyclerAdapter.notifyItemInserted(viewModel.getSize()-1);
//            checkCameraPermissionAndOpenCamera();
            viewModel.getPetsByOwnerId(3).observe(getViewLifecycleOwner(), pets -> {
                Log.d("PETS", "Pets");
                for (Pet p: pets){
                    Log.d("PETS", p.getName()+" owner->"+p.getOwnerId()+"  age->"+p.getAge());
                }
            });
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
            m.setSex(sex_s);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
            if (binding.mapView.getVisibility()==View.VISIBLE)
                Snackbar.make(binding.mapView, "Перемещай ещё", Toast.LENGTH_SHORT).show();

        }
    };
}