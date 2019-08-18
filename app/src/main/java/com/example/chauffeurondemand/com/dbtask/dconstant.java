package com.example.chauffeurondemand.com.dbtask;

public class dconstant {
    public static final String DB_NAME = "chauffeurdb8";


    public static final String TABLE_NAME = "DriverDetail";
    public static final String DID = "DriverId";
    public static final String DNAME = "Name";
    public static final String DEMAIL = "Email";
    public static final String DPHONE = "Phone";
    public static final String DAGE="Age";
    public static final String DGENDER="Gender";
    public static final String DADDRESS="Address";

    public static final int DB_VERSION=1;
    public static final String DRIVER_DETAIL_QUERY = "create table DriverDetail(DriverId text primary key not null,Name text not null,Email text not null,Phone text not null,Age integer not null,Gender text not null,Address text not null)";


    public static final String TABLE_NAME2 = "ClientDetail";
    public static final String CID = "ClientId";
    public static final String CNAME = "Name";
    public static final String CEMAIL = "Email";
    public static final String CPHONE = "Phone";
    public static final String CADDRESS="Address";
    public static final String CLIENT_DETAIL_QUERY = "create table ClientDetail(ClientId text primary key not null,Name text not null,Email text not null,Phone text not null,Address text not null)";


    public static final String TABLE_NAME3 = "FeedbackInfo";
    public static final String FID = "Fid";
    public static final String FDID= "DriverId";
    public static final String FCID = "ClientId";
    public static final String FDATE = "Date";
    public static final String FTEXT="Text";
    public static final String FEEDBACK_INFO_QUERY= "create table FeedbackInfo(Fid text primary key not null,DriverId text not null,ClientId text not null,Date date not null,Text text not null)";



    public static final String TABLE_NAME4 = "RequestDriver";
    public static final String RCID = "ClientId";
    public static final String RID = "RequestId";
    public static final String RDATE= "Date";
    public static final String RFT = "FromTime";
    public static final String RTT = "ToTime";

    public static final String REQUEST_DRIVER_QUERY= "create table RequestDriver(ClientId text not null,RequestId integer primary key autoincrement not null,Date date not null,FromTime text not null,ToTime text not null)";


    public static final String TABLE_NAME5 = "AssignDriver";
    public static final String AID = "AssignId";
    public static final String ADID= "DriverId";
    public static final String ACID= "ClientId";
    public static final String ADATE= "Date";
    public static final String AFT= "FromTime";
    public static final String ATT= "ToTime";

    public static final String ATOTAL= "TotalCharge";
    public static final String ASSIGN_DRIVER_QUERY="create table AssignDriver(AssignId integer primary key autoincrement,DriverId text not null,ClientId text not null, Date date not null,FromTime text not null,ToTime text not null,TotalCharge float not null)";

}
