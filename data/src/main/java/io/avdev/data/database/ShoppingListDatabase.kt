package io.avdev.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.avdev.data.dao.ProductDao
import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.entity.ProductEntity
import io.avdev.data.entity.ShoppingListEntity

@Database(
    entities = [ShoppingListEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun getShoppingItemDao(): ProductDao
    abstract fun getShoppingListDao(): ShoppingListDao

    companion object {
        private var instance: ShoppingListDatabase? = null

        fun getDataBase(context: Context): ShoppingListDatabase {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): ShoppingListDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ShoppingListDatabase::class.java,
                "slplus.db"
            ).build()
        }
    }
}