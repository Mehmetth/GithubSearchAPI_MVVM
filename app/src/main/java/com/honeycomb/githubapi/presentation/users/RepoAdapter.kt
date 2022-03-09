package com.honeycomb.githubapi.presentation.users

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.honeycomb.githubapi.R
import com.honeycomb.githubapi.domain.users.entity.UsersEntity
import kotlinx.android.synthetic.main.repo_item.view.*
import java.util.*

class RepoAdapter(var activity: Activity,
                  val iRepoClick: IRepoClick,
                  var allReposResponse: List<UsersEntity>):
    RecyclerView.Adapter<RepoAdapter.DataViewHolder> (), Filterable {
    class DataViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.repo_item,parent,false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allReposResponse.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.view.repoName_textview.text = allReposResponse[position].name

        holder.itemView.setOnClickListener {
            iRepoClick.repoNameClicked( holder.itemView,allReposResponse[position])
        }
    }

    fun updateDataList(newDataList: List<UsersEntity>) {
        allReposResponse = newDataList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                allReposResponse = filterResults.values as List<UsersEntity>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                Log.d("getFilter","queryString : ${queryString}")
                val filterResults = Filter.FilterResults()
                Log.d("getFilter","filterResults.values : ${filterResults.values}")
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    allReposResponse
                else
                    allReposResponse.filter {
                        it.name.lowercase(Locale.getDefault()).contains(queryString)
                    }
                return filterResults
            }
        }
    }
}