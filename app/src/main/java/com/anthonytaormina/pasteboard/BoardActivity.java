package com.anthonytaormina.pasteboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BoardActivity extends Activity {

    @InjectView(R.id.paste_board) EditText mPasteBoard;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.inject(this);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    public void onCopy() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("pasteboard app", mPasteBoard.getText());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
    }

    public void onRefresh() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Prefs.getLink(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mPasteBoard.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onRefresh", error.getMessage());
            }
        });

        mRequestQueue.add(stringRequest);

    }

    public void onNewLink() {
        Intent mIntent = new Intent(this, DialogActivity.class);
        mIntent.putExtra(DialogActivity.KEY_LAYOUT_ID, R.layout.activity_dialog_new_link);
        startActivity(mIntent);
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
            case R.id.action_refresh:
                onRefresh();
                return true;
            case R.id.new_text_link:
                onNewLink();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}