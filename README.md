Interaction Programing - Lab assignment - Android
=================================================

This project contains the startup code for Android version of the Interaction Programing course lab assignment. For more details on how to complete the assignment follow the instructions on the [course website](https://www.kth.se/social/course/DH2641).

# F.A.Q.

Here you will find answers and tips on how to do most common tasks in this project.

## Loading the picture in the view class

To help you load the image dynamically in your view class you could define this method in the [DinnerPlannerApplication](https://github.com/kth-csc-iprog/dinnerplanner-android/blob/master/src/se/kth/csc/iprog/dinnerplanner/android/DinnerPlannerApplication.java):

```Java
public static int getDrawable(Context context, String name)
{
  return context.getResources().getIdentifier(name,"drawable", context.getPackageName());
}
```

and then in your **view class**, you have a component you want to pass the image to (for instance  [ImageView](http://developer.android.com/reference/android/widget/ImageView.html)) and you do:

```Java
ImageView img = //You either create new or find from layout
int drawableResId = DinnerPlannerApplication.getDrawable(view.getContext(),"drawable_name_without_extension");
img.setImageResource(drawableResId);
```

**Note:** be sure to not have extension in the drawable name. If you are using the `getImage()` from the [DinnerModel](https://github.com/kth-csc-iprog/dinnerplanner-android/tree/master/src/se/kth/csc/iprog/dinnerplanner/model) this will give you the image name with `.jpg` extension. So either modify the model, or be sure to remove the extension before you pass it to the `getDrawable()` method.
