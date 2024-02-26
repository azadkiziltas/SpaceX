package com.redstoneapps.spacex.data.model


import com.google.gson.annotations.SerializedName

data class LaunchModel(
    @SerializedName("auto_update")
    val autoUpdate: Boolean,
    @SerializedName("capsules")
    val capsules: List<String>,
    @SerializedName("cores")
    val cores: List<Core>,
    @SerializedName("crew")
    val crew: List<String>,
    @SerializedName("date_local")
    val dateLocal: String,
    @SerializedName("date_precision")
    val datePrecision: String,
    @SerializedName("date_unix")
    val dateUnix: Int,
    @SerializedName("date_utc")
    val dateUtc: String,
    @SerializedName("details")
    val details: Any,
    @SerializedName("failures")
    val failures: List<Any>,
    @SerializedName("fairings")
    val fairings: Any,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("launch_library_id")
    val launchLibraryİd: String,
    @SerializedName("launchpad")
    val launchpad: String,
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("net")
    val net: Boolean,
    @SerializedName("payloads")
    val payloads: List<String>,
    @SerializedName("rocket")
    val rocket: String,
    @SerializedName("ships")
    val ships: List<Any>,
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: Any,
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: Any,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("tbd")
    val tbd: Boolean,
    @SerializedName("upcoming")
    val upcoming: Boolean,
    @SerializedName("window")
    val window: Any,
) {
    data class Core(
        @SerializedName("core")
        val core: String,
        @SerializedName("flight")
        val flight: Int,
        @SerializedName("gridfins")
        val gridfins: Boolean,
        @SerializedName("landing_attempt")
        val landingAttempt: Boolean,
        @SerializedName("landing_success")
        val landingSuccess: Boolean,
        @SerializedName("landing_type")
        val landingType: String,
        @SerializedName("landpad")
        val landpad: String,
        @SerializedName("legs")
        val legs: Boolean,
        @SerializedName("reused")
        val reused: Boolean,
    )

    data class Links(
        @SerializedName("article")
        val article: Any,
        @SerializedName("flickr")
        val flickr: Flickr,
        @SerializedName("patch")
        val patch: Patch,
        @SerializedName("presskit")
        val presskit: Any,
        @SerializedName("reddit")
        val reddit: Reddit,
        @SerializedName("webcast")
        val webcast: String,
        @SerializedName("wikipedia")
        val wikipedia: String,
        @SerializedName("youtube_id")
        val youtubeİd: String,
    ) {
        data class Flickr(
            @SerializedName("original")
            val original: List<Any>,
            @SerializedName("small")
            val small: List<Any>,
        )

        data class Patch(
            @SerializedName("large")
            val large: String,
            @SerializedName("small")
            val small: String,
        )

        data class Reddit(
            @SerializedName("campaign")
            val campaign: Any,
            @SerializedName("launch")
            val launch: String,
            @SerializedName("media")
            val media: Any,
            @SerializedName("recovery")
            val recovery: Any,
        )
    }
}