package ger.girod.interview.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ger.girod.interview.R
import ger.girod.interview.domain.model.UserModelView
import ger.girod.interview.ui.main.holder.UserHorizontalHolder

class UserHorizontalListAdapter() : RecyclerView.Adapter<UserHorizontalHolder>() {

    private var userList : ArrayList<UserModelView>  = ArrayList()

    fun setUserList(list : List<UserModelView>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMoreUserList(list: List<UserModelView>) {
        userList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHorizontalHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.users_horizontal_row, parent, false)
        return UserHorizontalHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserHorizontalHolder, position: Int) {
        holder.populateData(userList[position])
    }
}