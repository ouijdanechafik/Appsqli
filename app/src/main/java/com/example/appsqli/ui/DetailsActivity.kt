package com.example.appsqli.ui


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.appsqli.R
import com.example.appsqli.data.model.User
import com.example.appsqli.databinding.ActivityDetailsBinding
import de.hdodenhof.circleimageview.CircleImageView
class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null;
    private val binding get() = _binding!!;


    private var imageUri: Uri? = null
    private var user: User? = null
    private val pickImage = 100

    private  val TAG = "PERMISSION_TAG"
    lateinit var circleImageView: CircleImageView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Utilisation de View Binding pour lier la vue à l'activité
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération de l'objet User envoyé depuis l'activité précédente
        val user = intent.getParcelableExtra<User>("user")
        init()

         fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK && requestCode == pickImage) {
                imageUri = data?.data

                circleImageView.setImageURI(imageUri)
            }
        }

    }

    fun init(){
        circleImageView = findViewById<CircleImageView>(R.id.camera_user)

        binding.actionBarInfo.actionbarBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        var user = intent.getParcelableExtra<User>("user")
        Log.d("DetailsActivity", "User: $user")
        user = intent.extras?.getParcelable(MainActivity.USER_DATA)


        if (user != null) {
            Log.d("DetailsActivity", "User: $user")

            binding.actionBarInfo.textTitle.text = user?.first_name + " " + user?.last_name
            binding.textViewNomAndPrenom.text = user?.first_name + " " + user?.last_name
            binding.textViewFonction.text = user?.fonction
            binding.textViewDescription.text = user?.description
            binding.textViewLocation.text = user?.ville
            binding.textViewPays.text = user?.pays
            binding.telephone.text = user?.mobile
            binding.portable.text = user?.portable
            binding.email.text = user?.email
            Glide.with(applicationContext).load(user?.avatar).into(binding.cameraUser)
            val avatarUrl = user?.avatar
            if (avatarUrl != null) {
                Glide.with(this).load(user?.avatar).into(circleImageView)

            }



            binding.email.setOnClickListener {
                val email = binding.email.text.toString()
                val subject = "Sujet de l'e-mail"
                val body = "Corps de l'e-mail"
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("email:$email")
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, body)
                val chooser =
                    Intent.createChooser(intent, "Choisir une application d'envoi de mail")
                startActivity(chooser)
            }


        }}}









