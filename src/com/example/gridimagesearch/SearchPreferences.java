package com.example.gridimagesearch;

import java.io.Serializable;

public class SearchPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8078386006186876281L;
	
	private String imgsz;
	private String imgcolor;
	private String imgtype;	
	private String as_sitesearch;
	
	SearchPreferences(String s1, String s2, String s3, String s4) {
		imgsz=s1;
		imgcolor=s2;
		imgtype=s3;
		as_sitesearch=s4;
	}
	
	public String getImgsz() {
		return imgsz;
	}
	
	public String getImgcolor() {
		return imgcolor;
	}
	
	public String getImgtype() {
		return imgtype;
	}
	
	public String getas_sitesearch() {
		return as_sitesearch;
	}
	
	public void setImgsz(String s) {
		imgsz = s;
	}
	
	public void setImgcolor(String s) {
		imgcolor = s;
	}
	
	public void setImgType(String s) {
		imgtype = s;
	}
	
	public void setas_sitesearch(String s) {
		as_sitesearch = s;
	}
	
	
}
