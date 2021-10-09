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
package innotutor.innotutor_backend.service.utility.cardmanager;

import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.session.Subject;
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
import innotutor.innotutor_backend.service.utility.card.CardType;
import innotutor.innotutor_backend.service.utility.saver.SessionFormatsCardSaver;
import innotutor.innotutor_backend.service.utility.saver.SessionTypesCardSaver;
import innotutor.innotutor_backend.service.utility.saver.UserCardRelationSaver;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.SessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.SessionFormatEntityConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.SessionTypeConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.SessionTypeEntityConverter;

import java.util.List;

public class CardCreator {
    private final CardDTO cardDTO;
    private final CardType type;
    private final User creator;
    private final Subject subject;
    private final List<SessionFormat> sessionFormats;
    private final List<SessionType> sessionTypes;
    private final CardRepository cardRepository;
    private final ServiceRepository serviceRepository;
    private final RequestRepository requestRepository;
    private final CardSessionFormatRepository cardSessionFormatRepository;
    private final CardSessionTypeRepository cardSessionTypeRepository;

    public CardCreator(CardDTO cardDTO,
                       CardType type,
                       CardRepository cardRepository,
                       UserRepository userRepository,
                       SubjectRepository subjectRepository,
                       SessionFormatRepository sessionFormatRepository,
                       SessionTypeRepository sessionTypeRepository,
                       ServiceRepository serviceRepository,
                       RequestRepository requestRepository,
                       CardSessionFormatRepository cardSessionFormatRepository,
                       CardSessionTypeRepository cardSessionTypeRepository) {
        this.cardDTO = cardDTO;
        this.type = type;
        this.cardRepository = cardRepository;
        this.serviceRepository = serviceRepository;
        this.requestRepository = requestRepository;
        this.cardSessionFormatRepository = cardSessionFormatRepository;
        this.cardSessionTypeRepository = cardSessionTypeRepository;
        creator = userRepository.findById(cardDTO.getCreatorId()).orElse(null);
        subject = subjectRepository.findSubjectByName(cardDTO.getSubject());
        sessionFormats = new SessionFormatEntityConverter(cardDTO.getSessionFormat(), sessionFormatRepository)
                .toEntityList();
        sessionTypes = new SessionTypeEntityConverter(cardDTO.getSessionType(), sessionTypeRepository).toEntityList();
    }

    public CardDTO postCard() {
        if (creator != null && subject != null && !sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
            return createCard();
        }
        return null;
    }

    private CardDTO createCard() {
        try {
            if (this.isUniquePair()) {
                Card card = cardRepository.save(new Card(
                        subject.getSubjectId(),
                        cardDTO.getDescription(),
                        false,
                        subject)
                );
                new UserCardRelationSaver(creator, card, type, serviceRepository, requestRepository).save();
                card = new SessionFormatsCardSaver(card, sessionFormats, cardSessionFormatRepository).save();
                card = new SessionTypesCardSaver(card, sessionTypes, cardSessionTypeRepository).save();
                return new CardDTO(
                        card.getCardId(),
                        creator.getUserId(),
                        subject.getName(),
                        null,
                        0,
                        cardDTO.getDescription(),
                        false,
                        new SessionFormatConverter(sessionFormats).stringList(),
                        new SessionTypeConverter(sessionTypes).stringList());
            }
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private boolean isUniquePair() throws IllegalAccessException {
        List<Card> cards = cardRepository.findBySubjectId(subject.getSubjectId());
        switch (type) {
            case SERVICE:
                for (Card card : cards) {
                    if (card.getServiceByCardId() != null && card.getServiceByCardId().getTutorId().equals(creator.getUserId())) {
                        return false;
                    }
                }
                return true;
            case REQUEST:
                /*
                for (Card card : cards) {
                    if (card.getRequestByCardId() != null && card.getRequestByCardId().getStudentId().equals(creatorId)) {
                        return false;
                    }
                }
                 */
                return true;
            default:
                throw new IllegalAccessException("The card type is not specified");
        }
    }
}
