package apidez.com.week7;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nongdenchet on 11/17/16.
 */

public class RxUtils {

    public static <T> Observable.Transformer<T, T> withSchedulers() {
        return originObservable -> originObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
