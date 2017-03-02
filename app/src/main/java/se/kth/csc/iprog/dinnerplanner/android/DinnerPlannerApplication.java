package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Application;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.DishModelSpoon;

public class DinnerPlannerApplication extends Application {
	
	 private static DinnerModel mDinnerModel;

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
	}

	/*public DinnerModel getObserver() {
		return mDinnerModel;
	}*/
}
