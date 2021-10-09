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

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.card.CardSessionFormatRepository;
import innotutor.innotutor_backend.repository.card.CardSessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.utility.card.CardCreatorId;
import innotutor.innotutor_backend.service.utility.card.CardDTOCreator;
import innotutor.innotutor_backend.service.utility.card.CardType;
import innotutor.innotutor_backend.service.utility.saver.SessionFormatsCardSaver;
import innotutor.innotutor_backend.service.utility.saver.SessionTypesCardSaver;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.SessionFormatEntityConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.SessionTypeEntityConverter;

import java.util.List;


public class CardUpdater {
    private final CardDTO cardDTO;
    private final CardType type;
    private final Card card;
    private final List<SessionFormat> sessionFormats;
    private final List<SessionType> sessionTypes;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final ServiceRepository serviceRepository;
    private final RequestRepository requestRepository;
    private final CardSessionFormatRepository cardSessionFormatRepository;
    private final CardSessionTypeRepository cardSessionTypeRepository;

    public CardUpdater(CardDTO cardDTO, CardType type, CardRepository cardRepository, UserRepository userRepository, SubjectRepository subjectRepository, SessionFormatRepository sessionFormatRepository, SessionTypeRepository sessionTypeRepository, ServiceRepository serviceRepository, RequestRepository requestRepository, CardSessionFormatRepository cardSessionFormatRepository, CardSessionTypeRepository cardSessionTypeRepository) {
        this.cardDTO = cardDTO;
        this.type = type;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.sessionFormatRepository = sessionFormatRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.serviceRepository = serviceRepository;
        this.requestRepository = requestRepository;
        this.cardSessionFormatRepository = cardSessionFormatRepository;
        this.cardSessionTypeRepository = cardSessionTypeRepository;
        card = cardRepository.findById(cardDTO.getCardId()).orElse(null);
        sessionFormats = new SessionFormatEntityConverter(
                cardDTO.getSessionFormat(),
                sessionFormatRepository
        ).toEntityList();
        sessionTypes = new SessionTypeEntityConverter(
                cardDTO.getSessionType(),
                sessionTypeRepository
        ).toEntityList();
    }

    public CardDTO putCard() {
        if (card == null) {
            return new CardCreator(cardDTO,
                    type,
                    cardRepository,
                    userRepository,
                    subjectRepository,
                    sessionFormatRepository,
                    sessionTypeRepository,
                    serviceRepository,
                    requestRepository,
                    cardSessionFormatRepository,
                    cardSessionTypeRepository).postCard();
        }
        if (cardDTO.getCreatorId().equals(new CardCreatorId(card).creatorId())
                && !sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
            return updateCard();
        }
        return null;
    }

    private CardDTO updateCard() {
        card.setDescription(cardDTO.getDescription());
        card.setHidden(cardDTO.isHidden());
        Card savedCard = cardRepository.save(card);
        savedCard = this.updateSessionFormats(savedCard);
        savedCard = this.updateSessionTypes(savedCard);
        return new CardDTOCreator(savedCard, cardDTO.getCreatorId(), subjectRepository).create();
    }

    private Card updateSessionFormats(Card card) {
        cardSessionFormatRepository.deleteAll(card.getCardSessionFormatsByCardId());
        return new SessionFormatsCardSaver(card, sessionFormats, cardSessionFormatRepository).save();
    }

    private Card updateSessionTypes(Card card) {
        cardSessionTypeRepository.deleteAll(card.getCardSessionTypesByCardId());
        return new SessionTypesCardSaver(card, sessionTypes, cardSessionTypeRepository).save();
    }
}
