package com.global.imd.psiupdates.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.util.Constant;

/**
 * Created by Caca Rusmana on 18/09/2017.
 */

public class CallWebService extends AsyncTask<Object, String, String> {

    private Context context;
    private ProgressDialog dialog;
    private AsyncTaskCompleteListener<Object> callback;

    public CallWebService(Context context, AsyncTaskCompleteListener<Object> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, null, context.getString(R.string.label_retrieving_data), true, false);
        dialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {
        String param = (String) params[0];
        String result = null;
        try {

            String url = Constant.API_URL + param;
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
