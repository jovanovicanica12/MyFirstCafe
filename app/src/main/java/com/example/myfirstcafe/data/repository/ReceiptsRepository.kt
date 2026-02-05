package com.example.myfirstcafe.data.repository

import com.example.myfirstcafe.data.local.dao.ReceiptDao
import com.example.myfirstcafe.data.local.entity.ReceiptEntity
import com.example.myfirstcafe.data.local.entity.ReceiptItemEntity
import com.example.myfirstcafe.data.local.relation.ReceiptWithItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReceiptsRepository @Inject constructor(
    private val receiptDao: ReceiptDao
) {
    fun observeReceiptsWithItems(): Flow<List<ReceiptWithItems>> =
        receiptDao.observeReceiptsWithItems()

    fun observeReceiptDetails(receiptId: Long): Flow<ReceiptWithItems?> =
        receiptDao.observeReceiptDetails(receiptId)

    suspend fun createReceipt(receipt: ReceiptEntity, items: List<ReceiptItemEntity>): Long =
        receiptDao.insertReceiptWithItems(receipt, items)

    suspend fun deleteReceipt(receiptId: Long) {
        receiptDao.deleteReceipt(receiptId)
    }
}
