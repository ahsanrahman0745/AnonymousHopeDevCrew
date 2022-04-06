package dev.devcrew.anonymoushopedevcrew.isuser;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.EVENTS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.adpter.EventAdpter;
import dev.devcrew.anonymoushopedevcrew.isngo.model.EventModel;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.HomeAdpter;
import dev.devcrew.anonymoushopedevcrew.isuser.model.CategoryItem;

public class HomeFragment extends Fragment {

    RecyclerView rv_itemCategory;
    ImageView imgBackArrowHeader;
    String [ ] list = new String[]{
            "Food",
            "Blood",
            "Medicine",
            "Cloths",
            "Furniture",
            "Others"};
    String [ ] images = new String[]{
          "https://firebasestorage.googleapis.com/v0/b/anonymous-hope-with-devcrew.appspot.com/o/CategoryImages%2Fistockphoto-1223248918-612x612.jpg?alt=media&token=f7d358f4-6c30-4506-9cf0-2e9c326a7bf6",
            "https://firebasestorage.googleapis.com/v0/b/anonymous-hope-with-devcrew.appspot.com/o/CategoryImages%2Fdownload.png?alt=media&token=7e0be02e-ec20-4274-adf4-e4af22825b87",
            "https://firebasestorage.googleapis.com/v0/b/anonymous-hope-with-devcrew.appspot.com/o/CategoryImages%2Fback-to-school-activities-for-the-remote-learning-google-logo-meme-first-aid-cabinet-furniture-medicine-chest-transparent-png-2562659.png?alt=media&token=9019df52-33dd-4531-aca7-e1ec442c87d4",
            "https://firebasestorage.googleapis.com/v0/b/anonymous-hope-with-devcrew.appspot.com/o/CategoryImages%2Fdownload%20(1).png?alt=media&token=641f63ce-6608-41c7-8b69-b44fc8b12250",
            "https://firebasestorage.googleapis.com/v0/b/anonymous-hope-with-devcrew.appspot.com/o/CategoryImages%2F1a16851a4d591d118fab747af0f38b84-chair-furniture-icon.png?alt=media&token=3d02439a-6138-4ca3-a41e-f507291b1b7b",
            "https://firebasestorage.googleapis.com/v0/b/anonymous-hope-with-devcrew.appspot.com/o/CategoryImages%2Fistockphoto-1208102009-612x612.jpg?alt=media&token=9061484f-b9e0-4191-b4d1-90a22eea2d52"
              };

    HomeAdpter homeAdpter;
    List<CategoryItem> categoryItemList = new ArrayList<>();

    FirestoreRecyclerOptions<EventModel> eventModelFirestoreRecyclerOptions;
    EventAdpter eventAdpter;

    RecyclerView rv_Events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        inIt(root);

        if (categoryItemList.size()>0){
            categoryItemList.clear();
        }
        for (int i=0;i< list.length;i++){
            categoryItemList.add(new CategoryItem(images[i],list[i]));
        }

        homeAdpter = new HomeAdpter(categoryItemList,getActivity());

        rv_itemCategory.setAdapter(homeAdpter);



        eventModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<EventModel>()
                .setQuery(FirebaseFirestore.getInstance().collection(EVENTS), EventModel.class)
                .setLifecycleOwner(this)
                .build();

        eventAdpter  = new EventAdpter(eventModelFirestoreRecyclerOptions,getContext());
        rv_Events.setAdapter(eventAdpter);














        return root;
    }

    private void inIt(View root) {
        rv_itemCategory  = root.findViewById(R.id.rv_itemCategory);
        rv_itemCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        rv_Events  = root.findViewById(R.id.rv_Events);
        rv_Events.setLayoutManager(new GridLayoutManager(getActivity(), 2));



        imgBackArrowHeader  = root.findViewById(R.id.imgBackArrowHeader);
        imgBackArrowHeader.setVisibility(View.GONE);


    }

    @Override
    public void onStart() {
        super.onStart();
        eventAdpter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        eventAdpter.stopListening();
    }
}


