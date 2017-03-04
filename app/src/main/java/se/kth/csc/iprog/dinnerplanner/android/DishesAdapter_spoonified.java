package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;

import static com.squareup.picasso.Picasso.*;


public class DishesAdapter_spoonified extends BaseAdapter implements Filterable {
    private View previousSelectedView;
    private final Context mContext;
    private List<DishModelSpoon> dishes_list;
    DishModelSpoon selectedDish = null;

    // 1
    public DishesAdapter_spoonified(Context context, List<DishModelSpoon> dishes_list) {
        this.mContext = context;
        this.dishes_list = dishes_list;
    }

    @Override
    public int getCount() {
        return dishes_list.size();
    }

    @Override
    public Object getItem(int position) {
        return dishes_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
/*
        public void setSelectedDishId(DishModelSpoon dish){
            selectedDish = dish;
            notifyDataSetChanged();
        }*/


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            final DishModelSpoon dish = dishes_list.get(position);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridview_dishes, null);
            TextView textView = (TextView) grid.findViewById(R.id.textview_dish_name);
            final ImageView imageView = (ImageView) grid.findViewById(R.id.imageview_dish_picture);
            textView.setText(dish.getTitle());
/*
                if(dish == selectedDish) {
                    grid.setSelected(true);
                }
                */

            Context context = imageView.getContext();
            final String imageUrl = "https://spoonacular.com/recipeImages/" + dish.getId() + "-240x150.jpg";
            Log.d("ImageURL", imageUrl);

            //  Picasso.with(context).load(imageUrl).placeholder(R.drawable.progress_animation).into(imageView);
              /* Picasso.with(context).load(imageUrl).placeholder(R.drawable.progress_animation).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
                       dish.setDishBitmap(bitmap);
                        imageView.setImageBitmap(bitmap);

                        Log.d("Picasso", "LoadedImage");
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d("Picasso", "OnBitmapfailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Log.d("Picasso", "OnPrepareLoad");
                       // imageView.setImageDrawable(placeHolderDrawable);

                    }


                });*/
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.progress_animation).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    dish.setDishBitmap(bitmap);

                }

                @Override
                public void onError() {

                }
            });


        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}


