package com.nugrahanggara.submissiondicoding.Helper

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nugrahanggara.submissiondicoding.Model.ItemsItem
import com.nugrahanggara.submissiondicoding.Model.ModelUserResponse

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, NAME_DATABASE,null, VERSION_DATABASE){
    companion object{
        const val NAME_DATABASE = "submissionDicoding"
        const val VERSION_DATABASE = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE favorite (id INTEGER PRIMARY KEY autoincrement,login TEXT NOT NULL,avatar TEXT NOT NULL);"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS favorite;"
        db!!.execSQL(query)
        onCreate(db)
    }

    fun readData() :ArrayList<HashMap<String,String>>{
        val list = ArrayList<HashMap<String,String>>()
        val database : SQLiteDatabase = this.writableDatabase
        val query = "SELECT * FROM favorite;"
        val cursor : Cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                val map = HashMap<String,String>()
                map.put("id",cursor.getString(0))
                map.put("nama",cursor.getString(1))
                map.put("avatar",cursor.getString(2))
                list.add(map)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insert (data: ItemsItem){
        val database : SQLiteDatabase = this.writableDatabase
        val query = "INSERT INTO favorite (login,avatar) VALUES ('"+data.login+"','"+data.avatarUrl+"');"
        database.execSQL(query)
    }

    fun deleteBaseLogin(login : String){
        val database : SQLiteDatabase = this.writableDatabase
        val query = "DELETE FROM favorite WHERE login  = '"+login+"';"
        database.execSQL(query)
    }

}

