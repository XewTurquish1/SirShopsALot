package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by josh on 11/19/16.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
	private List<ShoppingList> items;
	private Context context;
	private int itemLayout;

	public ShoppingListAdapter(Context context, List<ShoppingList> items, int itemLayout) {
		this.context = context;
		this.items = items;
		this.itemLayout = itemLayout;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
		return new ViewHolder(v, context);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final ShoppingList item = items.get(position);
		// make changes to view here
		holder.text.setText(item.getName());
		holder.itemView.setTag(item);
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
}
