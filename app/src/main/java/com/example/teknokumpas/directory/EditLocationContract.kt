package com.example.teknokumpas.directory

interface EditLocationContract {
    interface View {
        fun finishWithEditResult(position: Int, name: String, desc: String)
        fun finishWithDeleteResult(position: Int)
        fun showError(message: String)
        fun closeScreen()
    }

    interface Presenter {
        fun onUpdateClicked(position: Int, name: String, desc: String)
        fun onDeleteClicked(position: Int)
        fun onBackClicked()
    }
}