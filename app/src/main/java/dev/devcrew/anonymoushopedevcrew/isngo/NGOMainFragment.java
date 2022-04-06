package dev.devcrew.anonymoushopedevcrew.isngo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isuser.ChatFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.ChatUserList;
import dev.devcrew.anonymoushopedevcrew.isuser.HomeFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.PostProduct;
import dev.devcrew.anonymoushopedevcrew.isuser.ProfileFragment;

public class NGOMainFragment extends Fragment {

    private BottomNavigationView userNaviagetionView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.ngo_main_fragment, container, false);
        inIt(root);
        NGOHomeFragments homeFragment = new NGOHomeFragments();
        FragmentTransaction homefragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        homefragmentTransaction.replace(R.id.mainDashboardFrameLayout, homeFragment,"Home Fragment");
        homefragmentTransaction.commit();




        return root;
    }

    private void inIt(View root) {
        userNaviagetionView = root.findViewById(R.id.bottomNavigationView);
        userNaviagetionView.setOnNavigationItemSelectedListener(userStatus);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener userStatus =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){

                case R.id.profileBotttombtn:
                    Toast.makeText(getActivity(), "Profile", Toast.LENGTH_SHORT).show();
                    NGOProfileFragments profileFragment = new NGOProfileFragments();
                    FragmentTransaction profileFragemntTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    profileFragemntTransaction.replace(R.id.mainDashboardFrameLayout,profileFragment,"Profile Fragment");
                    profileFragemntTransaction.commit();
                    return true;
                case R.id.homeBotttombtn:
                    Toast.makeText(getActivity(), "Our Events", Toast.LENGTH_SHORT).show();
                    NGOHomeFragments homeFragment = new NGOHomeFragments();
                    FragmentTransaction homefragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    homefragmentTransaction.replace(R.id.mainDashboardFrameLayout, homeFragment,"Home Fragment");
                    homefragmentTransaction.commit();
                    return true;
                case R.id.postProduct:
                    Toast.makeText(getActivity(), "Add Event", Toast.LENGTH_SHORT).show();
                    NGOEvents postProduct = new NGOEvents();
                    FragmentTransaction postProductTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    postProductTransaction.replace(R.id.mainDashboardFrameLayout, postProduct,"Home Fragment");
                    postProductTransaction.commit();
                    return true;
                case R.id.chatBotttombtn:
                    Toast.makeText(getActivity(), "Add Event", Toast.LENGTH_SHORT).show();
                    ChatUserList chatUserList = new ChatUserList();
                    FragmentTransaction chatTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    chatTransaction.replace(R.id.mainDashboardFrameLayout, chatUserList,"Home Fragment");
                    chatTransaction.commit();
                    return true;


            }

            return false;
        }
    };



}

