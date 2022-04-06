package dev.devcrew.anonymoushopedevcrew.isuser;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_COLLECTION_REFERENCE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_DATE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_DISC;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE1;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE2;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE3;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE4;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE5;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USER_PHONE_NO;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CATEGORY;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CHAT_COLLECTION;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_DISC;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_DOCUMENT_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES1;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES2;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES3;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES4;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES5;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_CHAT_LIST;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_EMAIL;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.ImageAdapter;

public class PostDetails extends Fragment {

    TextView titleNameOfUserOrNGO,titleOfEvent,descriptionOfEvent,addressNGO,dateOfNGO,phoneNo,dateOfEvent,nameNGOINEvent;
    ViewPager mViewPager;

    ImageAdapter adapterView;
    CardView delMYpost,editMYpost,chatMYpost;

    List<String> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.event_fragment, container, false);
        inIt(root);


        assert getArguments() != null;
        if (getArguments().getString(KEY_PRODUCT_IMAGES1)!=null){
            list.add(getArguments().getString(KEY_PRODUCT_IMAGES1));
            Log.d("TAG", list.size()+"onCreateView: "+getArguments().getString(KEY_PRODUCT_IMAGES1));
        }
        assert getArguments() != null;
        if (getArguments().getString(KEY_PRODUCT_IMAGES2)!=null){
            list.add(getArguments().getString(KEY_PRODUCT_IMAGES2));
            Log.d("TAG", list.size()+"onCreateView: "+getArguments().getString(KEY_PRODUCT_IMAGES2));
        }
        assert getArguments() != null;
        if (getArguments().getString(KEY_PRODUCT_IMAGES3)!=null){
            list.add(getArguments().getString(KEY_PRODUCT_IMAGES3));
            Log.d("TAG", list.size()+"onCreateView: "+getArguments().getString(KEY_PRODUCT_IMAGES3));
        }
        assert getArguments() != null;
        if (getArguments().getString(KEY_PRODUCT_IMAGES4)!=null){
            list.add(getArguments().getString(KEY_PRODUCT_IMAGES4));
            Log.d("TAG", list.size()+"onCreateView: "+getArguments().getString(KEY_PRODUCT_IMAGES4));
        }
        assert getArguments() != null;
        if (getArguments().getString(KEY_PRODUCT_IMAGES5)!=null){
            list.add(getArguments().getString(KEY_PRODUCT_IMAGES5));
            Log.d("TAG", list.size()+"onCreateView: "+getArguments().getString(KEY_PRODUCT_IMAGES5));
        }


        titleOfEvent.setText(getArguments().getString(KEY_PRODUCT_NAME));
        descriptionOfEvent.setText(getArguments().getString(KEY_PRODUCT_DISC));

        if (getArguments().getString(KEY_EVENT_DATE)!=null) {
            dateOfEvent.setText(getArguments().getString(KEY_EVENT_DATE));
        }

        FirebaseFirestore.getInstance().collection(USERS)
                .document(getArguments().getString(KEY_USERS_ID))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        addressNGO.setText(task.getResult().getString(KEY_USERS_EMAIL));
                        nameNGOINEvent.setText(task.getResult().getString(KEY_USERS_NAME));
                        phoneNo.setText( task.getResult().getString(KEY_NGO_OR_USER_PHONE_NO) );
                    }
                });


        delMYpost.setOnClickListener(View->{
            FirebaseFirestore.getInstance()
                    .collection(USERS)
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection(KEY_USERS_CHAT_LIST)
                    .document(getArguments().getString(KEY_PRODUCT_DOCUMENT_ID))
                    .delete();
            FirebaseFirestore.getInstance()
                    .collection(USERS)
                    .document(getArguments().getString(KEY_USERS_ID))
                    .collection(KEY_USERS_CHAT_LIST)
                    .document(getArguments().getString(KEY_PRODUCT_DOCUMENT_ID))
                    .delete();
            FirebaseFirestore.getInstance()
                    .collection(getArguments().getString(KEY_COLLECTION_REFERENCE))
                    .document(getArguments().getString(KEY_PRODUCT_DOCUMENT_ID))
                    .delete();

            UsersMainFragment chatFragment =  new UsersMainFragment();
            FragmentTransaction postProductTransaction =getActivity().getSupportFragmentManager().beginTransaction();
            postProductTransaction.replace(R.id.mainDashboardFrameLayout, chatFragment,"Home Fragment");
            postProductTransaction.commit();
        });


        editMYpost.setOnClickListener(View->{

        });

        chatMYpost.setOnClickListener(View->{
            Bundle bundle = new Bundle();
            bundle.putString(KEY_USERS_ID,getArguments().getString(KEY_USERS_ID));
            bundle.putString(KEY_PRODUCT_DOCUMENT_ID, getArguments().getString(KEY_PRODUCT_DOCUMENT_ID) );

            bundle.putString(KEY_PRODUCT_NAME, getArguments().getString(KEY_PRODUCT_NAME) );

            Map<Object,Object> map =  new HashMap<>();
            map.put(KEY_USERS_ID,getArguments().getString(KEY_USERS_ID) );
            map.put(KEY_PRODUCT_DOCUMENT_ID,getArguments().getString(KEY_PRODUCT_DOCUMENT_ID) );

            map.put(KEY_PRODUCT_NAME,getArguments().getString(KEY_PRODUCT_NAME) );

            FirebaseFirestore.getInstance().collection(USERS)
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection(KEY_USERS_CHAT_LIST).document( getArguments().getString(KEY_PRODUCT_DOCUMENT_ID) )
                    .set(map);

            Map<Object,Object> map2 =  new HashMap<>();
            map2.put(KEY_PRODUCT_DOCUMENT_ID, getArguments().getString(KEY_PRODUCT_DOCUMENT_ID) );
            map2.put(KEY_USERS_ID,FirebaseAuth.getInstance().getUid() );
            map2.put(KEY_PRODUCT_NAME,getArguments().getString(KEY_PRODUCT_NAME));

            FirebaseFirestore.getInstance().collection(USERS)
                    .document ( getArguments().getString(KEY_USERS_ID) )
                    .collection(KEY_USERS_CHAT_LIST).document( getArguments().getString(KEY_PRODUCT_DOCUMENT_ID))
                    .set(map2);

            ChatFragment chatFragment =  new ChatFragment();
            chatFragment.setArguments(bundle);
            FragmentTransaction postProductTransaction =getActivity().getSupportFragmentManager().beginTransaction();
            postProductTransaction.replace(R.id.mainDashboardFrameLayout, chatFragment,"Home Fragment");
            postProductTransaction.commit();

        });







        adapterView  = new ImageAdapter(getContext(),list);
        mViewPager.setAdapter(adapterView);

        return root;
    }

    private void inIt(View root) {
        list= new ArrayList<>();

        chatMYpost =  root.findViewById(R.id.chatMYpost);
        editMYpost =  root.findViewById(R.id.editMYpost);
        editMYpost.setVisibility(View.GONE);
        delMYpost = root.findViewById(R.id.delMYpost);
        delMYpost.setVisibility(View.GONE);

        Log.d("TAG inIt", getArguments().getString(KEY_USERS_ID)+" inIt: "+FirebaseAuth.getInstance().getCurrentUser().getUid());
        if (getArguments().getString(KEY_USERS_ID).equals(FirebaseAuth.getInstance().getCurrentUser().getUid() )){
        //    editMYpost.setVisibility(View.VISIBLE);
            delMYpost.setVisibility(View.VISIBLE);
        }


        mViewPager = (ViewPager) root.findViewById(R.id.imageView3);
        nameNGOINEvent = root.findViewById(R.id.nameNGOINEvent);
        titleNameOfUserOrNGO = root.findViewById(R.id.titleNameOfUserOrNGO);
        titleNameOfUserOrNGO.setText("Name: ");
        dateOfEvent = root.findViewById(R.id.dateOfEvent);
        descriptionOfEvent = root.findViewById(R.id.descriptionOfEvent);
        dateOfNGO = root.findViewById(R.id.dateOfNGO);
        phoneNo = root.findViewById(R.id.phoneNo);
        titleOfEvent = root.findViewById(R.id.titleOfEvent);
        addressNGO = root.findViewById(R.id.addressNGO);
    }
}
