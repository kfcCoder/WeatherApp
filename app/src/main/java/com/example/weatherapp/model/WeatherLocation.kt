package com.example.weatherforecastapp.model.current

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.util.Constants.WEATHER_LOCATION_ID
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

/**
 * create table "weather_location" in room db
 */
@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String,
    val lat: String,
    val localtime: String,
    val localtime_epoch: Long, // : Int
    val lon: String,
    val name: String,
    val region: String,
    val timezone_id: String,
    val utc_offset: String
) {
    @PrimaryKey(autoGenerate = false) // we only want to store latest data
    var id: Int = WEATHER_LOCATION_ID

//    val zoneDateTime = ZonedDateTime.ofInstant( // instantiate an object not accepted in room
//        Instant.ofEpochSecond(localtime_epoch),
//        ZoneId.of(timezone_id)
//    )

    val zonedDateTime: ZonedDateTime // the time involve timezone
        get() {
            val instant = Instant.ofEpochSecond(localtime_epoch)
            val zoneId = ZoneId.of(timezone_id)
            return ZonedDateTime.ofInstant(instant, zoneId) // the time of area being queried
        }

}