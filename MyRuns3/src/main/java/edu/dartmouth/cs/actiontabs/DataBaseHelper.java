package edu.dartmouth.cs.actiontabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xuehanyu on 4/19/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper{
    private final static String DATABASE_NAME = "RUN";
    private final static int DATABASE_VERSION = 1;
    private String TABLE_NAME = "ManualEntry";

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /*
     * called when the activity is created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table Orders(Id integer primary key, CustomName text, OrderPrice integer, Country text);
        String sql = "create table if not exists " + TABLE_NAME + " (ID text primary key,Date text,Time text,Duration real," +
                "Distance real, Calories integer, HeartRate integer, Comment text, InputType text, ActivityType text)";
        sqLiteDatabase.execSQL(sql);
    }

    /*
     * called when needs to upgrade
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    /*
     * add an item into the database
     */
    public void addItem(databaseItem item){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sqlAdd = "insert into " + TABLE_NAME + " (ID, Date, Time, Duration, Distance, Calories, " +
                    "HeartRate, Comment, InputType, ActivityType) values ('" + (item.getID().equals("") ? "-1" : item.getID()) +"','"+
                    (item.getDate().equals("") ? "0" : item.getDate()) +"','"+
                    (item.getTime().equals("") ? "0" : item.getTime()) +"',"+
                    (item.getDuration() < 0 ? 0 : item.getDuration())  +","+
                    (item.getDistance() < 0 ? 0 : item.getDistance())  +","+
                    (item.getCalories() < 0 ? 0 : item.getCalories())  +","+
                    (item.getHeartRate() < 0 ? 0 : item.getHeartRate())+",'"+
                    (item.getComment().equals("") ? "0" : item.getComment()) + "','" +
                    (item.getInputType().equals("") ? "0" : item.getInputType()) +"','"+
                    (item.getActivityType().equals("") ? "0" : item.getActivityType()) +"')";
            db.execSQL(sqlAdd);
        }catch (Exception exc){
            Log.d("addItem", exc.getMessage());
        }
    }

    /*
     * delete an item in the database
     */
    public void deleteItem(String ID){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from "+TABLE_NAME+" where ID=?", new String[]{ID});
        }catch (Exception exc){
            Log.d("deleteItem", exc.getMessage());
        }
    }

    /*
     * Get all items in the database
     */
    public List<databaseItem> allItems(){
        Log.d("database", "all");
        List<databaseItem> result = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by ID ASC", new String[]{});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    databaseItem thisItem = new databaseItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4),
                            cursor.getInt(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                    result.add(thisItem);
                    cursor.moveToNext();
                }
            }
        }catch (Exception exc){
            Log.d("addItem", exc.getMessage());
        }
        return result;
    }
}
