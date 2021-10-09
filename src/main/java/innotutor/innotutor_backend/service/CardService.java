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

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
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
import innotutor.innotutor_backend.service.utility.card.CardCreatorId;
import innotutor.innotutor_backend.service.utility.card.CardDTOCreator;
import innotutor.innotutor_backend.service.utility.card.CardType;
import innotutor.innotutor_backend.service.utility.cardmanager.CardCreator;
import innotutor.innotutor_backend.service.utility.cardmanager.CardUpdater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final SubjectRepository subjectRepository;
    private final ServiceRepository serviceRepository;
    private final RequestRepository requestRepository;
    private final CardSessionFormatRepository cardSessionFormatRepository;
    private final CardSessionTypeRepository cardSessionTypeRepository;

    public CardDTO getCardById(final Long cardId, final Long userId) {
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            final Card card = cardOptional.get();
            final Long creatorId = new CardCreatorId(card).creatorId();
            if (!card.getHidden() || userId.equals(creatorId)) {
                return new CardDTOCreator(card, creatorId, subjectRepository).create();
            }
        }
        return null;
    }

    public CardDTO postCvCard(final CardDTO cardDTO) {
        return new CardCreator(cardDTO,
                CardType.SERVICE,
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

    public CardDTO postRequestCard(final CardDTO cardDTO) {
        return new CardCreator(cardDTO,
                CardType.REQUEST,
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

    public CardDTO putCvCard(final CardDTO cardDTO) {
        return new CardUpdater(cardDTO,
                CardType.SERVICE,
                cardRepository,
                userRepository,
                subjectRepository,
                sessionFormatRepository,
                sessionTypeRepository,
                serviceRepository,
                requestRepository,
                cardSessionFormatRepository,
                cardSessionTypeRepository).putCard();
    }

    public CardDTO putRequestCard(final CardDTO cardDTO) {
        return new CardUpdater(cardDTO,
                CardType.REQUEST,
                cardRepository,
                userRepository,
                subjectRepository,
                sessionFormatRepository,
                sessionTypeRepository,
                serviceRepository,
                requestRepository,
                cardSessionFormatRepository,
                cardSessionTypeRepository).putCard();
    }

    public boolean deleteCardById(final Long userId, final Long cardId) {
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        final Optional<User> userOptional = userRepository.findById(userId);
        if (!cardOptional.isPresent() || !userOptional.isPresent()) {
            return false;
        }
        final innotutor.innotutor_backend.entity.user.Service service = serviceRepository.findByCardId(cardId);
        if (service != null) {
            if (service.getTutorId().equals(userId)) {
                cardRepository.deleteById(cardId);
                return true;
            }
        }
        final Request request = requestRepository.findByCardId(cardId);
        if (request != null) {
            if (request.getStudentId().equals(userId)) {
                cardRepository.deleteById(cardId);
                return true;
            }
        }
        return false;
    }
}
