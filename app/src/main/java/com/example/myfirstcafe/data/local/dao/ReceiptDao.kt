package com.example.myfirstcafe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myfirstcafe.data.local.entity.ReceiptEntity
import com.example.myfirstcafe.data.local.entity.ReceiptItemEntity
import com.example.myfirstcafe.data.local.relation.ReceiptWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM receipts ORDER BY createdAt DESC")
    fun observeReceipts(): Flow<List<ReceiptEntity>>

    @Transaction
    @Query("SELECT * FROM receipts ORDER BY createdAt DESC")
    fun observeReceiptsWithItems(): Flow<List<ReceiptWithItems>>

    @Transaction
    @Query("SELECT * FROM receipts WHERE id = :receiptId LIMIT 1")
    fun observeReceiptDetails(receiptId: Long): Flow<ReceiptWithItems?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceiptItems(items: List<ReceiptItemEntity>)

    @Transaction
    suspend fun insertReceiptWithItems(
        receipt: ReceiptEntity,
        items: List<ReceiptItemEntity>
    ): Long {
        val receiptId = insertReceipt(receipt)
        insertReceiptItems(items.map { it.copy(receiptId = receiptId) })
        return receiptId
    }

    @Query("DELETE FROM receipts WHERE id = :receiptId")
    suspend fun deleteReceipt(receiptId: Long)

}