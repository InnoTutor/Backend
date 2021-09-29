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
package innotutor.innotutor_backend.utility.session.sessiontype;

import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.utility.session.SessionConverter;

import java.util.ArrayList;
import java.util.List;

public class SessionTypeConverter implements SessionConverter {
    private final List<SessionType> sessionType;

    public SessionTypeConverter(List<SessionType> sessionType) {
        this.sessionType = sessionType;
    }

    @Override
    public List<String> stringList() {
        List<String> typesNames = new ArrayList<>();
        for (SessionType type : sessionType) {
            typesNames.add(type.getName());
        }
        return typesNames;
    }
}
