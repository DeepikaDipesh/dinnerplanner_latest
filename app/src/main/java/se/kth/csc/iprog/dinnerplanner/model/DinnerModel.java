package se.kth.csc.iprog.dinnerplanner.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observable;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import se.kth.csc.iprog.dinnerplanner.android.Main2Activity;

public class DinnerModel extends Observable implements IDinnerModel{
	

	Set<Dish> dishes = new HashSet<Dish>();
	Set<Dish> selectedDishes = new HashSet<Dish>();
	public int numberOfguests;

	/**
	 * TODO: For Lab2 you need to implement the IDinnerModel interface.
	 * When you do this you will have all the needed fields and methods
	 * for the dinner planner (number of guests, selected dishes, etc.). 
	 */
	
	
	/**
	 * The constructor of the overall model. Set the default values here
	 */
	public DinnerModel(){
		
		//Adding some example data, you can add more
		Dish dish1 = new Dish("French toast",Dish.STARTER,"toast.jpg","In a large mixing bowl, beat the eggs. Add the milk, brown sugar and nutmeg; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.\n\n");
		Ingredient dish1ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish1ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish1ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish1ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
		Ingredient dish1ing5 = new Ingredient("white bread",2,"slices",2);
		dish1.addIngredient(dish1ing1);
		dish1.addIngredient(dish1ing2);
		dish1.addIngredient(dish1ing3);
		dish1.addIngredient(dish1ing4);
		dish1.addIngredient(dish1ing5);
		dishes.add(dish1);
		
		Dish dish2 = new Dish("Meat balls",Dish.MAIN,"meatballs.jpg","Preheat an oven to 400 degrees F (200 degrees C). Place the beef into a mixing bowl, and season with salt, onion, garlic salt, Italian seasoning, oregano, red pepper flakes, hot pepper sauce, and Worcestershire sauce; mix well. Add the milk, Parmesan cheese, and bread crumbs. Mix until evenly blended, then form into 1 1/2-inch meatballs, and place onto a baking sheet. Bake in the preheated oven until no longer pink in the center, 20 to 25 minutes.\n\n");
		Ingredient dish2ing1 = new Ingredient("extra lean ground beef",115,"g",20);
		Ingredient dish2ing2 = new Ingredient("sea salt",0.7,"g",3);
		Ingredient dish2ing3 = new Ingredient("small onion, diced",0.25,"",2);
		Ingredient dish2ing4 = new Ingredient("garlic salt",0.6,"g",3);
		Ingredient dish2ing5 = new Ingredient("Italian seasoning",0.3,"g",3);
		Ingredient dish2ing6 = new Ingredient("dried oregano",0.3,"g",3);
		Ingredient dish2ing7 = new Ingredient("crushed red pepper flakes",0.6,"g",3);
		Ingredient dish2ing8 = new Ingredient("Worcestershire sauce",16,"ml",7);
		Ingredient dish2ing9 = new Ingredient("milk",20,"ml",4);
		Ingredient dish2ing10 = new Ingredient("grated Parmesan cheese",5,"g",8);
		Ingredient dish2ing11 = new Ingredient("seasoned bread crumbs",115,"g",4);
		dish2.addIngredient(dish2ing1);
		dish2.addIngredient(dish2ing2);
		dish2.addIngredient(dish2ing3);
		dish2.addIngredient(dish2ing4);
		dish2.addIngredient(dish2ing5);
		dish2.addIngredient(dish2ing6);
		dish2.addIngredient(dish2ing7);
		dish2.addIngredient(dish2ing8);
		dish2.addIngredient(dish2ing9);
		dish2.addIngredient(dish2ing10);
		dish2.addIngredient(dish2ing11);
		dishes.add(dish2);

		Dish dish3 = new Dish("Icecream",Dish.DESERT,"icecream.jpg","In a large mixing bowl, beat the eggs. Add the milk, brown sugar and nutmeg; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.\n\n");
		Ingredient dish3ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish3ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish3ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish3ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
		Ingredient dish3ing5 = new Ingredient("white bread",2,"slices",2);
		dish3.addIngredient(dish3ing1);
		dish3.addIngredient(dish3ing2);
		dish3.addIngredient(dish3ing3);
		dish3.addIngredient(dish3ing4);
		dish3.addIngredient(dish3ing5);
		dishes.add(dish3);

		Dish dish4 = new Dish("BakedBrie",Dish.STARTER,"bakedbrie.jpg","In a small mixing bowl, beat the eggs. Add the milk, sugar and cardomam; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.\n\n");
		Ingredient dish4ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish4ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish4ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish4ing4 = new Ingredient("ground cardomam",0.5,"g",12);
		Ingredient dish4ing5 = new Ingredient("white bread",2,"slices",2);
		dish4.addIngredient(dish4ing1);
		dish4.addIngredient(dish4ing2);
		dish4.addIngredient(dish4ing3);
		dish4.addIngredient(dish4ing4);
		dish4.addIngredient(dish4ing5);
		dishes.add(dish4);

		Dish dish5 = new Dish("French toast",Dish.STARTER,"toast.jpg","In a large mixing bowl, beat the eggs. Add the milk, brown sugar and nutmeg; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.\n\n");
		Ingredient dish5ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish5ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish5ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish5ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
		Ingredient dish5ing5 = new Ingredient("white bread",2,"slices",2);
		dish5.addIngredient(dish5ing1);
		dish5.addIngredient(dish5ing2);
		dish5.addIngredient(dish5ing3);
		dish5.addIngredient(dish5ing4);
		dish5.addIngredient(dish5ing5);
		dishes.add(dish5);

		Dish dish6 = new Dish("French toast",Dish.STARTER,"toast.jpg","In a large mixing bowl, beat the eggs. Add the milk, brown sugar and nutmeg; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.\n\n");
		Ingredient dish6ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish6ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish6ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish6ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
		Ingredient dish6ing5 = new Ingredient("white bread",2,"slices",2);
		dish6.addIngredient(dish6ing1);
		dish6.addIngredient(dish6ing2);
		dish6.addIngredient(dish6ing3);
		dish6.addIngredient(dish6ing4);
		dish6.addIngredient(dish6ing5);
		dishes.add(dish6);

		Dish dish7 = new Dish("sourdough",Dish.STARTER,"sourdough.jpg","Trim and finely chop the spring onions, and pick and finely chop the parsley. Beat the egg. Combine the crabmeat, potatoes, onion, parsley, pepper, cayenne and egg in a bowl with a little sea salt. Refrigerate for 30 minutes, then shape into 6cm cakes.\n\n");
		Ingredient dish7ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish7ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish7ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish7ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
		Ingredient dish7ing5 = new Ingredient("white bread",2,"slices",2);
		dish7.addIngredient(dish7ing1);
		dish7.addIngredient(dish7ing2);
		dish7.addIngredient(dish7ing3);
		dish7.addIngredient(dish7ing4);
		dish7.addIngredient(dish7ing5);
		dishes.add(dish7);
		
	}
	
