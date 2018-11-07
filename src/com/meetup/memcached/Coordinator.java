package com.meetup.memcached;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Coordinator{

    private static String address="http://localhost:8080/getRange/";

    public static Map<String, Boolean> getSessions(String clientId)
    {
        Map<String, Boolean> map = new ConcurrentHashMap<String, Boolean>();
        try {
            URL url = new URL(address+clientId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            output = br.readLine();
            String sessionIdList=output.substring(output.indexOf("sessionIds")+"sessionIds".length()+3, output.length()-2);
            String[] array=sessionIdList.split(",");
            for(String str: array)
            {
                map.put(str,true);
            }
            //Object sessions = ((jsonToMap(output)).get("sessionIds"));
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
      return map;

    }

    /*public static HashMap<String, String> jsonToMap(String t){

        HashMap<String, String> map = new HashMap<String, String>();
        Object jObject=JSONFunctions.parse(t, map);
        System.out.println("json : "+jObject);
        System.out.println("map : "+map);
        return map;
    }
*/


}
