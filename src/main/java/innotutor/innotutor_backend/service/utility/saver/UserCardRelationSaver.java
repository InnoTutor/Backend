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
package innotutor.innotutor_backend.service.utility.saver;

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.Service;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
import innotutor.innotutor_backend.service.utility.card.CardType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserCardRelationSaver {
    private final User creator;
    private final Card card;
    private final CardType type;
    private final ServiceRepository serviceRepository;
    private final RequestRepository requestRepository;

    public void save() {
        switch (type) {
            case SERVICE:
                serviceRepository.save(new Service(creator.getUserId(), card.getCardId(), creator, card));
                break;
            case REQUEST:
                requestRepository.save(new Request(creator.getUserId(), card.getCardId(), creator, card));
                break;
        }
    }
}
