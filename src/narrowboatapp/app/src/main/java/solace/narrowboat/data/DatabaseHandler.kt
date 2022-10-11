package solace.narrowboat.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 9
        private const val DATABASE_NAME = "Navlog"

        private const val TABLE_JOURNEY = "JourneyTable"
        private const val KEY_JOURNEYID = "jid"
        private const val KEY_JOURNEYNAME = "Name"

        private const val TABLE_SESSION = "SessionTable"
        private const val KEY_SESSIONID = "sid"
        private const val KEY_STARTTIME = "StartTime"
        private const val KEY_ENDTIME = "EndTime"
        private const val KEY_DATE = "Date"
        private const val KEY_DISTANCE = "Distnace"
        private const val KEY_BOATNAME = "BoatName"

        private const val TABLE_LOCATIONS = "LocationsTable"
        private const val KEY_LOCATIONID = "lid"
        private const val KEY_LATITUDE = "Latitude"
        private const val KEY_LONGITUDE = "Longitude"
        private const val KEY_TIME = "Time"

        private const val TABLE_BOATS = "BoatsTable"
        private const val KEY_BOATID = "bid"
        private const val KEY_BOATOWNER = "BoatOwner"
        private const val KEY_REGISTERNUM = "RegisterNum"
        private const val KEY_CIN = "CIN"
        private const val KEY_BOATTYPE = "BoatType"
        private const val KEY_BOATMODEL = "BoatModel"
        private const val KEY_BOATLENGTH = "BoatLength"
        private const val KEY_BOATBEAM = "BoatBeam"
        private const val KEY_BOATDRAFT = "BoatDraft"

        private const val TABLE_LOGBOOK = "LogbookTable"
        private const val KEY_LOGBOOKID = "logid"
        private const val KEY_CREW = "Crew"
        private const val KEY_GUESTS = "Guests"
        private const val KEY_STARTLOCATION = "StartLocation"
        private const val KEY_ENDLOCATION = "EndLocation"
        private const val KEY_WEATHER = "Weather"
        private const val KEY_ENGINEHOURS = "EngineHours"
        private const val KEY_FUEL = "Fuel"
        private const val KEY_WATER = "Water"
        private const val KEY_NOTES = "Notes"
        private const val KEY_NUMLOCKS = "NumberLocks"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE: String

        CREATE_TABLE = ("CREATE TABLE " + TABLE_JOURNEY + " (" + KEY_JOURNEYID + " INTEGER PRIMARY KEY," + KEY_JOURNEYNAME + " TEXT" + ")")
        db?.execSQL(CREATE_TABLE)

        CREATE_TABLE = ("CREATE TABLE " + TABLE_SESSION + " ("
                + KEY_SESSIONID + " INTEGER PRIMARY KEY," + KEY_JOURNEYID + " INTEGER," + KEY_LOGBOOKID + " INTEGER," + KEY_STARTTIME + " TEXT," + KEY_ENDTIME + " TEXT," + KEY_DATE + " TEXT," + KEY_DISTANCE + " TEXT," + KEY_BOATNAME + " TEXT" + ")")
        db?.execSQL(CREATE_TABLE)

        CREATE_TABLE = ("CREATE TABLE " + TABLE_LOCATIONS + " ("
                + KEY_LOCATIONID + " INTEGER PRIMARY KEY, " + KEY_SESSIONID + " INTEGER," + KEY_LATITUDE + " TEXT," + KEY_LONGITUDE + " TEXT," + KEY_TIME + " TEXT" + ")")
        db?.execSQL(CREATE_TABLE)

        CREATE_TABLE = ("CREATE TABLE " + TABLE_BOATS + " ("
                + KEY_BOATID + " INTEGER PRIMARY KEY, " + KEY_BOATNAME + " TEXT," + KEY_BOATOWNER + " TEXT," + KEY_REGISTERNUM + " TEXT," + KEY_CIN + " TEXT," + KEY_BOATTYPE + " TEXT," + KEY_BOATMODEL + " TEXT,"
                + KEY_BOATLENGTH + " TEXT," + KEY_BOATBEAM + " TEXT," + KEY_BOATDRAFT + " TEXT" + ")")
        db?.execSQL(CREATE_TABLE)

        CREATE_TABLE = ("CREATE TABLE " + TABLE_LOGBOOK + " ("
                + KEY_LOGBOOKID + " INTEGER PRIMARY KEY, " + KEY_CREW + " TEXT," + KEY_GUESTS + " TEXT," + KEY_STARTLOCATION + " TEXT," + KEY_ENDLOCATION + " TEXT," + KEY_WEATHER + " TEXT," + KEY_ENGINEHOURS + " TEXT," + KEY_FUEL + " TEXT,"
                + KEY_WATER + " TEXT," + KEY_NOTES + " TEXT," + KEY_NUMLOCKS + " TEXT" + ")")
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNEY)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_BOATS)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGBOOK)
        onCreate(db)
    }

    fun addMarker(latitude: String, longitude: String, sessionID: Int, time: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SESSIONID, sessionID)
        contentValues.put(KEY_LATITUDE, latitude)
        contentValues.put(KEY_LONGITUDE, longitude)
        contentValues.put(KEY_TIME, time)

        val success = db.insert(TABLE_LOCATIONS, null, contentValues)
        return success
    }

    fun addSession(journeyID: Int, logbookID: Int, startTime: String, endTime: String, date: String, distance: String, boatName: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_JOURNEYID, journeyID)
        contentValues.put(KEY_LOGBOOKID, logbookID)
        contentValues.put(KEY_STARTTIME, startTime)
        contentValues.put(KEY_ENDTIME, endTime)
        contentValues.put(KEY_DATE, date)
        contentValues.put(KEY_DISTANCE, distance)
        contentValues.put(KEY_BOATNAME, boatName)

        val success = db.insert(TABLE_SESSION, null, contentValues)
        return success
    }

    fun getMostRecentSession(): Int{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_SESSION ORDER BY $KEY_SESSIONID DESC LIMIT 1"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
           if(cursor.moveToFirst()){
               return cursor.getInt(cursor.getColumnIndex(KEY_SESSIONID))
           }
        }
        return 0
    }

    fun getMostRecentLogbook(): Int{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_LOGBOOK ORDER BY $KEY_LOGBOOKID DESC LIMIT 1"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                return cursor.getInt(cursor.getColumnIndex(KEY_LOGBOOKID))
            }
        }
        return 0
    }

    fun addJourney(name: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_JOURNEYNAME, name)

        val success = db.insert(TABLE_JOURNEY, null, contentValues)
        return success
    }

    fun viewJourneys(): ArrayList<Journey>{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_JOURNEY"
        var cursor: Cursor? = null
        var journeys = ArrayList<Journey>()
        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val journey = Journey(cursor.getInt(cursor.getColumnIndex(KEY_JOURNEYID)), cursor.getString(cursor.getColumnIndex(KEY_JOURNEYNAME)))
                    journeys.add(journey)
                }while(cursor.moveToNext())
            }
        }
        return journeys
    }

    fun deleteJourney(id: Int){
        val db = this.writableDatabase
        val query = ("DELETE FROM $TABLE_JOURNEY WHERE $KEY_JOURNEYID=$id")
        db.execSQL(query)
    }

    fun viewSessions(id: Int): ArrayList<Session>{
        val db = this.writableDatabase
        val query = ("SELECT * FROM $TABLE_SESSION WHERE $KEY_JOURNEYID=$id")
        var cursor: Cursor? = null
        var sessions = ArrayList<Session>()
        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val session = Session(cursor.getInt(cursor.getColumnIndex(KEY_SESSIONID)), cursor.getInt(cursor.getColumnIndex(KEY_JOURNEYID)), cursor.getInt(cursor.getColumnIndex(KEY_LOGBOOKID)), cursor.getString(cursor.getColumnIndex(KEY_STARTTIME))
                    , cursor.getString(cursor.getColumnIndex(KEY_ENDTIME)), cursor.getString(cursor.getColumnIndex(KEY_DATE)), cursor.getString(cursor.getColumnIndex(KEY_DISTANCE)), cursor.getString(cursor.getColumnIndex(KEY_BOATNAME)))
                    sessions.add(session)
                }while(cursor.moveToNext())
            }
        }
        return sessions
    }

    fun viewMarkers(id: Int): ArrayList<MarkerPosition>{
        val db = this.writableDatabase
        val query = ("SELECT * FROM $TABLE_LOCATIONS WHERE $KEY_SESSIONID=$id")
        var cursor: Cursor? = null
        var markers = ArrayList<MarkerPosition>()
        try{
            cursor = db.rawQuery(query, null)
        }catch(e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val marker = MarkerPosition(cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)), cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE)), cursor.getString(cursor.getColumnIndex(KEY_TIME)))
                    markers.add(marker)
                }while(cursor.moveToNext())
            }
        }
        return markers
    }

    fun addBoat(boatName: String, boatOwner: String, boatRegisterNum: String, boatCIN: String, boatType: String, boatModel: String, boatLength: String, boatBeam: String, boatDraft:String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_BOATNAME, boatName)
        contentValues.put(KEY_BOATOWNER, boatOwner)
        contentValues.put(KEY_REGISTERNUM, boatRegisterNum)
        contentValues.put(KEY_CIN, boatCIN)
        contentValues.put(KEY_BOATTYPE, boatType)
        contentValues.put(KEY_BOATMODEL, boatModel)
        contentValues.put(KEY_BOATLENGTH, boatLength)
        contentValues.put(KEY_BOATBEAM, boatBeam)
        contentValues.put(KEY_BOATDRAFT, boatDraft)

        val success = db.insert(TABLE_BOATS, null, contentValues)
        return success
    }

    fun viewBoats(): ArrayList<Boat>{
        val db = this.writableDatabase
        val query = ("SELECT * FROM $TABLE_BOATS")
        var cursor: Cursor? = null
        var boats = ArrayList<Boat>()
        try{
            cursor = db.rawQuery(query, null)
        }catch(e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val boat = Boat(cursor.getInt(cursor.getColumnIndex(KEY_BOATID)), cursor.getString(cursor.getColumnIndex(
                        KEY_BOATNAME)), cursor.getString(cursor.getColumnIndex(KEY_BOATOWNER)), cursor.getString(cursor.getColumnIndex(
                        KEY_REGISTERNUM)), cursor.getString(cursor.getColumnIndex(KEY_CIN)), cursor.getString(cursor.getColumnIndex(
                        KEY_BOATTYPE)), cursor.getString(cursor.getColumnIndex(KEY_BOATMODEL)), cursor.getString(cursor.getColumnIndex(
                        KEY_BOATLENGTH)), cursor.getString(cursor.getColumnIndex(KEY_BOATBEAM)), cursor.getString(cursor.getColumnIndex(
                        KEY_BOATDRAFT)))
                    boats.add(boat)
                }while(cursor.moveToNext())
            }
        }
        return boats
    }

    fun viewBoat(id: Int): Boat{
        val db = this.writableDatabase
        val query = ("SELECT * FROM $TABLE_BOATS WHERE $KEY_BOATID=$id")
        var cursor: Cursor? = null
        var boats = ArrayList<Boat>()
        try{
            cursor = db.rawQuery(query, null)
        }catch(e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val boat = Boat(cursor.getInt(cursor.getColumnIndex(KEY_BOATID)), cursor.getString(cursor.getColumnIndex(
                            KEY_BOATNAME)), cursor.getString(cursor.getColumnIndex(KEY_BOATOWNER)), cursor.getString(cursor.getColumnIndex(
                            KEY_REGISTERNUM)), cursor.getString(cursor.getColumnIndex(KEY_CIN)), cursor.getString(cursor.getColumnIndex(
                            KEY_BOATTYPE)), cursor.getString(cursor.getColumnIndex(KEY_BOATMODEL)), cursor.getString(cursor.getColumnIndex(
                            KEY_BOATLENGTH)), cursor.getString(cursor.getColumnIndex(KEY_BOATBEAM)), cursor.getString(cursor.getColumnIndex(
                            KEY_BOATDRAFT)))
                    boats.add(boat)
                }while(cursor.moveToNext())
            }
        }
        return boats.first()
    }

    fun addLogbook(logCrew: String, logGuests: String, logStartLocation: String, logEndLocation: String, logWeather: String, logEngineHours: String, logFuel: String, logWater: String, logNotes: String, logNumLocks: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_CREW, logCrew)
        contentValues.put(KEY_GUESTS, logGuests)
        contentValues.put(KEY_STARTLOCATION, logStartLocation)
        contentValues.put(KEY_ENDLOCATION, logEndLocation)
        contentValues.put(KEY_WEATHER, logWeather)
        contentValues.put(KEY_ENGINEHOURS, logEngineHours)
        contentValues.put(KEY_FUEL, logFuel)
        contentValues.put(KEY_WATER, logWater)
        contentValues.put(KEY_NOTES, logNotes)
        contentValues.put(KEY_NUMLOCKS, logNumLocks)

        val success = db.insert(TABLE_LOGBOOK, null, contentValues)
        return success
    }

    fun viewLogbook(id: Int): Logbook{
        val db = this.writableDatabase
        val query = ("SELECT * FROM $TABLE_LOGBOOK WHERE $KEY_LOGBOOKID=$id")
        var cursor: Cursor? = null
        var logbooks = ArrayList<Logbook>()
        try{
            cursor = db.rawQuery(query, null)
        }catch(e: SQLiteException){
            db.execSQL(query)
        }
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val logbook = Logbook(cursor.getInt(cursor.getColumnIndex(KEY_LOGBOOKID)), cursor.getString(cursor.getColumnIndex(KEY_CREW)), cursor.getString(cursor.getColumnIndex(KEY_GUESTS)),
                    cursor.getString(cursor.getColumnIndex(KEY_STARTLOCATION)), cursor.getString(cursor.getColumnIndex(KEY_ENDLOCATION)), cursor.getString(cursor.getColumnIndex(KEY_WEATHER)),
                    cursor.getString(cursor.getColumnIndex(KEY_ENGINEHOURS)), cursor.getString(cursor.getColumnIndex(KEY_FUEL)), cursor.getString(cursor.getColumnIndex(KEY_WATER)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOTES)), cursor.getString(cursor.getColumnIndex(KEY_NUMLOCKS)))
                    logbooks.add(logbook)
                }while(cursor.moveToNext())
            }
        }
        return logbooks.first()
    }

    fun updateLogbook(lid: Int, logCrew: String, logGuests: String, logStartLocation: String, logEndLocation: String, logWeather: String, logEngineHours: String, logFuel: String, logWater: String, logNotes: String, logNumLocks: String): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_LOGBOOKID, lid)
        contentValues.put(KEY_CREW, logCrew)
        contentValues.put(KEY_GUESTS, logGuests)
        contentValues.put(KEY_STARTLOCATION, logStartLocation)
        contentValues.put(KEY_ENDLOCATION, logEndLocation)
        contentValues.put(KEY_WEATHER, logWeather)
        contentValues.put(KEY_ENGINEHOURS, logEngineHours)
        contentValues.put(KEY_FUEL, logFuel)
        contentValues.put(KEY_WATER, logWater)
        contentValues.put(KEY_NOTES, logNotes)
        contentValues.put(KEY_NUMLOCKS, logNumLocks)

        val success = db.update(TABLE_LOGBOOK, contentValues, "$KEY_LOGBOOKID=?", arrayOf(lid.toString()))
        db.close()
        return success
    }



    fun emptyTable(){
        val db = this.writableDatabase
        var EMPTY_TABLE = ("DELETE FROM " + TABLE_SESSION)
        db?.execSQL(EMPTY_TABLE)
        EMPTY_TABLE = ("DELETE FROM " + TABLE_LOCATIONS)
        db?.execSQL(EMPTY_TABLE)
        EMPTY_TABLE = ("DELETE FROM " + TABLE_LOGBOOK)
        db?.execSQL(EMPTY_TABLE)
        EMPTY_TABLE = ("DELETE FROM " + TABLE_BOATS)
        db?.execSQL(EMPTY_TABLE)
        EMPTY_TABLE = ("DELETE FROM " + TABLE_JOURNEY)
        db?.execSQL(EMPTY_TABLE)
    }

    fun dropTable(){
        val db = this.writableDatabase
        val DROP_TABLE = ("DROP TABLE $TABLE_SESSION")
        db?.execSQL(DROP_TABLE)
    }

}