package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity
		implements ListNameDialogFragment.ListNameDialogListener {
	private List<ShoppingList> lists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Activity act = this;
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

		final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		final FloatingActionButton fabdelete = (FloatingActionButton) findViewById(R.id.fabdelete);
		final FloatingActionButton fabundo = (FloatingActionButton) findViewById(R.id.fabundo);


		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Use ListNameDialogFragment here.
				ListNameDialogFragment dialog = new ListNameDialogFragment();
				dialog.show(getFragmentManager(),"NoticeDialogFragment");
			}
		});

		fabundo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				myRecyclerVewAdapter.setOutDeleteMode();
				fabdelete.hide();
				fabundo.hide();
				fab.show();
			}
		});

		fabdelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(act);
				builder
						.setMessage("Are you sure you want to delete the shopping lists?")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								myRecyclerVewAdapter.delete();
								myRecyclerVewAdapter.setOutDeleteMode();
								fabdelete.hide();
								fabundo.hide();
								fab.show();
							}
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.cancel();
							}
						})
						.show();
			}
		});

		ImageButton delete = (ImageButton) findViewById(R.id.trashButton);
		delete.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				myRecyclerVewAdapter.setInDeleteMode();
				fab.hide();
				fabdelete.show();
				fabundo.show();
				return true;
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

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		ShoppingList newList = new ShoppingList(((ListNameDialogFragment)dialog).name);
		RecyclerView listView = (RecyclerView) findViewById(R.id.shoppingList);
		ShoppingListAdapter myRecyclerVewAdapter = new ShoppingListAdapter(this, lists, R.layout.shopping_list_item);
		myRecyclerVewAdapter.add(newList);
		listView.scrollToPosition(lists.size()-1);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {

	}
}
