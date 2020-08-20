package com.craiovadata.android.sunshine.ui.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "webcams", indices = [Index(value = ["id"], unique = true)])
class WebcamEntry(
    @PrimaryKey var id: String,
    var title: String,
    var updateDate: Date,
    var previewUrl: String
) {


}

/*

@Entity(tableName = "webcams", indices = [Index(value = ["id"], unique = true)])
class WebcamEntry {

//    @PrimaryKey(autoGenerate = true)

    @PrimaryKey
    var id = "0"
    var title = ""
    var updateDate: Date = Date()
    var previewUrl = ""

//    @Ignore
//    constructor()

    constructor(id: String, title: String, updateDate: Date, previewUrl: String) {
        this.id = id
        this.title = title
        this.updateDate = updateDate
        this.previewUrl = previewUrl
    }

}
*/
