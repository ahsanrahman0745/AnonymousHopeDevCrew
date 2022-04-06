package dev.devcrew.anonymoushopedevcrew.isuser;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CATEGORY;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_CHAT_LIST;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.ChatListAdpter;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.ProductCategoryAdpter;
import dev.devcrew.anonymoushopedevcrew.isuser.model.AllCategoryModel;
import dev.devcrew.anonymoushopedevcrew.isuser.model.ChatListModel;

public class ChatUserList extends Fragment {

    String categoryName;
    FirestoreRecyclerOptions<ChatListModel> allCategoryModelFirestoreRecyclerOptions;
    ChatListAdpter productCategoryAdpter;
    RecyclerView recylerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.chat_user_list_fragment, container, false);
        inIt(root);




        Query query = FirebaseFirestore.getInstance().collection(USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .collection(KEY_USERS_CHAT_LIST);

        allCategoryModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<ChatListModel>()
                .setQuery(query, ChatListModel.class)
                .setLifecycleOwner(this)
                .build();

        productCategoryAdpter =  new ChatListAdpter(allCategoryModelFirestoreRecyclerOptions);

        recylerView.setAdapter(productCategoryAdpter);




        return root;
    }

    private void inIt(View root) {
        recylerView = root.findViewById(R.id.chatUSerListRecyclerView);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onStart() {
        super.onStart();
        productCategoryAdpter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        productCategoryAdpter.stopListening();
    }
}

