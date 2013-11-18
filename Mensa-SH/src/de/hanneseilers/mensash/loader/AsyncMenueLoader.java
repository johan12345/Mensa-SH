package de.hanneseilers.mensash.loader;

import java.util.List;

import de.hanneseilers.mensash.CacheManager;
import de.hanneseilers.mensash.activities.ActivityMain;
import de.hanneseilers.mensash.enums.LoadingProgress;
import de.mensa.sh.core.Meal;
import de.mensa.sh.core.Mensa;
import android.os.AsyncTask;

public class AsyncMenueLoader extends AsyncTask<String, Integer, List<Meal>> {

	private ActivityMain ctx;
	private Mensa selectedMensa;
	
	public AsyncMenueLoader(ActivityMain ctx){
		super();
		this.ctx = ctx;
	}
	
	@Override
	protected void onPreExecute() {
		ctx.setLoadingProgress(LoadingProgress.INIT);
	}
	
	/**
	 * Gets cities
	 */
	@Override
	protected List<Meal> doInBackground(String... params) {
		
		// find mensa with params name
		for( Mensa m : ctx.getLocations() ){
			if( m.getName().equals(params[0]) ){				
				selectedMensa = m;
				return m.getMeals();				
			}
		}
		
		return null;
	}
	
	/**
	 * Adds cities to list
	 */
	@Override
	protected void onPostExecute(List<Meal> result) {
		
		// check if result is empty
		if( result == null ){
			ctx.setErrorMealList();
		}
		
		// load menue
		ctx.selectDrawerItem(ctx.locations.indexOf(selectedMensa));
		ctx.setMealList(result);
		ctx.setLoadingProgress(LoadingProgress.MENUE_LOADED);

	}
	
}
