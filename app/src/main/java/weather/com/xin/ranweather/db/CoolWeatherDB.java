package weather.com.xin.ranweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import weather.com.xin.ranweather.model.City;
import weather.com.xin.ranweather.model.County;
import weather.com.xin.ranweather.model.Province;

/**
 * Created by xin on 2016/2/29.
 */
public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "ran_weather";
    private static final int VERSION = 1;
    public static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    public CoolWeatherDB(Context context) {
        RanWeatherOpenHelper dbHelp = new RanWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelp.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 读取省份信息
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 获取所有的城市信息
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCountry(County country) {
        if (country != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", country.getCountyName());
            values.put("county_code", country.getCountyCode());
            values.put("city_id", country.getCityId());
            db.insert("Country", null, values);
        }
    }

    /**
     * 获取所有的县
     */
    public List<County> loadCountries(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("Country", null, "city_id=?", new String[]{String.valueOf(cityId)}, null,
                null, null);
        if (cursor.moveToFirst()) {
            do {
                County country = new County();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                country.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                country.setCityId(cityId);
                list.add(country);

            } while (cursor.moveToNext());
        }
        return list;
    }

}
