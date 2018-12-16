package com.example.hemant.gitpullrequest;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Used to fetch the PR(pull request) data
     */
    public static List<PullRequest> fetchPrData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (Exception e) {
            // Error making Http request
        }

        return extractDataFromJson(jsonResponse);
    }

    /**
     * Return URL
     *
     * @param stringUrl url string
     * @return URL created using url string
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            // Error while creating URL
        }
        return url;
    }

    /**
     * Establish HttpURLConnection to get the data through internet
     *
     * @param url URL to make HttpURLConnection
     * @return JSONResponse in string format
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(125000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.d(LOG_TAG, "++" + urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                // Error response code here
                Log.d(LOG_TAG, "Response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            // Error retrieving pull request JSON results
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Read the inputStream
     *
     * @param inputStream
     * @return inputStream data in String format
     * @throws IOException
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            inputStreamReader.close();
            reader.close();
        }
        return output.toString();
    }

    /**
     * Extract data from the JSONResponse
     *
     * @param pullRequestJSON
     * @return list of pullRequest
     */
    private static List<PullRequest> extractDataFromJson(String pullRequestJSON) {
        if (TextUtils.isEmpty(pullRequestJSON)) {
            return null;
        }

        List<PullRequest> pullRequests = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(pullRequestJSON);

            for (int i = 0; i <= jsonArray.length(); i++) {
                JSONObject currentPr = jsonArray.getJSONObject(i);

                /*********
                 * Title *
                 *********/
                String title = currentPr.getString("title");

                /**********
                 * Avatar *
                 **********/

                JSONObject userObject = currentPr.getJSONObject("user");
                String avatarUrl = userObject.getString("avatar_url");

                /************
                 * Diff URL *
                 ************/
                String diffUrl = currentPr.getString("diff_url");

                /****************
                 * Issue Number *
                 ****************/

                int issueNumber = currentPr.getInt("number");

                PullRequest pullRequest = new PullRequest(title, avatarUrl, diffUrl, issueNumber);

                pullRequests.add(pullRequest);
            }
        } catch (JSONException e) {
            // Error parsing the JSON
        }

        return pullRequests;
    }

    public static List<String> fetchDiffData(String requestUrl) {
        URL url = createUrl(requestUrl);

        InputStream inputStream;
        List<String> diffData = new ArrayList<>();
        try {
            inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();

            while (line != null) {
                diffData.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            // Error fetching diff data from the "diff_url"
        }
        return diffData;
    }

    public static String fetch(String requestUrl) {
        URL url = createUrl(requestUrl);

        String response = null;
        try {
            response = readFromInputStream(url.openStream());
        } catch (Exception e) {
            // Error parsing result from url
            e.printStackTrace();
        }
        return response;
    }
}
