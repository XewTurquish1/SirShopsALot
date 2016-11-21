package cs3410.voodoomissilse.sirshopsalot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by josh on 11/21/16.
 */

public class SettingLoginActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		SetupView();
	}

	protected void SetupView() {
		final SettingLoginActivity act = this;

		setContentView(R.layout.settings_login);

		final EditText username = (EditText) findViewById(R.id.username);
		final EditText password = (EditText) findViewById(R.id.password);

		final Button login = (Button) findViewById(R.id.btn_login);
		final Button register = (Button) findViewById(R.id.btn_register);
		final Button logout = (Button) findViewById(R.id.btn_logout);

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(password.getText().toString().equals("") || username.getText().toString().equals("")) {
					Toast.makeText(act, "Missing username or password", Toast.LENGTH_SHORT).show();
					return;
				}

				//Toast.makeText(act, password.getText().toString(), Toast.LENGTH_SHORT).show();
				if(password.getText().toString().equals("password")) {
					Toast.makeText(act, "Login Successful", Toast.LENGTH_SHORT).show();
					login.setVisibility(View.GONE);
					register.setVisibility(View.GONE);
					logout.setVisibility(View.VISIBLE);

					username.setText("");
					password.setText("");
				}
				else
					Toast.makeText(act, "Login failed", Toast.LENGTH_SHORT).show();
			}
		});

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(act, "Logout Successful", Toast.LENGTH_SHORT).show();
				SetupView();
			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(password.getText().toString().equals("") || username.getText().toString().equals("")) {
					Toast.makeText(act, "Missing username or password", Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(act, "Register new user: " + username.getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
