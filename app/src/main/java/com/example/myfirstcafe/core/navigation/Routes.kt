package com.example.myfirstcafe.core.navigation

object Routes {
    const val SPLASH = "splash"
    const val MAIN = "main"

    const val RECEIPTS = "receipts"
    const val ADD_RECEIPT = "add_receipt"
    const val RECEIPT_DETAILS_DRAFT_ROUTE = "receipt_details_draft/{draft}"
    fun receiptDetailsDraft(draft: String) = "receipt_details_draft/$draft"
    const val RECEIPT_DETAILS_ROUTE = "receipt_details/{receiptId}"
    fun receiptDetails(receiptId: Long) = "receipt_details/$receiptId"

    const val PRICELIST = "pricelist"

    const val ADD_EDIT_ITEM_ROUTE = "add_edit_item/{itemId}"
    fun addEditItem(itemId: Long = -1L) = "add_edit_item/$itemId"
}