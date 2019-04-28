package com.dldriver.driver.utils.api;


import android.util.Log;

import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.utils.ApiConstants;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ApiCalls {


    public void callApiGet(final BaseActivity mActivity, final SimpleArcDialog mDialog,
                           String url, final int requestId) {
        AsyncHttpClient client = new AsyncHttpClient();


        client.get(mActivity, ApiConstants.BaseUrl + url, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Failure-", "JSON:" + errorResponse);

                if (mDialog != null && mDialog.isShowing() & !mActivity.isFinishing())
                    mDialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Success-", "JSON:" + response.toString());

                if (mActivity != null) {
                    mActivity.getResponse(response.toString(), requestId);
                }
            }
        });
    }

    public void callApiPost(final BaseActivity mActivity, RequestParams params
            , final SimpleArcDialog mDialog, String url, final int requestId) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        mDialog.show();
        System.out.println("url"+ApiConstants.BaseUrl + url);

        client.post(mActivity, ApiConstants.BaseUrl + url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Failure-", "JSON:" + errorResponse);
              try{
                  mDialog.cancel();
                 // mActivity.getResponse(errorResponse.toString(), requestId);
              }catch (Exception e){

              }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Success-", "JSON:" + response.toString());
                if (mDialog != null && mDialog.isShowing() & !mActivity.isFinishing())
                    mDialog.cancel();
                mActivity.getResponse(response.toString(), requestId);
            }

        });
    }

  /*  public void callApiPut(final BaseActivity mActivity
            , final SimpleArcDialog mDialog, String url, JSONObject json, final int requestId) {
        AsyncHttpClient client = new AsyncHttpClient();
        if (mDialog != null)
            mDialog.show();

        client.setTimeout(20000);
        client.put(mActivity, ApiConstants.BaseUrl + url, mActivity.utilsPref.createJsonWithoutDataTag(json), "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Failureeeeeeeeeeeeeee-", "JSON:" + errorResponse);
                if (mDialog != null && mDialog.isShowing() & !mActivity.isFinishing())
                    mDialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Successsssssssssssssss-", "JSON:" + response.toString());
                if (mDialog != null && mDialog.isShowing() & !mActivity.isFinishing())
                    mDialog.cancel();
                mActivity.getResponse(response.toString(), requestId);
            }
        });
    }
*/
}
