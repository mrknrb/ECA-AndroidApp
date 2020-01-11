/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MondatDatabaseDao {
    @Insert
    fun insert(mondat: Mondat)
    @Update
    fun update(mondat: Mondat)
    @Query("SELECT * FROM mondatoktabla ORDER BY mondatindex DESC")
    fun getAllMondat(): List<Mondat>
    @Query("DELETE FROM mondatoktabla")
    fun clear()
    @Query("SELECT * FROM mondatoktabla ORDER BY mondatindex DESC LIMIT 1")
    fun utolsomondat(): Mondat?
    @Query("SELECT * FROM mondatoktabla ORDER BY mondatid DESC LIMIT 1")
    fun getTonight(): Mondat?
    @Query("SELECT * FROM mondatoktabla WHERE filename LIKE :filename ORDER BY mondatid DESC LIMIT 1")
    fun fajlnevfoglalt(filename:String): Mondat?
    @Query("DELETE FROM mondatoktabla WHERE filename LIKE :filename ")
    fun deletefile(filename:String)
}

