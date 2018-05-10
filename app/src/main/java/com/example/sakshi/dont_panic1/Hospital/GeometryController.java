/*
 * %W% %E% Zain-Ul-Abedin
 *
 * Copyright (c) 2017-2018. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of ZainMustafaaa.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * for learning purposes.
 *
 */

package com.example.sakshi.dont_panic1.Hospital;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sakshi on 26-Feb-18.
 */

public class GeometryController {

    public static boolean loading;
   public static StringBuffer response2;
   public static ArrayList<Integer> answers=new ArrayList<>();
    public static ArrayList<Double> finalrating=new ArrayList<>();
   public static String answer;
    public static ArrayList<NearbyHospitalsDetail> detailArrayList = new ArrayList();
    public static java.lang.StringBuffer stringBuffer = new StringBuffer();
    public static CloseableHttpResponse response;
    public static double avg;
    public static NearbyHospitalsDetail hospitalsDetail;
    public static void manipulateData(StringBuffer buffer) {

        loading = true;
        try {

            detailArrayList.clear();

            JSONObject jsonpObject = new JSONObject(buffer.toString());

            JSONArray array = jsonpObject.getJSONArray("results");

            Log.d("array", "df" + array.length());

            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                     hospitalsDetail = new NearbyHospitalsDetail();

                    if (jsonObject.getString("name") != null)
                        hospitalsDetail.setHospitalName(jsonObject.getString("name"));
                    else hospitalsDetail.setHospitalName("Not Available");

                    try {
                        hospitalsDetail.setRating(String.valueOf(jsonObject.getDouble("rating")));
                    } catch (Exception e) {
                        hospitalsDetail.setRating("Not Available");
                    }
                    try {


                        String res = jsonObject.getString("place_id");
                        MyTaskParams params = new MyTaskParams(res,i);
                       new ReviewsTask().execute(params);
                        //hospitalsDetail.setRating();


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("daf", "error");
                    }

                    try {
                        if (jsonObject.getJSONObject("opening_hours").getBoolean("open_now"))
                            hospitalsDetail.setOpeningHours("Opened");
                        else hospitalsDetail.setOpeningHours("closed");
                    } catch (Exception e) {
                        hospitalsDetail.setOpeningHours("Not Available");
                    }

                    hospitalsDetail.setAddress(jsonObject.getString("vicinity"));
                    hospitalsDetail.setGeometry(new double[]{jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng")});

                    detailArrayList.add(hospitalsDetail);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            NearestHospital.hospitalsLoaded=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        loading = false;
        Log.d("Array Loaded with size ", "Size of " + detailArrayList.size());
    }
    private static class MyTaskParams {
        String placeid;
        int i;

        MyTaskParams(String rating, int i ) {
            this.placeid = rating;
            this.i = i;

        }
    }


    static class ReviewsTask extends AsyncTask<MyTaskParams, String, StringBuffer> {


        @Override
        protected StringBuffer doInBackground(MyTaskParams... params) {
            try {


                String res = params[0].placeid;
                int i=params[0].i;
                URL url = new java.net.URL("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + res + "&key=AIzaSyBJ8O_MgT3BHO164RrKWRyQPAR6M2avEbg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = url.openStream();

                int ch = -1;
                StringBuffer buffer1 = new StringBuffer();
                while ((ch = in.read()) != -1) {
                    buffer1.append((char) ch);
                }

                JSONObject jObj = new JSONObject(buffer1.toString());
                JSONObject jResult = jObj.getJSONObject("result");
                JSONArray jReviewArray = jResult.getJSONArray("reviews");

                String csv=" ";

                for (int j = 0; j < jReviewArray.length(); j++) {

                    JSONObject jReview = jReviewArray.getJSONObject(j);
                    String review= jReview.getString("text") ;
                              String rating=jReview.getString("rating");
            //        Log.v("anuj", detailArrayList.get(i).getHospitalName()+"xxx"+avg);
               //     Log.v("anuj",review+"xxx");
                    JSONObject reviewtobetested = new JSONObject();
                    reviewtobetested.put("sentence2",review);
                    String jsonData=reviewtobetested.toString();
                    CloseableHttpClient client = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost("http://192.168.43.138:9000/api");
                    StringEntity entity = new StringEntity(jsonData);
                    httpPost.setEntity(entity);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    response = client.execute(httpPost);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String inputLine;
                    response2= new StringBuffer();
                    while ((inputLine = reader.readLine()) != null) {
                        response2.append(inputLine);
                    }
                    answer=response2.substring(20,21);
                    answers.add(j,Integer.parseInt(answer));
                    reader.close();
                }
                Integer sum = 0;
                if(!answers.isEmpty()) {
                    for (Integer mark : answers) {
                        sum += mark;
                    }
                   avg=sum.doubleValue() / answers.size();
                }
                finalrating.add(avg);
                detailArrayList.get(i).setRating(String.valueOf(avg));
               stringBuffer=buffer1;
                return buffer1;
            } catch (Exception e) {
                return null;
            }

        }

    }
}
