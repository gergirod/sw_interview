package ger.girod.interview.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import ger.girod.interview.R
import ger.girod.interview.domain.model.UserModelView
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SAVE_RESULT = "is_save"
class DetailFragment : Fragment() {

    companion object {
        fun newInstance(userModelView: UserModelView) : DetailFragment {
            val args : Bundle = Bundle().apply {
                putParcelable(USER_EXTRA, userModelView)
            }
            return DetailFragment().apply {
                arguments = args
            }
        }
    }

    private var isSave = false

    private val detailViewModel : DetailViewModel by viewModel()
    private lateinit var userModelView: UserModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.setResult(Activity.RESULT_OK, setResultIntent())
                activity?.finish()
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userModelView = arguments?.get(USER_EXTRA) as UserModelView

        setupToolbar()
        observeViewModelData()
        populateUserData()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun observeViewModelData() {

        detailViewModel.saveUserData.observe(viewLifecycleOwner, Observer {
            if(it>0) {
                Toast.makeText(activity, getString(R.string.user_successfully_saved), Toast.LENGTH_LONG).show()
                isSave = true
            }
        })

        detailViewModel.errorData.observe(viewLifecycleOwner, Observer {
            displayErrorMessage(it)
        })
    }

    fun setResultIntent() : Intent {
        return Intent().apply {
            putExtra(SAVE_RESULT, isSave)
        }
    }

    fun populateUserData() {

        user_image_big.load(userModelView.picture.large) {
            transformations(CircleCropTransformation())
        }
        user_name_detail.text = userModelView.nameModelView.getCompleteName()
        user_email_detail.text = String.format(resources.getString(R.string.email), userModelView.email)
        user_phone_detail.text = String.format(resources.getString(R.string.phone_number), userModelView.phone)
        user_cell_phone_detail.text = String.format(resources.getString(R.string.cell_phone_number), userModelView.cell)
        user_nationality_detail.text = String.format(resources.getString(R.string.nationality), userModelView.nat)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_save -> {
                detailViewModel.saveUserToFavorites(userModelView)
            }
            android.R.id.home -> {
                activity?.setResult(Activity.RESULT_OK, setResultIntent())
                activity?.finish()
            }
        }
        return true
    }

    private fun displayErrorMessage(errorMessage : String) {
        Snackbar.make(main, errorMessage, Snackbar.LENGTH_LONG).apply {
            setAction(R.string.dismiss) {
                dismiss()
            }
        }.show()
    }
}