package org.wit.bierdeckel.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class debtModel(var schuldnerVorName: String ="",
                     var schuldnerNachname: String ="",
                     var schulden: Double,

                     ) : Parcelable
