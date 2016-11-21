package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by josh on 11/19/16.
 */

public class ListActivity extends AppCompatActivity
	implements ListNameDialogFragment.ListNameDialogListener {
	private ShoppingList list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Activity act = this;

		setContentView(R.layout.activity_list);
		Intent intent = getIntent();
		list = intent.getParcelableExtra("Shopping List");

		List<ShoppingListItem> items = list.getItems();
		Collections.sort(items);

		TextView ListTitle = (TextView) findViewById(R.id.shoppingListTitle);
		ListTitle.setText(list.getName());

		ListTitle.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				ListNameDialogFragment dialog = new ListNameDialogFragment();
				dialog.name = list.getName();
				dialog.show(getFragmentManager(),"NoticeDialogFragment");
				return false;
			}
		});

		final RecyclerView listView = (RecyclerView) findViewById(R.id.shoppingList);
		final ShoppingListItemAdapter myRecyclerVewAdapter = new ShoppingListItemAdapter(this, list.getItems(), R.layout.shopping_list_item_item);
		listView.setAdapter(myRecyclerVewAdapter);
		listView.setLayoutManager(new LinearLayoutManager(this));
		listView.addItemDecoration(new SimpleDividerItemDecoration(this));


		final Context context = this;
		final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		final FloatingActionButton fabdelete = (FloatingActionButton) findViewById(R.id.fabdelete);
		final FloatingActionButton fabundo = (FloatingActionButton) findViewById(R.id.fabundo);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, ItemDetailActivity.class);

				ShoppingListItem item = new ShoppingListItem("","",1);
				intent.putExtra("Item", item);

				((Activity)context).startActivityForResult(intent, list.getItems().size());
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
						.setMessage("Are you sure you want to delete the shopping list items?")
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
		getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if(id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_CANCELED)
			return;
		RecyclerView listView = (RecyclerView) findViewById(R.id.shoppingList);

		List<ShoppingListItem> items = list.getItems();

		if(resultCode == Activity.RESULT_FIRST_USER) {
			items.remove(requestCode);

			listView.getAdapter().notifyDataSetChanged();
			return;
		}
		//Toast.makeText(this, "Results " + String.valueOf(requestCode), Toast.LENGTH_SHORT).show();

		ShoppingListItem item = data.getParcelableExtra("Item");



		boolean departmentChanged = false;


		if(requestCode == items.size()) {
			items.add(item);

			departmentChanged = true;
		}
		else {
			ShoppingListItem oldItem = items.get(requestCode);
			items.set(requestCode, item);
			if(oldItem.getDepartment() != item.getDepartment()) {
				departmentChanged = true;
			}
		}

		if(!departmentChanged)
			listView.getAdapter().notifyItemChanged(requestCode);
		else {
			Collections.sort(items);
			listView.getAdapter().notifyDataSetChanged();
		}

		Intent intent = new Intent();
		intent.putExtra("List", list);

		setResult(Activity.RESULT_OK, intent);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		list.setName(((ListNameDialogFragment)dialog).name);
		TextView ListTitle = (TextView) findViewById(R.id.shoppingListTitle);
		ListTitle.setText(list.getName());

		Intent intent = new Intent();
		intent.putExtra("List", list);

		setResult(Activity.RESULT_OK, intent);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {

	}
}
