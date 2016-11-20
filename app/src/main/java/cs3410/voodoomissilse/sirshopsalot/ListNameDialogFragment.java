package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by josh on 11/20/16.
 */

public class ListNameDialogFragment extends DialogFragment {
	public String name;

	public interface ListNameDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}

	ListNameDialogListener mlistener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		try {
			mlistener = (ListNameDialogListener) context;
		}
		catch(ClassCastException e){
			throw new ClassCastException(context.toString() + " must implement ListNameDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.dialog_list_name,null);

		builder.setView(view)
				.setPositiveButton("Set", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						mlistener.onDialogPositiveClick(ListNameDialogFragment.this);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						mlistener.onDialogNegativeClick(ListNameDialogFragment.this);
					}
				});

		EditText listName = (EditText) view.findViewById(R.id.list_name);
		listName.setText(name);

		listName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				name = charSequence.toString();
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});


		return builder.create();
	}
}
