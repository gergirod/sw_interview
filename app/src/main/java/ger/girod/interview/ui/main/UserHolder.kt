package ger.girod.interview.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.model.UserModelView
import kotlinx.android.synthetic.main.users_row.view.*

class UserHolder(itemView : View , val onUsesRowClickListener: UserListAdapter.OnUserRowClickListener) : RecyclerView.ViewHolder(itemView) {

    fun populateData(userModel: UserModelView) {
        itemView.user_image.load(userModel.picture.thumbnail) {
            transformations(CircleCropTransformation())
        }
        itemView.user_name.text = userModel.nameModelView.getCompleteName()

        itemView.setOnClickListener {
            onUsesRowClickListener.onUsersRowClicked(userModel)
        }

        itemView.add_user.setOnClickListener {
            onUsesRowClickListener.onAddUserClick(userModel)
        }
    }
}