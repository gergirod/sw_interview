package ger.girod.interview.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import ger.girod.interview.R
import ger.girod.interview.domain.model.UserModelView
import kotlinx.android.synthetic.main.suggestion_row.view.*

class SuggestionsAdapter(context: Context, private val onSuggestionRowClickListener: OnSuggestionRowClickListener)
    : ArrayAdapter<UserModelView>(context, 0) {

    var list : ArrayList<UserModelView> = ArrayList()


    fun setList(userList : List<UserModelView>) {
        list.clear()
        list.addAll(userList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view : View
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.suggestion_row, parent, false)
        }else{
            view = convertView
        }

        val userModel : UserModelView = list[position]

        view.user_image.load(userModel.picture.thumbnail) {
            transformations(CircleCropTransformation())
        }
        view.user_name.text = userModel.nameModelView.getCompleteName()

        view.setOnClickListener {
            onSuggestionRowClickListener.onSuggestionRowClicked(userModel)
        }


        return  view
    }


    override fun getItem(position: Int): UserModelView {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    interface OnSuggestionRowClickListener {

        fun onSuggestionRowClicked(user : UserModelView)

    }

}