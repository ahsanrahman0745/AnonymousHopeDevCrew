package dev.devcrew.anonymoushopedevcrew.isuser.adpter;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_PRODUCT_CATEGORY;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isuser.CategoryList;
import dev.devcrew.anonymoushopedevcrew.isuser.PostProduct;
import dev.devcrew.anonymoushopedevcrew.isuser.model.CategoryItem;

public class HomeAdpter extends RecyclerView.Adapter<HomeAdpter.ViewHOME> {
    List<CategoryItem> categoryItemList ;
    Context context ;

    public HomeAdpter(List<CategoryItem> categoryItemList, Context context) {
        this.categoryItemList = categoryItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHOME onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itemcategory, null);
        return new ViewHOME(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOME holder, int position) {
        holder.bindView(position,categoryItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public class ViewHOME extends RecyclerView.ViewHolder {
        ImageView txt_itemName;
        TextView img_item;
        LinearLayout ll_category_list;
        public ViewHOME(@NonNull View itemView) {
            super(itemView);
            img_item = itemView.findViewById(R.id.img_item);
            ll_category_list = itemView.findViewById(R.id.ll_category_list);
            txt_itemName = itemView.findViewById(R.id.txt_itemName);
        }

        public void bindView(int position, CategoryItem categoryItem) {
            img_item.setText(categoryItem.getName());
            Picasso.get()
                    .load(categoryItem.getImages())
                    .placeholder(R.drawable.progress_animation)
                    .into(txt_itemName);

            ll_category_list.setOnClickListener(View->{
                CategoryList categoryList = new CategoryList();
                Bundle bundle=new Bundle();
                bundle.putString(KEY_PRODUCT_CATEGORY,categoryItem.getName());
                categoryList.setArguments(bundle);
                FragmentTransaction postProductTransaction =((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                postProductTransaction.replace(R.id.mainDashboardFrameLayout, categoryList,"Home Fragment").addToBackStack(null);
                postProductTransaction.commit();
            });
        }
    }
}
