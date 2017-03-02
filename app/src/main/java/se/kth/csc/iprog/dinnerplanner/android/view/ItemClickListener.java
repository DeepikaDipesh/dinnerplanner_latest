package se.kth.csc.iprog.dinnerplanner.android.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import se.kth.csc.iprog.dinnerplanner.android.DinnerPlannerApplication;
import se.kth.csc.iprog.dinnerplanner.android.R;
import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;

/**
 * Created by dipeshmitthalal on 02/03/17.
 */
public class ItemClickListener implements AdapterView.OnItemClickListener{
    private View previousSelectedView;
    private ArrayList<DishModelSpoon> dishesList;
    private DinnerModel dinnerModel;
    public ItemClickListener(ArrayList<DishModelSpoon> _dishesList) {
        this.dishesList = _dishesList;
        dinnerModel = DinnerPlannerApplication.getModel();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.previousSelectedView = view;
        long itemIdAtPosition = parent.getItemIdAtPosition(position);
        final DishModelSpoon selectedDish = dishesList.get((int) itemIdAtPosition);

        // custom dialog
        final Dialog dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.activity_selecteddish);
        dialog.setTitle("You have selected Dish");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.selectedDishText);
        text.setText(selectedDish.getTitle());
        ImageView imageView = (ImageView) dialog.findViewById(R.id.selectedDishImage);


        Context context = imageView.getContext();
        String image = selectedDish.getImage();
        image = image.substring(0, image.lastIndexOf("."));
        int selectedDishImageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        imageView.setImageResource(selectedDishImageId);

       /*
        //price of dish for total no.of.guests
        String totalPricePerDishDisplay = costPrice(selectedDish);
        TextView price = (TextView) dialog.findViewById(R.id.totalPricePerDish);
        price.setText("Total cost for" + selected_no_of_guest + " guests: " + totalPricePerDishDisplay);


        Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
        Button CancelButton = (Button) dialog.findViewById(R.id.cancelDishButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                previousSelectedView.setBackground(v.getResources().getDrawable(R.drawable.redcolor));
                dinnerModel.addDishToMenu(selectedDish);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                previousSelectedView.setBackground(v.getResources().getDrawable(R.drawable.transparent));
                dinnerModel.removeDishFromMenu(selectedDish);
                previousSelectedView = null;
                dialog.dismiss();


            }
        });
*/
        dialog.show();
    }


}
