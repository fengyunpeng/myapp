package com.example.testapp;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.message.BufferedHeader;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText mEdit;
	private TextView mTextView,mTv;
	private Button mSaveDataBtn,mSqliteBtn,addDataBtn,querydataBtn; 
	private MyDatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new MyDatabaseHelper(getApplicationContext(), "BookStore.db", null, 2);
		mTextView = (TextView) findViewById(R.id.text);
		mTv = (TextView) findViewById(R.id.textView1);
		mSaveDataBtn = (Button) findViewById(R.id.button1);
		mSqliteBtn = (Button) findViewById(R.id.button2);
		addDataBtn = (Button) findViewById(R.id.button3);
		querydataBtn = (Button) findViewById(R.id.button6);
		mEdit = (EditText) findViewById(R.id.edit);
	    String text = load();
 		if(text != null){
 			mTextView.setText(text);
 			Toast.makeText(getApplicationContext(), "load success", Toast.LENGTH_SHORT).show();
 		}else{	
 			mTextView.setText(" It is a dog");
 		}
 		
 		SharedPreferences mSpf = getSharedPreferences("data", MODE_PRIVATE);
 		
 		String name = mSpf.getString("name", "");
		int age = mSpf.getInt("age", 0);
		boolean married = mSpf.getBoolean("married", false);
		mTv.setText("name:"+name +"  age:"+ age +"  married "+ married);
		
 		mSaveDataBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor mSpfEditor = getSharedPreferences("data", MODE_PRIVATE).edit();
				mSpfEditor.putString("name", "Tom");
				mSpfEditor.putInt("age", 25);
				mSpfEditor.putBoolean("married", false);
				mSpfEditor.commit();
				
			}
		});
 		
 		mSqliteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbHelper.getWritableDatabase();
			}
		});
 		
 		addDataBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				
				values.put("name", "The Da Vinci Code");
				values.put("author", "bilongfei");
				values.put("pages", 454);
				values.put("price", 15.9);
				db.insert("Book", null, values);
				
				values.clear();
				values.put("name", "The Lost Symbol");
				values.put("author", "bilongding");
				values.put("pages", 510);
				values.put("price", 19);
				db.insert("Book", null, values);
				
				Toast.makeText(getApplicationContext(), "add success", Toast.LENGTH_SHORT).show();
			}
		});
 		
 		querydataBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				Cursor cursor = db.query("Book", null, null, null, null, null, null);
				//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
				if(cursor.moveToFirst()){
					
					do {
						
						String name = cursor.getString(cursor.getColumnIndex("name"));
						String author = cursor.getString(cursor.getColumnIndex("author"));
						int pages = cursor.getInt(cursor.getColumnIndex("pages"));	
						double prices = cursor.getDouble(cursor.getColumnIndex("price"));	
						
						Log.i("msg", "BOOK name = "+name+ " BOOK author ="+author+" BOOK pages = "+pages+" BOOK prices = "+prices+" kk");
						
					} while(cursor.moveToNext());
				}
				cursor.close();
			}
		});
	}

	


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		String mInputText = mEdit.getText().toString();
		save(mInputText);
	}


	private void save(String mInputText) {
		// TODO Auto-generated method stub
		FileOutputStream out = null;
		BufferedWriter writer = null;
		
		try {
			out = openFileOutput("mydata", Context.MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(mInputText);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(writer != null){
					writer.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	private String load() {
		// TODO Auto-generated method stub
		FileInputStream in = null;
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder();

		try {
			in = openFileInput("mydata");
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";

			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return content.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
