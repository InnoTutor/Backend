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
package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardRating;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;

import java.util.Collection;

public class CardDTOCreator {
    private final Card card;
    private final Long creatorId;
    private final SubjectRepository subjectRepository;

    public CardDTOCreator(Card card, Long creatorId, SubjectRepository subjectRepository) {
        this.card = card;
        this.creatorId = creatorId;
        this.subjectRepository = subjectRepository;
    }

    public CardDTO create() {
        Collection<CardRating> ratings = card.getCardRatingsByCardId();
        return new CardDTO(
                card.getCardId(),
                creatorId,
                subjectRepository.getById(card.getSubjectId()).getName(),
                new AverageCardRating(ratings).averageRating(),
                ratings.size(),
                card.getDescription(),
                card.getHidden(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
        );
    }
}