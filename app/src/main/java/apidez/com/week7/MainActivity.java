package apidez.com.week7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    private EditText edtSearch;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
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
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> results = new ArrayList<>(Arrays.asList(keyword.split("")));
                results.remove(0);
                subscriber.onNext(results);
                subscriber.onCompleted();
            }
        }).compose(RxUtils.withSchedulers());
    }
}
