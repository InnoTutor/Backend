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

import innotutor.innotutor_backend.DTO.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.DTO.searcher.TutorCvDTO;
import innotutor.innotutor_backend.DTO.searcher.UserCard;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.utility.AverageRating;
import innotutor.innotutor_backend.utility.session.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.utility.session.sessiontype.CardSessionTypeConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearcherService {
    final CardRepository cardRepository;

    public SearcherService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<TutorCvDTO> getTutorCvDTOList(String specifiedSubject,
                                              String specifiedFormat,
                                              String specifiedType) {
        List<TutorCvDTO> tutors = new ArrayList<>();
        for (UserCard user : this.filterCards(
                new ArrayList<>(this.getAllTutorCvDTOList()),
                specifiedSubject,
                specifiedFormat,
                specifiedType)) {
            tutors.add((TutorCvDTO) user);
        }
        return tutors;
    }

    public List<StudentRequestDTO> getStudentRequestDTOList(String specifiedSubject,
                                                            String specifiedFormat,
                                                            String specifiedType) {
        List<StudentRequestDTO> students = new ArrayList<>();
        for (UserCard user : this.filterCards(
                new ArrayList<>(this.getAllStudentRequestDTOList()),
                specifiedSubject,
                specifiedFormat,
                specifiedType)) {
            students.add((StudentRequestDTO) user);
        }
        return students;
    }

    private List<TutorCvDTO> getAllTutorCvDTOList() {
        List<TutorCvDTO> tutors = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            if (card.getServiceByCardId() != null) {
                tutors.add(
                        new TutorCvDTO(
                                card.getServiceByCardId().getTutorId(),
                                card.getCardId(),
                                new AverageRating(card.getCardRatingsByCardId()).averageRating(),
                                card.getDescription(),
                                card.getSubjectBySubjectId().getName(),
                                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
                        )
                );
            }
        }
        return tutors;
    }

    private List<StudentRequestDTO> getAllStudentRequestDTOList() {
        List<StudentRequestDTO> students = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            if (card.getRequestByCardId() != null) {
                students.add(
                        new StudentRequestDTO(
                                card.getRequestByCardId().getStudentId(),
                                card.getCardId(),
                                card.getDescription(),
                                card.getSubjectBySubjectId().getName(),
                                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
                        )
                );
            }
        }
        return students;
    }

    private List<UserCard> filterCards(List<UserCard> cards,
                                       String specifiedSubject,
                                       String specifiedFormat,
                                       String specifiedType) {
        List<UserCard> result = cards;
        if (specifiedSubject != null) {
            result = result.stream().filter(x -> x.getSubject().equals(specifiedSubject)).collect(Collectors.toList());
        }
        if (specifiedFormat != null) {
            result = result.stream().filter(x -> x.getSessionFormat().contains(specifiedFormat)).collect(Collectors.toList());
        }
        if (specifiedType != null) {
            result = result.stream().filter(x -> x.getSessionType().contains(specifiedType)).collect(Collectors.toList());
        }
        return result;
    }
}
