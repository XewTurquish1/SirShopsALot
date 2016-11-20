package cs3410.voodoomissilse.sirshopsalot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by josh on 11/20/16.
 */

public class SplashScreenActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Intent intent = new Intent(this, ShoppingListActivity.class);

		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				startActivity(intent);
				finish();

			}
		}, 1000);

	}
}
