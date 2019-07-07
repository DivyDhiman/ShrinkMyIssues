package Call_Back;

/**
 * Created by Abhay dhiman
 */

//Interface class for passing data
public interface Api_callback {

    //Call back for passing API Resposne
    void onPost_data(String response, String type);

}
