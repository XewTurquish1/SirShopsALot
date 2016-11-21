package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by josh on 11/19/16.
 */

public class ItemDetailActivity extends AppCompatActivity {
	private ShoppingListItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Activity act = this;

		setContentView(R.layout.activity_item_detail);

		Intent intent = getIntent();
		item = intent.getParcelableExtra("Item");

		TextView ItemTitle = (TextView) findViewById(R.id.shoppingListTitle);
		ItemTitle.setText(item.getName());

		EditText name = (EditText) findViewById(R.id.item_name);
		name.setText(item.getName());
		name.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				item.setName(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		EditText department = (EditText) findViewById(R.id.item_department);
		department.setText(item.getDepartment());
		department.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				item.setDepartment(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		EditText qty = (EditText) findViewById(R.id.item_qty);
		qty.setText(String.valueOf(item.getQty()));
		qty.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if(charSequence.length() == 0)
					 return;
				item.setQty(Integer.parseInt(charSequence.toString()));
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		EditText notes = (EditText) findViewById(R.id.item_notes);
		notes.setText(item.getNotes());
		notes.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				item.setNotes(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		FloatingActionButton cancel = (FloatingActionButton) findViewById(R.id.cancel_button);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});

		FloatingActionButton accept = (FloatingActionButton) findViewById(R.id.accept_button);
		accept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent results = new Intent();
				results.putExtra("Item", item);
				setResult(Activity.RESULT_OK, results);
				finish();
			}
		});

		ImageButton delete = (ImageButton) findViewById(R.id.trashButton);
		delete.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(act);
				builder
						.setMessage("Are you sure you want to delete the item?")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								setResult(Activity.RESULT_FIRST_USER);
								finish();
							}
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.cancel();
							}
						})
						.show();
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
}
