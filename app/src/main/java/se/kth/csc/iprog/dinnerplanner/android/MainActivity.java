package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.android.view.ExampleView;
import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DinnerPlanner;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;


public class MainActivity extends Activity {

    String stupid;
    int participants_int;
    int totalPricePerDish = 0;
    Set<Ingredient> selectedDishIngredients = new HashSet<Ingredient>();
    Dish selectedDish = null;
    Set<Dish> dishAddedToTheMenu = new HashSet<Dish>();
    DinnerModel dishes = null;
    DinnerPlanner.MenuCalculator  dinnerPlanner = DinnerPlanner.MenuCalculator.getInstance();
    TextView cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent receive_Intent_i = getIntent();
        // Default call to load previous state
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dishes = new DinnerModel();
        Set<Dish> starters = dishes.getDishesOfType(Dish.STARTER);
        final List<Dish> starters_list = new ArrayList<Dish>(starters);
        GridView gridView = (GridView) findViewById(R.id.gridViewStarter);
        DishesAdapter dishesAdapter = new DishesAdapter(this, starters_list);
        gridView.setAdapter(dishesAdapter);

        Set<Dish> desert = dishes.getDishesOfType(Dish.DESERT);
        final List<Dish> desert_list = new ArrayList<Dish>(desert);
        GridView gridView2 = (GridView) findViewById(R.id.gridViewDesert);
        DishesAdapter dishesAdapter2 = new DishesAdapter(this, desert_list);
        gridView2.setAdapter(dishesAdapter2);

        Set<Dish> main = dishes.getDishesOfType(Dish.MAIN);
        final List<Dish> main_list = new ArrayList<Dish>(main);
        GridView gridView1 = (GridView) findViewById(R.id.gridViewMaincourse);
        DishesAdapter dishesAdapter1 = new DishesAdapter(this, main_list);
        gridView1.setAdapter(dishesAdapter1);

       // ExampleView mainView = new ExampleView(findViewById(R.id.this_is_example_view_id));


        Button b1 = (Button) findViewById(R.id.Create_button);
        cost = (TextView)findViewById(R.id.Cost);
        final EditText No_of_participant = (EditText)findViewById(R.id.No_of_participants);




       final Context context = this;

