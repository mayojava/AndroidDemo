package com.mytaxi.android_demo;

import com.mytaxi.android_demo.models.Driver;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class TestUtils {
    public static String calculateSHA256(final String password, final String salt) {
        String passwordWithSalt = password + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordWithSalt;
    }

    public static ArrayList<Driver> getDversList() {
        final ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Sarah Meyer", "15279809827", "", "London", new Date()));
        drivers.add(new Driver("Sarah Coleman", "15279809827", "", "Hamburg", new Date()));
        drivers.add(new Driver("Sarah Friedrich", "15279809827", "", "Monaco", new Date()));
        drivers.add(new Driver("John Meyer", "15279809827", "", "Paris", new Date()));
        drivers.add(new Driver("Michel Larson", "15279809827", "", "Zagreb", new Date()));
        drivers.add(new Driver("Ham Stanton", "15279809827", "", "Minsk", new Date()));
        drivers.add(new Driver("Jane Jin", "15279809827", "", "Amsterdam", new Date()));
        drivers.add(new Driver("Luke Shaw", "15279809827", "", "Berlin", new Date()));
        return drivers;
    }
}
