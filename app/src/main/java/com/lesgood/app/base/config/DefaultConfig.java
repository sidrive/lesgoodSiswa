/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.lesgood.app.base.config;

import android.content.Context;
import android.content.SharedPreferences;


public class DefaultConfig {
    public static final String KEY_USER_PREF = "LesgoodUserPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NEW_JOB_RETRY_COUNT = "new_post_retry_count";
    private static final String KEY_API_URL = "api_url";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_API_SECRET = "secret_key";
    private static final String KEY_USER_KEY = "user_key";
    private static final String KEY_PASS_KEY = "pass_key";
    public static final String KEY_USER_LAT = "user_lat_ley";
    public static final String KEY_USER_LNG = "user_lng_key";

    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME =
            "com.lesgood.app";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String LOCALITY_DATA_EXTRA = PACKAGE_NAME + ".LACALITY_DATA_EXTRA";

    public static final String POSTAL_CODE_DATA_EXTRA = PACKAGE_NAME + ".POSTAL_CODE_DATA_EXTRA";

    public static final String ADMIN_AREA_DATA_EXTRA = PACKAGE_NAME + ".ADMIN_AREA_DATA_EXTRA";

    public static final String COUNTRY_CODE_DATA_EXTRA = PACKAGE_NAME + ".COUNTRY_CODE_DATA_EXTRA";


    private final SharedPreferences mSharedPreferences;
    public DefaultConfig(Context context) {
        mSharedPreferences = context.getSharedPreferences("default_config", Context.MODE_PRIVATE);
    }

    public long getUserId() {
        return mSharedPreferences.getLong(KEY_USER_ID, 1);
    }

    public void setUserId(long userId) {
        mSharedPreferences.edit().putLong(KEY_USER_ID, userId).apply();
    }

    public int getNewPostRetryCount() {
        return mSharedPreferences.getInt(KEY_NEW_JOB_RETRY_COUNT, 20);
    }

    public void setNewPostRetryCount(int count) {
        mSharedPreferences.edit().putInt(KEY_NEW_JOB_RETRY_COUNT, count).apply();
    }

    public String getApiUrl() {
        return mSharedPreferences.getString(KEY_API_URL, "https://api.nexmo.com/");
    }

    public void setApiUrl(String url) {
        mSharedPreferences.edit().putString(KEY_API_URL, url).apply();
    }

    public String getApiKey() {
        return mSharedPreferences.getString(KEY_API_KEY, "26401283");
    }

    public void setApiKey(String url) {
        mSharedPreferences.edit().putString(KEY_API_KEY, url).apply();
    }

    public String getApiSecret() {
        return mSharedPreferences.getString(KEY_API_SECRET, "075f9a8f51c1d5ae");
    }

    public void setApiSecret(String apiSecret) {
        mSharedPreferences.edit().putString(KEY_API_SECRET, apiSecret).apply();
    }

    public String getUserKey() {
        return mSharedPreferences.getString(KEY_USER_KEY, "jd9kyl");
    }

    public void setUserKey(String url) {
        mSharedPreferences.edit().putString(KEY_USER_KEY, url).apply();
    }

    public String getPassKey() {
        return mSharedPreferences.getString(KEY_PASS_KEY, "doremi");
    }

    public void setPassKet(String url) {
        mSharedPreferences.edit().putString(KEY_PASS_KEY, url).apply();
    }



}
