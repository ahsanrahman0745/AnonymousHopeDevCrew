package dev.devcrew.anonymoushopedevcrew.isuser.adpter;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.MESSAGE_TYPE_TEXT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isuser.model.ChatModel;

public class ChatAdpter extends FirestoreRecyclerAdapter<ChatModel,ChatAdpter.ViewHOME> {

        Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatAdpter(@NonNull FirestoreRecyclerOptions<ChatModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHOME holder, int position, @NonNull ChatModel model) {
        holder.bindDate(position, model);
    }

    @NonNull
    @Override
    public ViewHOME onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_receiver_msg, parent, false);
        return new ViewHOME(view);    }

    public class ViewHOME extends RecyclerView.ViewHolder {
        TextView txt_receiveMsg, txt_senderMsg;
        ImageView txt_receiveImg,txt_senderImg;
        public ViewHOME(@NonNull View itemView) {
            super(itemView);
            txt_senderMsg = itemView.findViewById(R.id.txt_senderMsg);
            txt_receiveMsg = itemView.findViewById(R.id.txt_receiveMsg);
            txt_receiveImg = itemView.findViewById(R.id.txt_receiveImg);
            txt_senderImg = itemView.findViewById(R.id.txt_senderImg);
        }

        public void bindDate(int position, ChatModel model) {
            if (model.getKEY_SENDER_ID().equals(FirebaseAuth.getInstance().getUid())) {
                if (model.getKEY_MESSAGE_TYPE().equals(MESSAGE_TYPE_TEXT)){
                    txt_senderMsg.setText(model.getKEY_MESSAGE_TEXT());
                    txt_receiveMsg.setVisibility(View.GONE);
                    txt_receiveImg.setVisibility(View.GONE);
                    txt_senderImg.setVisibility(View.GONE);
                }else {
                    txt_senderMsg.setVisibility(View.GONE);
                    txt_receiveMsg.setVisibility(View.GONE);
                    txt_receiveImg.setVisibility(View.GONE);
                    //txt_senderImg.setVisibility(View.GONE);
                    Picasso.get()
                            .load(model.getKEY_MESSAGE_TEXT() )
                            .placeholder(R.drawable.progress_animation)
                            .into(txt_senderImg);
                }


            }

            if (!model.getKEY_SENDER_ID().equals(FirebaseAuth.getInstance().getUid())) {
                if ( model.getKEY_MESSAGE_TYPE().equals(MESSAGE_TYPE_TEXT) ){
                    txt_receiveMsg.setText(model.getKEY_MESSAGE_TEXT());
                    txt_senderMsg.setVisibility(View.GONE);
                    txt_receiveImg.setVisibility(View.GONE);
                    txt_senderImg.setVisibility(View.GONE);
                }else {
                    txt_senderMsg.setVisibility(View.GONE);
                    txt_receiveMsg.setVisibility(View.GONE);
                    //txt_receiveImg.setVisibility(View.GONE);
                    txt_senderImg.setVisibility(View.GONE);
                    Picasso.get()
                            .load(model.getKEY_MESSAGE_TEXT() )
                            .placeholder(R.drawable.progress_animation)
                            .into(txt_receiveImg);

                }
            }
        }

    }
}
