package com.successfactors.logging.api;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Xc on 2016/4/19.
 */
public class Logger {
    String prefix="";
    public Logger(Class clazz){
        prefix = clazz.toString();
    }

    public Logger(){

    }

    public boolean isEnabled(Level level) {
        return true;
    }

    public void log(Level level, String message, Object[] parameters) {

    }

    public static void log(String s, Exception e) {
    }

    public void error(String s, IOException e) {

    }

    public void error(String s) {
    }

    public void info(String s) {

    }
}
