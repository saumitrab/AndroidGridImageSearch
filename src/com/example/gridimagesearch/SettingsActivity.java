package com.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	SearchPreferences spObject = new SearchPreferences("","","","");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	
		// TODO: Use saved state of spObject passed back from SearchActivity
		Spinner spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
		spinnerSize.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View parent, int position,
					long rowid) {
				spObject.setImgsz(adapter.getItemAtPosition(position).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Spinner spinnerImgcolor = (Spinner) findViewById(R.id.spinnerImgcolor);
		spinnerImgcolor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View parent, int position,
					long rowid) {
				spObject.setImgcolor(adapter.getItemAtPosition(position).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		Spinner spinnerImgtype = (Spinner) findViewById(R.id.spinnerImgtype);
		spinnerImgtype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View parent, int position,
					long rowid) {
				spObject.setImgType(adapter.getItemAtPosition(position).toString());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		EditText etSite = (EditText) findViewById(R.id.etSite);
        etSite.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub		
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				EditText etSite = (EditText) findViewById(R.id.etSite);
				spObject.setas_sitesearch(etSite.getText().toString());
			}
		});   
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	


	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		i.putExtra("result", spObject);
		setResult(RESULT_OK, i);
		super.finish();
	}
	
	

}
