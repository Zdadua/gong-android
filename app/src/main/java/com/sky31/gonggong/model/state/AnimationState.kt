package com.sky31.gonggong.model.state

sealed class AnimationState {
    data object Unstarted : AnimationState()
    data object Loading : AnimationState()
    data object Finished : AnimationState()
}