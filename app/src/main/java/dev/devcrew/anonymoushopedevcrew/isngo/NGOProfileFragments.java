package dev.devcrew.anonymoushopedevcrew.isngo;

import static android.app.Activity.RESULT_OK;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USERS_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USER_PROFILE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_DOB;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_EMAIL;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.PICK_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.manifests_framents.SplashScreen;

public class NGOProfileFragments extends Fragment {

    TextView nameNGO,emailNGO,phoneNGO,dobNGO;
    ImageView profileImage,logout;

    Bitmap bitmap;
    ByteArrayOutputStream stream;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.ngo_profile_fragment, container, false);
        inIt(root);

        profileImage.setOnClickListener(View->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);

        });


        FirebaseFirestore.getInstance().collection(USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        if (error ==null){
                            if (value.exists()){

                                if (value.getString(KEY_NGO_OR_USERS_IMAGE)!=null){
                                    Log.d("TAG", "onEvent: "+value.getString(KEY_NGO_OR_USERS_IMAGE));
                                    Picasso.get()
                                            .load(value.getString(KEY_NGO_OR_USERS_IMAGE))
                                            .placeholder(R.drawable.progress_animation)
                                            .into(profileImage);
                                }
                                nameNGO.setText(value.getString(KEY_NGO_NAME));
                                emailNGO.setText(value.getString(KEY_USERS_EMAIL));
                                phoneNGO.setText( FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() );
                                dobNGO.setText(value.getString(KEY_USERS_DOB));
                            }
                        }


                    }
                });



        logout.setOnClickListener(View->{
            FirebaseAuth.getInstance().signOut();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentMainLayout,new SplashScreen() ,"SplashScreen");
            fragmentTransaction.commit();
        });




        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                profileImage.setImageBitmap(bitmap);
                stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality
                uploadImage1();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage1() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        DocumentReference documentReference1;
        documentReference1 = FirebaseFirestore.getInstance().collection(USERS).document(FirebaseAuth.getInstance().getCurrentUser().getUid());


        StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_NGO_OR_USER_PROFILE)
                .child(documentReference1.getId());


        BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality

        UploadTask uploadTask = storageReference.putBytes(stream.toByteArray());

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTaskSubcategory = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTaskSubcategory.isSuccessful()) ;
                Uri downladUri1 = uriTaskSubcategory.getResult();
                if (uriTaskSubcategory.isSuccessful()) {

                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put(KEY_NGO_OR_USERS_IMAGE, downladUri1.toString());

                    FirebaseFirestore.getInstance().collection(USERS)
                            .document(FirebaseAuth.getInstance().getUid())
                            .update(objectMap);

                    progressDialog.dismiss();

                }
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading Data " + (int) progress + "%");
                    }
                });
    }

    private void inIt(View root) {
        logout = root.findViewById(R.id.logout);
        nameNGO = root.findViewById(R.id.nameNGO);
        emailNGO = root.findViewById(R.id.emailNGO);
        profileImage = root.findViewById(R.id.profileImage);
        phoneNGO = root.findViewById(R.id.phoneNGO);
        dobNGO = root.findViewById(R.id.dobNGO);
    }

}
