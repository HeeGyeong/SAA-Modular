package com.example.data.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Movie Data Class
 * Response 에서 받아온 Data 중 Object 인 경우 해당 Entity 를 선언하여 데이터를 받는다.
 * MovieEntity data class 는 MovieResponse 에서 items 로 가져온 movies 데이터를 movie 라는 table 을 생성 후 해당 테이블에 저장시킨다.
 */
@Entity(tableName = "movie")
data class MovieEntity(
    @SerializedName("actor")
    val actor: String,

    @SerializedName("director")
    val director: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("link")
    val link: String,

    @SerializedName("pubDate")
    val pubDate: String,

    @SerializedName("subtitle")
    val subtitle: String,

    @PrimaryKey(autoGenerate = false)
    @SerializedName("title")
    val title: String,

    @SerializedName("userRating")
    val userRating: String
)
