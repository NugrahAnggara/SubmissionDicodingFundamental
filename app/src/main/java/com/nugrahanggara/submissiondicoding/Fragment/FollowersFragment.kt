@file:Suppress("DEPRECATION")

package com.nugrahanggara.submissiondicoding.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nugrahanggara.submissiondicoding.Adapter.FollowersAdapter
import com.nugrahanggara.submissiondicoding.Model.FollowersResponse
import com.nugrahanggara.submissiondicoding.R
import com.nugrahanggara.submissiondicoding.Retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment()
{
    private lateinit var adapter : FollowersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var testing : TextView
    private var list = ArrayList<FollowersResponse>()
    private lateinit var loading : ProgressBar

    companion object
    {
        fun newInstance(data: String?): FollowersFragment
        {
            val fragment = FollowersFragment()
            val bundle = Bundle().apply { putString("data", data) }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View?
    {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val data = arguments?.getString("data")
        super.onViewCreated(view, savedInstanceState)
        initialisation(view)
        val login = data

        if (login != null)
        {
            getFollowers(login)
        }

        listRv()
    }

    private fun initialisation(view: View)
    {
        list = arrayListOf()
        loading = view.findViewById(R.id.loading)
        recyclerView = view.findViewById(R.id.rv_followers)
    }

    private fun getFollowers(login : String)
    {
        val client = ApiConfig.getApiService().getFollowers(login)
        client.enqueue(object : Callback<List<FollowersResponse>>
        {
            override fun onResponse(call: Call<List<FollowersResponse>>, response: Response<List<FollowersResponse>>, )
            {
                if (response.isSuccessful){
                    loading.visibility = View.GONE
                        val data = response.body()
                        data?.let { list.addAll(it)
                            adapter.notifyDataSetChanged() }
                }else{
                    loading.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable)
            {
                Toast.makeText(requireActivity(),"Data Gagal Diambil",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun listRv()
    {
        adapter = FollowersAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}