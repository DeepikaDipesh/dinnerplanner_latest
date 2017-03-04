package se.kth.csc.iprog.dinnerplanner.model;

import android.app.ProgressDialog;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class DinnerModel extends Observable implements IDinnerModel{

	public static final int STARTER = 1;
	public static final int MAIN = 2;
	public static final int DESERT = 3;
	Set<DishModelSpoon> dishes = new HashSet<DishModelSpoon>();
	Set<DishModelSpoon> selectedDishes = new HashSet<DishModelSpoon>();
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
		

		
	}
	
	/**
	 * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
	 */
	public Set<DishModelSpoon> getDishes(){
		return dishes;
	}
	

	/**
	 * Returns the set of dishes of specific type, that contain filter in their name
	 * or name of any ingredient. 
	 */
	/*public Set<Dish> filterDishesOfType(int type, String filter){
		Set<Dish> result = new HashSet<Dish>();
		for(Dish d : dishes){
			if(d.getType() == type && d.contains(filter)){
				result.add(d);
			}
		}
		return result;
	}*/


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

//	@Override
	/*public Dish getSelectedDish(int type) {
		return null;
	}*/

	@Override
	public Set<DishModelSpoon> getFullMenu() {
		return this.selectedDishes;
	}


	@Override
	public void addDishToMenu(DishModelSpoon selectedDish) {
		selectedDishes.add(selectedDish);
		setChanged();
		notifyObservers(selectedDishes);
	}

	@Override
	public void removeDishFromMenu(DishModelSpoon selectedDish) {
		selectedDishes.remove(selectedDish);
		setChanged();
		notifyObservers(selectedDishes);
	}

	@Override
	public Set<Ingredient> getAllIngredients() {

		Map<String,Ingredient> totalIngredients = new HashMap<String,Ingredient>();
		//Get me all selected dishes for the menu
		Iterator<DishModelSpoon> selectedDishesIterator = selectedDishes.iterator();


		while(selectedDishesIterator.hasNext()){
			DishModelSpoon _presentDish = selectedDishesIterator.next();

			//1. Get me all ingredients for a dish
			List<Ingredient> _presentDishIngredients =_presentDish.getIngredients();

			//2. Look through each of ingredients of present dish and add it to the total Ingredients
			Iterator<Ingredient> _presentDishIngredientsIterator = _presentDishIngredients.iterator();
			while(_presentDishIngredientsIterator.hasNext()){

				Ingredient _presentIngredient = _presentDishIngredientsIterator.next();

				//Access the total ingredient list to see if ingredient is part of list or not
				Ingredient _ingredientFromTheList = totalIngredients.get(_presentIngredient.getName());

				//IF the ingredient is already present in the  Total Ingredient List
				if (_ingredientFromTheList != null) {
					//Ingredient already present in total ingredient
					_ingredientFromTheList.setAmount(_ingredientFromTheList.getAmount()+_presentIngredient.getAmount());
					//+(_presentIngredient.getPrice()*getNumberOfGuests()));
					_ingredientFromTheList.setQuantity(_ingredientFromTheList.getAmount() +( _presentIngredient.getAmount()*getNumberOfGuests()));

				} else {
					// Ingredient not present in Total Ingredient
					//Create a new ingredient and add it
					Ingredient copyOfTheIngredient = new Ingredient(_presentIngredient.getName(),(_presentIngredient.getAmount()*getNumberOfGuests()), _presentIngredient.getUnit(), _presentIngredient.getAmount());
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
			totalCost = (float) (totalCost + (ingredientIterator.next().getAmount() * this.getNumberOfGuests()));
		}
		return totalCost;
	}

    public interface AsyncData {
        public void onData(Object data);
		public void onError (String errorMessage);
    }

    public void getDishes( int type, final AsyncData callback){

		final List<DishModelSpoon> dishesFromAPI;
        final String tag = "API Result";
        RequestParams params = new RequestParams();
        if (type == STARTER) {
            System.out.println("===========Fetching starters============");
            params.add("type", "appetizer");
        }
        if (type == MAIN){
            System.out.println("===========Fetching main course============");
            params.add("type","main course");
        }
        if (type == DESERT){
            System.out.println("===========Fetching Desserts============");
            params.add("type","dessert");
        }
		if (type == 0){
			params.add("query","dessert");
		}

            SpoonacularAPIClient.get("recipes/search", params, new JsonHttpResponseHandler() {

				private ProgressDialog dialog;

				@Override
				public void onStart() {

				}
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

					try {
						System.out.println(response.toString());
						Object object = response.get("results");

						callback.onData(object);
						//JSONArray results = response.getJSONArray("results");
						//System.out.println(results.toString());
						//Log.d(tag, results.toString());
						//Gson gson = new Gson();
						//Type type = new TypeToken<ArrayList<DishModelSpoon>>() {
						//}.getType();
						//dishesFromAPI = gson.fromJson(results.toString(), type);

					} catch (JSONException e) {
						e.printStackTrace();

					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					System.out.println(responseString);
					callback.onError(responseString);
				}

			});

		System.out.println("==================================================");
            // callback.onData(data); // You call the callback function once you have the results



        }

	public void searchDishes( String query, final AsyncData callback){

		final List<DishModelSpoon> dishesFromAPI;
		final String tag = "API Result";
		RequestParams params = new RequestParams();

		if (query.isEmpty() || query.trim().length()==0 || query.matches(".*\\d+.*")){
			callback.onError("Empty search String");
		}else{
			params.add("query",query);
		}


		SpoonacularAPIClient.get("recipes/search", params, new JsonHttpResponseHandler() {

			private ProgressDialog dialog;

			@Override
			public void onStart() {

			}
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

				try {
					System.out.println(response.toString());
					Object object = response.get("results");

					callback.onData(object);
					//JSONArray results = response.getJSONArray("results");
					//System.out.println(results.toString());
					//Log.d(tag, results.toString());
					//Gson gson = new Gson();
					//Type type = new TypeToken<ArrayList<DishModelSpoon>>() {
					//}.getType();
					//dishesFromAPI = gson.fromJson(results.toString(), type);

				} catch (JSONException e) {
					callback.onError(e.getMessage());

				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				System.out.println(responseString);
				callback.onError(responseString);
			}

		});

		System.out.println("==================================================");
		// callback.onData(data); // You call the callback function once you have the results



	}

	public void getIngredients( int id, final AsyncData callback){

		final List<DishModelSpoon> dishesFromAPI;
		final String tag = "API Result";
		RequestParams params = new RequestParams();


		SpoonacularAPIClient.get("recipes/"+id+"/information", params, new JsonHttpResponseHandler() {

			private ProgressDialog dialog;

			@Override
			public void onStart() {

			}
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


					//System.out.println(response.toString());
					//Object extendedIngredients =  response.get("extendedIngredients");
					//System.out.println(extendedIngredients.toString());
					callback.onData(response);
					//JSONArray results = response.getJSONArray("results");
					//System.out.println(results.toString());
					//Log.d(tag, results.toString());
					//Gson gson = new Gson();
					//Type type = new TypeToken<ArrayList<DishModelSpoon>>() {
					//}.getType();
					//dishesFromAPI = gson.fromJson(results.toString(), type);


			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				System.out.println(responseString);
			}

		});

		System.out.println("==================================================");
		// callback.onData(data); // You call the callback function once you have the results



	}
}
