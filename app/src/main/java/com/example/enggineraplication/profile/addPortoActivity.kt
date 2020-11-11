package com.example.enggineraplication.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ActivityAddExperienceBinding
import com.example.enggineraplication.databinding.ActivityAddPortoBinding
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.portofolio.portofolioApiService
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class addPortoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPortoBinding
    private lateinit var viewModel: AddPortoViewModel
    lateinit var sharedPref: PreferenceHelper

    companion object {

        private const val IMAGE_PICK_CODE = 1000;

        private const val PERMISSION_CODE = 1001;

        const val ADD_WORD_REQUEST_CODE = 9013;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_porto)
        sharedPref = PreferenceHelper(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_porto)
        viewModel = ViewModelProvider(this).get(AddPortoViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(portofolioApiService::class.java)
        if (service != null) {
            viewModel.setLoginService(service)
        }

        binding.btnPickImage.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);

                    requestPermissions(permissions, PERMISSION_CODE);
                } else {

                    pickImageFromGallery();
                }
            } else {

                pickImageFromGallery();
            }
        }

        subcribeLiveData()

    }

    private fun pickImageFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {

                    pickImageFromGallery()
                } else {

                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imageView.setImageURI(data?.data)

            val filePath = getPath(this, data?.data)
            val file = File(filePath)

            var img: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            var ad = sharedPref.getString(Constant.PREF_IDWORKERP)
            val id_worker = createPartFromString("$ad")
            val name_aplication = createPartFromString(binding.etNameapk.text.toString())
            val link_repository = createPartFromString(binding.etLinkrepo.text.toString())
            val type_repository = createPartFromString(binding.etTyperepo.text.toString())
            val type_portofolio = createPartFromString(binding.etTypeporto.text.toString())

            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData(
                    "image", file.name,
                    it1
                )
            }


            binding.btnSubmit.setOnClickListener {


                if (img != null) {
                    viewModel.postPortoApi(
                        id_worker,
                        name_aplication,
                        link_repository,
                        type_repository,
                        type_portofolio,
                        img
                    )

                }


            }

        }

    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            uri?.let { context.contentResolver.query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }

    fun subcribeLiveData(){
        viewModel.isLoadingProgressBarLiveData.observe(this , Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                setResult(Activity.RESULT_OK)
                finish()
            }
        })


    }


}