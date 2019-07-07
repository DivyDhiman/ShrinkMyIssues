package Controllers;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.ShrinkMyIssues.App.R;

import java.util.HashMap;

import Call_Back.Api_callback;
import Functionality_Class.Animationall;
import Functionality_Class.Check_all;
import Functionality_Class.Toast_all;
import Webservice_handle.ApiResponse;

/**
 * Created by Abhay dhiman
 */

//Controller centralized call calling in whole application extended Application
public class Controller extends Application {

    private Check_all checkAll = new Check_all();
    private Animationall animationall = new Animationall();
    private ProgressDialog pDialog;
    private ApiResponse apiResponse;
    private Api_callback api_callback;
    private Toast_all toast_all = new Toast_all();

    //Check if Edit Text has empty value or not by calling class CheckAll method Empty_CHeck from controller class
    public boolean Check_all_empty(EditText editText) {
        return checkAll.EmptyCheck_edittext(editText);
    }


    //Check if Edit Text has email formate like "@ etc value or not by calling class CheckAll method Email_edittext from controller class
    public boolean Check_all_email(EditText editText) {
        return checkAll.Email_edittext(editText);
    }

    //Check if Edit Text password nad confirm password value are matches or not by calling class CheckAll method Check_all_confirmpassword from controller class
    public boolean Check_all_matches(EditText editText, String match) {
        return checkAll.Matches_edittext(editText, match);
    }

    //Check if Edit Text password nad confirm password value are matches or not by calling class CheckAll method Check_all_confirmpassword from controller class
    public boolean Matches_textview(String textView, String match) {
        return checkAll.Matches_textview(textView, match);
    }

    public boolean Password_length(EditText editText,int start, int end) {
        return checkAll.Password_length(editText,start, end);
    }

    public void Animation_forward(Context context) {
        animationall.Animallforward(context);
    }

    public void Animation_backward(Context context) {
        animationall.Animallbackward(context);
    }

    public void Animation_up(Context context) {
        animationall.Animallslide_up(context);
    }

    public void Animation_down(Context context) {
        animationall.Animallslide_down(context);
    }

    //Check Internet connections
    public boolean InternetCheck(Context context) {
        return checkAll.isNetWorkStatusAvialable(context);
    }

    public void Toast_show(Context context, String message){
        toast_all.Toast_show(context,message);
    }

    //Start Progress Dialog
    public void Pd_start(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getString(R.string.Pleasewait));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    //Stop Progress Dialog
    public void Pd_stop() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    //Calling Api Execute class
    public void Api_start(Context context,String url, Api_callback api_callback, HashMap<String, String> all_detail, String type) {
        apiResponse = new ApiResponse();
        this.api_callback = api_callback;

        //Calling Api hitting class
        new All_api(context,url, all_detail, type).execute();
    }

    //Call Api hitting class
    class All_api extends AsyncTask<String, String, String> {

        private HashMap<String, String> all_detail;
        private String response,url,type;
        private Context context;

        //All_api constructor
        public All_api(Context context,String url, HashMap<String, String> all_detail,String type) {
            this.context = context;
            this.url = url;
            this.all_detail = all_detail;
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Hit all api in Background Task
        @Override
        protected String doInBackground(String... args) {

            if (type.equals(context.getString(R.string.type_location_search))) {
                response = apiResponse.ResponseGet_Location_search(url);
            }
//            else if (type.equals(context.getString(R.string.deleteoffertype))) {
//                response = apiResponse.ResponseDelte_Headerparam(Config.LINKED_OFFERS + "/" + all_detail.get("offerLinkid"), all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.addoffertype))) {
//                response = apiResponse.ResponsePost_SaveOffers_Card(Config.LINKED_OFFERS, all_detail.get("param"), all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.availbletype))) {
//                response = apiResponse.ResponseGet_Headerparam(Config.AVAILABLE_OFFERS + "?affiliateCode=" + Config.affiliateCode + "&campaignType=" + Config.CAMPAIGN_TYPE, all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.redeemedtype))) {
//                response = apiResponse.ResponseGet_Headerparam(Config.REDEEMED_OFFERS + "?affiliateCode=" + Config.affiliateCode + "&campaignType=" + Config.CAMPAIGN_TYPE, all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.cardviewtype))) {
//                response = apiResponse.ResponseGet_Cardparam(Config.CARDS_LIST, all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.cardudtype))) {
//                response = apiResponse.ResponsePut_SaveOffers_Card(all_detail.get("url"), all_detail.get("body"), all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.carddeltype))) {
//                response = apiResponse.ResponseDelete_Card(all_detail.get("url"), all_detail.get("token"));
//            } else if (type.equals(context.getString(R.string.delteacctype))) {
//                response = apiResponse.ResponseDelete_Account(Config.DELETE_ACCOUNT, all_detail.get("token"));
//            }else if (type.equals(context.getString(R.string.logintype))) {
//                response = apiResponse.ResponsePost(Config.OAUTH, all_detail.get("param"));
//            }else if (type.equals(context.getString(R.string.signuptype))) {
//                response = apiResponse.ResponsePost_SignUp(Config.Create_Consumer, all_detail.get("object"), all_detail.get("token"));
//            }else if (type.equals(context.getString(R.string.authtype))) {
//                response = apiResponse.ResponsePost(Config.OAUTH, all_detail.get("param"));
//            }else if (type.equals(context.getString(R.string.fbtype))) {
//                response = apiResponse.ResponsePost_SignUp(Config.SOCIAL_LOGIN, all_detail.get("fb_detailall"), all_detail.get("token"));
//            }else if (type.equals(context.getString(R.string.receiptper))) {
//                response = apiResponse.ResponsePost_Select_offer(Config.RECEIPT_URL, all_detail.get("object"), all_detail.get("token"));
//            }else if (type.equals(context.getString(R.string.receiptsumit))) {
//                response = apiResponse.ResponsePost_Select_offer_Put(Config.RECEIPT_URL, all_detail.get("object"), all_detail.get("token"));
//            }else if (type.equals(context.getString(R.string.customertokentype))) {
//                response = apiResponse.ResponseGet_Customer_Token(Config.Create_Consumer, all_detail.get("token"));
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

            //Check is Response is empty or not
            if (response == null) {
                response = "Error";
            }

            //Call Back pass response to parent class
            api_callback.onPost_data(response, type);
        }
    }
}