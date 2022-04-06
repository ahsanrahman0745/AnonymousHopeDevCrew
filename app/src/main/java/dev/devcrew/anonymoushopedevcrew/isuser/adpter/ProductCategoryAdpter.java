package dev.devcrew.anonymoushopedevcrew.isuser.adpter;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_COLLECTION_REFERENCE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CATEGORY;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_DISC;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_DOCUMENT_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES1;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES2;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES3;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES4;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_IMAGES5;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_CHAT_LIST;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.adpter.EventAdpter;
import dev.devcrew.anonymoushopedevcrew.isuser.ChatFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.PostDetails;
import dev.devcrew.anonymoushopedevcrew.isuser.model.AllCategoryModel;

public class ProductCategoryAdpter extends FirestoreRecyclerAdapter<AllCategoryModel,ProductCategoryAdpter.ViewHOME> {

    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductCategoryAdpter(@NonNull FirestoreRecyclerOptions<AllCategoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductCategoryAdpter.ViewHOME holder, int position, @NonNull AllCategoryModel model) {
        holder.bindView(model, position);
    }

    @NonNull
    @Override
    public ProductCategoryAdpter.ViewHOME onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, null);
        context = parent.getContext();
        return new ViewHOME(view);
    }

    public class ViewHOME extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardViewRequest,cardViewProduct;
        TextView titleName,description,dateOfExpery;
        public ViewHOME(@NonNull View itemView) {
            super(itemView);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
            dateOfExpery = itemView.findViewById(R.id.dateOfExpery);
            titleName = itemView.findViewById(R.id.titleName);
            cardViewRequest = itemView.findViewById(R.id.cardViewRequest);

        }

        public void bindView(AllCategoryModel model, int position) {
            cardViewRequest.setOnClickListener( View->{
                Bundle bundle = new Bundle();
                bundle.putString(KEY_USERS_ID,model.getKEY_USERS_ID());
                bundle.putString(KEY_PRODUCT_CATEGORY,model.getKEY_PRODUCT_CATEGORY());
                bundle.putString(KEY_PRODUCT_DOCUMENT_ID,getSnapshots().getSnapshot(position).getId());
                bundle.putString(KEY_PRODUCT_DISC,model.getKEY_PRODUCT_DISC());
                bundle.putString(KEY_PRODUCT_NAME,model.getKEY_PRODUCT_NAME());
                if (model.getKEY_PRODUCT_IMAGES1()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES1,model.getKEY_PRODUCT_IMAGES1());
                }
                if (model.getKEY_PRODUCT_IMAGES2()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES2,model.getKEY_PRODUCT_IMAGES2());
                }
                if (model.getKEY_PRODUCT_IMAGES3()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES3,model.getKEY_PRODUCT_IMAGES3());
                }
                if (model.getKEY_PRODUCT_IMAGES4()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES4,model.getKEY_PRODUCT_IMAGES4());
                }
                if (model.getKEY_PRODUCT_IMAGES5()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES5,model.getKEY_PRODUCT_IMAGES5());
                }
                Map<Object,Object> map =  new HashMap<>();
                map.put(KEY_USERS_ID,model.getKEY_USERS_ID() );
                map.put(KEY_PRODUCT_DOCUMENT_ID,getSnapshots().getSnapshot(position).getId());
                map.put(KEY_PRODUCT_NAME,model.getKEY_PRODUCT_NAME());

                                    FirebaseFirestore.getInstance().collection(USERS)
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .collection(KEY_USERS_CHAT_LIST).document(getSnapshots().getSnapshot(position).getId())
                                            .set(map);

                                    Map<Object,Object> map2 =  new HashMap<>();
                                    map2.put(KEY_PRODUCT_DOCUMENT_ID,getSnapshots().getSnapshot(position).getId());
                                    map2.put(KEY_USERS_ID,FirebaseAuth.getInstance().getUid() );
                map2.put(KEY_PRODUCT_NAME,model.getKEY_PRODUCT_NAME());

                                    FirebaseFirestore.getInstance().collection(USERS)
                                            .document (model.getKEY_USERS_ID() )
                                            .collection(KEY_USERS_CHAT_LIST).document(getSnapshots().getSnapshot(position).getId())
                                            .set(map2);

                                    ChatFragment chatFragment =  new ChatFragment();
                                    chatFragment.setArguments(bundle);
                                    FragmentTransaction postProductTransaction =((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                    postProductTransaction.replace(R.id.mainDashboardFrameLayout, chatFragment,"Home Fragment");
                                    postProductTransaction.commit();


            });


            cardViewProduct.setOnClickListener(View->{
                Bundle bundle = new Bundle();
                bundle.putString(KEY_USERS_ID,model.getKEY_USERS_ID());
                bundle.putString(KEY_COLLECTION_REFERENCE, KEY_PRODUCTS);
                bundle.putString(KEY_PRODUCT_CATEGORY,model.getKEY_PRODUCT_CATEGORY());
                bundle.putString(KEY_PRODUCT_DOCUMENT_ID,getSnapshots().getSnapshot(position).getId());
                bundle.putString(KEY_PRODUCT_DISC,model.getKEY_PRODUCT_DISC());
                bundle.putString(KEY_PRODUCT_NAME,model.getKEY_PRODUCT_NAME());
                if (model.getKEY_PRODUCT_IMAGES1()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES1,model.getKEY_PRODUCT_IMAGES1());
                }
                if (model.getKEY_PRODUCT_IMAGES2()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES2,model.getKEY_PRODUCT_IMAGES2());
                }
                if (model.getKEY_PRODUCT_IMAGES3()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES3,model.getKEY_PRODUCT_IMAGES3());
                }
                if (model.getKEY_PRODUCT_IMAGES4()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES4,model.getKEY_PRODUCT_IMAGES4());
                }
                if (model.getKEY_PRODUCT_IMAGES5()!=null){
                    bundle.putString(KEY_PRODUCT_IMAGES5,model.getKEY_PRODUCT_IMAGES5());
                }
                PostDetails postDetails =  new PostDetails();
                postDetails.setArguments(bundle);
                FragmentTransaction postProductTransaction =((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                postProductTransaction.replace(R.id.mainDashboardFrameLayout, postDetails,"Home Fragment");
                postProductTransaction.commit();


            });

            if (model.getKEY_PRODUCT_EXPIRE_DATE()!=null ){
                dateOfExpery.setText("Expiry: "+model.getKEY_PRODUCT_EXPIRE_DATE());
            }


            if (model.getKEY_PRODUCT_IMAGES1()!=null){
                Picasso.get()
                        .load(model.getKEY_PRODUCT_IMAGES1())
                        .placeholder(R.drawable.progress_animation)
                        .into(imageView);
            }else
            if (model.getKEY_PRODUCT_IMAGES2()!=null){
                Picasso.get()
                        .load(model.getKEY_PRODUCT_IMAGES2())
                        .placeholder(R.drawable.progress_animation)
                        .into(imageView);
            }else
            if (model.getKEY_PRODUCT_IMAGES3()!=null){
                Picasso.get()
                        .load(model.getKEY_PRODUCT_IMAGES3())
                        .placeholder(R.drawable.progress_animation)
                        .into(imageView);
            }else
            if (model.getKEY_PRODUCT_IMAGES4()!=null){
                Picasso.get()
                        .load(model.getKEY_PRODUCT_IMAGES4())
                        .placeholder(R.drawable.progress_animation)
                        .into(imageView);
            }else
            if (model.getKEY_PRODUCT_IMAGES5()!=null){
                Picasso.get()
                        .load(model.getKEY_PRODUCT_IMAGES5())
                        .placeholder(R.drawable.progress_animation)
                        .into(imageView);
            }
            titleName.setText(model.getKEY_PRODUCT_NAME());
            description.setText(model.getKEY_PRODUCT_DISC());
        }
    }
}
