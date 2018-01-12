package com.igdb.dmacedo.testera;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

public class Behaviour {
    private static ReplaySubject<Integer> subject;

    public static ReplaySubject<Integer> getSubject() {
        if (subject == null){

            subject = ReplaySubject.create();

            Observable.range(1,5)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .concatMap(new Function<Integer, ObservableSource<Integer>>() {
                        @Override
                        public ObservableSource<Integer> apply(Integer integer) throws Exception {
                            if (integer == 4){
                               return Observable.error(new RuntimeException("eae"));
                            }
                            return Observable.just(integer).delay(2, TimeUnit.SECONDS);
                        }
                    })
                    .subscribe(subject);
        }
        return subject;
    }
}
