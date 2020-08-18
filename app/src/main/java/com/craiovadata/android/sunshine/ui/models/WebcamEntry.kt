package com.craiovadata.android.sunshine.ui.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "webcams", indices = [Index(value = ["id"], unique = true)])
class WebcamEntry {

//    @PrimaryKey(autoGenerate = true)

    @PrimaryKey
    var id = "0"
    var statusActive = true
    var title = ""
    var inserted: Date = Date()
    var update: Date = Date()
    var previewUrl = ""

//    @Ignore
//    constructor()

    constructor(id: String,inserted: Date, statusActive: Boolean, title: String, update: Date, previewUrl: String) {
        this.id = id
        this.inserted = inserted
        this.statusActive = statusActive
        this.title = title
        this.update = update
        this.previewUrl = previewUrl
    }



}