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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 11/19/16.
 */

public class ShoppingListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<ShoppingListItem> items;
	private Context context;
	private int itemLayout;
	private boolean inDeleteMode;
	private List<ShoppingListItem> deleteItemsList;

	public ShoppingListItemAdapter(Context context, List<ShoppingListItem> items, int itemLayout) {
		this.context = context;
		this.items = items;
		this.itemLayout = itemLayout;
		inDeleteMode = false;
		deleteItemsList = new ArrayList<>();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(!inDeleteMode) {
			View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
			return new ShoppingListItemAdapter.ViewHolder(v, context);
		}
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item_item_deletion, parent, false);
		return new ViewHolder2(v, deleteItemsList);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final ShoppingListItem item = items.get(position);
		// make changes to view here
		if(!inDeleteMode) {
			((ViewHolder)holder).name.setText(item.getName());
			((ViewHolder)holder).qty.setText(String.valueOf(item.getQty()));
			((ViewHolder)holder).department.setText(item.getDepartment());
			((ViewHolder)holder).lineNumber.setText(String.valueOf(position + 1) + ".");

			((ViewHolder)holder).inCart.setOnCheckedChangeListener(null);

			((ViewHolder)holder).inCart.setChecked(item.isInShoppingCart());

			((ViewHolder)holder).inCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
					item.setInShoppingCart(b);
				}
			});
		}
		else {
			((ViewHolder2)holder).name.setText(item.getName());
			((ViewHolder2)holder).qty.setText(String.valueOf(item.getQty()));
			((ViewHolder2)holder).department.setText(item.getDepartment());
			((ViewHolder2)holder).lineNumber.setText(String.valueOf(position + 1) + ".");
		}

		holder.itemView.setTag(item);
	}

	@Override
	public int getItemViewType(int position) {
		return inDeleteMode ? 1 : 0;
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

	public static class ViewHolder2 extends RecyclerView.ViewHolder {
		public TextView name;
		public TextView qty;
		public TextView department;
		public TextView lineNumber;
		public CheckBox markedForDeletion;
		private List<ShoppingListItem> deletionList;


		public ViewHolder2(View itemView, List<ShoppingListItem> list) {
			super(itemView);
			deletionList = list;
			name = (TextView) itemView.findViewById(R.id.item_name);
			qty = (TextView) itemView.findViewById(R.id.item_qty);
			department = (TextView) itemView.findViewById(R.id.department);
			lineNumber = (TextView) itemView.findViewById(R.id.line_number);
			markedForDeletion = (CheckBox) itemView.findViewById(R.id.checkBox);
			markedForDeletion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
					ShoppingListItem item = (ShoppingListItem)((View)compoundButton.getParent()).getTag();
					if(b && !deletionList.contains(item)) {
						deletionList.add(item);
					}
					else {
						deletionList.remove(item);
					}
				}
			});
			this.setIsRecyclable(false);

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

	public void setInDeleteMode() {
		inDeleteMode = true;
		notifyDataSetChanged();
	}

	public void setOutDeleteMode() {
		inDeleteMode = false;

		notifyDataSetChanged();
	}

	public void delete() {
		for (ShoppingListItem item : deleteItemsList) {
			items.remove(item);
		}
		notifyDataSetChanged();
	}
}
