package se.kth.csc.iprog.dinnerplanner.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dipeshmitthalal on 27/02/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DishModelSpoon {

    /*"id": 216686,
			"title": "Pork & noodle pan-fry with sweet & spicy sauce",
			"readyInMinutes": 10,
			"image": "Pork---noodle-pan-fry-with-sweet---spicy-sauce-216686.jpg",
			"imageUrls": [
			"Pork---noodle-pan-fry-with-sweet---spicy-sauce-216686.jpg"
			*/
    int id;
    String title;
    String[] imageUrls;
    int readyInMinutes;
    String  image;
    Bitmap dishBitmap;
    public List<Ingredient> ingredients;
    public String recipe;
    int type;
    String instructions;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bitmap getDishBitmap() {
        return dishBitmap;
    }

    public void setDishBitmap(Bitmap dishBitmap) {
        this.dishBitmap =  Bitmap.createBitmap(dishBitmap);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public List<Ingredient> getIngredients(){
        return ingredients==null?new ArrayList<Ingredient>():ingredients;
    }


}
