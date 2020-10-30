import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserBaseRoleName } from 'app/shared/model/user-base-role-name.model';

type EntityResponseType = HttpResponse<IUserBaseRoleName>;
type EntityArrayResponseType = HttpResponse<IUserBaseRoleName[]>;

@Injectable({ providedIn: 'root' })
export class UserBaseRoleNameService {
  public resourceUrl = SERVER_API_URL + 'api/user-base-role-names';

  constructor(protected http: HttpClient) {}

  create(userBaseRoleName: IUserBaseRoleName): Observable<EntityResponseType> {
    return this.http.post<IUserBaseRoleName>(this.resourceUrl, userBaseRoleName, { observe: 'response' });
  }

  update(userBaseRoleName: IUserBaseRoleName): Observable<EntityResponseType> {
    return this.http.put<IUserBaseRoleName>(this.resourceUrl, userBaseRoleName, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserBaseRoleName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserBaseRoleName[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
