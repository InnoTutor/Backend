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

import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardSessionFormat;
import innotutor.innotutor_backend.entity.card.CardSessionType;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.session.Subject;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.card.CardSessionFormatRepository;
import innotutor.innotutor_backend.repository.card.CardSessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.utility.session.sessionformat.SessionFormatConverter;
import innotutor.innotutor_backend.utility.session.sessionformat.SessionFormatEntityConverter;
import innotutor.innotutor_backend.utility.session.sessiontype.SessionTypeConverter;
import innotutor.innotutor_backend.utility.session.sessiontype.SessionTypeEntityConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    final CardRepository cardRepository;
    final UserRepository userRepository;
    final SessionFormatRepository sessionFormatRepository;
    final SessionTypeRepository sessionTypeRepository;
    final SubjectRepository subjectRepository;
    final ServiceRepository serviceRepository;
    final RequestRepository requestRepository;
    final CardSessionFormatRepository cardSessionFormatRepository;
    final CardSessionTypeRepository cardSessionTypeRepository;

    public CardService(CardRepository cardRepository,
                       UserRepository userRepository,
                       SessionFormatRepository sessionFormatRepository,
                       SessionTypeRepository sessionTypeRepository,
                       SubjectRepository subjectRepository,
                       ServiceRepository serviceRepository,
                       RequestRepository requestRepository,
                       CardSessionFormatRepository cardSessionFormatRepository,
                       CardSessionTypeRepository cardSessionTypeRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.sessionFormatRepository = sessionFormatRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.subjectRepository = subjectRepository;
        this.serviceRepository = serviceRepository;
        this.requestRepository = requestRepository;
        this.cardSessionFormatRepository = cardSessionFormatRepository;
        this.cardSessionTypeRepository = cardSessionTypeRepository;
    }

    public CardDTO postCvCard(CardDTO cardDTO) {
        return createCard(cardDTO, CardType.SERVICE);
    }

    public CardDTO postRequestCard(CardDTO cardDTO) {
        return createCard(cardDTO, CardType.REQUEST);
    }

    private CardDTO createCard(CardDTO cardDTO, CardType type) {
        Optional<User> creatorOptional = userRepository.findById(cardDTO.getCreatorId());
        Subject subject = subjectRepository.findSubjectByName(cardDTO.getSubject());
        List<SessionFormat> sessionFormat = new SessionFormatEntityConverter(
                cardDTO.getSessionFormat(),
                sessionFormatRepository
        ).toEntityList();
        List<SessionType> sessionType = new SessionTypeEntityConverter(
                cardDTO.getSessionType(),
                sessionTypeRepository
        ).toEntityList();
        if (creatorOptional.isPresent() && subject != null && !sessionFormat.isEmpty() && !sessionType.isEmpty()) {
            User creator = creatorOptional.get();
            if (this.isUniquePair(creator.getUserId(), subject.getSubjectId())) {
                Card card = cardRepository.save(new Card(subject.getSubjectId(), cardDTO.getDescription(), subject));
                this.saveUserCardRelation(creator, card, type);
                this.saveSessionFormat(card, sessionFormat);
                this.saveSessionType(card, sessionType);
                return new CardDTO(
                        card.getCardId(),
                        creator.getUserId(),
                        subject.getName(),
                        null,
                        cardDTO.getDescription(),
                        new SessionFormatConverter(sessionFormat).stringList(),
                        new SessionTypeConverter(sessionType).stringList());
            }
        }
        return null;
    }

    private boolean isUniquePair(Long creatorId, Long subjectId) {
        List<Card> cards = cardRepository.findBySubjectId(subjectId);
        for (Card card : cards) {
            if (card.getRequestByCardId() != null) {
                if (card.getRequestByCardId().getStudentId().equals(creatorId)) {
                    return false;
                }
            }
            if (card.getServiceByCardId() != null) {
                if (card.getServiceByCardId().getTutorId().equals(creatorId)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void saveUserCardRelation(User creator, Card card, CardType type) {
        switch (type) {
            case SERVICE:
                serviceRepository.save(
                        new innotutor.innotutor_backend.entity.user.Service(
                                creator.getUserId(),
                                card.getCardId(),
                                creator,
                                card
                        )
                );
                break;
            case REQUEST:
                requestRepository.save(new Request(creator.getUserId(), card.getCardId(), creator, card));
                break;
        }
    }

    private void saveSessionFormat(Card card, List<SessionFormat> sessionFormat) {
        for (SessionFormat format : sessionFormat) {
            cardSessionFormatRepository.save(
                    new CardSessionFormat(
                            card.getCardId(),
                            format.getSessionFormatId(),
                            card,
                            format
                    )
            );
        }
    }

    private void saveSessionType(Card card, List<SessionType> sessionType) {
        for (SessionType type : sessionType) {
            cardSessionTypeRepository.save(
                    new CardSessionType(
                            card.getCardId(),
                            type.getSessionTypeId(),
                            card,
                            type
                    )
            );
        }
    }

    private enum CardType {
        SERVICE, REQUEST
    }
}
