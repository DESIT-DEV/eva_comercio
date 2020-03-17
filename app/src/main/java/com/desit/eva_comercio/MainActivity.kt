package com.desit.eva_comercio

import android.accounts.Account
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.desit.eva_comercio.Common.Common
import com.desit.eva_comercio.Model.UserModel
import com.desit.eva_comercio.Remote.ICloundFuncionts
import com.desit.eva_comercio.Remote.RetrofitCloudClient
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var listener:FirebaseAuth.AuthStateListener
    private lateinit var dialog: AlertDialog
    private val compositeDisposable=CompositeDisposable()
    private lateinit var cloudFunctions: ICloundFuncionts

    private lateinit var userRef:DatabaseReference
    private var providers:List<AuthUI.IdpConfig> ? = null

    companion object{
        private val APP_QUEST_CODE=7171
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        if (listener!=null)
            firebaseAuth.removeAuthStateListener(listener)
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        providers= Arrays.asList<AuthUI.IdpConfig>(AuthUI.IdpConfig.PhoneBuilder().build())

        providers= Arrays.asList<AuthUI.IdpConfig>(AuthUI.IdpConfig.GoogleBuilder().build())
        userRef= FirebaseDatabase.getInstance().getReference(Common.USER_REFENCE)
        firebaseAuth= FirebaseAuth.getInstance()
        dialog= SpotsDialog.Builder().setContext(this).setCancelable(false).build()
        cloudFunctions=RetrofitCloudClient.getInstance().create(ICloundFuncionts::class.java)
        listener=FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user=firebaseAuth.currentUser

            if (user!=null)
                {
                    //simon si ya se logueo
                    checkUserFromFirebase(user!!)

                }
            else{

                    phoneLogin()

            }
        }
    }

    private fun checkUserFromFirebase(user:FirebaseUser) {

        dialog!!.show()
        userRef!!.child(user.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@MainActivity,""+p0!!.message,Toast.LENGTH_SHORT).show()

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists())
                    {

                        val userModel=p0.getValue(UserModel::class.java)
                        goToHomeActivity(userModel)
                    }
                    else
                    {
                        showRegisterDialog(user)
                    }
                    dialog.dismiss()
                }

            })


    }

    private fun showRegisterDialog(user:FirebaseUser) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("REGISTRO")
        builder.setMessage("Porfavor llene la informacion")
        val itemView=LayoutInflater.from(this@MainActivity)
            .inflate(R.layout.layoutregister,null)

        val edt_name=itemView.findViewById<EditText>(R.id.edt_name)
        val edt_apellido=itemView.findViewById<EditText>(R.id.edt_apellido)
        val edt_phone=itemView.findViewById<EditText>(R.id.edt_phone)

        edt_phone.setText(user.phoneNumber)
        builder.setView(itemView)
        builder.setNegativeButton("CANCELAR"){dialogInterface, i ->dialogInterface.dismiss()  }
        builder.setPositiveButton("REGISTRAR"){dialogInterface, i ->
            if(TextUtils.isDigitsOnly(edt_name.text.toString())){
                Toast.makeText(this@MainActivity,"rellena los campos no los dejes en blanco",Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            else if(TextUtils.isDigitsOnly(edt_apellido.text.toString())){
                Toast.makeText(this@MainActivity,"rellena los campos no los dejes en blanco",Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
            else if(TextUtils.isDigitsOnly(edt_phone.text.toString())){
                Toast.makeText(this@MainActivity,"rellena los campos no los dejes en blanco",Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
           val userModel=UserModel()
            userModel.uid=user.uid
            userModel.Name=edt_name.text.toString()
            userModel.apellido=edt_apellido.text.toString()
            userModel.phone=edt_phone.text.toString()
            userRef.child(user.uid)
                .setValue(userModel)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        dialogInterface.dismiss()
                        Toast.makeText(this@MainActivity,"Registro con exito",Toast.LENGTH_SHORT).show()

                        goToHomeActivity(userModel)
                    }
                }
        }

        val dialog=builder.create()
        dialog.show()
    }

    private fun goToHomeActivity(userModel: UserModel?) {
            Common.CurrentUser=userModel!!
        startActivity(Intent(this@MainActivity,HomeActivity::class.java))
        finish()
    }

    private fun phoneLogin() {


        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers!!).build(), APP_QUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== APP_QUEST_CODE)
        {
            val response=IdpResponse.fromResultIntent(data)
            if (resultCode== Activity.RESULT_OK){

                val user=FirebaseAuth.getInstance().currentUser
            }else{
                Toast.makeText(this@MainActivity,"Algo fallo al iniciar sesion",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
