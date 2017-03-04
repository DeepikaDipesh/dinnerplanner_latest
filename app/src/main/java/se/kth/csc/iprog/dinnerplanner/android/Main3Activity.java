package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.kth.csc.iprog.dinnerplanner.android.view.ItemClickListener;
import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;

public class Main3Activity extends Activity {

    DinnerModel dinnerModel;
    ArrayList searchFromAPI;
    String search_Query;

    EditText search_Edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        dinnerModel = ((DinnerPlannerApplication) getApplication()).getModel();


        search_Edit = (EditText)findViewById(R.id.searchEdit);
        Button search_Query_Button = (Button) findViewById(R.id.searchQueryButton);
        GridView grid_View_Search = (GridView)findViewById(R.id.gridViewSearch);

        search_Query = search_Edit.getText().toString();

        final DishesAdapter_spoonified searchAdapter;
        searchFromAPI = new ArrayList<DishModelSpoon>();
        searchAdapter = new DishesAdapter_spoonified(this, searchFromAPI);
        grid_View_Search.setAdapter(searchAdapter);
        grid_View_Search.setOnItemClickListener(new ItemClickListener(searchFromAPI));
        search_Query_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                hideSoftKeyboard(Main3Activity.this);
                searchFromAPI.clear();
                searchAdapter.notifyDataSetChanged();
                String search_Query = search_Edit.getText().toString();
                dinnerModel.searchDishes(search_Query, new DinnerModel.AsyncData() {
                    @Override
                    public void onData(Object dishes) {

                        List<DishModelSpoon> starters_list_Spoon = convertJSONObjectToList(dishes, 4);
                        if (starters_list_Spoon.size()==0){
                            Toast.makeText(view.getContext(), "No Results Found", Toast.LENGTH_SHORT).show();}
                        else {
                            searchFromAPI.addAll(starters_list_Spoon);
                            searchAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
