package com.example.mm_kau.smart_point;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Designable{



   private Button Check ;
private EditText ID ;
    private SharedPreferences userfile;
    private SharedPreferences.Editor userfileEditer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeView();

    }


    @Override
    public void InitializeView() {
        Check = findViewById(R.id.button_log_in);
        ID = findViewById(R.id.IDOfHAJJ);
        Design();
    }

    @Override
    public void Design() {
        HandleAction();
    }

    @Override
    public void HandleAction() {


        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Call Server to check the login Information.
                StringRequest request = new StringRequest(Request.Method.POST, "https://hajj-reward-system.herokuapp.com/DB_getPoints.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("state");

                            if (status.equals("yes")) {

                                JSONObject info = jsonObject.getJSONObject("info");
                                String Points = info.getString("points");
                                String name = info.getString("name");

                                Toast.makeText(getBaseContext(), "WELCOME", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getBaseContext(), HomePage.class);
                                intent.putExtra("Pointe", Points);
                                intent.putExtra("Name", name);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), "The ID not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getBaseContext(), "There is an error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "There is an error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        // HTTP request parameters
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ID", ID.getText().toString());
                        return map;
                    }
                };
                // Add The volly request to the Singleton Queue.
                Singleton_Queue.getInstance(getBaseContext()).Add(request);

                // End of Volly http request




            }
        });

    }
}
