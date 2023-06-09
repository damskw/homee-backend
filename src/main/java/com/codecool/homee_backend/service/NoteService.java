package com.codecool.homee_backend.service;

import com.codecool.homee_backend.controller.dto.note.NewNoteDto;
import com.codecool.homee_backend.controller.dto.note.NoteDto;
import com.codecool.homee_backend.controller.dto.note.UpdatedNoteDto;
import com.codecool.homee_backend.entity.Device;
import com.codecool.homee_backend.entity.Note;
import com.codecool.homee_backend.mapper.NoteMapper;
import com.codecool.homee_backend.repository.DeviceRepository;
import com.codecool.homee_backend.repository.NoteRepository;
import com.codecool.homee_backend.service.exception.DeviceNotFoundException;
import com.codecool.homee_backend.service.exception.NoteNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final DeviceRepository deviceRepository;
    private final NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, DeviceRepository deviceRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.deviceRepository = deviceRepository;
        this.noteMapper = noteMapper;
    }

    public NoteDto getNote(UUID noteId) {
        return noteRepository.findById(noteId)
                .map(noteMapper::mapNoteEntityToDto)
                .orElseThrow(() -> new NoteNotFoundException(noteId));
    }

    public List<NoteDto> getNotesForDevice(UUID deviceId) {
        return noteRepository.findAllByDeviceIdOrderByCreationTime(deviceId).stream()
                .map(noteMapper::mapNoteEntityToDto)
                .toList();
    }

    public NoteDto addNewNote(NewNoteDto newNote) {
        Device device = deviceRepository.findById(newNote.deviceId())
                .orElseThrow(() -> new DeviceNotFoundException(newNote.deviceId()));
        Note note = noteMapper.mapNoteDtoToEntity(newNote);
        Note noteDb = noteRepository.save(note);
        device.addNote(note);
        note.setDevice(device);
        deviceRepository.save(device);
        return noteMapper.mapNoteEntityToDto(noteDb);
    }

    public NoteDto updateNote(UpdatedNoteDto updatedNote) {
        Note dbNote = noteRepository.findById(updatedNote.id())
                .orElseThrow(() -> new NoteNotFoundException(updatedNote.id()));
        assignUpdatedNoteData(updatedNote, dbNote);
        noteRepository.save(dbNote);
        return noteMapper.mapNoteEntityToDto(dbNote);
    }


    public void assignNoteToDevice(UUID noteId, UUID deviceId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
        note.setDevice(device);
        device.addNote(note);
        deviceRepository.save(device);
    }

    public void deleteNote(UUID noteId) {
        noteRepository.deleteById(noteId);
    }

    private void assignUpdatedNoteData(UpdatedNoteDto updatedNote, Note entity) {
        entity.setTitle(updatedNote.title());
        entity.setDescription(updatedNote.description());
    }


}
