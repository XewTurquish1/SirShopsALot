package cs3410.voodoomissilse.sirshopsalot;

import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingListItem
	implements Parcelable, Comparable<ShoppingListItem> {
	private String name;
	private String department;
	private int qty;
	private String notes;
	private boolean inShoppingCart;

	public ShoppingListItem(String name, String department, int qty) {
		this.name = name;
		this.department = department;
		this.qty = qty;
		inShoppingCart = false;
	}

	private ShoppingListItem(Parcel in) {
		String[] data = new String[5];
		in.readStringArray(data);
		this.name = data[0];
		this.department = data[1];
		this.qty = Integer.parseInt(data[2]);
		this.notes = data[3];
		this.inShoppingCart = Boolean.parseBoolean(data[4]);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {name, department, String.valueOf(qty), notes, String.valueOf(inShoppingCart)});
	}

	public static final Parcelable.Creator<ShoppingListItem> CREATOR = new Parcelable.Creator<ShoppingListItem>() {
		@Override
		public ShoppingListItem createFromParcel(Parcel source) {
			return new ShoppingListItem(source);
		}

		@Override
		public ShoppingListItem[] newArray(int size) {
			return new ShoppingListItem[size];
		}
	};

	@Override
	public int compareTo(ShoppingListItem right) {
		return department.compareToIgnoreCase(right.getDepartment());
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public int getQty() {
		return qty;
	}

	public String getNotes() {
		return notes;
	}

	public boolean isInShoppingCart() {
		return inShoppingCart;
	}

	public void setInShoppingCart(boolean b) {
		inShoppingCart = b;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
