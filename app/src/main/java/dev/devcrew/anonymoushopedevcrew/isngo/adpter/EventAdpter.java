package dev.devcrew.anonymoushopedevcrew.isngo.adpter;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.EVENTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_COLLECTION_REFERENCE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_DATE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_DISC;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_DOCUMENT_ID;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE1;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE2;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE3;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE4;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_IMAGE5;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_EVENT_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCTS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_ID;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.model.EventModel;
import dev.devcrew.anonymoushopedevcrew.isuser.EventDetials;
import dev.devcrew.anonymoushopedevcrew.isuser.HomeFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.adpter.HomeAdpter;

public class EventAdpter extends FirestoreRecyclerAdapter<EventModel, EventAdpter.ViewHOLDERS> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param context
     */
    Context context;

    public EventAdpter(@NonNull FirestoreRecyclerOptions<EventModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHOLDERS holder, int position, @NonNull EventModel model) {
        holder.bindView(model, position);
    }

    @NonNull
    @Override
    public ViewHOLDERS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_layouts, null);
        return new ViewHOLDERS(view);
    }

    public class ViewHOLDERS extends RecyclerView.ViewHolder {
        ImageView eventImages;
        TextView eventNAme;

        CardView cardViewEV;

        public ViewHOLDERS(@NonNull View itemView) {
            super(itemView);
            cardViewEV = itemView.findViewById(R.id.cardViewEV);
            eventNAme = itemView.findViewById(R.id.eventNAme);
            eventImages = itemView.findViewById(R.id.eventImages);
        }

        public void bindView(EventModel model, int position) {

            cardViewEV.setOnClickListener(View->{
                Bundle bundle =  new Bundle();
                bundle.putString(KEY_COLLECTION_REFERENCE, EVENTS);
                bundle.putString(KEY_EVENT_DOCUMENT_ID, getSnapshots().getSnapshot(position).getId());
                bundle.putString(KEY_EVENT_NAME, model.getKEY_EVENT_NAME() );
                bundle.putString(KEY_EVENT_DISC, model.getKEY_EVENT_DISC() );
                bundle.putString(KEY_EVENT_DATE, model.getKEY_EVENT_DATE() );
                bundle.putString(KEY_USERS_ID, model.getKEY_USERS_ID() );
                if (model.getKEY_EVENT_IMAGE1()!=null){
                    Log.d("TAG", "bindView: 1"+model.getKEY_EVENT_IMAGE1());
                    bundle.putString(KEY_EVENT_IMAGE1, model.getKEY_EVENT_IMAGE1() );
                }
                if (model.getKEY_EVENT_IMAGE2()!=null){
                    Log.d("TAG", "bindView: 2"+model.getKEY_EVENT_IMAGE2());
                    bundle.putString(KEY_EVENT_IMAGE2, model.getKEY_EVENT_IMAGE2() );
                }
                if (model.getKEY_EVENT_IMAGE3()!=null){
                    Log.d("TAG", "bindView: 3"+model.getKEY_EVENT_IMAGE3());
                    bundle.putString(KEY_EVENT_IMAGE3, model.getKEY_EVENT_IMAGE3() );
                }
                if (model.getKEY_EVENT_IMAGE4()!=null){
                    bundle.putString(KEY_EVENT_IMAGE4, model.getKEY_EVENT_IMAGE4() );
                }
                if (model.getKEY_EVENT_IMAGE5()!=null){
                    bundle.putString(KEY_EVENT_IMAGE5, model.getKEY_EVENT_IMAGE5() );
                }

                EventDetials eventDetials = new EventDetials();
                eventDetials.setArguments(bundle);
                FragmentTransaction homefragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                homefragmentTransaction.replace(R.id.mainDashboardFrameLayout, eventDetials,"Home Fragment").addToBackStack(null);
                homefragmentTransaction.commit();


            });

            eventNAme.setText(model.getKEY_EVENT_NAME());
            if (model.getKEY_EVENT_IMAGE1()!=null){
                Log.d("TAG", "bindView: 1"+model.getKEY_EVENT_IMAGE1());
                Glide.with(context).
                        load(model.getKEY_EVENT_IMAGE1())
                        .placeholder(R.drawable.progress_animation)
                        .into(eventImages);
            }else if (model.getKEY_EVENT_IMAGE2()!=null){
                Log.d("TAG", "bindView: 2"+model.getKEY_EVENT_IMAGE2());

                Glide.with(context).
                        load(model.getKEY_EVENT_IMAGE2())
                        .placeholder(R.drawable.progress_animation).
                        into(eventImages);
            }else if (model.getKEY_EVENT_IMAGE3()!=null){
                Log.d("TAG", "bindView: 3"+model.getKEY_EVENT_IMAGE3());
                Glide.with(context).
                        load(model.getKEY_EVENT_IMAGE3())
                        .placeholder(R.drawable.progress_animation).
                        into(eventImages);
            }else if (model.getKEY_EVENT_IMAGE4()!=null){
                Log.d("TAG", "bindView: 4"+model.getKEY_EVENT_IMAGE4());
                Glide.with(context).
                        load(model.getKEY_EVENT_IMAGE4())
                        .placeholder(R.drawable.progress_animation).
                        into(eventImages);
            }else if (model.getKEY_EVENT_IMAGE5()!=null){
                Log.d("TAG", "bindView: 5"+model.getKEY_EVENT_IMAGE5());
                Glide.with(context).
                        load(model.getKEY_EVENT_IMAGE5()) .placeholder(R.drawable.progress_animation).
                        into(eventImages);
            }else {
                Log.d("TAG", "bindView: 6"+context.getResources().getDrawable(R.drawable.ic_baseline_image_24));
                eventImages.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_image_24));
            }



        }
    }
}
