package com.script972.clutchclient.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class helper for validation input data
 */
public class ValidatorHelper {

    /**
     * Method for validation email String
     * @param input email string
     * @return validate or not
     */
    public static boolean validateEmail(String input){
        Pattern pattern;
        final String ePattern =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(ePattern);
        return pattern.matcher(input).matches();
    }

}
