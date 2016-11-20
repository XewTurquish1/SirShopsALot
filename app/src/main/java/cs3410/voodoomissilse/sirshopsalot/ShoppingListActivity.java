package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
	private List<ShoppingList> lists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(lists == null)
			lists = new ArrayList<>();

		if(lists.size() == 0)
		{
			if(savedInstanceState != null) {
				ArrayList<Parcelable> temp = savedInstanceState.getParcelableArrayList("Lists");
				for(int i = 0; i < temp.size(); ++i) {
					lists.add((ShoppingList)temp.get(i));
				}
			}
			else
				lists = ShoppingList.createShoppingList();
		}

		setContentView(R.layout.activity_shopping_list);
		final RecyclerView listView = (RecyclerView) findViewById(R.id.shoppingList);
		final ShoppingListAdapter myRecyclerVewAdapter = new ShoppingListAdapter(this, lists, R.layout.shopping_list_item);
		listView.setAdapter(myRecyclerVewAdapter);
		listView.setLayoutManager(new LinearLayoutManager(this));
		listView.addItemDecoration(new SimpleDividerItemDecoration(this));

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			ShoppingList newList = new ShoppingList("ADD");
			myRecyclerVewAdapter.add(newList);
			listView.scrollToPosition(lists.size()-1);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_CANCELED)
			return;
		ShoppingList list = data.getParcelableExtra("List");

		RecyclerView listView = (RecyclerView) findViewById(R.id.shoppingList);


		if(requestCode == lists.size()) {
			lists.add(list);
		}
		else {
			lists.set(requestCode, list);
		}

		listView.getAdapter().notifyItemChanged(requestCode);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putParcelableArrayList("Lists", (ArrayList<ShoppingList>)lists);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		ArrayList<Parcelable> temp = savedInstanceState.getParcelableArrayList("Lists");
		lists.clear();
		for(int i = 0; i < temp.size(); ++i) {
			lists.add((ShoppingList)temp.get(i));
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
