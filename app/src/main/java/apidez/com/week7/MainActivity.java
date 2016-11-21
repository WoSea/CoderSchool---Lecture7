package apidez.com.week7;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    private EditText edtSearch;
    private ListView listView;
    private ProgressBar pbLoading;
    private Handler handler;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        handler = new Handler();
        apiClient = new ApiClient();

        // Start observe
        RxTextView.textChangeEvents(edtSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .map(event -> event.text().toString())
                .flatMap(keyword -> search(keyword))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    listView.setAdapter(new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, result));
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    private Observable<List<String>> search(String keyword) {
        return apiClient.search(keyword)
                .compose(RxUtils.withSchedulers())
                .compose(RxUtils.withLoading(handler, pbLoading, listView));
    }
}
