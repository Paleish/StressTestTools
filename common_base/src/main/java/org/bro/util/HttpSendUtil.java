package org.bro.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSendUtil {

    /**
     * HTTP GET请求
     *
     * @param strUrl
     * @return
     */
    public static String wxHttpGetRequest(String strUrl) {
        String resp = null;
        try {
            URL httpUrl = new URL(strUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            resp = stringBuffer.toString();
            if (stringBuffer != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * HTTP POST请求
     *
     * @param strUrl
     * @return
     */
    public static String wxHttpPostRequest(String strUrl, String reqBody) {
        String resp = null;
        try {
            URL httpUrl = new URL(strUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(reqBody.getBytes("UTF8"));
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            resp = stringBuffer.toString();
            if (stringBuffer != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
        return resp;
    }
}
