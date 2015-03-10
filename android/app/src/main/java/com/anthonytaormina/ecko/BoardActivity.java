package com.anthonytaormina.pasteboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BoardActivity extends Activity {

    private static final String API_LOCATION = "http://pasteboard.elasticbeanstalk.com/api/";

    @InjectView(R.id.paste_board) EditText mPasteBoard;
    @InjectView(R.id.endpoint) EditText mEndpoint;
    private RequestQueue mRequestQueue;
    private String lastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.inject(this);

        Cache cache = new DiskBasedCache(getCacheDir(), 20 * 1024); // 20KB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        mEndpoint.setText("hello");
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh(Request.Method.GET);
    }

    public void onCopy() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("pasteboard app", mPasteBoard.getText());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
    }


    public void onRefresh(final int method) {
        StringRequest stringRequest = new StringRequest(
                method,
                API_LOCATION + mEndpoint.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (method == Request.Method.GET) {
                            mPasteBoard.setText(response);
                        }
                    }
                },
                null
        ){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return mPasteBoard.getText().toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "text/plain";
            }
        };

        mRequestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                onCopy();
                return true;
            case R.id.action_upload:
                onRefresh(Request.Method.POST);
                return true;
            case R.id.action_download:
                onRefresh(Request.Method.GET);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}