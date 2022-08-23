package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {

    public Date getLastNMonthsDate(int n){
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        calendar.add((GregorianCalendar.MONTH), -n);

        return calendar.getTime();
    }

    public String convertDate(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(date);
    }
}
