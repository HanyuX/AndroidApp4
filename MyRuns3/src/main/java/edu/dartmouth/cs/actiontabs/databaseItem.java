package edu.dartmouth.cs.actiontabs;

/**
 * Created by xuehanyu on 4/19/16.
 */
public class databaseItem {
    private String ID = "";                //ID of the item
    private String Date = "";              //Date of the item
    private String Time = "";              //time of the item
    private double Duration = -1;          //the total time for the activity
    private double Distance = -1;          //the total distance for the activity
    private int Calories = -1;             //the total calories cost in the activity
    private int HeartRate = -1;            //the heart rate in the activity
    private String Comment = "";           //comments for the activity
    private String InputType = "";         //input type
    private String ActivityType = "";      //the activity type

    //constructor
    public databaseItem(String ID, String Date, String Time, double Duration, double Distance, int Calories,
                        int HeartRate, String Comment, String InputType, String ActivityType){
        this.ID = ID;
        this.Date = Date;
        this.Time = Time;
        this.Duration = Duration;
        this.Distance = Distance;
        this.Calories = Calories;
        this.HeartRate = HeartRate;
        this.Comment = Comment;
        this.InputType = InputType;
        this.ActivityType = ActivityType;
    }

    public databaseItem(){}

    //getter and setter
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public double getDuration() {
        return Duration;
    }

    public void setDuration(double duration) {
        Duration = duration;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(int heartRate) {
        HeartRate = heartRate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getInputType() {
        return InputType;
    }

    public void setInputType(String inputType) {
        InputType = inputType;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }
}
