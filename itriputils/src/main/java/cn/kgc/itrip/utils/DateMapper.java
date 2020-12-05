package cn.kgc.itrip.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @author 杨幸运
 * @create 2020/10/14
 */
public class DateMapper extends ObjectMapper {

    public DateMapper(){
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

}
