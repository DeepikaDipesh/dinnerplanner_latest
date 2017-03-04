package se.kth.csc.iprog.dinnerplanner.android.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import se.kth.csc.iprog.dinnerplanner.android.DinnerPlannerApplication;
import se.kth.csc.iprog.dinnerplanner.android.R;
import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;

/**
 * Created by dipeshmitthalal on 02/03/17.
 */
public class ItemClickListener implements AdapterView.OnItemClickListener, Observer{
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
        final int selected_no_of_guest = DinnerPlannerApplication.getModel().getNumberOfGuests();

        // custom dialog
        final Dialog dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.activity_selecteddish);
        dialog.setTitle("You have selected Dish");
        final TextView price = (TextView) dialog.findViewById(R.id.totalPricePerDish);
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.selectedDishText);
        text.setText((CharSequence) selectedDish.getTitle());

        Log.d("Selected Dish", String.valueOf(selectedDish.getTitle()));
        ImageView imageView = (ImageView) dialog.findViewById(R.id.selectedDishImage);
        imageView.setImageBitmap(selectedDish.getDishBitmap());
        if (selected_no_of_guest == 0){
            Toast.makeText(view.getContext(),"Please select Number of participants", Toast.LENGTH_LONG).show();
        }
        else {

            dinnerModel.getIngredients(selectedDish.getId(), new DinnerModel.AsyncData() {

                @Override
                public void onData(Object data) {
                    try {
                        JSONObject jsonObject = (JSONObject) data;
                        selectedDish.setInstructions(jsonObject.getString("instructions"));

                        List<Ingredient> ingredientList = convertJSONArrayToList((JSONArray) jsonObject.get("extendedIngredients"));
                        selectedDish.setIngredients(ingredientList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String totalPricePerDishDisplay = costPrice(selectedDish);

                    price.setText("Total cost for" + selected_no_of_guest + " guests: " + totalPricePerDishDisplay);
                    dialog.show();
                }

                @Override
                public void onError(String errorMessage) {

                }
            });
        }



        //price of dish for total no.of.guests



        Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
        Button CancelButton = (Button) dialog.findViewById(R.id.cancelDishButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                previousSelectedView.setBackground(v.getResources().getDrawable(R.drawable.redcolor));
                dinnerModel.addDishToMenu(selectedDish);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                previousSelectedView.setBackground(v.getResources().getDrawable(R.drawable.transparent));
                dinnerModel.removeDishFromMenu(selectedDish);
                previousSelectedView = null;
                dialog.dismiss();


            }
        });



    }
    public String costPrice(DishModelSpoon selectedDish) {

        double cost = 0;
        List<Ingredient> _presentDishIngredients = selectedDish.getIngredients();
        Iterator<Ingredient> _presentDishIngredientsIterator = _presentDishIngredients.iterator();
        while (_presentDishIngredientsIterator.hasNext()) {
            Ingredient _presentIngredient = _presentDishIngredientsIterator.next();
            cost += _presentIngredient.getAmount();
        }
        System.out.println(cost);

        //participants_int = Integer.parseInt(stupid);
        String totalPricePerDish = String.valueOf(cost * dinnerModel.getNumberOfGuests());
        return totalPricePerDish;

    }
    private static List<Ingredient> convertJSONArrayToList(JSONArray jsonArrayOfIngredients) {

        List<Ingredient> ingredientsFromAPI;
        //do something here

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Ingredient>>() {
        }.getType();
        ingredientsFromAPI = gson.fromJson(jsonArrayOfIngredients.toString(), type);
        return ingredientsFromAPI;
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
