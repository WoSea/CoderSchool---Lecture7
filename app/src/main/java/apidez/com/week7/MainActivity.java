package apidez.com.week7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private EditText edtSearch;
    private ListView listView;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
    }
}
