package se.kth.csc.iprog.dinnerplanner.model;

/**
 * Created by dipeshmitthalal on 27/02/17.
 */
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
}
