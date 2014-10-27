package view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wavon.greenwave.R;

import control.Globale;

/**
 * TwitterFragment is a fragment which displays a Twitter Feed.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class TwitterFragment extends Fragment{

	public static final String TAG = "TimeLineFragment";
    private static final String baseURl = "https://twitter.com";
    private View v;
    
    private WebView webView;
    private RelativeLayout loadingTwitter;
    private RelativeLayout textLayout;
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {	 
		  
	  v = inflater.inflate(R.layout.fragment_actualite, container, false); 

	  webView = (WebView) v.findViewById(R.id.timeline_webview);
	  loadingTwitter = (RelativeLayout) v.findViewById(R.id.loadingTwitter);
	  textLayout = (RelativeLayout) v.findViewById(R.id.textLayout);
	  
	  
      webView.getSettings().setDomStorageEnabled(true);
      webView.getSettings().setJavaScriptEnabled(true);

      webView.setWebViewClient(new WebViewClient() {
    	  
    	  public void onPageStarted(WebView view, String url, Bitmap favicon){
    		  textLayout.setVisibility(View.GONE);
    		  webView.setVisibility(View.GONE);
   		   	  loadingTwitter.setVisibility(View.VISIBLE);
    	  }
    	  
    	   public void onPageFinished(WebView view, String url) {
    	        // do your stuff here
    		   webView.setVisibility(View.VISIBLE);
    		   loadingTwitter.setVisibility(View.GONE);
    	    }
    	   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {	
    		   TextView text = (TextView) v.findViewById(R.id.text);
    		   text.setText("Erreur de connexion : "+description);
    	        textLayout.setVisibility(View.VISIBLE);
    	    }
    	   
    	   @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               try{
                   System.out.println("url called:::" + url);
                   if (url.startsWith("tel:")) {
                       Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                       startActivity(intent);
                   }  else if (url.startsWith("http:")
                           || url.startsWith("https:")) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); 
                        startActivity(intent);

                   } 
                   else {
                       return false;
                   }
               }catch(Exception e){
                   e.printStackTrace();
               }

               return true;
           }
    	   
    	});
      
	  if(Globale.engine.getReseau().getTwitterTimeline()!=null){
		  webView.loadDataWithBaseURL(baseURl, Globale.engine.getReseau().getTwitterTimeline(), "text/html", "UTF-8", null);
	  }
	  else{
		 webView.setVisibility(View.GONE);
		 loadingTwitter.setVisibility(View.GONE);
		 textLayout.setVisibility(View.VISIBLE);
	  }

  	setHasOptionsMenu(true);
	  return v;
	 }
	
	@Override
	public void onResume() {
		  super.onResume();
		  Log.d("OnResume()", "Actualite");
	   }
	   
	@Override
	public void setUserVisibleHint(boolean visible)
	{
		super.setUserVisibleHint(visible);
		if (visible && isResumed()){
			//Only manually call onResume if fragment is already visible
			//Otherwise allow natural fragment lifecycle to call onResume
			onResume();
		}
	} 
	
	public String toString(){
		return "Actualité";
	}
	
}
