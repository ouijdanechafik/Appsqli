package com.example.appsqli.ui


import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsqli.R
import com.example.appsqli.adapter.UserAdapter
import com.example.appsqli.data.base.Status
import com.example.appsqli.data.model.User
import com.example.appsqli.databinding.ActivityMainBinding
import com.example.appsqli.util.Coroutines
import com.example.appsqli.util.UtilExtensions.myToast
import com.example.appsqli.util.UtilExtensions.openActivity

import dagger.hilt.android.AndroidEntryPoint


interface OnItemClickListener {
    fun onItemClick(item: User)
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels()
    private var isDelete = false
    private var page:Int? = null
    private lateinit var binding: ActivityMainBinding
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var imageUri: Uri? = null



    companion object {
        const val USER_DATA = "USER_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        init()




    }

    fun onItemClick(user: User) {

        openActivity(AddUserActivity::class.java) {
            putParcelable(MainActivity.USER_DATA, user)
        }

    }

    private fun init(){
        binding.listviewUsers.layoutManager = LinearLayoutManager(this)


        viewModel.getAllUser().observe(this) { it ->
            if (it.isEmpty() && !isDelete){ // if data local empty and delete state false fetch from server
                viewModel.getUser(id=null).observe(this){
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressCircular.visibility = View.VISIBLE
                            binding.listviewUsers.visibility = View.GONE
                        }
                        Status.SUCCESS -> {
                            binding.progressCircular.visibility = View.GONE
                            binding.listviewUsers.visibility = View.VISIBLE
                            Coroutines.main {
                                viewModel.addAllUser(it.data!!.users).also {
                                    print("success")
                                }
                            }

                            userAdapter = UserAdapter(this, it.data!!.users, object : UserAdapter.OnItemClickListener{
                               override fun onItemClick(user: User, type:String) {
                                   if(type.equals("EDIT")){
                                       openActivity(AddUserActivity::class.java) {
                                           putParcelable(USER_DATA, user)
                                       }
                                   }

                                   if(type.equals("DETAIL")){
                                       openActivity(DetailsActivity::class.java) {
                                           putParcelable(USER_DATA, user)
                                       }
                                   }

                                }

                            })


                            binding.listviewUsers.adapter = userAdapter
                        }
                        Status.FAIL -> {
                            binding.progressCircular.visibility = View.GONE

                            (it.message.toString())
                        }
                    }
                }
            }else{ // if data local not empty get from local dao
                binding.textDeleteAll.visibility = View.VISIBLE
                userAdapter = UserAdapter(this, it.toMutableList(), object : UserAdapter.OnItemClickListener{
                    override fun onItemClick(user: User, type:String) {
                        if(type.equals("EDIT")){
                            openActivity(AddUserActivity::class.java) {
                                putParcelable(USER_DATA, user)
                            }
                        }

                        if(type.equals("DETAIL")){
                            openActivity(DetailsActivity::class.java) {
                                putParcelable(USER_DATA, user)
                            }
                        }

                    }

                })
                binding.listviewUsers.adapter = userAdapter
            }

/*           listview_users.setOnScrollListener(object : AbsListView.OnScrollListener {
                override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}
                override fun onScroll(
                    view: AbsListView?,
                    firstVisibleItem: Int,
                    visibleItemCount: Int,
                    totalItemCount: Int,
                ) {
                    println("$firstVisibleItem---$visibleItemCount---$totalItemCount")
                    // add more items to listview when there are two items remaining at the scroll end.
                    if (visibleItemCount >= totalItemCount) {
                      //  loadMore();
                    }
                }
            })*/

        }


    }

/*    private fun loadMore() {
        Log.e("FETCH API", "Loading data ...")
        viewModel.getUser(id=null).observe(this){
            when (it.status) {
                Status.LOADING -> {
                    progress_circular.visibility = View.VISIBLE
                    listview_users.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    progress_circular.visibility = View.GONE
                    listview_users.visibility = View.VISIBLE
                    Coroutines.main {
                        viewModel.addAllUser(it.data!!.users).also {
                            print("success")
                        }
                    }

                    userAdapter.notifyDataSetChanged()

                }
                Status.FAIL -> {
                    progress_circular.visibility = View.GONE
                    myToast(it.message.toString())
                }
            }
        }
    }*/



    fun addUser(view: View) {
        openActivity(AddUserActivity::class.java)
    }

    fun deleteAll(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(getString(R.string.delete_message))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.delete_ok)) { dialog, id ->
                Coroutines.main {
                    viewModel.deleteAllUser()
                    myToast(getString(R.string.success_delete_all))
                    isDelete = true
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.delete_no)
            ) { _, _ -> }

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.delete_title))
        alert.show()
    }




}
