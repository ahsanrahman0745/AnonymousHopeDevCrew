package dev.devcrew.anonymoushopedevcrew.isuser.adpter;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_DOCUMENT_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USERS_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USER_PROFILE;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import dev.devcrew.anonymoushopedevcrew.isuser.ChatFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.PostDetails;
import dev.devcrew.anonymoushopedevcrew.isuser.model.AllCategoryModel;
import dev.devcrew.anonymoushopedevcrew.isuser.model.ChatListModel;

public class ChatListAdpter extends FirestoreRecyclerAdapter<ChatListModel,ChatListAdpter.ViewHOME> {

        Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatListAdpter(@NonNull FirestoreRecyclerOptions<ChatListModel> options) {
        super(options);
    }




    @Override
    protected void onBindViewHolder(@NonNull ViewHOME holder, int position, @NonNull ChatListModel model) {
        holder.bindView(model, position);
    }

    @NonNull
    @Override
    public ViewHOME onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_list, null);
        context = parent.getContext();
        return new ViewHOME(view);
    }

    public class ViewHOME extends RecyclerView.ViewHolder {
        LinearLayout linerLayout;
        ImageView profileImage;
        TextView nameOfUser,nameOfProduct;
    public ViewHOME(@NonNull View itemView) {
        super(itemView);
        nameOfProduct = itemView.findViewById(R.id.nameOfProduct);
        nameOfUser = itemView.findViewById(R.id.nameOfUser);
        linerLayout = itemView.findViewById(R.id.linerLayout);
        profileImage = itemView.findViewById(R.id.profileImage);
    }

    public void bindView(ChatListModel model, int position) {

        FirebaseFirestore.getInstance()
                .collection(USERS)
                .document(model.getKEY_USERS_ID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            if (task.getResult().getString(KEY_USERS_NAME)!=null){
                                nameOfUser.setText(task.getResult().getString(KEY_USERS_NAME));
                            }else {
                                nameOfUser.setText(task.getResult().getString(KEY_NGO_NAME));
                            }

                            if (task.getResult().getString(KEY_NGO_OR_USERS_IMAGE)!=null){
                                Log.d("TAG", "onEvent: "+task.getResult().getString(KEY_NGO_OR_USERS_IMAGE));
                                Picasso.get()
                                        .load(task.getResult().getString(KEY_NGO_OR_USERS_IMAGE))
                                        .placeholder(R.drawable.progress_animation)
                                        .into(profileImage);
                            }
                        }
                    }
                });


        if (model.getKEY_PRODUCT_NAME()!=null){
            nameOfProduct.setText("Product: "+model.getKEY_PRODUCT_NAME());
            Log.d("TAG", "bindView: checking"+model.getKEY_EVENT_DOCUMENT_ID());
        }else {
            nameOfProduct.setText("Event: "+model.getKEY_EVENT_NAME());
        }

        linerLayout.setOnClickListener( View->{
            Bundle bundle = new Bundle();
            bundle.putString(KEY_USERS_ID,model.getKEY_USERS_ID());
            if (model.getKEY_EVENT_DOCUMENT_ID()!=null){
                bundle.putString(KEY_EVENT_DOCUMENT_ID,model.getKEY_EVENT_DOCUMENT_ID() );
                Log.d("TAG", "bindView: checking"+model.getKEY_EVENT_DOCUMENT_ID());
            }else {
                bundle.putString(KEY_PRODUCT_DOCUMENT_ID,model.getKEY_PRODUCT_DOCUMENT_ID() );
            }


            ChatFragment chatFragment =  new ChatFragment();
            chatFragment.setArguments(bundle);
            FragmentTransaction postProductTransaction =((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            postProductTransaction.replace(R.id.mainDashboardFrameLayout, chatFragment,"Home Fragment").addToBackStack(null);
            postProductTransaction.commit();

        });

    }
}
}
