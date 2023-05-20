package com.example.wayhome.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentAppBinding;
import com.example.wayhome.data.room.Person;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AppFragment extends Fragment {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentAppBinding mBinding;
    FirebaseAuth mAuth;
    DatabaseReference usersRef;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mAuth= FirebaseAuth.getInstance();
        mBinding = FragmentAppBinding.inflate(inflater);
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        insertData();
        return mBinding.getRoot();
    }

    private void insertData() {
        String uid = mAuth.getCurrentUser().getUid();
        usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                } else {
                    String name = "";
                    String email = mAuth.getCurrentUser().getEmail();
                    String phone = "";

                    Person u = new Person(name, email, phone, 0);
                    usersRef.child(uid).setValue(u);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView navigationBar = mBinding.bottomNav;
            AppBarConfiguration appBarConfiguration =
                    new AppBarConfiguration.Builder(navController.getGraph()).build();
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.toolbar_menu);
            toolbar.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
//                        case (R.id.tool_notifications):
////                            Navigation.findNavController(getView()).navigate(R.id.to);
//                            Toast.makeText(getActivity(), getText(R.string.tool_notification_text), Toast.LENGTH_SHORT).show();
//                            break;
                    case (R.id.tool_share):
                        Navigation.findNavController(requireView()).navigate(R.id.action_appFragment_to_shareFragment);
                        break;
                    case (R.id.tool_feedback):
                        Navigation.findNavController(requireView()).navigate(R.id.action_appFragment_to_feedBackFragment);
                        break;
                    case (R.id.tool_settings):
                        Navigation.findNavController(requireView()).navigate(R.id.action_appFragment_to_settingsFragment);
                        break;
                    case (R.id.tool_logout):
                        mAuth.signOut();
                        Navigation.findNavController(requireView()).navigate(R.id.action_appFragment_to_mainFragment);

                        break;
                }
                return false;
            });


            NavigationUI.setupWithNavController(
                    toolbar, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navigationBar, navController);
        }


    }
}



