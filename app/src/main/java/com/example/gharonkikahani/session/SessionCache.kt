package com.example.gharonkikahani.session

interface SessionCache{

    fun saveSession(session: Session)

    fun getActiveSession(): Session?

    fun clearSession()
}
