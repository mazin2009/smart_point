
package com.example.mm_kau.smart_point;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity implements Designable {


    private TextView Points;
    private TextView Name;
    private ListView LV;
    private ArrayList<REWARDS> ListOfReward;
    private SharedPreferences userfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Points = findViewById(R.id.TextPoints);
        Name = findViewById(R.id.Name);
        this.userfile = getSharedPreferences(Constrant.UserFile, MODE_PRIVATE);

        Points.setText(userfile.getString(Constrant.Points,"0"));
        Name.setText("Mr. "+userfile.getString(Constrant.NameOfHajj,"0"));
        InitializeView();
    }

    @Override
    public void InitializeView() {



        ListOfReward = new ArrayList<>();
        this.LV = findViewById(R.id.ListView);
        this.Points = findViewById(R.id.TextPoints);

        //Call Server to check the login Information.
        StringRequest request = new StringRequest(Request.Method.POST, "https://hajj-reward-system.herokuapp.com/DB_getRewards.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("state");

                    if (status.equals("yes")) {

                        JSONArray companies = jsonObject.getJSONArray("companies");
                        for (int i = 0; i < companies.length(); i++) {

                      ListOfReward.add( new REWARDS(companies.get(i).toString()));
                        }


                        Adapter_Reword adapter = new Adapter_Reword(getBaseContext(), ListOfReward);
                        LV.setAdapter(adapter);

                        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Intent intent = new Intent(getBaseContext(), RE_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("comp", ListOfReward.get(i).getName());
                                startActivity(intent);



                            }
                        });


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
                return map;
            }
        };
        // Add The volly request to the Singleton Queue.
        Singleton_Queue.getInstance(getBaseContext()).Add(request);


        Design();
    }

    @Override
    public void Design() {
        HandleAction();
    }

    @Override
    public void HandleAction() {

    }
}
