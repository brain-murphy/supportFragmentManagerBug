package org.gitmad.beginners.sessionsix.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.gitmad.beginners.sessionsix.fragment.OnThreadClickedListener;
import org.gitmad.beginners.sessionsix.R;
import org.gitmad.beginners.sessionsix.fragment.ThreadDetailsFragment;

public class MainActivity extends AppCompatActivity implements OnThreadClickedListener {

    private static String KEY_CURRENT_THREAD_INDEX = "current thread index key";
    private int currentThreadIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CURRENT_THREAD_INDEX)) {

            currentThreadIndex = savedInstanceState.getInt(KEY_CURRENT_THREAD_INDEX);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (orientationIsLandscape()) {
            displayThread();
        }
    }

    @Override
    public void onThreadClicked(int threadIndex) {
        currentThreadIndex = threadIndex;
        displayThread();
    }

    private void displayThread() {
        if (orientationIsLandscape()) {
            useFragmentToDisplayThread(currentThreadIndex);

        } else {
            startThreadDetailsActivity(currentThreadIndex);
        }
    }

    private void startThreadDetailsActivity(int threadIndex) {
        Intent intent = new Intent(this, ThreadDetailsActivity.class);
        intent.putExtra(ThreadDetailsActivity.KEY_THREAD_INDEX, threadIndex);

        startActivity(intent);
    }

    private void useFragmentToDisplayThread(int threadIndex) {
        String threadDetailsFragmentTag = getResources().getString(R.string.threaddetailsfragment_tag);

        getSupportFragmentManager().executePendingTransactions();

        ThreadDetailsFragment detailsFragment = (ThreadDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(threadDetailsFragmentTag);

        detailsFragment.setThread(threadIndex);
    }

    private boolean orientationIsLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CURRENT_THREAD_INDEX, currentThreadIndex);

        super.onSaveInstanceState(outState);
    }
}
