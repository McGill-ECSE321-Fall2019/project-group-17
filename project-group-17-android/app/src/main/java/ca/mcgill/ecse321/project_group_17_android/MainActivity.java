package ca.mcgill.ecse321.project_group_17_android;

import android.R.layout;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //
    private String error = null;

    private String selectedCourseID = "";

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    public void login(View v) {
        error = "";
        final TextView username = (TextView) findViewById(R.id.login_username);
        final TextView password = (TextView) findViewById(R.id.login_password);
        HttpUtils.get("/persons/getByUsername/?username=" + username.getText().toString() + "&password=" + password.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                System.out.println(response);
                username.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    // AVAILABILITY
    public void createAvailability(View v) {
        error = "";
        final String tutorUsername = "charStar";
        final TextView date = (TextView) findViewById(R.id.newavail_date);
        final TextView startTime = (TextView) findViewById(R.id.starttime);
        final TextView endTime = (TextView) findViewById(R.id.endtime);
        long longDate = getDateFromLabel(date.getText().toString()).getLong("longDate");
        long longStart = getTimeFromLabel(startTime.getText().toString()).getLong("longTime");
        long longEnd = getTimeFromLabel(endTime.getText().toString()).getLong("longTime");
        long createdDate = new Date().getTime();
        HttpUtils.post("/availabilities/createAvailability?tutorUsername="+tutorUsername+"&date="+longDate+"&createdDate="+createdDate
                +"&startTime="+longStart+"&endTime="+longEnd, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                System.out.println(response);
                date.setText("");
                startTime.setText("");
                endTime.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }


    // SPECIFIC COURSE
    public void createSpecificCourse(View v) {
        error = "";

        final TextView hourlyRate = (TextView) findViewById(R.id.specificCourse_hourlyRate);

        String tutor = "charStar";

        HttpUtils.post("/specificCourses/create?hourlyRate="+hourlyRate.getText().toString()+"&tutorUsername="+tutor+"&courseID="+selectedCourseID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                System.out.println(response);
                hourlyRate.setText("");

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner coursesList = (Spinner) findViewById(R.id.coursesList);
        final ArrayList<String> courses =  new ArrayList<String>();
        coursesList.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // Populate courses
                HttpUtils.get("/courses", new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        System.out.println(response);

                        ArrayList<String> coursesArray =  new ArrayList<String>();

                        for(int i=0; i < response.length(); i++) {
                            try {
                                //System.out.println(response.getJSONObject(i).get("courseName"));
                                coursesArray.add(response.getJSONObject(i).get("courseID").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        courses.addAll(coursesArray);

                        refreshErrorMessage();

                        System.out.println(coursesArray.size());
                        System.out.println(coursesArray);

                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                courses
                        );

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        coursesList.setAdapter(adapter);


                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            error += errorResponse.get("message").toString();
                        } catch (JSONException e) {
                            error += e.getMessage();
                        }
                        refreshErrorMessage();
                    }
                });

                return true;
            }
        });

        coursesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

                String courseID = coursesList.getSelectedItem().toString();

                selectedCourseID = courseID;


            }
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedCourseID = "";
            }
        });



        /*FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
        */

        // initialize error message text view
        refreshErrorMessage();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Bundle getTimeFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        Time t = new Time(hour, minute, 0);

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);
        rtn.putLong("longTime", t.getTime());

        return rtn;
    }

    private Bundle getDateFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        Date d = new Date(year, month, day);
        long longDate = d.getTime();

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);
        rtn.putLong("longDate", longDate);

        return rtn;
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }
}
