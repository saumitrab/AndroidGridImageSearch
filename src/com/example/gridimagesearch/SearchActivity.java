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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
	Button   btnLoadMore;

	ArrayList<ImageResult> alImageResult = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	int imageResultStartIndex = 0;
	int countImages=12;
	SearchPreferences spObject = new SearchPreferences("","","","");
	
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
				// myIntent.putExtra("fullUrl", imageResult.getFullUrl());
				// make ImageResult serializable and pass whole imageResult as argument 
				myIntent.putExtra("imageResult", imageResult);
				
				startActivity(myIntent);
			}
		});
        
        btnLoadMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageResultStartIndex += countImages;
				loadImages();
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
    	btnLoadMore = (Button) findViewById(R.id.btnLoadMore);
    	btnLoadMore.setVisibility(Button.INVISIBLE);
    }
    
    public void onImageSearch(View v) {
    	String query = etQuery.getText().toString();
    	Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
    	
    	imageResultStartIndex = 0;
    	
    	loadImages(query);
    	btnLoadMore.setVisibility(Button.VISIBLE);
    }
    
    public void loadImages() {
    	String query = etQuery.getText().toString();
    	loadImages(query);
    }
    
    public void loadImages(String query) {
    	AsyncHttpClient ahClient = new AsyncHttpClient();
    	
    	int fetchImageCount = 8;
    	int fetchedImages = 0;
    	alImageResult.clear();
    	
    	while ( fetchedImages < countImages ) {
    	
    		String searchQuery = "https://ajax.googleapis.com/ajax/services/search/images?rsz=" + fetchImageCount + "&start=" + imageResultStartIndex + "&v=1.0&q=" + Uri.encode(query);
    		if (!spObject.getImgsz().isEmpty() && !spObject.getImgsz().equalsIgnoreCase("all")){
    			searchQuery += "&imgsz=" + spObject.getImgsz();
    		}
    		if (!spObject.getImgcolor().isEmpty() && !spObject.getImgcolor().equalsIgnoreCase("all")){
    			searchQuery += "&imgcolor=" + spObject.getImgcolor();
    		}
    		if (!spObject.getImgtype().isEmpty() && !spObject.getImgtype().equalsIgnoreCase("all") ){
    			searchQuery += "&imgtype=" + spObject.getImgtype();
    		}
    		if (!spObject.getas_sitesearch().isEmpty()){
    			searchQuery += "&as_sitesearch=" + spObject.getas_sitesearch();
    		}
    		
    		Log.d("DEBUGQUERY", searchQuery);
    		
    		ahClient.get(searchQuery, new JsonHttpResponseHandler() {    	
    			@Override
    			public void onSuccess(JSONObject response) {
    				JSONArray jsonArrayImageResults = null;
    				try {
    					//Get image results from JSON
    					jsonArrayImageResults = response.getJSONObject("responseData").getJSONArray("results");
    					// change alImageResult.addAll to imageAdapter.addAll
    					imageAdapter.addAll(ImageResult.fromJSONArray(jsonArrayImageResults));
    					Log.d("DEBUG", alImageResult.toString());
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		});
    		fetchedImages += fetchImageCount;
    		fetchImageCount = (countImages - 8 ) > 8 ? 8 : countImages-8;  
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent mySettingIntent = new Intent(getApplicationContext(), SettingsActivity.class);
    	mySettingIntent.putExtra("spObject", spObject);
    	startActivityForResult(mySettingIntent, 1);
    	return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode == RESULT_OK && requestCode == 1) {
        if (data.hasExtra("result")) {
        	spObject = (SearchPreferences) data.getExtras().getSerializable("result");
        	//Toast.makeText(this, spObject.getas_sitesearch(), Toast.LENGTH_SHORT).show();
        	loadImages();
        }
      }
    } 
}
