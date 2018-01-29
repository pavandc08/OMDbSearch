package com.pavandc.omdbsearchmovies.util;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import java.util.Collection;

/**
 * Created by pavandc on 2018-01-27.
 */

public class ViewVisibilityIdlingResource implements IdlingResource {
    private int mResourceId;

    private boolean mIdle;
    private ResourceCallback mResourceCallback;
    private boolean mWaitToBeNotVisible;

    public ViewVisibilityIdlingResource(final int resourceId) {
        this.mResourceId = resourceId;
        this.mIdle = false;
        this.mResourceCallback = null;
    }

    public ViewVisibilityIdlingResource(final int resourceId, final boolean waitToBeNotVisible) {
        this.mResourceId = resourceId;
        this.mWaitToBeNotVisible = waitToBeNotVisible;
        this.mIdle = false;
        this.mResourceCallback = null;
    }

    private Activity getCurrentActivity() {
        Activity currentActivity = null;
        Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        for (Activity activity : resumedActivity) {
            currentActivity = activity;
        }
        return currentActivity;
    }

    @Override
    public String getName() {
        return ViewVisibilityIdlingResource.class.getSimpleName() + mResourceId;
    }

    @Override
    public boolean isIdleNow() {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            return false;
        }

        View view = getCurrentActivity().findViewById(mResourceId);

        if (mWaitToBeNotVisible) {
            mIdle = mIdle || view == null || view.getVisibility() != View.VISIBLE || !view.isShown();
        } else {
            mIdle = mIdle || (view != null && view.getVisibility() == View.VISIBLE && view.isShown());
        }

        if (mIdle && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }

        return mIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }
}
