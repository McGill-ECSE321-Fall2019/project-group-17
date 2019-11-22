package ca.mcgill.ecse321.project_group_17_android;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //
    private String error = null;
    private String success = null;

    public String loggedInUsername = "";

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

    private void refreshSuccessMessage() {
        // set the success message
        TextView tvSuccess = (TextView) findViewById(R.id.success);
        tvSuccess.setText(success);

        if (success == null || success.length() == 0) {
            tvSuccess.setVisibility(View.GONE);
        } else {
            tvSuccess.setVisibility(View.VISIBLE);
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
                loggedInUsername = username.getText().toString();
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

    public void createAvailability(View v) {
        error = "";
        success = "";
        final String tutorUsername = loggedInUsername;
        final TextView date = (TextView) findViewById(R.id.newavail_date);
        final TextView startTime = (TextView) findViewById(R.id.starttime);
        final TextView endTime = (TextView) findViewById(R.id.endtime);
        long longDate = getDateFromLabel(date.getText().toString()).getLong("longDate");
        long longStart = getTimeFromLabel(startTime.getText().toString()).getLong("longTime");
        long longEnd = getTimeFromLabel(endTime.getText().toString()).getLong("longTime");
        long createdDate = new Date().getTime();
        HttpUtils.postByUrl("/availabilities/createAvailability?tutorUsername="+tutorUsername+"&date="+longDate+"&createdDate="+createdDate
                +"&startTime="+longStart+"&endTime="+longEnd, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                System.out.println(response);
                date.setText("");
                startTime.setText("");
                endTime.setText("");
                success = "Availability successfully added!";
                refreshSuccessMessage();
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

    public void signup(){
        error = "";
        final TextView firstName = (TextView) findViewById(R.id.signup_firstName);
        final TextView lastName = (TextView) findViewById(R.id.signup_lastName);
        final TextView username = (TextView) findViewById(R.id.signup_username);
        final TextView email = (TextView) findViewById(R.id.signup_email);
        final TextView password = (TextView) findViewById(R.id.signup_password);
        final TextView passwordConfirm = (TextView) findViewById(R.id.signup_confirmPassword);

        if (!password.equals(passwordConfirm)){
            error += "Passwords do not match! ";
            refreshErrorMessage();
            return;
        }

        HttpUtils.post("/persons/createPerson/?firstName=" + firstName.getText().toString() +
                        "&lastName=" + lastName.getText().toString() + "&username=" + username.getText().toString()
                + "&email=" + email.getText().toString() + "&password=" + password.getText().toString() + "&personType=Tutor",
                new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                System.out.println(response);
                firstName.setText("");
                lastName.setText("");
                username.setText("");
                email.setText("");
                password.setText("");
                passwordConfirm.setText("");
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
