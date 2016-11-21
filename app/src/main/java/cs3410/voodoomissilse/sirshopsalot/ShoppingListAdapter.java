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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 11/19/16.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<ShoppingList> items;
	private Context context;
	private int itemLayout;
	private boolean inDeleteMode;
	private List<ShoppingList> deleteItemsList;

	public ShoppingListAdapter(Context context, List<ShoppingList> items, int itemLayout) {
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
			return new ViewHolder(v, context);
		}
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item_deletion, parent, false);
		return new ViewHolder2(v, deleteItemsList);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final ShoppingList item = items.get(position);
		// make changes to view here
		if(inDeleteMode)
			((ViewHolder2)holder).text.setText(item.getName());
		else
			((ViewHolder)holder).text.setText(item.getName());
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
		public TextView text;

		public Context mContext;

		public ViewHolder(View itemView, Context context) {
			super(itemView);
			text = (TextView) itemView.findViewById(R.id.list_name);
			mContext = context;

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Toast.makeText(mContext, "CLICK: " + ((ShoppingList)v.getTag()).getName(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext, ListActivity.class);
					intent.putExtra("Shopping List", (ShoppingList)v.getTag());
					((Activity)mContext).startActivityForResult(intent, getAdapterPosition());
				}
			});

		}
	}

	public static class ViewHolder2 extends RecyclerView.ViewHolder {
		public TextView text;
		public CheckBox markedForDeletion;
		private List<ShoppingList> deletionList;

		public ViewHolder2(View itemView, List<ShoppingList> list) {
			super(itemView);
			deletionList = list;
			text = (TextView) itemView.findViewById(R.id.list_name);
			markedForDeletion = (CheckBox) itemView.findViewById(R.id.deletion);
			markedForDeletion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
					ShoppingList list = (ShoppingList)((View)compoundButton.getParent()).getTag();
					if(b && !deletionList.contains(list)) {
							deletionList.add(list);
					}
					else {
						deletionList.remove(list);
					}
				}
			});
			this.setIsRecyclable(false);
		}
	}

	public void add(ShoppingList item) {
		items.add(item);
		notifyItemInserted(items.size()-1);
	}

	public void insert(ShoppingList item, int position) {
		items.add(position, item);
		notifyItemInserted(position);
	}

	public void remove(ShoppingList item) {
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
		for (ShoppingList list : deleteItemsList) {
			items.remove(list);
		}
		notifyDataSetChanged();
	}
}
