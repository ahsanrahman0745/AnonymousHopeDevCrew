package dev.devcrew.anonymoushopedevcrew.isuser.adpter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dev.devcrew.anonymoushopedevcrew.R;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    List<String>  stringList = new ArrayList<>();

    public ImageAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.stringList = list;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

  /*  private int[] sliderImageId = new int[]{
            R.drawable.image1, R.drawable.image2, R.drawable.image3,R.drawable.image4, R.drawable.image5,
    };
*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(stringList.get(position))
                .placeholder(R.drawable.progress_animation)
                .into(imageView);
        Log.d("TAG", "instantiateItem: "+stringList.get(position));
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return stringList.size();
    }
}