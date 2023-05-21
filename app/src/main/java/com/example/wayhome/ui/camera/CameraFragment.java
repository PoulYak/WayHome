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

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentCameraBinding;
import com.example.wayhome.data.room.MyMy;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class CameraFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    FragmentCameraBinding binding;
    CameraViewModel viewModel;
    FirebaseAuth mAuth;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapView.getMap().addCameraListener(cameraListener);
        addMask();

        if (viewModel.isActive()){
            if (viewModel.isPhoto())
                binding.ivSuccess.setVisibility(View.VISIBLE);
            if (viewModel.isMap())
                binding.ivSuccess2.setVisibility(View.VISIBLE);
        }
        else{
            viewModel.setActive(true);
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
        });

        binding.nextBtn.setOnClickListener(v -> {
            if (checkEdits()){
                String name_s = Objects.requireNonNull(binding.edit1.getText()).toString();

                String sex_s = (binding.toggleSex.isChecked())?"Мальчик":"Девочка";
                String collar_s = (binding.toggleCollar.isChecked())?"В ошейнике":"Без ошейника";
                String birthday_s = Objects.requireNonNull(binding.edit3.getText()).toString();
                String breed_s = Objects.requireNonNull(binding.edit4.getText()).toString();
                String color_s = Objects.requireNonNull(binding.edit5.getText()).toString();

                String status_s = (binding.toggleSex.isChecked())?"Потерян":"Найден";
                MyMy m = new MyMy(R.drawable.pets, name_s, status_s, breed_s);
                m.setBirthday(birthday_s);
                m.setColor(color_s);
                m.setCollar(collar_s);

                m.setSex(sex_s);
                m.setLatitude(viewModel.getLatitude());
                m.setLongitude(viewModel.getLongitude());
                String chip_s = (!Objects.requireNonNull(binding.edit6.getText()).toString().equals("")?binding.edit6.getText().toString():"-");
                m.setChip_number(chip_s);
                String stigma_s = (!Objects.requireNonNull(binding.edit14.getText()).toString().equals("")?binding.edit14.getText().toString():"-");
                m.setPhone_number(Objects.requireNonNull(binding.edit13.getText()).toString());
                m.setComment(Objects.requireNonNull(binding.edit7.getText()).toString());
                m.setFeatures(Objects.requireNonNull(binding.edit8.getText()).toString());
                m.setStigma_number(stigma_s);
                m.setOwner_mail(mAuth.getCurrentUser().getUid());
                m.setActive("true");

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



    private boolean checkEdits(){
        if (viewModel.isPhoto() && viewModel.isMap()
                && !Objects.requireNonNull(binding.edit1.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(binding.edit3.getText()).toString().isEmpty() && !Objects.requireNonNull(binding.edit13.getText()).toString().isEmpty() && isGoodNumber(binding.edit13.getText().toString()))
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
    private void addMask(){
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("+7 (___) ___-__-__");
        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(binding.edit13);
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
        binding.toggleSex.setChecked(false);
        binding.toggleFound.setChecked(false);
        binding.toggleCollar.setChecked(false);


        binding.edit1.setText("");
        binding.edit3.setText("");
        binding.edit4.setText("");
        binding.edit5.setText("");
        binding.edit6.setText("");
        binding.edit14.setText("");
        binding.edit13.setText("");
        binding.edit7.setText("");
        binding.edit8.setText("");

    }

    private boolean isGoodNumber(String number){
        if (number==null)
            return false;
        if (TextUtils.isEmpty(number)) return true;
        Pattern pattern = Pattern.compile("[^0-9]*");

        Matcher matcher = pattern.matcher(number);
        if (matcher.replaceAll("").length()==11)
            return true;
        return false;
    }

}