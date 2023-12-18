import { Injectable } from '@angular/core';
import { PersonPageRequest } from '../model/person-page-request';
import { Observable, of } from 'rxjs';
import { PageResponse } from '../model/page-response';
import { Person } from '../model/person';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

const BASE_URL = environment.baseUrl;
@Injectable({
  providedIn: 'root',
})
export class PersonService {
  constructor(private http: HttpClient) {}

  personPage(pageRequest: PersonPageRequest): Observable<PageResponse<Person>> {
    return this.http.post<PageResponse<Person>>(
      `${BASE_URL}/api/persons/search`,
      pageRequest
    );
  }

  find(id: string): Observable<Person> {
    return this.http.get<Person>(`${BASE_URL}/api/persons/${id}`);
  }

  delete(id: string): Observable<any> {
    return this.http.delete<Person>(`${BASE_URL}/api/persons/${id}`);
  }

  save(request: Person): Observable<any> {
    return this.http.put<Person>(
      `${BASE_URL}/api/persons/${request.id}`,
      request
    );
  }

  create(request: Person): Observable<any> {
    return this.http.post<Person>(`${BASE_URL}/api/persons`, request);
  }
}
