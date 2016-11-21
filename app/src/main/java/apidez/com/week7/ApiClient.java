package apidez.com.week7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nongdenchet on 11/21/16.
 */

public class ApiClient {

    public Observable<List<String>> search(String keyword) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> results = new ArrayList<>(Arrays.asList(keyword.split("")));
                results.remove(0);
                subscriber.onNext(results);
                subscriber.onCompleted();
            }
        });
    }
}
