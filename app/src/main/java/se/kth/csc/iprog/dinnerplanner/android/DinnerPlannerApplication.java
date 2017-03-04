package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Application;
import android.content.Context;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;

public class DinnerPlannerApplication extends Application {
	
	private static DinnerModel mDinnerModel;
    private static Context context;
	public static DinnerModel getModel() {
		return mDinnerModel;
	}

	public void setModel(DinnerModel model) {
		this.mDinnerModel = model;
	}


	@Override
	public void onCreate() {
		super.onCreate();

		mDinnerModel = new DinnerModel();
		context = getApplicationContext();
	}

	public static Context getBaseApplicationContext(){
		return context;
	}

	/*public DinnerModel getObserver() {
		return mDinnerModel;
	}*/
}
