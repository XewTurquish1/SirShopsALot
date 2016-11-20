package cs3410.voodoomissilse.sirshopsalot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 11/14/16.
 */

public class ShoppingList
	implements Parcelable{
	private String _name;
	private ArrayList<ShoppingListItem> _items;

	public ShoppingList(String name) {
		_name = name;
		_items = new ArrayList<>();
	}

	public ShoppingList(Parcel in) {
		_name = in.readString();
		_items = in.createTypedArrayList(ShoppingListItem.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(_name);
		dest.writeTypedList(_items);
	}

	public static final Parcelable.Creator<ShoppingList> CREATOR = new Parcelable.Creator<ShoppingList>() {
		@Override
		public ShoppingList createFromParcel(Parcel in) {
			return new ShoppingList(in);
		}

		@Override
		public ShoppingList[] newArray(int size) {
			return new ShoppingList[size];
		}
	};

	public String getName() {
		return _name;
	}

	public int getNumOfItems() {
		return _items.size();
	}

	public List<ShoppingListItem> getItems() {
		return _items;
	}

	public static ArrayList<ShoppingList> createShoppingList() {
		ArrayList<ShoppingList> lists = new ArrayList<>();

		lists.add(new ShoppingList("goats"));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Banana","Produce",4));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Apple","Produce",10));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Milk","Diary",1));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Bread","Bakery",2));


		lists.add(new ShoppingList("Walmart"));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Call of duty 3","Electronics",1));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Canned Air","Electronics",6));

		lists.add(new ShoppingList("Staples"));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Paper","Supplies", 100));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Markers","Supplies",10));

		lists.add(new ShoppingList("Costco"));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Jar of Almonds","Somewhere",1));
		lists.get(lists.size()-1).getItems().add(new ShoppingListItem("Frozen Berries","Frozen",1));

		return lists;
	}

	public void setName(String name) {
		this._name = name;
	}
}
