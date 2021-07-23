package com.codexo.notes.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.codexo.notes.data.Note
import com.codexo.notes.data.NoteDatabase
import kotlinx.coroutines.launch

class NotesViewModel constructor(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getInstance(application).noteDao()

    lateinit var searchQuery: String

    val allNotes: LiveData<List<Note>> = noteDao.getNotes()

    val sortByTitle: LiveData<List<Note>> = noteDao.sortByTitle()
    val sortByDateCreated: LiveData<List<Note>> = noteDao.sortByDateCreated()
    val sortByDateUpdated: LiveData<List<Note>> = noteDao.sortByDateUpdated()

    fun searchNote(searchQuery: String): LiveData<List<Note>> = noteDao.searchNote(searchQuery)

    fun deleteAllNotes() = viewModelScope.launch { noteDao.clearNotes() }

    fun initDummyNote() = viewModelScope.launch {
        noteDao.insert(
            Note(
                title = "hey",
                note = "this is A dummy note!",
                favorite = true,
                archived = false,
                lastUpdatedAt = System.currentTimeMillis(),
                bgColor = -1
            )
        )
    }
}