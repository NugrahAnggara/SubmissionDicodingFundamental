package com.nugrahanggara.submissiondicoding.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nugrahanggara.submissiondicoding.Adapter.FollowingAdapter
import com.nugrahanggara.submissiondicoding.Model.FollowersResponse
import com.nugrahanggara.submissiondicoding.R
import com.nugrahanggara.submissiondicoding.Retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment()
{
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : FollowingAdapter
    private var list = ArrayList<FollowersResponse>()
    private lateinit var loading : ProgressBar

    companion object
    {
        fun newInstance( data: String? ): FollowingFragment
        {
            val fragment = FollowingFragment()
            val bundle = Bundle().apply { putString("data", data) }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View?
    {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle? )
    {
        super.onViewCreated( view, savedInstanceState )
        initialisation( view )
        val data = arguments?.getString("data")

        if (data != null)
        {
            getFollowing(data)
        }
        settingRecyclerView()
    }

    private fun initialisation( view : View )
    {
        recyclerView = view.findViewById(R.id.rv_following)
        loading = view.findViewById(R.id.loading)
    }

    private fun getFollowing(data: String)
    {
        val client = ApiConfig.getApiService().getFollowing(data)
        client.enqueue(object  : Callback<List<FollowersResponse>>
        {
            override fun onResponse(call: Call<List<FollowersResponse>>, response: Response<List<FollowersResponse>>, )
            {
                if (response.isSuccessful){
                    loading.visibility = View.GONE
                    val responseBody = response.body()
                    responseBody.let {
                        if (it != null) {
                            list.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }else{
                    loading.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable)
            {
                Toast.makeText(requireContext(),"Data Gagal Didapatkan",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun settingRecyclerView()
    {
        adapter = FollowingAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}