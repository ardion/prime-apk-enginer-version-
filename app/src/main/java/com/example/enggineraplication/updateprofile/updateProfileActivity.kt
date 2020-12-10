package com.example.enggineraplication.updateprofile

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

import com.example.enggineraplication.databinding.ActivityUpdateProfileBinding
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.parentActivity
import com.example.enggineraplication.postprofile.postProfileApiService
import com.example.enggineraplication.postprofile.postProfileViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class updateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var viewModel: updateProfileViewModel
    lateinit var sharedPref: PreferenceHelper

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;
        const val ADD_WORD_REQUEST_CODE = 9013;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceHelper(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile)
        viewModel = ViewModelProvider(this).get(updateProfileViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(updateProfileApiService::class.java)
        if (service != null) {
            viewModel.setLoginService(service)
        }

        binding.tvNamedetail.text = sharedPref.getString(Constant.NAME_USER)

        binding.updateprofilepoto.setOnClickListener {
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
            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData(
                    "image", file.name,
                    it1
                )
            }

            binding.btnsubmit.setOnClickListener {
                if (img != null) {
                    val a = sharedPref.getString(Constant.PREF_ID)
                    val id_user = createPartFromString("$a")
                    val jobdesk = createPartFromString(binding.etJobdesku.text.toString())
                    val domicile = createPartFromString(binding.etDomicileu.text.toString())
                    Log.d("jobdeskcoba", binding.etDomicileu.text.toString())
                    val workplace = createPartFromString(binding.etWorkplaceu.text.toString())
                    val job_status = createPartFromString(binding.etJobstatusu.text.toString())
                    val instagram = createPartFromString(binding.etInstagramu.text.toString())
                    val github = createPartFromString(binding.etGithubu.text.toString())
                    val gitlab = createPartFromString(binding.etGitlabu.text.toString())
                    val description_personal = createPartFromString(binding.etDescu.text.toString())

                    sharedPref.getString(Constant.PREF_IDWORKERP)?.let { it1 ->
                        viewModel.updateprofileworker(
                            it1,
                            id_user,
                            jobdesk,
                            domicile,
                            workplace,
                            job_status,
                            instagram,
                            github,
                            gitlab,
                            description_personal,
                            img
                        )
                    }
                    Log.d("tesprofile", sharedPref.getString(Constant.PREF_IDCOMPANY).toString())

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

    fun subcribeLiveData() {
        viewModel.isLoadingProgressBarLiveData.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                setResult(RESULT_OK)
                finish()
            }
        })
    }
}