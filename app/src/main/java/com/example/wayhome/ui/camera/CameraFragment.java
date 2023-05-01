package com.example.wayhome.ui.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wayhome.R;

import com.example.wayhome.databinding.FragmentCameraBinding;
import com.example.wayhome.ui.main.MainViewModel;
import com.example.wayhome.ui.profile.MyMy;
import com.example.wayhome.ui.profile.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;

public class CameraFragment extends Fragment {

    private static final int CAMERA_PERMISSION_CODE = 1;
    FragmentCameraBinding binding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    CameraViewModel viewModel;

    private ArrayList<Photo> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        binding.setViewModel(viewModel);



        arrayList = new ArrayList<>();
        PhotoRecyclerAdapter recyclerAdapter = new PhotoRecyclerAdapter(arrayList);
        binding.recyclerView.setAdapter(recyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


//        linearLayoutManager = new LinearLayoutManager(ActivityHorizontalRV.this, LinearLayoutManager.HORIZONTAL, false);
//        rv.setLayoutManager(linearLayoutManager);
//        rv.setAdapter(myRvAdapter);










        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerPictureLauncher();



        if (viewModel.isActive()){
            //checkImagesAndSetThem
//            binding.ivUser.setImageURI(viewModel.getImageUri());
        }
        else{
            viewModel.clearImages();
        }

        arrayList.add(new Photo(createUri(0)));
        binding.btnTakePicture.setOnClickListener(v -> {
            viewModel.addImageUri(createUri(viewModel.getImageUri().size()));
            checkCameraPermissionAndOpenCamera();
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

    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    try {
                        if (result) {
                            Photo p = new Photo(viewModel.getImageUriLast());
                            arrayList.add(p);
                            viewModel.setActive(true);

                        }
                    } catch (Exception exception) {
                        exception.getStackTrace();
                    }
                }
        );
    }

    private void checkCameraPermissionAndOpenCamera() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            takePictureLauncher.launch(viewModel.getImageUriLast());
            viewModel.setActive(true);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePictureLauncher.launch(viewModel.getImageUriLast());
                viewModel.setActive(true);
            }
            else
                Toast.makeText(requireContext(), "Camera permission denied, please allow permission to take a photo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}