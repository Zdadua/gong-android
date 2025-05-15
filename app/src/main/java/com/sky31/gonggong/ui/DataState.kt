package com.sky31.gonggong.ui

/**
 * 数据状态类
 */
sealed class DataState() {
    data object Uninitialized : DataState()
    data object Loading : DataState()
    data object Newest : DataState()
    data object Expired : DataState()
    data object Error : DataState()
}