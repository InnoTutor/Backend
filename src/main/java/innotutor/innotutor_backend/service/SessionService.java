/*
MIT License

Copyright (c) 2021 InnoTutor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.DTO.card.SubjectDTO;
import innotutor.innotutor_backend.DTO.session.sessionsettings.SessionFormatDTO;
import innotutor.innotutor_backend.DTO.session.sessionsettings.SessionTypeDTO;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.session.Subject;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    final SessionFormatRepository sessionFormatRepository;
    final SessionTypeRepository sessionTypeRepository;
    final SubjectRepository subjectRepository;

    public SessionService(SessionFormatRepository sessionFormatRepository,
                          SessionTypeRepository sessionTypeRepository,
                          SubjectRepository subjectRepository) {
        this.sessionFormatRepository = sessionFormatRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<SessionFormatDTO> getSessionFormats() {
        List<SessionFormatDTO> sessionFormats = new ArrayList<>();
        for (SessionFormat format : sessionFormatRepository.findAll()) {
            sessionFormats.add(new SessionFormatDTO(format.getSessionFormatId(), format.getName()));
        }
        return sessionFormats;
    }

    public List<SessionTypeDTO> getSessionTypes() {
        List<SessionTypeDTO> sessionTypes = new ArrayList<>();
        for (SessionType type : sessionTypeRepository.findAll()) {
            sessionTypes.add(new SessionTypeDTO(type.getSessionTypeId(), type.getName()));
        }
        return sessionTypes;
    }

    public List<SubjectDTO> getSubjects() {
        List<SubjectDTO> subjects = new ArrayList<>();
        for (Subject subject : subjectRepository.findAll()) {
            subjects.add(new SubjectDTO(subject.getSubjectId(), subject.getName()));
        }
        return subjects;
    }
}
