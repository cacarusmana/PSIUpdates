
package com.global.imd.psiupdates.network;

/**
 * Created by Caca Rusmana on 17/09/2017.
 */

public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T... params);
}
