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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mondatoktabla")
data class Mondat(
        @PrimaryKey(autoGenerate = true)
        var mondatid: Long = 0L,
        @ColumnInfo(name = "letrehozasdatuma")
        val letrehozasdatuma: Long,
        @ColumnInfo(name = "mondatindex")
        val mondatindex: Int?,
        @ColumnInfo(name = "mondat")
        var mondat: String?,
        @ColumnInfo(name = "fejezetindex")
        var fejezetindex: Int?,
        @ColumnInfo(name = "fejezetcim")
        var fejezetcim: String?,
        @ColumnInfo(name = "filename")
        var filename: String?,
        @ColumnInfo(name = "utolsomodositas")
        var utolsomodositas: Long
)
