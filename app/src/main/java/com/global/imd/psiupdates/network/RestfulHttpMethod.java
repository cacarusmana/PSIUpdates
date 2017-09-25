package com.global.imd.psiupdates.network;

import com.global.imd.psiupdates.util.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Caca Rusmana on 17/09/2017.
 */

public class RestfulHttpMethod {

    public static String connect(String url) throws Exception {
        String result;

        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod(Constant.METHOD_GET);
        con.setConnectTimeout(Constant.TIMEOUT_CONN);
        con.addRequestProperty(Constant.API_KEY_NAME, Constant.API_KEY);

        if (con.getResponseCode() == Constant.RESPONSE_SUCCEED) {
            result = readStream(con.getInputStream());
        } else {
            result = readStream(con.getErrorStream());
        }

        return result;
    }

    private static String readStream(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
}
