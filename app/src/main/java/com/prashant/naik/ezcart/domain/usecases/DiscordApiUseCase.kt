package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.data.discord.DiscordObject
import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class DiscordApiUseCase @Inject constructor(val repository: Repository){
    suspend fun postToDiscord(discordObject: DiscordObject) = repository.postToDiscord(discordObject)
}