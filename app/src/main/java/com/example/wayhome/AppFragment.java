package com.example.wayhome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.databinding.FragmentAppBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

public class AppFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private FirebaseAuth mAuth;
//    private FragmentAppBinding mBinding;
//    private NavController mNavController;
//
//    public AppFragment() {
//        // Required empty public constructor
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mBinding = FragmentAppBinding.inflate(inflater, container, false);
//        mAuth = FirebaseAuth.getInstance();
//        mNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//
//        mBinding.toolbar.inflateMenu(R.menu.toolbar_menu);
//        mBinding.toolbar.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.tool_notifications:
//                    Toast.makeText(requireContext(), getText(R.string.tool_notification_text), Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.tool_ads:
//                    Toast.makeText(requireContext(), getText(R.string.tool_ads_text), Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.tool_share:
//                    Toast.makeText(requireContext(), getText(R.string.tool_share_text), Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.tool_about:
//                    Toast.makeText(requireContext(), getText(R.string.tool_about_text), Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.tool_settings:
//                    Toast.makeText(requireContext(), getText(R.string.tool_settings_text), Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.tool_logout:
//                    mAuth.signOut(); //todo logout
//                    startActivity(new Intent(requireContext(), MainActivity.class));
//                    requireActivity().finish();
//                    return true;
//                default:
//                    return false;
//            }
//        });
//
//        mBinding.bottomNavigationView.setBackground(null);
//        mBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.home:
//                case R.id.add:
//                case R.id.camera:
//                case R.id.profile:
//                case R.id.map:
//                    return true;
//                default:
//                    return false;
//            }
//        });
//
//        if (savedInstanceState == null) {
//            mNavController.navigate(R.id.action_global_homeFragment);
//        }
//
//        return mBinding.getRoot();
//    }




}