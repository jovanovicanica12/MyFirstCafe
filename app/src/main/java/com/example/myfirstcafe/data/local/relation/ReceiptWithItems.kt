package com.example.myfirstcafe.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myfirstcafe.data.local.entity.ReceiptEntity
import com.example.myfirstcafe.data.local.entity.ReceiptItemEntity

data class ReceiptWithItems(
    @Embedded val receipt: ReceiptEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "receiptId"
    )
    val items: List<ReceiptItemEntity>
)