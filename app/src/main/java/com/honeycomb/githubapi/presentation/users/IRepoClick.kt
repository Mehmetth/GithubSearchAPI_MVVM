package com.honeycomb.githubapi.presentation.users

import android.view.View
import com.honeycomb.githubapi.domain.users.entity.UsersEntity

interface IRepoClick {
    fun repoNameClicked(view: View, usersEntity: UsersEntity)
}