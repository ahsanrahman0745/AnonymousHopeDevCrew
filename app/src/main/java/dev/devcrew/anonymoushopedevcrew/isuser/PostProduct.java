package dev.devcrew.anonymoushopedevcrew.isuser;

import static android.app.Activity.RESULT_OK;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CATEGORY;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_DISC;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_EXPIRE_DATE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES1;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES2;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES3;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES4;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES5;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.ReferenceContext;

public class PostProduct extends Fragment {

    private static final int PICK_IMAGE = 1001;
    EditText productDescription;
    TextView edt_expireDate;
    TextInputLayout productName;
    Spinner spinner;


    private DatePickerDialog.OnDateSetListener mDateSetListener;


    Button upload;

    List<String> stringListCategory = new ArrayList<>();
    String selectCategory = "Select Category";

    ImageView image1, image2, image3, image4, image5;
    private String checkImageSelecter = null;

    Bitmap bitmap;
    ByteArrayOutputStream stream;

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;


    String sTringDownladUri1,sTringDownladUri2,sTringDownladUri3,sTringDownladUri4,sTringDownladUri5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.post_product_fragment, container, false);
        inIt(root);


        //////////// spinner cities
        stringListCategory.add(selectCategory);

        stringListCategory.add("Blood");
        stringListCategory.add("Medicine");
        stringListCategory.add("Food");
        stringListCategory.add("Cloths");
        stringListCategory.add("Furniture");
        stringListCategory.add("Others");


        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, stringListCategory);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ("Select Cities" != stringListCategory.get(position).toString()) {
                    selectCategory = stringListCategory.get(position);
                    //selectCityOwnerID = stringListCityOwnerID.get(position - 1);
                    Toast.makeText(getActivity(), selectCategory, Toast.LENGTH_SHORT).show();
                    if (selectCategory.equals("Medicine")) {
                        edt_expireDate.setVisibility(View.VISIBLE);
                    } else if (selectCategory.equals("Food")) {
                        edt_expireDate.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("TAG", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                edt_expireDate.setText(date);
            }
        };



        upload.setOnClickListener(view -> {
              Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put(KEY_PRODUCT_IMAGES1, sTringDownladUri1);
                    objectMap.put(KEY_PRODUCT_IMAGES2, sTringDownladUri2);
                    objectMap.put(KEY_PRODUCT_IMAGES3, sTringDownladUri3);
                    objectMap.put(KEY_PRODUCT_IMAGES4, sTringDownladUri4);
                    objectMap.put(KEY_PRODUCT_IMAGES5, sTringDownladUri5);

                    objectMap.put(KEY_PRODUCT_NAME, productName.getEditText().getText().toString());
                    objectMap.put(KEY_PRODUCT_DISC, productDescription.getText().toString());
                    if (selectCategory.equals("Food")){
                        objectMap.put(KEY_PRODUCT_EXPIRE_DATE, edt_expireDate.getText().toString());
                    }
                    objectMap.put(KEY_PRODUCT_CATEGORY, selectCategory);


            objectMap.put(KEY_USERS_ID, mAuth.getCurrentUser().getUid());

            firestore.collection(KEY_PRODUCTS)
                            .add(objectMap);


            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction homefragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            homefragmentTransaction.replace(R.id.mainDashboardFrameLayout, homeFragment,"Home Fragment");
            homefragmentTransaction.commit();



        });

        image1.setOnClickListener(view -> {
            checkImageSelecter = "image1";
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);
        });

        image2.setOnClickListener(view -> {
            checkImageSelecter = "image2";
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);
        });

        image3.setOnClickListener(view -> {
            checkImageSelecter = "image3";
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);
        });

        image4.setOnClickListener(view -> {
            checkImageSelecter = "image4";
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);
        });

        image5.setOnClickListener(view -> {
            checkImageSelecter = "image5";
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);
        });


        return root;
    }

    private void inIt(View root) {

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        upload = root.findViewById(R.id.upload);
        image1 = root.findViewById(R.id.image1);
        image2 = root.findViewById(R.id.image2);
        image3 = root.findViewById(R.id.image3);
        image4 = root.findViewById(R.id.image4);
        image5 = root.findViewById(R.id.image5);
        productName = root.findViewById(R.id.productName);
        spinner = root.findViewById(R.id.spinner);
        edt_expireDate = root.findViewById(R.id.edt_expireDate);
        productDescription = root.findViewById(R.id.productDescription);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            if (checkImageSelecter == "image1") {
                // stringDriverProfile = String.valueOf(data.getData());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    image1.setImageBitmap(bitmap);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality
                    uploadImage1();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (checkImageSelecter == "image2") {
                //stringLicenceFront = String.valueOf(data.getData());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    image2.setImageBitmap(bitmap);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality
                    uploadImage2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (checkImageSelecter == "image3") {
                // stringLicenceBack = String.valueOf(data.getData());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    image3.setImageBitmap(bitmap);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality
                    uploadImage3();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (checkImageSelecter == "image4") {
                //  stringNICFront = String.valueOf(data.getData());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    image4.setImageBitmap(bitmap);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality
                    uploadImage4();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (checkImageSelecter == "image5") {
                // stringNICBack = String.valueOf(data.getData());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    image5.setImageBitmap(bitmap);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //compress to 50% of original image quality
                    uploadImage5();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    private void uploadImage5() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_PRODUCT_IMAGES).child(FirebaseAuth.getInstance().getUid()+new Date());



        BitmapDrawable drawable = (BitmapDrawable) image5.getDrawable();
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
                    sTringDownladUri5 = downladUri1.toString();


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

    private void uploadImage4() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_PRODUCT_IMAGES).child(FirebaseAuth.getInstance().getUid()+new Date());


        BitmapDrawable drawable = (BitmapDrawable) image4.getDrawable();
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
                    sTringDownladUri4 = downladUri1.toString();

                   /* Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put(ContentRef.D_CAPTAIN_PROFILEIMG, downladUri1.toString());

                    firestore.collection(ContentRef.DRIVERS)
                            .document(mAuth.getUid())
                            .update(objectMap);*/

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

    private void uploadImage3() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_PRODUCT_IMAGES).child(FirebaseAuth.getInstance().getUid()+new Date());



        BitmapDrawable drawable = (BitmapDrawable) image3.getDrawable();
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
                    sTringDownladUri3 = downladUri1.toString();

                   /* Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put(ContentRef.D_CAPTAIN_PROFILEIMG, downladUri1.toString());

                    firestore.collection(ContentRef.DRIVERS)
                            .document(mAuth.getUid())
                            .update(objectMap);*/

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

    private void uploadImage2() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_PRODUCT_IMAGES).child(FirebaseAuth.getInstance().getUid()+new Date());



        BitmapDrawable drawable = (BitmapDrawable) image2.getDrawable();
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
                    sTringDownladUri2 = downladUri1.toString();

                   /* Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put(ContentRef.D_CAPTAIN_PROFILEIMG, downladUri1.toString());

                    firestore.collection(ContentRef.DRIVERS)
                            .document(mAuth.getUid())
                            .update(objectMap);*/

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

    private void uploadImage1() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

          StorageReference storageReference = FirebaseStorage.getInstance().getReference(KEY_PRODUCT_IMAGES).child(FirebaseAuth.getInstance().getUid()+new Date());


        BitmapDrawable drawable = (BitmapDrawable) image1.getDrawable();
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
                    sTringDownladUri1 = downladUri1.toString();

                   /* Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put(ContentRef.D_CAPTAIN_PROFILEIMG, downladUri1.toString());

                    firestore.collection(ContentRef.DRIVERS)
                            .document(mAuth.getUid())
                            .update(objectMap);*/

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

}

