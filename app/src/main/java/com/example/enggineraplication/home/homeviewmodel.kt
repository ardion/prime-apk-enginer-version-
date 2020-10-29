//package com.example.enggineraplication.home
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.*
//import kotlin.coroutines.CoroutineContext
//
//class HomeViewModel: ViewModel(), CoroutineScope {
//    override val coroutineContext: CoroutineContext
//        get() = Job() + Dispatchers.Main
//
//    private lateinit var service: WorkerService
//    val isEmptyAndroid = MutableLiveData<Boolean>()
//    val isEmptyWeb = MutableLiveData<Boolean>()
//    val isProgressAndroid = MutableLiveData<Boolean>()
//    val isProgressWeb = MutableLiveData<Boolean>()
//    val workerAndroidLiveData = MutableLiveData<List<WorkerModel>>()
//    val workerWebLiveData = MutableLiveData<List<WorkerModel>>()
//
//
//    fun setService(service: WorkerService) {
//        this.service = service
//    }
//
//    fun getAndroid(){
//        launch {
//            isProgressAndroid.value = true
//            val response = withContext(Job()+Dispatchers.IO){
//                try {
//                    service.getAllWorker("Android")
//                } catch (e: Throwable) {
//                    e.printStackTrace()
//                }
//            }
//            if (response is GetWorkerResponse){
//                isEmptyAndroid.value = response.success
//                val list = response.data.map {
//                    WorkerModel(it.idWorker, it.image.orEmpty(),it.name.orEmpty(), it.title.orEmpty(), it.skill.orEmpty())
//                }
//                workerAndroidLiveData.value = list
//            }
//            isProgressAndroid.value = false
//        }
//    }
//    fun getWeb(){
//        launch {
//            isProgressWeb.value = true
//            val response = withContext(Job()+Dispatchers.IO){
//                try {
//                    service.getAllWorker("Web")
//                } catch (e: Throwable){
//                    e.printStackTrace()
//                }
//            }
//            if (response is GetWorkerResponse) {
//                isEmptyWeb.value = response.success
//                val list = response.data.map {
//                    WorkerModel(it.idWorker, it.image.orEmpty(),it.name.orEmpty(), it.title.orEmpty(), it.skill.orEmpty())
//                }
//                workerWebLiveData.value = list
//            }
//            isProgressWeb.value = false
//        }
//    }
//}
//
//
//class HomeFragment : Fragment() {
//
//    private lateinit var binding : FragmentHomeBinding
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var recyclerViewAndroid : RecyclerWorkerAdapter
//    private lateinit var recyclerViewWeb : RecyclerWorkerAdapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//
//        val service = ApiClient.getApiClientToken(activity as AppCompatActivity)?.create(WorkerService::class.java)
//        viewModel = ViewModelProvider(activity as AppCompatActivity).get(HomeViewModel::class.java)
//        if (service != null) {
//            viewModel.setService(service)
//        }
//        viewModel.getAndroid()
//        viewModel.getWeb()
//        subscribeLiveData()
//
//        setUpRecyclerView()
//        binding.ivNotify.setOnClickListener { startActivity(Intent(activity, NotifyActivity:: class.java)) }
//        return binding.root
//    }
//
//    private fun subscribeLiveData(){
//        viewModel.workerWebLiveData.observe(this, Observer {
//            (binding.rvWeb.adapter as RecyclerWorkerAdapter).addList(it)
//        })
//        viewModel.workerAndroidLiveData.observe(this, Observer {
//            (binding.rvAndroid.adapter as RecyclerWorkerAdapter).addList(it)
//        })
//        viewModel.isEmptyAndroid.observe(this, Observer {
//            if(it){
//                binding.tvEmptyWorkerAndroid.visibility = View.GONE
//                binding.rvAndroid.visibility = View.VISIBLE
//            } else {
//                binding.tvEmptyWorkerAndroid.visibility = View.VISIBLE
//                binding.rvAndroid.visibility = View.GONE
//            }
//        })
//        viewModel.isEmptyWeb.observe(this, Observer {
//            if(it){
//                binding.tvEmptyWorkerWeb.visibility = View.GONE
//                binding.rvWeb.visibility = View.VISIBLE
//            } else {
//                binding.tvEmptyWorkerWeb.visibility = View.VISIBLE
//                binding.rvWeb.visibility = View.GONE
//            }
//        })
//        viewModel.isProgressAndroid.observe(this, Observer {
//            if(it){
//                binding.progressBarWorkerAndroid.visibility = View.VISIBLE
//            } else {
//                binding.progressBarWorkerAndroid.visibility = View.GONE
//
//            }
//        })
//        viewModel.isProgressWeb.observe(this, Observer {
//            if(it){
//                binding.progressBarWorkerWeb.visibility = View.VISIBLE
//            } else {
//                binding.progressBarWorkerWeb.visibility = View.GONE
//
//            }
//        })
//    }
//
//    private fun setUpRecyclerView(){
//        recyclerViewAndroid = RecyclerWorkerAdapter(arrayListOf(), object: RecyclerWorkerAdapter.OnClickViewListener{
//            override fun OnClick(id: Int?) {
//                if (id != null) {
//                    val intent = Intent(activity, ProfileWorkerActivity::class.java)
//                    intent.putExtra("KEY_ID_WORKER", id.toString())
//                    startActivity(intent)
//                }
//            }
//        })
//        recyclerViewWeb = RecyclerWorkerAdapter(arrayListOf(), object: RecyclerWorkerAdapter.OnClickViewListener{
//            override fun OnClick(id: Int?) {
//                if (id != null) {
//                    val intent = Intent(activity, ProfileWorkerActivity::class.java)
//                    intent.putExtra("KEY_ID_WORKER", id.toString())
//                    startActivity(intent)
//                }
//            }
//        })
//        binding.rvAndroid.adapter = recyclerViewAndroid
//        binding.rvWeb.adapter = recyclerViewWeb
//        binding.rvAndroid.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
//        binding.rvWeb.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
//    }
//
//}