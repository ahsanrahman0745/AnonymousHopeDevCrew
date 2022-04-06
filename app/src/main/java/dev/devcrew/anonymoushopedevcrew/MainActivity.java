package dev.devcrew.anonymoushopedevcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import dev.devcrew.anonymoushopedevcrew.manifests_framents.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainLayout,new SplashScreen() ,"SplashScreen");
        fragmentTransaction.commit();


    }
}