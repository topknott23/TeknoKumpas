package com.example.teknokumpas.directory

class EditLocationPresenter(private val view: EditLocationContract.View) : EditLocationContract.Presenter {

    override fun onUpdateClicked(position: Int, name: String, desc: String) {
        if (name.isNotEmpty() && desc.isNotEmpty()) {
            view.finishWithEditResult(position, name, desc)
        } else {
            view.showError("Fields cannot be empty")
        }
    }

    override fun onDeleteClicked(position: Int) {
        view.finishWithDeleteResult(position)
    }

    override fun onBackClicked() {
        view.closeScreen()
    }
}