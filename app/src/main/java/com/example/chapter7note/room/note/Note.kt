package com.example.chapter7note.room.note

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    @ColumnInfo(name = "username")
    var username : String,
    @ColumnInfo(name = "judul")
    var judul : String,
    @ColumnInfo(name = "tanggal")
    var tanggal : String,
    @ColumnInfo(name = "isi")
    var isi : String
) : Parcelable
