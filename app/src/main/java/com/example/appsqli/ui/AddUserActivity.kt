package com.example.appsqli.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.appsqli.R
import com.example.appsqli.data.model.User
import com.example.appsqli.databinding.ActivityAddUserBinding
import com.example.appsqli.databinding.ActivityDetailsBinding
import com.example.appsqli.util.Coroutines
import com.example.appsqli.util.UtilExtensions.myToast
import com.example.appsqli.util.UtilExtensions.setTextEditable
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_add_user.*


@AndroidEntryPoint
class AddUserActivity : AppCompatActivity() {

    private val viewModel by viewModels<UserViewModel>()
    private var user: User? = null
    private var _binding: ActivityAddUserBinding? = null
    private val binding get() = _binding!!
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var icontext: Context;
    private  val STORAGE_PERMISSION_CODE = 100
    private  val TAG = "PERMISSION_TAG"
    lateinit var circleImageView: CircleImageView;




    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)





        initToolbar()
        user = intent.extras?.getParcelable(MainActivity.USER_DATA)
        init()
        image_user.setOnClickListener {

            if (checkPermission()){
                Log.d(TAG, "onCreate: Permission already granted, create folder")
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, pickImage)
            }
            else{
                Log.d(TAG, "onCreate: Permission was not granted, request")
                requestPermission()
            }
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        circleImageView = findViewById(R.id.image_user)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            circleImageView.setImageURI(imageUri)
        }
    }



    private fun initToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    @SuppressLint("SetTextI18n")
    private fun init(){

        /*    binding.actionBarInfo.actionbarBack.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            })*/
        circleImageView = findViewById<CircleImageView>(R.id.image_user)


        if (user != null){

            /*           text_title.text = getString(R.string.text_update_user)
                       edit_name.setTextEditable(user?.first_name ?: "")
                       edit_email.setTextEditable(user?.email ?: "")
                       edit_phone.setTextEditable(user?.phone ?: "")
                       edit_prenom.setTextEditable(user?.last_name ?: "")
                       edit_ville.setTextEditable(user?.ville ?: "")
                       edit_portable.setTextEditable(user?.portable ?: "")
                       edit_description.setTextEditable(user?.description ?: "")
                       edit_fonction.setTextEditable(user?.fonction ?: "")
                       edit_pays.setTextEditable(user?.pays ?: "")
                       */
            /*     var uri =
                     Uri.parse(user!!.avatar)
                 circleImageView.setImageURI(uri)

     */

            binding.actionBarInfo.textTitle.text = getString(R.string.text_update_user)
            binding.editName.setTextEditable(user?.first_name ?: "")
            binding.editEmail.setTextEditable(user?.email ?: "")
            binding.editPhone.setTextEditable(user?.phone ?: "")
            binding.editPrenom.setTextEditable(user?.last_name ?: "")
            binding.editVille.setTextEditable(user?.ville ?: "")
            binding.editPortable.setTextEditable(user?.portable ?: "")
            binding.editDescription.setTextEditable(user?.description ?: "")
            binding.editFonction.setTextEditable(user?.fonction ?: "")
            binding.editPays.setTextEditable(user?.pays ?: "")
            val avatarUrl = user?.avatar
            if (avatarUrl != null) {
                Glide.with(this).load(user?.avatar).into(circleImageView)


                /*val uri = Uri.parse(avatarUrl)
                circleImageView.setImageURI(uri)*/
            }

            binding.btnDelete.visibility = View.VISIBLE
        }else{
            // binding. = getString(R.string.text_add_user)
        }


        /*       binding.imageUser.setOnClickListener {
                   // Check if permission is not granted
                   if (ContextCompat.checkSelfPermission(
                           requireContext(),
                           Manifest.permission.READ_EXTERNAL_STORAGE
                       ) == PackageManager.PERMISSION_DENIED
                   ) {
                       // Ask permission
                       requestPermissions(
                           permissions, 1
                       )
                   } else {
                       // Permission is already granted, open image gallery
                       openGallery()
                   }
               }*/
    }

    private fun saveData() {


        val id = if (user != null) user?.id else null
        val name = binding.editName.text.toString().trim()
        val prenom = binding.editPrenom.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val ville = binding.editVille.text.toString().trim()
        val portable = binding.editPortable.text.toString().trim()
        val description  = binding.editDescription.text.toString().trim()
        val fonction  = binding.editFonction.text.toString().trim()
        val pays  = binding.editPays.text.toString().trim()

        val avatar = imageUri.toString()
//        var uri =
//            Uri.parse(user!!.avatar)
//        circleImageView.setImageURI(uri)
        if (user != null) {
            var uri = Uri.parse(user!!.avatar)
            circleImageView.setImageURI(uri)
        }






        /* val name = edit_name.text.toString().trim()
         val email = edit_email.text.toString().trim()
         val phone = edit_phone.text.toString().trim()
         val prenom = edit_prenom.text.toString().trim()
         val ville = edit_ville.text.toString().trim()
         val pays = edit_pays.text.toString().trim()
         val portable = edit_portable.text.toString().trim()
         val description = edit_description.text.toString().trim()
         val fonction = edit_fonction.text.toString().trim()
         val avatar = imageUri.toString()*/
        /*var uri =
             Uri.parse(user!!.avatar)
         circleImageView.setImageURI(uri)*/


        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val user = User(id = id, first_name = name, email = email, mobile = phone,
            last_name =prenom, pays = pays, description = description,
            fonction = fonction,ville=ville, portable = portable, avatar = avatar.toString(),
        )
        Coroutines.main {
            if (id != null) { // for update user
                viewModel.updateUser(user).also {
                    myToast(getString(R.string.success_update))
                    finish()
                }
            } else { //for insert note
                viewModel.addUser(user).also {
                    myToast(getString(R.string.success_save))
                    finish()
                }
            }
        }
    }

    fun submit(view: View) {
        saveData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun delete(view: View) {
        Coroutines.main {
            viewModel.deleteUser(user!!)
            myToast(getString(R.string.success_delete))
            finish()
        }
    }



    private fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            try {
                Log.d(TAG, "requestPermission: try")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)
            }
            catch (e: Exception){
                Log.e(TAG, "requestPermission: ", e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        }
        else{
            //Android is below 11(R)
            ActivityCompat.requestPermissions(
                icontext as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private val storageActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d(TAG, "storageActivityResultLauncher: ")
        //here we will handle the result of our intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            if (Environment.isExternalStorageManager()){
                //Manage External Storage Permission is granted
                Log.d(TAG, "storageActivityResultLauncher: Manage External Storage Permission is granted")

            }
            else{
                //Manage External Storage Permission is denied....
                Log.d(TAG, "storageActivityResultLauncher: Manage External Storage Permission is denied....")
                //toast("Manage External Storage Permission is denied....")
            }
        }
        else{
            //Android is below 11(R)
        }
    }

    private fun checkPermission(): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            Environment.isExternalStorageManager()
        }
        else{
            //Android is below 11(R)
            val write = ContextCompat.checkSelfPermission(icontext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(icontext, Manifest.permission.READ_EXTERNAL_STORAGE)
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.isNotEmpty()){
                //check each permission if granted or not
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (write && read){
                    //External Storage Permission granted
                    Log.d(TAG, "onRequestPermissionsResult: External Storage Permission granted")

                }
                else{
                    //External Storage Permission denied...
                    Log.d(TAG, "onRequestPermissionsResult: External Storage Permission denied...")

                }
            }
        }
    }

}





