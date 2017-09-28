package com.global.imd.psiupdates.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.global.imd.psiupdates.R;

/**
 * Created by Caca Rusmana on 18/09/2017.
 */

public class CallWebService extends AsyncTask<Object, String, String> {

    private Context context;
    private ProgressDialog dialog;
    private AsyncTaskCompleteListener<Object> callback;
    private boolean showDialog;

    public CallWebService(Context context, AsyncTaskCompleteListener<Object> callback, boolean showDialog) {
        this.context = context;
        this.callback = callback;
        this.showDialog = showDialog;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, null, context.getString(R.string.label_retrieving_data), true, false);
        if (showDialog)
            dialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {
        String url = (String) params[0];
        String result = null;
        try {

            result = RestfulHttpMethod.connect(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        callback.onTaskComplete(result);
        if (dialog.isShowing()) dialog.dismiss();
    }
}
