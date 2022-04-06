package dev.devcrew.anonymoushopedevcrew.manifests_framents;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS_TYPE;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.MainActivity;
import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.NGOMainFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.UsersMainFragment;

public class SplashScreen extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private MainActivity mainActivity;

    String currentVersion;
    String newVersion = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.splesh_fragment,container,false);




        return root;
    }




    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    private void moveFarmgents() {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser()!=null){
           // checkUserRoll();
            checkUserType(mAuth.getCurrentUser().getUid());
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentMainLayout,new Login() ,"ListOfFriends");
                    fragmentTransaction.commit();
                }
            },100);
        }

    }

    private void checkUserType(String uid) {
        FirebaseFirestore.getInstance().collection(USERS)
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                if (task.getResult().getString(USERS_TYPE).equals("isAdmin")){
                                    Log.d("TAG", "onComplete: admin");
                                }else if (task.getResult().getString(USERS_TYPE).equals("isNGO")){
                                    Log.d("TAG", "onComplete: isNGO");
                                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentMainLayout,new NGOMainFragment() ,"ListOfFriends");
                                    fragmentTransaction.commit();

                                }else if (task.getResult().getString(USERS_TYPE).equals("isUser")){
                                    Log.d("TAG", "onComplete: isUser");
                                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentMainLayout,new UsersMainFragment() ,"ListOfFriends");
                                    fragmentTransaction.commit();

                                }
                            }else {
                                CreateNewAccount(mAuth.getCurrentUser().getUid());
                            }
                        }
                    }
                });
    }

    private void CreateNewAccount(String uid) {
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainLayout,new CreateUserAccount() ,"ListOfFriends");
        fragmentTransaction.commit();
    }


    @Override
    public void onStart() {
        super.onStart();
        moveFarmgents();

      /*  new GetVersionCode().execute();
        if(currentVersion.equals(newVersion)){
        }*/
    }


}