        /**********************Selecting Item from Starter*********************************/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long itemIdAtPosition = parent.getItemIdAtPosition(position);
                selectedDish = starters_list.get((int) itemIdAtPosition);

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_selecteddish);
                dialog.setTitle("You have selected Dish");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.selectedDishText);
                text.setText(selectedDish.getName());
                ImageView imageView = (ImageView) dialog.findViewById(R.id.selectedDishImage);


                Context context = imageView.getContext();
                String image = selectedDish.getImage();
                image = image.substring(0, image.lastIndexOf("."));
                int selectedDishImageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                imageView.setImageResource(selectedDishImageId);

                //price of dish for total no.of.guests
                stupid = (String) No_of_participant.getText().toString();
                String totalPricePerDishDisplay = costPrice(selectedDish);
                TextView price = (TextView) dialog.findViewById(R.id.totalPricePerDish);
                price.setText("Total cost for" + stupid + " guests: " + totalPricePerDishDisplay);


                    Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            TextView totalCostForDinner = (TextView) findViewById(R.id.Cost);
                            totalCostForDinner.setText(totalPricePerDish + " SEK");
                            dinnerPlanner.addDishToMenu(selectedDish);
                            cost.setText(String.valueOf(dinnerPlanner.getTotalMenuPrice()));

                        }
                    });

                    dialog.show();
                    view.setSelected(true);
                }

            }

            );


        dinnerPlanner.setNumberOfGuests(participants_int);
        No_of_participant.addTextChangedListener(new

                                                             TextWatcher() {


                                                                 @Override
                                                                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                                 }

                                                                 @Override
                                                                 public void onTextChanged(CharSequence s, int start,
                                                                                           int before, int count) {
                                                                     stupid = (String) No_of_participant.getText().toString();
                                                                     participants_int = Integer.parseInt(0 + stupid);
                                                                     Toast.makeText(getApplicationContext(), "Participants - " + stupid, Toast.LENGTH_LONG).show();

                                                                     dinnerPlanner.setNumberOfGuests(participants_int);
                                                                 }

                                                                 @Override
                                                                 public void afterTextChanged(Editable editable) {

                                                                 }
                                                             }

            );
            /**********************Selecting Item from MainCourse*********************************/

            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                             {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                     long itemIdAtPosition = parent.getItemIdAtPosition(position);
                                                     selectedDish = main_list.get((int) itemIdAtPosition);

                                                     // custom dialog
                                                     final Dialog dialog = new Dialog(context);
                                                     dialog.setContentView(R.layout.activity_selecteddish);
                                                     dialog.setTitle("You have selected Dish");

                                                     // set the custom dialog components - text, image and button
                                                     TextView text = (TextView) dialog.findViewById(R.id.selectedDishText);
                                                     text.setText(selectedDish.getName());
                                                     ImageView imageView = (ImageView) dialog.findViewById(R.id.selectedDishImage);


                                                     Context context = imageView.getContext();
                                                     String image = selectedDish.getImage();
                                                     image = image.substring(0, image.lastIndexOf("."));

                                                     int selectedDishImageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                                                     imageView.setImageResource(selectedDishImageId);

                                                     //price of dish for total no.of.guests

                                                     String totalPricePerDishDisplay = costPrice(selectedDish);
                                                     TextView price = (TextView) dialog.findViewById(R.id.totalPricePerDish);
                                                     price.setText("Total cost for" + stupid + " guests: " + totalPricePerDishDisplay);


                                                     Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
                                                     // if button is clicked, close the custom dialog
                                                     dialogButton.setOnClickListener(new View.OnClickListener() {

                                                         @Override
                                                         public void onClick(View v) {
                                                             dialog.dismiss();
                                                             TextView totalCostForDinner = (TextView) findViewById(R.id.Cost);
                                                             totalCostForDinner.setText(totalPricePerDish + " SEK");
                                                             dinnerPlanner.addDishToMenu(selectedDish);
                                                             cost.setText(String.valueOf(dinnerPlanner.getTotalMenuPrice()));
                                                         }
                                                     });

                                                     dialog.show();
                                                     view.setSelected(true);
                                                 }

                                             }

            );

            /**********************Selecting Item from Desert*********************************/

            gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                             {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                     long itemIdAtPosition = parent.getItemIdAtPosition(position);
                                                     selectedDish = desert_list.get((int) itemIdAtPosition);

                                                     // custom dialog
                                                     final Dialog dialog = new Dialog(context);
                                                     dialog.setContentView(R.layout.activity_selecteddish);
                                                     dialog.setTitle("You have selected Dish");

                                                     // set the custom dialog components - text, image and button
                                                     TextView text = (TextView) dialog.findViewById(R.id.selectedDishText);
                                                     text.setText(selectedDish.getName());
                                                     ImageView imageView = (ImageView) dialog.findViewById(R.id.selectedDishImage);


                                                     Context context = imageView.getContext();
                                                     String image = selectedDish.getImage();
                                                     image = image.substring(0, image.lastIndexOf("."));
                                                     System.out.println(image);
                                                     int selectedDishImageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                                                     imageView.setImageResource(selectedDishImageId);

                                                     //price of dish for total no.of.guests

                                                     String totalPricePerDishDisplay = costPrice(selectedDish);
                                                     TextView price = (TextView) dialog.findViewById(R.id.totalPricePerDish);
                                                     price.setText("Total cost for" + stupid + " guests: " + totalPricePerDishDisplay);


                                                     Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
                                                     // if button is clicked, close the custom dialog
                                                     dialogButton.setOnClickListener(new View.OnClickListener() {


                                                         @Override
                                                         public void onClick(View v) {
                                                             dialog.dismiss();
                                                             TextView totalCostForDinner = (TextView) findViewById(R.id.Cost);
                                                             totalCostForDinner.setText(totalPricePerDish + " SEK");
                                                             dinnerPlanner.addDishToMenu(selectedDish);
                                                             cost.setText(String.valueOf(dinnerPlanner.getTotalMenuPrice()));

                                                         }
                                                     });

                                                     dialog.show();
                                                     view.setSelected(true);

                                                 }

                                             }

            );




             System.out.println(dinnerPlanner.getTotalMenuPrice());


            /**********************Create Menu and Move to next activity****************************/

            b1.setOnClickListener(new View.OnClickListener()

                                  {

                                      public void onClick(View v) {


                                          Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                          startActivity(i);
                                      }
                                  }

            );
        }

    public String costPrice(Dish selectedDish){

        double cost = 0;
        Set<Ingredient> _presentDishIngredients = selectedDish.getIngredients();
        Iterator<Ingredient> _presentDishIngredientsIterator = _presentDishIngredients.iterator();
        while (_presentDishIngredientsIterator.hasNext()) {
            Ingredient _presentIngredient = _presentDishIngredientsIterator.next();
             cost += _presentIngredient.getPrice();
        }
        System.out.println(cost);

        participants_int = Integer.parseInt(stupid);
        // int pricePerDish = selectedDish.getPrice();
        // System.out.println(pricePerDish);
        String totalPricePerDish = String.valueOf(cost * dinnerPlanner.getNumberOfGuests());

        return totalPricePerDish;

    }


}
