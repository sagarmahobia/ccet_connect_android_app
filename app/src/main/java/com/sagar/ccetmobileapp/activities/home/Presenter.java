package com.sagar.ccetmobileapp.activities.home;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 16:16
 */

@HomeActivityScope
public class Presenter implements Contract.Presenter {

    private Contract.View view;

    private CompositeDisposable compositeDisposable;

    @Inject
    public Presenter(Contract.View view) {
        this.view = view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        compositeDisposable = new CompositeDisposable();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        compositeDisposable = new CompositeDisposable();
    }

}
