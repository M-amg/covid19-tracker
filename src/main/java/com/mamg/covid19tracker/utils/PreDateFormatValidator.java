package com.mamg.covid19tracker.utils;

import com.mamg.covid19tracker.enums.PreDateType;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PreDateFormatValidator {


    public boolean test(String date, PreDateType dateType) {

        switch (dateType) {
            case YEAR:
                if(!date.matches("[0-9]{4}")) return false;
                date +="-01-01";
                break;
            case MONTH:
               if(!date.matches("[0-9]{4}-(0[1-9]|1[0-2])")) return false;
                date +="-01";
                break;
            default:
                if(!date.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])")) return false;
                break;
        }

        Integer year = Integer.parseInt(date.substring(0, 4));
        Integer mount = Integer.parseInt(date.substring(5, 7));
        Integer day = Integer.parseInt(date.substring(8, 10));

        Calendar calendar = new GregorianCalendar(year, mount-1, day);
        Calendar currantDate = Calendar.getInstance();


        if(this.compareDate(calendar, currantDate)!=1)
            return true;


        return false;

    }


    protected int compareDate(Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) >= c2.get(Calendar.YEAR)) {
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
                if (c1.get(Calendar.MONTH) >= c2.get(Calendar.MONTH)) {
                    if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
                        if (c1.get(Calendar.DAY_OF_MONTH) >= c2.get(Calendar.DAY_OF_MONTH)) {
                            if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
                                return 0;
                            } else {
                                return 1;
                            }
                        } else {
                            return 2;
                        }
                    } else {
                        return 1;
                    }
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }

}
