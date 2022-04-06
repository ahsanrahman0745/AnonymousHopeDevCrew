package dev.devcrew.anonymoushopedevcrew.isuser;

import static android.app.Activity.RESULT_OK;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_MESSAGE_TEXT;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_MESSAGE_TIMESTAMP;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_MESSAGE_TYPE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USER_PROFILE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CHAT_COLLECTION;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_DOCUMENT_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_RECEIVER_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_SENDER_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.MESSAGE_TYPE_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.MESSAGE_TYPE_TEXT;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.PICK_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.ChatAdpter;
import dev.devcrew.anonymoushopedevcrew.isuser.model.ChatModel;

public class ChatMates extends Fragment {

    ImageView imgBackArrowHeader,imageView26;
    TextView txtNameHeader;
    RecyclerView recylerViewChat;

    ImageView imageView2;
    TextView textView;

    ChatAdpter chatAdpter;
    FirestoreRecyclerOptions<ChatModel> chatModelFirestoreRecyclerOptions;

    Bitmap bitmap;
    ByteArrayOutputStream stream;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.chat_fragment, container, false);
        inIt(root);


        imgBackArrowHeader.setOnClickListener(View->{


        });

        imageView26.setOnClickListener(View->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_IMAGE);
        });

        FirebaseFirestore.getInstance().collection(USERS)
                .document(getArguments().getString(KEY_USERS_ID))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            txtNameHeader.setText(task.getResult().getString(KEY_USERS_NAME));
                        }
                    }
                });


        Query query2 = FirebaseFirestore.getInstance()
                .collection(KEY_PRODUCTS)
                .document(getArguments().getString(KEY_PRODUCT_DOCUMENT_ID))
                .collection(KEY_PRODUCT_CHAT_COLLECTION);

        chatModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ChatModel>()
                .setQuery(query2.orderBy(KEY_MESSAGE_TIMESTAMP), ChatModel.class)
                .setLifecycleOwner(this)
                .build();

        chatAdpter = new ChatAdpter(chatModelFirestoreRecyclerOptions);
        recylerViewChat.setAdapter(chatAdpter);

        imageView2.setOnClickListener(View->{
            if (!textView.getText().toString().equals("")){
                Map<Object,Object> map = new HashMap<>();
                map.put(KEY_SENDER_ID, FirebaseAuth.getInstance().getUid());
                map.put(KEY_RECEIVER_ID,getArguments().getString(KEY_USERS_ID));
                map.put(KEY_MESSAGE_TEXT,textView.getText().toString());
                map.put(KEY_MESSAGE_TYPE,MESSAGE_TYPE_TEXT);
                map.put(KEY_MESSAGE_TIMESTAMP,new Date());
                FirebaseFirestore.getInstance().collection(KEY_PRODUCTS)
                        .document(getArguments().getString(KEY_PRODUCT_DOCUMENT_ID))
                        .collection(KEY_PRODUCT_CHAT_COLLECTION)
                        .add(map);
                textView.setText("");
            }

        });



        return root;
    }

    private void inIt(View root) {
        textView = root.findViewById(R.id.textView);
        imageView26 = root.findViewById(R.id.imageView26);
        imageView2 = root.findViewById(R.id.imageView2);

        txtNameHeader = root.findViewById(R.id.txtNameHeader);
        txtNameHeader.setText("Chat");

        recylerViewChat = root.findViewById(R.id.recylerViewChat);
        recylerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));

        imgBackArrowHeader = root.findViewById(R.id.imgBackArrowHeader);

    }

    @Override
    public void onStart() {
        super.onStart();
        chatAdpter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        chatAdpter.stopListening();
    }


}
