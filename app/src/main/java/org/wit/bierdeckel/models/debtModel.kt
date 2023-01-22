package org.wit.bierdeckel.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class debtModel(var schuldnerVorName: String? = null,
                     var schuldnerNachname: String? = null,
                     var schulden: Double?=null,
                     var schuldnerID: String?=null
                     ) : Parcelable
