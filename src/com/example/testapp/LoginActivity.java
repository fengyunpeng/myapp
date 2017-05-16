package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private SharedPreferences pref;
  	
	private SharedPreferences.Editor editor;

	private EditText accountEdit;

	private EditText passwordEdit;

	private Button login;
	
	private CheckBox rememberPass;
	//boolean isRemember= false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//¹Ø¼üµÄcontext
		pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		initView();
		initData();
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String account = accountEdit.getText().toString();
				String password = passwordEdit .getText().toString();
				if(account.equals("admin")&&password.equals("123")){
					
					editor = pref.edit();
					if(rememberPass.isChecked()){
						
						editor.putBoolean("remember_password", true);
						editor.putString("account", account);
						editor.putString("password", password);
						
					}else{
						editor.clear();
						
					}
					editor.commit();
					
					Intent intents = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intents);
					finish();
					
				}else{
					
					Toast.makeText(getApplicationContext(), "ac and pwd is invalid", Toast.LENGTH_SHORT).show();
				}
			}
		});	
	}

	
	private void initView() {
		// TODO Auto-generated method stub
		accountEdit = (EditText) findViewById(R.id.account);
		passwordEdit = (EditText) findViewById(R.id.password);
		rememberPass = (CheckBox) findViewById(R.id.checkBox1);
		login = (Button) findViewById(R.id.login);
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		boolean isRemember = pref.getBoolean("remember_password", false);
		if(isRemember){
			String account = pref.getString("account", "");
			String password = pref.getString("password", "");
			accountEdit.setText(account);
			passwordEdit.setText(password);
			rememberPass.setChecked(true);		
		}
	}


}
