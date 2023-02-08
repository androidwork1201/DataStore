package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


/**
 * 創建PreferenceDataStore*/

/**數據儲存Key鍵常數*/
private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

/**創建DataStore實例*/
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)

/**數據儲存Key鍵*/
private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")

class SettingDataStore(context: Context) {


    /**
     *添加異常處理觀察讀取寫入 */
    val preferencesFlow: Flow<Boolean> = context.dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        //程式運行時默認使用
        it[IS_LINEAR_LAYOUT_MANAGER] ?: true

    }

    /**設定儲存內容Function(此例為儲存layout)*/
    suspend fun saveLayoutToPreferenceStore(isLinearLayoutManager: Boolean, context: Context) {
        context.dataStore.edit {
            it[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }

    }

}