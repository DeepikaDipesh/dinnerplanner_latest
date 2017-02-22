package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DinnerPlanner;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;

public class Main2Activity extends Activity implements View.OnClickListener {

    DinnerPlanner.MenuCalculator data = DinnerPlanner.MenuCalculator.getInstance();
    DinnerModel finallist = new DinnerModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent receive_intent = getIntent();

        DinnerModel dishes = (DinnerModel) getIntent().getSerializableExtra("mainActivityObject");

        TextView finalParticipantsValue = ((TextView) findViewById(R.id.totalNoOfParticipants));
        finalParticipantsValue.setText(String.valueOf(data.getNumberOfGuests()));

        TextView finalCostValue = (TextView) findViewById(R.id.finalCostValue);
        finalCostValue.setText(String.valueOf(data.getTotalMenuPrice()));


        //ingredientButton.
        ImageButton ingredientButton = (ImageButton) findViewById(R.id.ingredients);
        TextView description = (TextView) findViewById(R.id.description);
        Set<Ingredient> listOfIngredients = data.getAllIngredients();
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
        starterreceipeButton.setOnClickListener(this);
        maincourserecipeButton.setOnClickListener(this);
        desertrecipeButton.setOnClickListener(this);
        ingredientButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        int id = v.getId();
        switch (id) {
            case R.id.starterrecipe:
                //display recipe on button click
                int i=0;
                Set<Dish> finalStarterList = data.getFullMenu();
                int size = finalStarterList.size();
                TextView description = (TextView) findViewById(R.id.description);
                description.setMovementMethod(new ScrollingMovementMethod());
                description.setText("\n");
                Iterator<Dish> _finalStarterList = finalStarterList.iterator();
                while (_finalStarterList.hasNext()) {
                    Dish starterItem = _finalStarterList.next();
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
                Set<Dish> finalMainCourseList = data.getFullMenu();
                size = finalMainCourseList.size();
                Iterator<Dish> _finalMainCourseList = finalMainCourseList.iterator();
                while (_finalMainCourseList.hasNext()) {
                    Dish mainCourseItem = _finalMainCourseList.next();
                    int itemtype = mainCourseItem.getType();
                    if (itemtype == 2) {
                        String mainCourseRecipeDescription = mainCourseItem.getDescription();
                        description = (TextView) findViewById(R.id.description);
                        description.setText(mainCourseItem.getName()+"\n");
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
                Set<Dish> finalDesertList = data.getFullMenu();
                size = finalDesertList.size();
                Iterator<Dish> _finalDesertList = finalDesertList.iterator();
                while (_finalDesertList.hasNext()) {
                    Dish desertItem = _finalDesertList.next();
                    int itemType = desertItem.getType();
                    if (itemType == 3) {
                        String desertRecipeDescription = desertItem.getDescription();
                        description = (TextView) findViewById(R.id.description);
                        description.setText(desertItem.getName()+"\n");
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
                Set<Ingredient> listOfIngredients = data.getAllIngredients();
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
    }




