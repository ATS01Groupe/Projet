import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Rooms } from './rooms.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RoomsService {

    private resourceUrl = 'api/rooms';
    private resourceSearchUrl = 'api/_search/rooms';

    constructor(private http: Http) { }

    create(rooms: Rooms): Observable<Rooms> {
        const copy = this.convert(rooms);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(rooms: Rooms): Observable<Rooms> {
        const copy = this.convert(rooms);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Rooms> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(rooms: Rooms): Rooms {
        const copy: Rooms = Object.assign({}, rooms);
        return copy;
    }
}
