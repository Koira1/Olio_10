package com.example.olio_10;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    WebView webView;
    Button search;
    Button refresh;
    Button initialize;
    Button shoutout;
    Button back;
    Button next;
    EditText url;
    String previous;
    String forward;
    ArrayList<String> history = new ArrayList<>();
    ListIterator itr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.WebView);
        search = findViewById(R.id.search);
        url = findViewById(R.id.url);
        refresh = findViewById(R.id.refresh);
        initialize = findViewById(R.id.initialize);
        shoutout = findViewById(R.id.shoutout);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);


        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.google.fi");


        itr = history.listIterator();
        itr.add(webView.getUrl());
        initialize.setOnClickListener(this);
        shoutout.setOnClickListener(this);
        refresh.setOnClickListener(this);
        search.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search:

                if(itr.hasNext()){
                    Log.d("hasNext", "True");
                    removeForward();
                }

                Log.d("history", history.toString());

                Log.d("search", url.getText().toString());
                if (url.getText().toString().equals("index.html")){
                    webView.loadUrl("file:///android_asset/index.html");
                } else {
                    webView.loadUrl("https://" + url.getText().toString());
                }

                if(history.size() < 10){
                    Log.d("history", "Adding address");
                    itr.add(webView.getUrl());
                } else {
                    Log.d("Shiftleft", "Shifting left");
                    shiftLeft();
                    itr.add(webView.getUrl());
                }
                while(itr.hasNext()){
                    itr.next();
                }

                Log.d("history", history.toString());

                break;
            case R.id.refresh:
                String getUrl = webView.getUrl().toString();
                Log.d("getUrl", getUrl);
                webView.loadUrl(getUrl);

            case R.id.initialize:
                webView.evaluateJavascript("javascript:initialize()", null);
                break;

            case R.id.shoutout:
                webView.evaluateJavascript("javascript:shoutOut()", null);
                break;

            case R.id.back:
                if(itr.hasPrevious()){
                    previous = (String) itr.previous();
                    Log.d("previous", previous);
                    webView.loadUrl(previous);
                }

                break;

            case R.id.next:
                if(itr.hasNext()){
                    forward = (String) itr.next();
                    webView.loadUrl(forward);
                }


        }



    }
    void shiftLeft(){
        while(itr.hasPrevious()){
            itr.previous();
        }
        itr.remove();
        while(itr.hasNext()){
            itr.next();
        }
    }

    void removeForward(){
       while(itr.hasNext()){
            itr.remove();
            itr.next();
        }
        itr.remove();
        Log.d("Removed", history.toString());
    }
}
