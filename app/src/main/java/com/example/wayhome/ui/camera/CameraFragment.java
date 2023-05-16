package com.example.wayhome.ui.camera;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentCameraBinding;
import com.example.wayhome.data.room.MyMy;
import com.google.android.material.snackbar.Snackbar;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;

import java.io.File;
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
            if (checkEdits()){
                String name_s = Objects.requireNonNull(binding.edit1.getText()).toString();

                String sex_s = (binding.edit2.getCheckedButtonId()==R.id.button21)?"Мальчик":"Девочка";
                String collar_s = (binding.edit15.getCheckedButtonId()==R.id.button150)?"В ошейнике":"Без ошейника";
                String birthday_s = Objects.requireNonNull(binding.edit3.getText()).toString();
                String breed_s = Objects.requireNonNull(binding.edit4.getText()).toString();
                String color_s = Objects.requireNonNull(binding.edit5.getText()).toString();
                String status_s = (binding.edit0.getCheckedButtonId()==R.id.button01)?"Потерян":"Найден";
                MyMy m = new MyMy(R.drawable.pets, name_s, status_s, breed_s);
                m.setBirthday(birthday_s);
                m.setColor(color_s);
                m.setCollar(collar_s);

                m.setSex(sex_s);
                m.setLatitude(viewModel.getLatitude());
                m.setLongitude(viewModel.getLongitude());
                String chip_s = (!Objects.requireNonNull(binding.edit6.getText()).toString().equals("")?binding.edit6.getText().toString():"-");
                m.setChip_number(chip_s);
                m.setPlaceComment(Objects.requireNonNull(binding.edit10.getText()).toString());
                String stigma_s = (!Objects.requireNonNull(binding.edit14.getText()).toString().equals("")?binding.edit14.getText().toString():"-");
                m.setPhone_number(Objects.requireNonNull(binding.edit13.getText()).toString());
                m.setComment(Objects.requireNonNull(binding.edit7.getText()).toString());
                m.setFeatures(Objects.requireNonNull(binding.edit8.getText()).toString());
                m.setStigma_number(stigma_s);

//            Toast.makeText(requireContext(), String.valueOf(binding.edit0.getCheckedButtonId()), Toast.LENGTH_SHORT).show();

                viewModel.uploadFile(requireContext(), requireActivity());
                viewModel.insertPet(m);
                Snackbar.make(view, "Ваша форма успешно отправлена",Snackbar.LENGTH_SHORT).show();
                resetData();



            }
            else{
                Snackbar.make(view, "Введены не все данные",Snackbar.LENGTH_SHORT).show();
            }



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


    private boolean checkEdits(){
        if (viewModel.isPhoto() && viewModel.isMap() && binding.edit0.getCheckedButtonId()!=-1
                && !binding.edit1.getText().toString().isEmpty() &&  binding.edit2.getCheckedButtonId()!=-1 &&
                !binding.edit3.getText().toString().isEmpty() && !binding.edit13.getText().toString().isEmpty() && binding.edit2.getCheckedButtonId()!=-1)
            return true;
        return false;
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

    private void resetData(){
        viewModel.setLatitude(0);
        viewModel.setLongitude(0);
        viewModel.setMap(false);
        viewModel.setPhoto(false);
        binding.ivSuccess.setVisibility(View.GONE);
        binding.ivSuccess2.setVisibility(View.GONE);
        binding.edit0.clearChecked();
        binding.edit2.clearChecked();
        binding.edit15.clearChecked();
        binding.edit1.setText("");
        binding.edit3.setText("");
        binding.edit4.setText("");
        binding.edit5.setText("");
        binding.edit6.setText("");
        binding.edit14.setText("");
        binding.edit13.setText("");
        binding.edit7.setText("");
        binding.edit8.setText("");
        binding.edit10.setText("");

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