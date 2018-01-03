package com.lesgood.app.util.preference;

import android.content.Context;

/**
 * Developer at Geekgarden.
 * Created by sim-x on 1/3/18.
 * Website geekgarden.id .
 * More info  geekgardendev@gmail.com.
 */

public class UserPreferences {
  private static final String CACHE_NAME = UserPreferences.class.getName();
  private static CachePreferences cachePreferences;
  private Context mContext;
  public UserPreferences(Context context) {
    this.mContext = context;
  }
  private static CachePreferences getInstance(Context context) {
    if (cachePreferences == null)
      cachePreferences = new CachePreferences(context,CACHE_NAME);
    return cachePreferences;
  }

  public synchronized <T> T read(String key, Class<T> tClass) {
    return getInstance(this.mContext).read(key, tClass);
  }

  public synchronized <T> void write(String key, T value, Class<T> tClass) {
    getInstance(this.mContext).write(key, value, tClass);
  }

  public synchronized void clear() {
    getInstance(this.mContext).clear();
  }

}
