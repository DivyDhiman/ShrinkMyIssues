package Webservice_handle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Abhay dhiman
 */

// All Api Request Method class
public class ApiResponse {
    String result;

    HttpURLConnection urlConnection;
    String response;
    URL url1;


    //Get request method
    public String ResponseGet_Location_search(String str) {

        try {
            url1 = new URL(str);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.connect();
            InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
            urlConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
        }
        return result;
    }

    //Post request method
    public String ResponsePost(String url, String parm) {

        try {
            response = "";
            url1 = new URL(url);

            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setFixedLengthStreamingMode(parm.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parm);
            out.close();

            Scanner inStream1 = new Scanner(urlConnection.getInputStream());

            while (inStream1.hasNextLine())
                response += (inStream1.nextLine());

            urlConnection.disconnect();
        } catch (MalformedURLException ex) {
            urlConnection.disconnect();
            response = "Error";

        } catch (IOException ex) {
            urlConnection.disconnect();
            response = "Error";
        }
        return response;
    }

    //Post request method
    public String ResponsePost_SignUp(String url, String parm, String token) {

        try {
            response = "";
            url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            JSONObject jsonObject = new JSONObject(parm);
            urlConnection.setFixedLengthStreamingMode(jsonObject.toString().getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(jsonObject);
            out.close();
            Scanner inStream1 = new Scanner(urlConnection.getInputStream());

            while (inStream1.hasNextLine())
                response += (inStream1.nextLine());

            urlConnection.disconnect();

        } catch (MalformedURLException ex) {
            urlConnection.disconnect();
            response = "Error";

        } catch (IOException ex) {
            urlConnection.disconnect();
            response = "Error";
        } catch (JSONException e) {
            response = "Error";
        }
        return response;
    }


    //Post request method
    public String ResponsePost_SaveOffers_Card(String url, String parm, String token) {

        try {
            response = "";
            url1 = new URL(url);

            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/text; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setFixedLengthStreamingMode(parm.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parm);

            out.close();

            Scanner inStream1 = new Scanner(urlConnection.getInputStream());

            while (inStream1.hasNextLine())
                response += (inStream1.nextLine());

            urlConnection.disconnect();
        } catch (MalformedURLException ex) {
            urlConnection.disconnect();
            response = "Error";

        } catch (IOException ex) {
            urlConnection.disconnect();
            response = "Error";
        }
        return response;
    }

    //Post request method
    public String ResponsePost_Select_offer(String url, String parm, String token) {

        try {
            response = "";
            url1 = new URL(url);

            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setFixedLengthStreamingMode(parm.getBytes().length);

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parm);

            out.close();

            Scanner inStream1 = new Scanner(urlConnection.getInputStream());

            while (inStream1.hasNextLine())
                response += (inStream1.nextLine());

            urlConnection.disconnect();
        } catch (MalformedURLException ex) {
            urlConnection.disconnect();
            response = "Error";

        } catch (IOException ex) {
            urlConnection.disconnect();
            response = "Error";
        }
        return response;
    }

    //Post request method
    public String ResponseGet_Customer_Token(String url, String token) {

        try {
            url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
            urlConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
        }
        return result;
    }

    //Post request method
    public String ResponsePost_Select_offer_Put(String url, String parm, String token) {

        try {
            response = "";
            url1 = new URL(url);

            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setFixedLengthStreamingMode(parm.getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parm);
            out.close();

            Scanner inStream1 = new Scanner(urlConnection.getInputStream());

            while (inStream1.hasNextLine())
                response += (inStream1.nextLine());

            urlConnection.disconnect();
        } catch (MalformedURLException ex) {
            urlConnection.disconnect();
            response = "Error";

        } catch (IOException ex) {
            urlConnection.disconnect();
            response = "Error";
        }
        return response;
    }


    //Post request method
    public String ResponsePut_SaveOffers_Card(String url, String parm, String token) {

        try {
            response = "";
            url1 = new URL(url);

            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setFixedLengthStreamingMode(parm.toString().getBytes().length);

            urlConnection.connect();
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parm);
            out.close();

            Scanner inStream1 = new Scanner(urlConnection.getInputStream());

            while (inStream1.hasNextLine())
                response += (inStream1.nextLine());

            urlConnection.disconnect();
        } catch (MalformedURLException ex) {
            urlConnection.disconnect();
            response = "Error";

        } catch (IOException ex) {
            urlConnection.disconnect();
            response = "Error";
        }
        return response;
    }

    //Post request method
    public String ResponseDelete_Card(String url, String token) {

        try {
            url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
            urlConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
        }
        return result;
    }


    //Post request method
    public String ResponseDelete_Account(String url, String token) {

        try {
            url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
            urlConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
        }
        return result;
    }


    //Get request method
    public String ResponseDelte_Headerparam(String str, String token) {

        try {
            url1 = new URL(str);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/text; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");

            InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
            urlConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
        }
        return result;
    }

    //Get request method
    public String ResponseGet_Cardparam(String str, String token) {

        try {
            url1 = new URL(str);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("api", "1.8.0");
            urlConnection.setRequestProperty("password", "Futiq@123");
            InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            br.close();
            urlConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
        }
        return result;
    }

}