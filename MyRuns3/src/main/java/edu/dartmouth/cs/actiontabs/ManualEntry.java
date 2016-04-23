package edu.dartmouth.cs.actiontabs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by xuehanyu on 4/5/16.
 */
public class ManualEntry extends ListActivity{
    static final String[] content = new String[] { "Date", "Time", "Duration",
            "Distance", "Calories","Heart Rate", "Comment" };
    Calendar mDateAndTime = Calendar.getInstance();
    public static databaseItem item = new databaseItem();
    private DataBaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Don't have to do this anymore
        setContentView(R.layout.manualentry_layout);

        // Define a new adapter
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, content);

        // Assign the adapter to ListView
        setListAdapter(mAdapter);

        // Define the listener interface
        AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selected = ((TextView) view).getText().toString();
                switch(selected){
                    case "Date":
                        onDateClicked();
                        break;
                    case "Time":
                        onTimeClicked();
                        break;
                    default:
                        showDialog(selected);
                }
            }
        };
        //Database Helper
        helper = new DataBaseHelper(getApplicationContext());

        //Intent
        Intent intent = getIntent();
        item.ActivityType = intent.getStringExtra("ActivityType");
        item.InputType = intent.getStringExtra("InputType");

        // Get the ListView and binds to the listener
        ListView listView = getListView();
        listView.setOnItemClickListener(mListener);
    }

    /*
     * called when the date is clicked
     */
    private void onDateClicked() {
        DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mDateAndTime.set(Calendar.YEAR, year);
                mDateAndTime.set(Calendar.MONTH, monthOfYear);
                mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        new DatePickerDialog(ManualEntry.this, mDateListener,
                mDateAndTime.get(Calendar.YEAR),
                mDateAndTime.get(Calendar.MONTH),
                mDateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    /*
     * called when the time is clicked
     */
    private void onTimeClicked() {

        TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mDateAndTime.set(Calendar.MINUTE, minute);
                mDateAndTime.set(Calendar.SECOND,0);
            }
        };

        new TimePickerDialog(ManualEntry.this, mTimeListener,
                mDateAndTime.get(Calendar.HOUR_OF_DAY),
                mDateAndTime.get(Calendar.MINUTE), true).show();
    }

    /*
     * showing the dialog
     */
    private void showDialog(String title){
        MyAlertDialogFragment mydialog = new MyAlertDialogFragment();
        DialogFragment newFragment = mydialog
                .newInstance(title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doCancelClick() {
    }

    /*
     * called when the user clicks the save button
     */
    public void doOkClick(String title, String data) {
        int dataInt = 0;
        double dataDouble = 0;
        if(title.equals("Comment"));
        else if(title.equals("Duration") || title.equals("Distance"))
            dataDouble = Double.parseDouble(data);
        else
            dataInt = Integer.parseInt(data);
        switch (title){
            case "Duration":
                item.Duration = dataDouble;
                break;
            case "Distance":
                item.Distance = dataDouble;
                break;
            case "Calories":
                item.Calories = dataInt;
                break;
            case "Heart Rate":
                item.HeartRate = dataInt;
                break;
            case "Comment":
                item.Comment = data;
        }
    }


    /** the fragment used to show the dialog */
    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(String title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final String title = getArguments().getString("title");
            final EditText editText = new EditText(getActivity());
            if(title.equals("Comment")) {
                editText.setHint("How did it go? Notes here.");
                editText.setHeight(400);
            }else if(title.equals("Duration") || title.equals("Distance"))
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            else
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

//            //show the result in the dialog
//            switch (title){
//                case "Duration":
//                    editText.setText(item.Duration < 0 ? "" : item.Duration+"");
//                    break;
//                case "Distance":
//                    editText.setText(item.Distance < 0 ? "" : item.Distance+"");
//                    break;
//                case "Calories":
//                    editText.setText(item.Calories < 0 ? "" : item.Calories+"");
//                    break;
//                case "Heart Rate":
//                    editText.setText(item.HeartRate < 0 ? "" : item.HeartRate+"");
//                    break;
//                case "Comment":
//                    editText.setText(item.Comment+"");
//            }

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setView(editText)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    ((ManualEntry) getActivity())
                                            .doOkClick(title, editText.getText().toString());
                                }
                            })
                    .setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    ((ManualEntry) getActivity())
                                            .doCancelClick();
                                }
                            }).create();
        }
    }

    /*
     * called when the save button is clicked
     */
    public void onEntrySaveClicked(View v) {
        item.Date = mDateAndTime.get(Calendar.YEAR) +"-"+ (mDateAndTime.get(Calendar.MONTH)+1) +"-"+ mDateAndTime.get(Calendar.DAY_OF_MONTH);
        item.Time = mDateAndTime.get(Calendar.HOUR_OF_DAY) +":"+ mDateAndTime.get(Calendar.MINUTE) +":"+
                (mDateAndTime.get(Calendar.SECOND) == 0 ? "00" : mDateAndTime.get(Calendar.SECOND));
        item.ID = System.currentTimeMillis()+"-"+item.InputType+"-"+item.ActivityType;
        new asyncTask(item).execute();
        Toast.makeText(getApplicationContext(), "Entry saved.",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    /*
     * called when the cancel button is clicked
     */
    public void onEntryCancelClicked(View v) {
        Toast.makeText(getApplicationContext(), "Entry discarded.",
                Toast.LENGTH_SHORT).show();

        finish();
    }

    /*
     * create an asynctask to handle writing to the database.
     */
    class asyncTask extends AsyncTask<Void, Void, Void> {
        private databaseItem item;

        public asyncTask(databaseItem item){
            this.item = item;
        }
        @Override
        protected Void doInBackground(Void... params) {
            helper.addItem(item);
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}
