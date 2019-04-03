import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITuit } from 'app/shared/model/tuit.model';

type EntityResponseType = HttpResponse<ITuit>;
type EntityArrayResponseType = HttpResponse<ITuit[]>;

@Injectable({ providedIn: 'root' })
export class TuitService {
    public resourceUrl = SERVER_API_URL + 'api/tuits';

    constructor(private http: HttpClient) {}

    create(tuit: ITuit): Observable<EntityResponseType> {
        return this.http.post<ITuit>(this.resourceUrl, tuit, { observe: 'response' });
    }

    update(tuit: ITuit): Observable<EntityResponseType> {
        return this.http.put<ITuit>(this.resourceUrl, tuit, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITuit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITuit[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
