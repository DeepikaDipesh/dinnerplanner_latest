package se.kth.csc.iprog.dinnerplanner.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Created by dipeshmitthalal on 07/02/17.
 */
public class DinnerPlanner {
    public static class MenuCalculator extends Observable implements IDinnerModel {
       // Set<Dish> dishes = new HashSet<Dish>();
        Set<Dish> selectedDishes = new HashSet<Dish>();

        public int numberOfguests;
        @Override
        public int getNumberOfGuests() {
            return numberOfguests;
        }

        @Override
        public void setNumberOfGuests(int numberOfGuests) {
            this.numberOfguests = numberOfGuests;
            setChanged();
            notifyObservers(numberOfguests);
        }

        @Override
        public Dish getSelectedDish(int type) {
            return null;
        }

        @Override
        public Set<Dish> getFullMenu() {
            return this.selectedDishes;
        }

        @Override
        public Set<Ingredient> getAllIngredients() {

            Map<String,Ingredient> totalIngredients = new HashMap<String,Ingredient>();
            //Get me all selected dishes for the menu
            Iterator<Dish> selectedDishesIterator = selectedDishes.iterator();


            while(selectedDishesIterator.hasNext()){
                Dish _presentDish = selectedDishesIterator.next();

                //1. Get me all ingredients for a dish
                Set<Ingredient> _presentDishIngredients =_presentDish.getIngredients();

                //2. Look through each of ingredients of present dish and add it to the total Ingredients
                Iterator<Ingredient> _presentDishIngredientsIterator = _presentDishIngredients.iterator();
                while(_presentDishIngredientsIterator.hasNext()){

                    Ingredient _presentIngredient = _presentDishIngredientsIterator.next();

                    //Access the total ingredient list to see if ingredient is part of list or not
                    Ingredient _ingredientFromTheList = totalIngredients.get(_presentIngredient.getName());

                    //IF the ingredient is already present in the  Total Ingredient List
                    if (_ingredientFromTheList != null) {
                        //Ingredient already present in total ingredient
                        _ingredientFromTheList.setPrice(_ingredientFromTheList.getPrice()+(_presentIngredient.getPrice()*getNumberOfGuests()));
                        _ingredientFromTheList.setQuantity(_ingredientFromTheList.getQuantity() +( _presentIngredient.getQuantity()*getNumberOfGuests()));

                    } else {
                        // Ingredient not present in Total Ingredient
                        //Create a new ingredient and add it
                        Ingredient copyOfTheIngredient = new Ingredient(_presentIngredient.getName(),(_presentIngredient.getQuantity()*getNumberOfGuests()), _presentIngredient.getUnit(), _presentIngredient.getPrice());
                        totalIngredients.put(_presentIngredient.getName(),copyOfTheIngredient);
                    }


                }
            }
            Set<Ingredient> ingredientSet = new HashSet<Ingredient>(totalIngredients.values());

            return ingredientSet;
        }

        @Override
        public float getTotalMenuPrice() {

            Set<Ingredient> totalIngredients = this.getAllIngredients();
            Iterator<Ingredient> ingredientIterator = totalIngredients.iterator();
            float totalCost = 0;

            while(ingredientIterator.hasNext()){
                totalCost = (float) (totalCost + (ingredientIterator.next().getPrice() * (double) this.getNumberOfGuests()));
            }
            return totalCost;
        }

        @Override
        public void addDishToMenu(Dish dish) {
            selectedDishes.add(dish);

        }

        @Override
        public void removeDishFromMenu(Dish dish) {

        }


        private static final MenuCalculator instanceOfMenuCalculator = new MenuCalculator();
        public static MenuCalculator getInstance() {return instanceOfMenuCalculator;}

    }
}