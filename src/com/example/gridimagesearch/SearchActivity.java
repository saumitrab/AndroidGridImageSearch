package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	
	EditText etQuery;
	GridView gvResults;
	Button   btnSearch;

	ArrayList<ImageResult> alImageResult = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        setupViews();
        
        
        imageAdapter = new ImageResultArrayAdapter(this, alImageResult);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				
				Intent myIntent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				// Give ImageDisplayActivity an image to display
				
				ImageResult imageResult = alImageResult.get(position);
				//myIntent.putExtra("fullUrl", imageResult.getFullUrl());
				// make ImageResult serializable and pass whole imageResult as argument 
				myIntent.putExtra("imageResult", imageResult);
				
				startActivity(myIntent);
			}
			
			
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    public void setupViews() {
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	btnSearch = (Button) findViewById(R.id.btnSearch);
    }
    
    public void onImageSearch(View v) {
    	String query = etQuery.getText().toString();
    	Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
    	
    	AsyncHttpClient ahClient = new AsyncHttpClient();
    	//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=dog
    	
    	ahClient.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" + 0 + "&v=1.0&q=" + Uri.encode(query), new JsonHttpResponseHandler() {
    	//ahClient.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + Uri.encode(query), new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONObject response) {
    			JSONArray jsonArrayImageResults = null;
    			
    			try {
    				//Get image results from JSON
    				jsonArrayImageResults = response.getJSONObject("responseData").getJSONArray("results");
    				alImageResult.clear();
    				
    				// change alImageResult.addAll to imageAdapter.addAll
    				imageAdapter.addAll(ImageResult.fromJSONArray(jsonArrayImageResults));
    				
    				Log.d("DEBUG", alImageResult.toString());
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});
    }
    
}
