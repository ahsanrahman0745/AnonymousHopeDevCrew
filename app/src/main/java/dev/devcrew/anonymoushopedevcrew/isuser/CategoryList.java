package dev.devcrew.anonymoushopedevcrew.isuser;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.EVENTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CATEGORY;

import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.adpter.EventAdpter;
import dev.devcrew.anonymoushopedevcrew.isngo.model.EventModel;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.ProductCategoryAdpter;
import dev.devcrew.anonymoushopedevcrew.isuser.model.AllCategoryModel;

public class CategoryList extends Fragment {

    TextView txtNameHeader;
    String categoryName;
    FirestoreRecyclerOptions<AllCategoryModel> allCategoryModelFirestoreRecyclerOptions;
    ProductCategoryAdpter productCategoryAdpter;
    RecyclerView recylerView;
    ImageView imgBackArrowHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.ccategory_list_fragment, container, false);
        inIt(root);
        imgBackArrowHeader.setVisibility(View.GONE);



        assert getArguments() != null;
        categoryName = getArguments().getString(KEY_PRODUCT_CATEGORY);
        txtNameHeader.setText(categoryName);
        allCategoryModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<AllCategoryModel>()
                .setQuery(FirebaseFirestore.getInstance().collection(KEY_PRODUCTS)
                        .whereEqualTo(KEY_PRODUCT_CATEGORY,categoryName), AllCategoryModel.class)
                .setLifecycleOwner(this)
                .build();

        productCategoryAdpter =  new ProductCategoryAdpter(allCategoryModelFirestoreRecyclerOptions);

        recylerView.setAdapter(productCategoryAdpter);




        return root;
    }

    private void inIt(View root) {
        imgBackArrowHeader = root.findViewById(R.id.imgBackArrowHeader);
        txtNameHeader = root.findViewById(R.id.txtNameHeader);

        recylerView = root.findViewById(R.id.recylerView);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
