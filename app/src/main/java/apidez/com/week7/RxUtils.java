package apidez.com.week7;

import android.os.Handler;
import android.view.View;

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

    public static <T> Observable.Transformer<T, T> withLoading(Handler handler, View loadingView,
                                                               View content) {
        return originObservable -> originObservable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> handler.post(() -> {
                    loadingView.setVisibility(View.VISIBLE);
                    content.setVisibility(View.INVISIBLE);
                }))
                .doOnTerminate(() -> handler.post(() -> {
                    loadingView.setVisibility(View.INVISIBLE);
                    content.setVisibility(View.VISIBLE);
                }));
    }
}
