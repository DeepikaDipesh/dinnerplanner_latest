package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

import se.kth.csc.iprog.dinnerplanner.android.view.ItemClickListener;
import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;


public class MainActivity extends Activity implements Observer {

    int selected_no_of_guest;
    int totalPricePerDish = 0;
    Set<Ingredient> selectedDishIngredients = new HashSet<Ingredient>();

    DishModelSpoon selectedDish = null;
    DinnerModel dinnerModel;
    TextView cost;

    //Declaring all GridViews
    GridView startersGridView;
    GridView mainCourseGridView;
    GridView dessertGridView;

    //Declaring all Custom array Adapters
    DishesAdapter_spoonified starterDishAdapter;
    DishesAdapter_spoonified mainCourseDishAdapter;
    DishesAdapter_spoonified dessertsDishAdapter;

    //Declaring all lists to hold dishes data fetched form spoon API
    ArrayList<DishModelSpoon> dessertFromAPI;
    ArrayList<DishModelSpoon> startersFromAPI;
    ArrayList<DishModelSpoon> mainCourseFromAPI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default call to load previous state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dinnerModel = ((DinnerPlannerApplication) getApplication()).getModel();
        //Working in Rest Version
        dinnerModel.addObserver(this);


        // Instantiating lists to hold data from API
        mainCourseFromAPI = new ArrayList<DishModelSpoon>();
        dessertFromAPI = new ArrayList<DishModelSpoon>();
        startersFromAPI = new ArrayList<DishModelSpoon>();


        //Instantiating views for holding different category of dishes
        startersGridView = (GridView) findViewById(R.id.gridViewStarter);
        dessertGridView = (GridView) findViewById(R.id.gridViewDesert);
        mainCourseGridView = (GridView) findViewById(R.id.gridViewMaincourse);
        ImageButton search = (ImageButton) findViewById(R.id.search);


        //  dishesAdapter = new DishesAdapter(this, starters_list);
        starterDishAdapter = new DishesAdapter_spoonified(this, startersFromAPI);
        mainCourseDishAdapter = new DishesAdapter_spoonified(this, mainCourseFromAPI);
        dessertsDishAdapter = new DishesAdapter_spoonified(this,dessertFromAPI);


        //Assigning adapter for gridviews
        startersGridView.setAdapter(starterDishAdapter);
        mainCourseGridView.setAdapter(mainCourseDishAdapter);
        dessertGridView.setAdapter(dessertsDishAdapter);


        //Populate the list with data, notify the apadpter about data update and refresh grid
        dinnerModel.getDishes(DinnerModel.STARTER, new DinnerModel.AsyncData() {
            @Override
            public void onData(Object dishes) {
                List<DishModelSpoon> starters_list_Spoon = convertJSONObjectToList(dishes,DinnerModel.STARTER);
                startersFromAPI.addAll(starters_list_Spoon);
                starterDishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        startersGridView.setOnItemClickListener(new ItemClickListener(startersFromAPI));
        dinnerModel.getDishes(DinnerModel.MAIN, new DinnerModel.AsyncData() {
            @Override
            public void onData(Object dishes) {
                List<DishModelSpoon> maincourse_list_Spoon = convertJSONObjectToList(dishes,DinnerModel.MAIN);
                mainCourseFromAPI.addAll(maincourse_list_Spoon);
                mainCourseDishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getApplicationContext(),errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        mainCourseGridView.setOnItemClickListener(new ItemClickListener(mainCourseFromAPI));

        dinnerModel.getDishes(DinnerModel.DESERT, new DinnerModel.AsyncData() {
            @Override
            public void onData(Object dishes) {
                List<DishModelSpoon> desert_list_Spoon = convertJSONObjectToList(dishes, DinnerModel.DESERT);
                dessertFromAPI.addAll(desert_list_Spoon);
                dessertsDishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getBaseContext(),errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        dessertGridView.setOnItemClickListener(new ItemClickListener(dessertFromAPI));



        //View items

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

        b1.setOnClickListener(new View.OnClickListener()

                              {

                                  public void onClick(View v) {
                                      Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                      startActivity(i);
                                  }
                              }

        );

        search.setOnClickListener(new View.OnClickListener()

                            {

                                public void onClick(View v) {
                                    Intent i_search = new Intent(getApplicationContext(), Main3Activity.class);
                                    startActivity(i_search);
                                }
                            }

        );
    }



    @Override
    public void update(Observable observable, Object o) {
        //  int numguests = Integer.valueOf((Integer) o);
        cost.setText(String.valueOf(dinnerModel.getTotalMenuPrice()));

    }


    private static List<DishModelSpoon> convertJSONObjectToList(Object O, int type) {
       /* List<DishModelSpoon> dishesFromAPI = new ArrayList<DishModelSpoon>();
        //do something here

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<DishModelSpoon>>() {
        }.getType();
        dishesFromAPI = gson.fromJson(O.toString(), type);
        return dishesFromAPI;
*/
        ObjectMapper mapper = new ObjectMapper();
        List<DishModelSpoon> myObjects = null;
        try {
            myObjects = mapper.readValue(O.toString(), new TypeReference<List<DishModelSpoon>>(){});
            for(DishModelSpoon obj:myObjects){
                obj.setType(type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  myObjects;
    }


}
