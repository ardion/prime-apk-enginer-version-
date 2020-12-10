package com.example.enggineraplication.postprofile

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
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.enggineraplication.Constant
import com.example.enggineraplication.PreferenceHelper
import com.example.enggineraplication.R
import com.example.enggineraplication.databinding.ActivityPostProfileBinding
import com.example.enggineraplication.login.ApiClient
import com.example.enggineraplication.parentActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class postProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostProfileBinding
    private lateinit var viewModel: postProfileViewModel
    lateinit var sharedPref: PreferenceHelper

    companion object {
        private const val IMAGE_PICK_CODE = 2000;
        private const val PERMISSION_CODE = 2001;
        const val idd_company = "anjay"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceHelper(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_profile)
        viewModel = ViewModelProvider(this).get(postProfileViewModel::class.java)
        viewModel.getSharedPreference(this)
        val service = ApiClient.getApiClient(this)?.create(postProfileApiService::class.java)
        if (service != null) {
            viewModel.setLoginService(service)
        }

        Log.d("testttttttp", binding.etJobdeskp.text.toString())

        binding.updatefoto.setOnClickListener {
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


        binding.tvNamedetailp.text = sharedPref.getString(Constant.NAME_USER)
        Log.d("idpc", sharedPref.getString(Constant.PREF_IDWORKERP).toString())
    }


    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_form) && sharedPref.getBoolean(Constant.pref_is_login)) {
            startActivity(Intent(this, parentActivity::class.java))
            finish()
        }
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

            binding.btnsubmitp.setOnClickListener {
                if (img != null) {
                    Log.d("testttttttp", binding.etJobdeskp.text.toString())
                    val a = sharedPref.getString(Constant.PREF_ID)
                    val id_user = createPartFromString("$a")
                    val jobdesk = createPartFromString(binding.etJobdeskp.text.toString())
                    Log.d("hmmm", binding.etJobdeskp.text.toString())
                    val domicile = createPartFromString(binding.etDomicilep.text.toString())
                    val workplace = createPartFromString(binding.etWorkplacep.text.toString())
                    val job_status = createPartFromString(binding.etJobstatusp.text.toString())
                    val instagram = createPartFromString(binding.etInstagramp.text.toString())
                    val github = createPartFromString(binding.etGithubp.text.toString())
                    val gitlab = createPartFromString(binding.etGitlabp.text.toString())
                    val description_personal = createPartFromString(binding.etDescp.text.toString())
                    viewModel.postProfileApi(
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
                    sharedPref.put(Constant.pref_is_form, true)
                    Log.d("idpsubmit", sharedPref.getString(Constant.PREF_IDWORKERP).toString())
                    val intent = Intent(this, parentActivity::class.java)
                    startActivity(intent)
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

}
