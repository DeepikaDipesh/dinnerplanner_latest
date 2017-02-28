package se.kth.csc.iprog.dinnerplanner.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import se.kth.csc.iprog.dinnerplanner.android.R;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;
import se.kth.csc.iprog.dinnerplanner.model.SpoonacularAPIClient;

import static com.squareup.picasso.Picasso.*;
import static se.kth.csc.iprog.dinnerplanner.android.R.drawable.meatballs;

public class DishesAdapter_spoonified extends BaseAdapter {

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
        public DishModelSpoon getItem(int position) {
             return dishes_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        public void setSelectedDishId(DishModelSpoon dish){
            selectedDish = dish;
            notifyDataSetChanged();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          //  if (convertView == null) {
                DishModelSpoon dish = dishes_list.get(position);
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.gridview_dishes, null);
                TextView textView = (TextView) grid.findViewById(R.id.textview_dish_name);
                final ImageView imageView = (ImageView)grid.findViewById(R.id.imageview_dish_picture);
                textView.setText(dish.getTitle());

                if(dish == selectedDish) {
                    grid.setSelected(true);
                }

                Context context = imageView.getContext();
                String image = dishes_list.get(position).getImage();
                image = image.substring(0, image.lastIndexOf("."));
                //System.out.println(image);
                //int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                //imageView.setImageResource(id);
                //imageView.setImageResource(R.drawable.meatballs);
                String imageUrl = "https://spoonacular.com/recipeImages/"+dish.getId()+"-240x150.jpg";
                Log.d("ImageURL",imageUrl);
                with(context).load(imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
                        imageView.setImageBitmap(bitmap);
                        Log.d("Picasso", "LoadedImage");
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                         Log.d("Picasso","OnBitmapfailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Log.d("Picasso","OnPrepareLoad");
                    }
                });

          //  } else {
         //       grid = (View) convertView;
          //  }

            return grid;
        }


}

