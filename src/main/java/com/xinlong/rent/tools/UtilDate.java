package com.xinlong.rent.tools;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilDate {
    //前一个月年月
    public static String getDrontMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return format.format(m);
    }
    //当前年月
    public static String getThisMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date m = new Date();
        return format.format(m);
    }


      // @description: 返回指定年月的月的第一天.

    public static String getFirstDayOfMonth(){
        Date dt =new Date();//-MM-dd HH:mm:ss
        SimpleDateFormat sf=new SimpleDateFormat("yyyy");
        String str=sf.format(dt);
        return str+"/01/01 00:00:00";
    }
    public static String getEndMonth(){
        Date dt =new Date();//-MM-dd HH:mm:ss
        SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sf.format(dt);
    }

    public static String getMonth() {
        Date dt =new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMM");
        return sf.format(dt);
    }
    public static String getYearMonth() {
        Date dt =new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月份");
        return sf.format(dt);
    }
    public static String getMonths(String str) {
        String st=str.substring(0,7);
        return  st.replaceAll("-","" );
    }
    public static String getMonthr() {
        Date dt =new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMM");
        String fd=sf.format(dt);

        return  fd.substring(4,6)+"月份";
    }

    public static void main(String[] args) {
//
            System.out.println("====" + getFirstDayOfMonth());
        System.out.println("====" + getEndMonth());

    }


 public static Date strToDateLong(String strDate) throws Exception {

     /*   if(strDate.length()<=10){
            strDate= strDate+" 00:00:00";

        }*/

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(strDate);
    }


}
