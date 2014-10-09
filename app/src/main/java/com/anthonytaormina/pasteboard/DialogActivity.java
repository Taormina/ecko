package com.anthonytaormina.pasteboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DialogActivity extends Activity {

    public static final String KEY_LAYOUT_ID = "layout_id";

    @LayoutRes private Integer layoutId;
    @InjectView(R.id.link_field) EditText linkField;
    @InjectView(R.id.done) Button done;
    @InjectView(R.id.reset) Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer layoutId = getIntent().getExtras().getInt(KEY_LAYOUT_ID);
        setContentView(layoutId);

        ButterKnife.inject(this);
        linkField.setText(Prefs.getLink());
        if (BuildConfig.DEBUG)
            reset.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.done)
    public void done() {
        Prefs.setLink(linkField.getText().toString());
        finish();
    }

    @OnClick(R.id.reset)
    public void reset() {
        Prefs.setLink(getResources().getString(R.string.default_link));
        finish();
    }

}
