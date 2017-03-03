package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;

public class Main2Activity extends Activity implements Observer{

    DinnerModel finallist;
    TextView finalParticipantsValue;
    TextView finalCostValue;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receive_intent = getIntent();
        finallist = ((DinnerPlannerApplication)getApplication()).getModel();
       finallist.addObserver(this);

        finalParticipantsValue = ((TextView) findViewById(R.id.totalNoOfParticipants));
        finalParticipantsValue.setText(String.valueOf(finallist.getNumberOfGuests()));


        finalCostValue = (TextView) findViewById(R.id.finalCostValue);
        finalCostValue.setText(String.valueOf(finallist.getTotalMenuPrice()));

        //ingredientButton.
        ImageButton ingredientButton = (ImageButton) findViewById(R.id.ingredients);
        description = (TextView) findViewById(R.id.description);
        Set<Ingredient> listOfIngredients = finallist.getAllIngredients();
        Iterator<Ingredient> _listOfAllIngredientsIterator = listOfIngredients.iterator();
        while (_listOfAllIngredientsIterator.hasNext()) {
            Ingredient _ingredients = _listOfAllIngredientsIterator.next();
            String itemName = _ingredients.getName();
            description.append(itemName + "\t");

            double itemQuantity = _ingredients.getQuantity();
            description.append(String.valueOf(itemQuantity));

            String itemUnit = _ingredients.getUnit();
            description.append(itemUnit + "\n");


        }

        //********************SwitchCase to display recipes for different dishes*************//
        ImageButton maincourserecipeButton = (ImageButton) findViewById(R.id.maincourserecipe);
        ImageButton starterreceipeButton = (ImageButton) findViewById(R.id.starterrecipe);
        ImageButton desertrecipeButton = (ImageButton) findViewById(R.id.desertrecipe);
     /*  starterreceipeButton.setOnClickListener(this);
        maincourserecipeButton.setOnClickListener(this);
        desertrecipeButton.setOnClickListener(this);
        ingredientButton.setOnClickListener(this);*/

    }
    /*

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        int id = v.getId();
        switch (id) {
            case R.id.starterrecipe:
                //display recipe on button click
                int i=0;
                Set<DishModelSpoon> finalStarterList = finallist.getFullMenu();
                int size = finalStarterList.size();
                TextView description = (TextView) findViewById(R.id.description);
                description.setMovementMethod(new ScrollingMovementMethod());
                description.setText("\n");
                Iterator<DishModelSpoon> _finalStarterList = finalStarterList.iterator();
                while (_finalStarterList.hasNext()) {
                    DishModelSpoon starterItem = _finalStarterList.next();
                    int itemtype = starterItem.getType();
                    if (itemtype == 1) {
                        String starterRecipeDescription = starterItem.getDescription();
                        description.append(starterItem.getName() + "\n");
                        description.append(starterRecipeDescription);
                    } else
                        i++;
                }
                    if(i==size){
                        description.setText("No item selected, if you would like pleas go to previous page");
                    }
                break;

            case R.id.maincourserecipe:
                i= 0;
                Set<DishModelSpoon> finalMainCourseList = finallist.getFullMenu();
                size = finalMainCourseList.size();
                Iterator<DishModelSpoon> _finalMainCourseList = finalMainCourseList.iterator();
                while (_finalMainCourseList.hasNext()) {
                    DishModelSpoon mainCourseItem = _finalMainCourseList.next();
                    int itemtype = mainCourseItem.getType();
                    if (itemtype == 2) {
                        String mainCourseRecipeDescription = mainCourseItem.getDescription();
                        description = (TextView) findViewById(R.id.description);
                        description.setText(mainCourseItem.getTitle()+"\n");
                        description.append(mainCourseRecipeDescription);
                    }
                    else
                        i++;
                }
               if(i==size) {
                    description = (TextView) findViewById(R.id.description);
                   description.setText("No item selected, if you would like pleas go to previous page");
               }
                break;

            case R.id.desertrecipe:
                i= 0;
                Set<DishModelSpoon> finalDesertList = finallist.getFullMenu();
                size = finalDesertList.size();
                Iterator<DishModelSpoon> _finalDesertList = finalDesertList.iterator();
                while (_finalDesertList.hasNext()) {
                    DishModelSpoon desertItem = _finalDesertList.next();
                    int itemType = desertItem.getType();
                    if (itemType == 3) {
                        String desertRecipeDescription = desertItem.getDescription();
                        description = (TextView) findViewById(R.id.description);
                        description.setText(desertItem.getTitle()+"\n");
                        description.append(desertRecipeDescription);
                    }
                    else i++;
                }
                if(i==size) {
                        description = (TextView) findViewById(R.id.description);
                        description.setText("No item selected, if you would like pleas go to previous page");
                    }
               break;

            case R.id.ingredients:
                description = (TextView) findViewById(R.id.description);
                description.setText("\n");
                Set<Ingredient> listOfIngredients = finallist.getAllIngredients();
                Iterator<Ingredient> _listOfAllIngredientsIterator = listOfIngredients.iterator();
                while (_listOfAllIngredientsIterator.hasNext()) {
                    Ingredient _ingredients = _listOfAllIngredientsIterator.next();
                    String itemName = _ingredients.getName();
                    description.append(itemName + "\t");

                    double itemQuantity = _ingredients.getQuantity();
                    description.append(String.valueOf(itemQuantity));

                    String itemUnit = _ingredients.getUnit();
                    description.append(itemUnit + "\n");

                }
                break;
        }
    }
*/

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main2, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    @Override
    public void update(Observable observable, Object o) {
       // finalCostValue.setText(String.valueOf(finallist.getTotalMenuPrice()));
      //  description.setText(String.valueOf(finallist.getAllIngredients()));
    }

}




