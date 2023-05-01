package com.example.wayhome.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.data.room.Person;
import com.example.wayhome.data.room.PersonDatabase;
import com.example.wayhome.databinding.FragmentAppBinding;
import com.example.wayhome.databinding.FragmentMainBinding;
import com.example.wayhome.ui.main.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;


public class AppFragment extends Fragment {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentAppBinding mBinding;
    FirebaseAuth mAuth;
    PersonDatabase db;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mAuth= FirebaseAuth.getInstance();
        mBinding = FragmentAppBinding.inflate(inflater);
        db = Room.databaseBuilder(requireContext(),
                PersonDatabase.class, "person-database").allowMainThreadQueries().build();
        Person joe = new Person("Joe", "Swedish");
        Person joe2 = new Person("Joe", "Dura");
        db.personDao().insertAll(joe, joe2);
        List<Person> personList = db.personDao().getAllPersons();

        for (Person lists: personList){
            Log.d("persons", lists.firstName+" "+lists.lastName);
        }

        return mBinding.getRoot();
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
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case (R.id.tool_notifications):
//                            Navigation.findNavController(getView()).navigate(R.id.to);
                            Toast.makeText(getActivity(), getText(R.string.tool_notification_text), Toast.LENGTH_SHORT).show();
                            break;
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
                }
            });


            NavigationUI.setupWithNavController(
                    toolbar, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navigationBar, navController);
        }


    }
}



