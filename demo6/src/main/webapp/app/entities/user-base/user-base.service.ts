import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserBase } from 'app/shared/model/user-base.model';

type EntityResponseType = HttpResponse<IUserBase>;
type EntityArrayResponseType = HttpResponse<IUserBase[]>;

@Injectable({ providedIn: 'root' })
export class UserBaseService {
  public resourceUrl = SERVER_API_URL + 'api/user-bases';

  constructor(protected http: HttpClient) {}

  create(userBase: IUserBase): Observable<EntityResponseType> {
    return this.http.post<IUserBase>(this.resourceUrl, userBase, { observe: 'response' });
  }

  update(userBase: IUserBase): Observable<EntityResponseType> {
    return this.http.put<IUserBase>(this.resourceUrl, userBase, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserBase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserBase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
