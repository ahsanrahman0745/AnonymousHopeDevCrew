package dev.devcrew.anonymoushopedevcrew.isuser;

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

public class UsersMainFragment extends Fragment {

    private BottomNavigationView userNaviagetionView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.users_main_fragment, container, false);
        inIt(root);
        HomeFragment homeFragment = new HomeFragment();
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
                case R.id.myChatBotttombtn:
                    Toast.makeText(getActivity(), "Chat", Toast.LENGTH_SHORT).show();
                    ChatUserList payFragemnt = new ChatUserList();
                    FragmentTransaction payFragemntTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    payFragemntTransaction.replace(R.id.mainDashboardFrameLayout,payFragemnt,"Payment Fragment");
                    payFragemntTransaction.commit();
                    return true;
                case R.id.profileBotttombtn:
                    Toast.makeText(getActivity(), "Profile", Toast.LENGTH_SHORT).show();
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction profileFragemntTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    profileFragemntTransaction.replace(R.id.mainDashboardFrameLayout,profileFragment,"Profile Fragment");
                    profileFragemntTransaction.commit();
                    return true;
                case R.id.homeBotttombtn:
                    Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction homefragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    homefragmentTransaction.replace(R.id.mainDashboardFrameLayout, homeFragment,"Home Fragment");
                    homefragmentTransaction.commit();
                    return true;
                case R.id.postProduct:
                    Toast.makeText(getActivity(), "Post Product", Toast.LENGTH_SHORT).show();
                    PostProduct postProduct = new PostProduct();
                    FragmentTransaction postProductTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    postProductTransaction.replace(R.id.mainDashboardFrameLayout, postProduct,"Home Fragment");
                    postProductTransaction.commit();
                    return true;

            }

            return false;
        }
    };


}