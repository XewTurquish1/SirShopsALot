package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by josh on 11/19/16.
 */

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ViewHolder> {
	private List<ShoppingListItem> items;
	private Context context;
	private int itemLayout;

	public ShoppingListItemAdapter(Context context, List<ShoppingListItem> items, int itemLayout) {
		this.context = context;
		this.items = items;
		this.itemLayout = itemLayout;
	}

	@Override
	public ShoppingListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
		return new ShoppingListItemAdapter.ViewHolder(v, context);
	}

	@Override
	public void onBindViewHolder(ShoppingListItemAdapter.ViewHolder holder, int position) {
		final ShoppingListItem item = items.get(position);
		// make changes to view here
		holder.name.setText(item.getName());
		holder.qty.setText(String.valueOf(item.getQty()));
		holder.department.setText(item.getDepartment());
		holder.lineNumber.setText(String.valueOf(position+1) + ".");

		holder.inCart.setOnCheckedChangeListener(null);

		holder.inCart.setChecked(item.isInShoppingCart());

		holder.inCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				item.setInShoppingCart(b);
			}
		});

		holder.itemView.setTag(item);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView name;
		public TextView qty;
		public TextView department;
		public TextView lineNumber;
		public CheckBox inCart;

		public Context mContext;

		public ViewHolder(View itemView, Context context) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.item_name);
			qty = (TextView) itemView.findViewById(R.id.item_qty);
			department = (TextView) itemView.findViewById(R.id.department);
			lineNumber = (TextView) itemView.findViewById(R.id.line_number);
			inCart = (CheckBox) itemView.findViewById(R.id.checkBox);
			ImageButton edit = (ImageButton) itemView.findViewById(R.id.edit_button);

			mContext = context;

			edit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Toast.makeText(mContext, "CLICK: " + ((ShoppingList)v.getTag()).getName(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext, ItemDetailActivity.class);

					intent.putExtra("Item", (ShoppingListItem)((View)v.getParent()).getTag());
					((Activity)mContext).startActivityForResult(intent, getAdapterPosition());
				}
			});

		}
	}

	public void add(ShoppingListItem item) {
		items.add(item);
		notifyItemInserted(items.size()-1);
	}

	public void insert(ShoppingListItem item, int position) {
		items.add(position, item);
		notifyItemInserted(position);
	}

	public void remove(ShoppingListItem item) {
		int position = items.indexOf(item);
		items.remove(position);
		notifyItemRemoved(position);
	}

}
