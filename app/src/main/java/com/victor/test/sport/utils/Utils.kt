package com.victor.test.sport.utils

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.victor.test.sport.MainApplication

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

val Activity.app: MainApplication
    get() = application as MainApplication

fun trace(traceToShow: String) {
    System.out.println("SportMen Lagunages - Traces || $traceToShow")
}

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun getDpFromValue(context: Context, value: Int): Int =
        Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics))

fun ImageView.loadPhotoUrl(photoUrl: String) {
    Picasso.with(this.context).load(photoUrl).into(this)
}

fun getMockedSportMenResponse(): String {
    return "[  \n" +
            "   {  \n" +
            "      \"players\":[  \n" +
            "         {  \n" +
            "            \"image\":\"http://www.segundoasegundo.com/sas/wp-content/uploads/2014/01/cristiano-ronaldo.jpg\",\n" +
            "            \"surname\":\"Ronaldo\",\n" +
            "            \"name\":\"Cristiano\"\n" +
            "         },\n" +
            "         {  \n" +
            "            \"image\":\"http://1.bp.blogspot.com/-YGMAme-l8DQ/T6fJARFRgAI/AAAAAAAAAj8/0cB5NiT4R5Q/s320/lionel-messi-2011-2-5-18-11-2.jpg\",\n" +
            "            \"surname\":\"Messi\",\n" +
            "            \"name\":\"Lionel\"\n" +
            "         },\n" +
            "         {  \n" +
            "            \"image\":\"http://mibundesliga.com/wp-content/uploads/2013/03/ribery.jpg\",\n" +
            "            \"surname\":\"Ribéry\",\n" +
            "            \"name\":\"Franck\"\n" +
            "         },\n" +
            "         {  \n" +
            "            \"image\":\"http://www.lasportsanostra.com/wordpress/wp-content/uploads/2013/12/Suarez-slider-2.jpg\",\n" +
            "            \"surname\":\"Suárez\",\n" +
            "            \"name\":\"Luis\"\n" +
            "         },\n" +
            "         {  \n" +
            "            \"image\":\"http://www.abc.es/Media/201308/22/bale-gales--644x362.jpg\",\n" +
            "            \"surname\":\"Bale\",\n" +
            "            \"name\":\"Gareth\"\n" +
            "         },\n" +
            "         {  \n" +
            "            \"image\":\"http://gritasports.com.mx/wp-content/uploads/2013/01/Iker_Casillas5.jpg\",\n" +
            "            \"surname\":\"Casillas\",\n" +
            "            \"name\":\"Iker\"\n" +
            "         },\n" +
            "         {  \n" +
            "            \"image\":\"http://www.periodistadigital.com/imagenes/2014/01/08/xabi-alonso.jpg\",\n" +
            "            \"surname\":\"Alonso\",\n" +
            "            \"name\":\"Xabi\"\n" +
            "         }\n" +
            "      ],\n" +
            "      \"type\":\"LIST_A\",\n" +
            "      \"title\":\"Football\"\n" +
            "   }\n" +
            "]"
}