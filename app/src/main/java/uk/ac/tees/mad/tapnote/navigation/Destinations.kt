package uk.ac.tees.mad.tapnote.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Home

@Serializable
object Auth

@Serializable
data class NoteDetail(val noteId: Long)