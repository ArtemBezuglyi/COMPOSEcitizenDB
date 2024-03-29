package ru.artbez.composecitizendb

import android.app.Application
import ru.artbez.composecitizendb.data.base.CitizenDatabase

class App : Application() {
    val database by lazy { CitizenDatabase.createDatabase(this) }

}