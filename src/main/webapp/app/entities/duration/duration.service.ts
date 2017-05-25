import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Duration } from './duration.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DurationService {

    private resourceUrl = 'api/durations';
    private resourceSearchUrl = 'api/_search/durations';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(duration: Duration): Observable<Duration> {
        const copy = this.convert(duration);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(duration: Duration): Observable<Duration> {
        const copy = this.convert(duration);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Duration> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.datecome = this.dateUtils
            .convertLocalDateFromServer(entity.datecome);
        entity.datego = this.dateUtils
            .convertLocalDateFromServer(entity.datego);
    }

    private convert(duration: Duration): Duration {
        const copy: Duration = Object.assign({}, duration);
        copy.datecome = this.dateUtils
            .convertLocalDateToServer(duration.datecome);
        copy.datego = this.dateUtils
            .convertLocalDateToServer(duration.datego);
        return copy;
    }
}
