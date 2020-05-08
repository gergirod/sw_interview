package ger.girod.interview.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ger.girod.interview.ui.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ger.girod.interview.R
import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import com.google.android.material.snackbar.Snackbar
import ger.girod.interview.domain.model.UserModelView
import ger.girod.interview.ui.detail.SAVE_RESULT
import ger.girod.interview.ui.detail.UserDetailActivity
import ger.girod.interview.ui.main.adapter.SuggestionsAdapter
import ger.girod.interview.ui.main.adapter.UserHorizontalListAdapter
import ger.girod.interview.ui.main.adapter.UserListAdapter
import ger.girod.interview.ui.utils.ScreenState
import ger.girod.interview.ui.utils.hideKeyBoard
import ger.girod.interview.ui.utils.isIntenteSafe

const val USER_DETAIL_REQUEST = 1

class MainFragment : Fragment() , SuggestionsAdapter.OnSuggestionRowClickListener, UserListAdapter.OnUserRowClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val mainViewModel : MainViewModel by viewModel()
    private val userAdapter : UserListAdapter by lazy {
        UserListAdapter(this)
    }
    private lateinit var suggestionsAdapter: SuggestionsAdapter

    private val horizontalAdapter : UserHorizontalListAdapter by lazy {
        UserHorizontalListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initViewModelDataObserver()
        initUsersList()
        initFavoritesUsersList()
        setSearchView()
        mainViewModel.getInitialUserList()
        mainViewModel.getFavoritesUserList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == USER_DETAIL_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data!!.getBooleanExtra(SAVE_RESULT, false)) {
                mainViewModel.getFavoritesUserList()
            }
        }
    }

    private fun setSearchView() {
        search_view.threshold = 1
        suggestionsAdapter =
            SuggestionsAdapter(activity!!, this)
        search_view.setAdapter(suggestionsAdapter)
        search_view.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                suggestionsAdapter.setList(mainViewModel.searchUsers(text.toString()))
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        search_view.onItemClickListener = null
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun initViewModelDataObserver() {
        mainViewModel.userInitialData.observe(viewLifecycleOwner, Observer {
            userAdapter.setUserList(it)
        })

        mainViewModel.userLoadMoreData.observe(viewLifecycleOwner, Observer {
            userAdapter.loadMoreUserList(it)
        })

        mainViewModel.favoritesUserListData.observe(viewLifecycleOwner, Observer {
            populateFavoritesUserList(it)
        })

        mainViewModel.errorData.observe(viewLifecycleOwner, Observer {
            displayErrorMessage(it)
        })

        mainViewModel.screenStateData.observe(viewLifecycleOwner, Observer {
            when(it) {
                ScreenState.LoadingFinish -> progress_bar.visibility = View.GONE
                ScreenState.Loading -> progress_bar.visibility = View.VISIBLE
            }
        })
    }

    private fun populateFavoritesUserList(users: List<UserModelView>) {
        if(users.isNotEmpty()) {
            favorites_title.visibility = View.VISIBLE
            favorites_user_list.visibility = View.VISIBLE
            horizontalAdapter.setUserList(users)
        }
    }

    private fun initUsersList() {

        val linearLayoutManager = GridLayoutManager(activity, 2)
        users_list.setHasFixedSize(true)
        users_list.layoutManager = linearLayoutManager
        users_list.adapter = userAdapter

        val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
               mainViewModel.getLoadMoreList(page)
            }
        }

        users_list.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun initFavoritesUsersList() {

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        favorites_user_list.setHasFixedSize(true)
        favorites_user_list.layoutManager = linearLayoutManager
        favorites_user_list.adapter = horizontalAdapter
    }

    override fun onSuggestionRowClicked(user: UserModelView) {
        search_view.setText("")
        hideKeyBoard(activity)
        startActivityForResult(UserDetailActivity.getIntent(activity!!, user), USER_DETAIL_REQUEST)

    }

    override fun onUsersRowClicked(user: UserModelView) {
        startActivityForResult(UserDetailActivity.getIntent(activity!!, user), USER_DETAIL_REQUEST)
    }

    override fun onAddUserClick(user: UserModelView) {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.EMAIL, user.email)
            putExtra(ContactsContract.Intents.Insert.PHONE, user.cell)
            putExtra(ContactsContract.Intents.Insert.NAME, user.nameModelView.getCompleteName())
        }

        if(isIntenteSafe(activity, intent)) {
            startActivity(intent)
        }else {
            displayErrorMessage(getString(R.string.not_available))
        }
    }

    private fun displayErrorMessage(errorMessage : String) {
        Snackbar.make(main, errorMessage, Snackbar.LENGTH_LONG).apply {
            setAction(R.string.dismiss) {
                dismiss()
            }
        }.show()
    }

}
