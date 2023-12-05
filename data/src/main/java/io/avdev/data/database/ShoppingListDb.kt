package io.avdev.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.avdev.data.dao.ShoppingItemDao
import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.entity.ShoppingItemEntity
import io.avdev.data.entity.ShoppingListEntity

@Database(entities = [ShoppingListEntity::class, ShoppingItemEntity::class], version = 2)
abstract class ShoppingListDb : RoomDatabase() {

    abstract fun getShoppingItemDao(): ShoppingItemDao
    abstract fun getShoppingListDao(): ShoppingListDao

    companion object {
        private var instance: ShoppingListDb? = null

        fun getDataBase(context: Context): ShoppingListDb {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): ShoppingListDb {
            return Room.databaseBuilder(
                context.applicationContext,
                ShoppingListDb::class.java,
                "slplus.db"
            ).build()
        }
    }
}