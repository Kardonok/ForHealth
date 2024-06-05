package com.example.forhealth.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile_list")
data class ProfileItem(
    @PrimaryKey @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "user_height") val userHeight: String,
    @ColumnInfo(name = "user_weight") val userWeight: String,
    @ColumnInfo(name = "user_gender") val userGender: String,
    @ColumnInfo(name = "user_token") val userToken: String,
)
//@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val id:Int=0
