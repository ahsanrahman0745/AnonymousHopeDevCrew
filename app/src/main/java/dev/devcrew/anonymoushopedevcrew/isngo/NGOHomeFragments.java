package dev.devcrew.anonymoushopedevcrew.isngo;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.EVENTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_ID;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.adpter.EventAdpter;
import dev.devcrew.anonymoushopedevcrew.isngo.model.EventModel;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NGOHomeFragments extends Fragment {

    RecyclerView rv_Events;

    ImageView imgBackArrowHeader;
    private static final int VERTICAL_ITEM_SPACE = 48;

    EventAdpter eventAdpter;
    FirestoreRecyclerOptions<EventModel> eventModelFirestoreRecyclerOptions;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.ngo_home_fragment, container, false);
        inIt(root);

        eventModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<EventModel>()
                .setQuery(FirebaseFirestore.getInstance().collection(EVENTS)
                        .whereEqualTo(KEY_USERS_ID, FirebaseAuth.getInstance().getUid()), EventModel.class)
                .setLifecycleOwner(this)
                .build();


        eventAdpter  = new EventAdpter(eventModelFirestoreRecyclerOptions,getContext());

      //  rv_Events.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        rv_Events.setAdapter(eventAdpter);


        return root;
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
            outRect.top = verticalSpaceHeight;
        }
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

    private void inIt(View root) {
        rv_Events = root.findViewById(R.id.rv_Events);
        rv_Events.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        imgBackArrowHeader = root.findViewById(R.id.imgBackArrowHeader);
        imgBackArrowHeader.setVisibility(View.GONE);
    }

}
