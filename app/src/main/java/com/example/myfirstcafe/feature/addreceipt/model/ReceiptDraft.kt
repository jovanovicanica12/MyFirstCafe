package com.example.myfirstcafe.feature.addreceipt.model

import android.net.Uri
import com.example.myfirstcafe.data.local.entity.MenuItemEntity

data class DraftLine(
    val id: Long,
    val name: String,
    val price: Double,
    val categoryId: Long,
    val qty: Int
)

data class ReceiptDraft(val lines: List<DraftLine>) {
    val subtotal: Double get() = lines.sumOf { it.price * it.qty }
}

fun MenuItemEntity.toDraftLine(qty: Int) = DraftLine(
    id = id,
    name = name,
    price = price,
    categoryId = categoryId,
    qty = qty
)

fun ReceiptDraft.toRouteArg(): String {
    val raw = lines.joinToString("|") {
        "${it.id},${it.name},${it.price},${it.categoryId},${it.qty}"
    }
    return Uri.encode(raw)
}

fun receiptDraftFromRouteArg(arg: String): ReceiptDraft {
    val raw = Uri.decode(arg).trim()
    if (raw.isBlank()) return ReceiptDraft(emptyList())

    val lines = raw.split("|").mapNotNull { token ->
        val parts = token.split(",")
        if (parts.size != 5) return@mapNotNull null
        val id = parts[0].toLongOrNull() ?: return@mapNotNull null
        val name = parts[1]
        val price = parts[2].toDoubleOrNull() ?: return@mapNotNull null
        val categoryId = parts[3].toLongOrNull() ?: return@mapNotNull null
        val qty = parts[4].toIntOrNull() ?: return@mapNotNull null
        DraftLine(id, name, price, categoryId, qty)
    }
    return ReceiptDraft(lines)
}