	/**
	 * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
	 */
	public Set<Dish> getDishes(){
		SpoonacularAPIClient.get("recipes/search", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(responseString);
            }
        });
		return dishes;
	}
	
	/**
	 * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
	 */
	public Set<Dish> getDishesOfType(int type){
		Set<Dish> result = new HashSet<Dish>();
		for(Dish d : dishes){
			if(d.getType() == type){
				result.add(d);
			}
		}

		return result;
	}


	
	/**
	 * Returns the set of dishes of specific type, that contain filter in their name
	 * or name of any ingredient. 
	 */
	public Set<Dish> filterDishesOfType(int type, String filter){
		Set<Dish> result = new HashSet<Dish>();
		for(Dish d : dishes){
			if(d.getType() == type && d.contains(filter)){
				result.add(d);
			}
		}
		return result;
	}


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
	public void addDishToMenu(Dish dish) {
		selectedDishes.add(dish);
		setChanged();
		notifyObservers(selectedDishes);
	}

	@Override
	public void removeDishFromMenu(Dish dish) {

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
					_ingredientFromTheList.setPrice(_ingredientFromTheList.getPrice()+_presentIngredient.getPrice());
					//+(_presentIngredient.getPrice()*getNumberOfGuests()));
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
			totalCost = (float) (totalCost + (ingredientIterator.next().getPrice() * this.getNumberOfGuests()));
		}
		return totalCost;
	}

    public interface AsyncData {
        public void onData(Object data);
    }

    public void getDishes( int type, final AsyncData callback){
        final String tag = "API Result";
        RequestParams params = new RequestParams();
        if (type == Dish.STARTER) {
            System.out.println("===========Fetching starters============");
            params.add("type", "appetizer");
        }
        if (type == Dish.MAIN){
            System.out.println("===========Fetching main course============");
            params.add("type","main course");
        }
        if (type == Dish.DESERT){
            System.out.println("===========Fetching Desserts============");
            params.add("type","dessert");
        }

            SpoonacularAPIClient.get("recipes/search", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        System.out.println(response.toString());
                        JSONArray results = response.getJSONArray("results");
                        System.out.println(results.toString());
                        Log.d(tag, results.toString());
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DishModelSpoon>>(){}.getType();
                        List<DishModelSpoon> dishes = gson.fromJson(results.toString(), type);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println(responseString);
                }
            });
            System.out.println("==================================================");
            // callback.onData(data); // You call the callback function once you have the results
        callback.onData(dishes);
        }


}
