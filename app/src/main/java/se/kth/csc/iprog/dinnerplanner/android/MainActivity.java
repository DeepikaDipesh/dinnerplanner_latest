package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;


public class MainActivity extends Activity implements Observer {

    int selected_no_of_guest;
    int totalPricePerDish = 0;
    Set<Ingredient> selectedDishIngredients = new HashSet<Ingredient>();
    Dish selectedDish = null;
    DinnerModel dinnerModel;
    TextView cost;
    List<Dish> starters_list;
    GridView gridView;
    DishesAdapter dishesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default call to load previous state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dinnerModel = ((DinnerPlannerApplication) getApplication()).getModel();
        dinnerModel.addObserver(this);


        Set<Dish> starters = dinnerModel.getDishesOfType(Dish.STARTER);
        starters_list = new ArrayList<Dish>(starters);
        gridView = (GridView) findViewById(R.id.gridViewStarter);
        dishesAdapter = new DishesAdapter(this, starters_list);
        gridView.setAdapter(dishesAdapter);

        Set<Dish> desert = dinnerModel.getDishesOfType(Dish.DESERT);
        final List<Dish> desert_list = new ArrayList<Dish>(desert);
        GridView gridView2 = (GridView) findViewById(R.id.gridViewDesert);
        DishesAdapter dishesAdapter2 = new DishesAdapter(this, desert_list);
        gridView2.setAdapter(dishesAdapter2);

        Set<Dish> main = dinnerModel.getDishesOfType(Dish.MAIN);
        final List<Dish> main_list = new ArrayList<Dish>(main);
        GridView gridView1 = (GridView) findViewById(R.id.gridViewMaincourse);
        DishesAdapter dishesAdapter1 = new DishesAdapter(this, main_list);
        gridView1.setAdapter(dishesAdapter1);


//        gridView.getPos

        Button b1 = (Button) findViewById(R.id.Create_button);
        cost = (TextView) findViewById(R.id.Cost);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_no_of_guest, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_of_guest = (String) adapterView.getItemAtPosition(i);
                selected_no_of_guest = Integer.parseInt(no_of_guest);
                dinnerModel.setNumberOfGuests(selected_no_of_guest);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Context context = this;

        /**********************Selecting Item from Starter*********************************/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            int flag = 0;

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
                                                String totalPricePerDishDisplay = costPrice(selectedDish);
                                                TextView price = (TextView) dialog.findViewById(R.id.totalPricePerDish);
                                                price.setText("Total cost for" + selected_no_of_guest + " guests: " + totalPricePerDishDisplay);


                                                Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
                                                // if button is clicked, close the custom dialog
                                                dialogButton.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        flag=1;
                                                        dialog.dismiss();
                                                        dinnerModel.addDishToMenu(selectedDish);
                                                       // cost.setText(String.valueOf(dinnerModel.getTotalMenuPrice() + " SEK"));
                                                    }
                                                });

                                                dialog.show();
                                                   // view.setBackgroundColor(Color.parseColor("#f46842"));
//                                                 if(flag==1){
//                                                     view.setSelected(true);
//                                                     flag = 0;
//                                                 }
//                                                else {
//                                                     view.setSelected(false);
//                                                 }
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
                                                 price.setText("Total cost for" + selected_no_of_guest + " guests: " + totalPricePerDishDisplay);


                                                 Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
                                                 // if button is clicked, close the custom dialog
                                                 dialogButton.setOnClickListener(new View.OnClickListener() {

                                                     @Override
                                                     public void onClick(View v) {
                                                         dialog.dismiss();
                                                         dinnerModel.addDishToMenu(selectedDish);
                                                         //cost.setText(String.valueOf(dinnerModel.getTotalMenuPrice()));
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
                                                 price.setText("Total cost for" + selected_no_of_guest + " guests: " + totalPricePerDishDisplay);


                                                 Button dialogButton = (Button) dialog.findViewById(R.id.chooseDishButton);
                                                 // if button is clicked, close the custom dialog
                                                 dialogButton.setOnClickListener(new View.OnClickListener() {


                                                     @Override
                                                     public void onClick(View v) {
                                                         dialog.dismiss();
                                                         dinnerModel.addDishToMenu(selectedDish);
                                                         //cost.setText(String.valueOf(dinnerModel.getTotalMenuPrice()) + " SEK");

                                                     }
                                                 });

                                                 dialog.show();
                                                 view.setSelected(true);

                                             }

                                         }

        );


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

    public String costPrice(Dish selectedDish) {

        double cost = 0;
        Set<Ingredient> _presentDishIngredients = selectedDish.getIngredients();
        Iterator<Ingredient> _presentDishIngredientsIterator = _presentDishIngredients.iterator();
        while (_presentDishIngredientsIterator.hasNext()) {
            Ingredient _presentIngredient = _presentDishIngredientsIterator.next();
            cost += _presentIngredient.getPrice();
        }
        System.out.println(cost);
        //participants_int = Integer.parseInt(stupid);
        String totalPricePerDish = String.valueOf(cost * dinnerModel.getNumberOfGuests());
        return totalPricePerDish;

    }


    @Override
    public void update(Observable observable, Object o) {
        //  int numguests = Integer.valueOf((Integer) o);
        cost.setText(String.valueOf(dinnerModel.getTotalMenuPrice()));
        dinnerModel.getAllIngredients();
        for(Dish d : dinnerModel.getFullMenu()){
            if(d.getType() == 1) {
                dishesAdapter.setSelectedDishId(d);
            }
        };


    }
}
