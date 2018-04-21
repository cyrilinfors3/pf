import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Appointmentmessages } from './appointmentmessages.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppointmentmessagesService {

    private resourceUrl = SERVER_API_URL + 'api/appointmentmessages';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/appointmentmessages';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(appointmentmessages: Appointmentmessages): Observable<Appointmentmessages> {
        const copy = this.convert(appointmentmessages);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(appointmentmessages: Appointmentmessages): Observable<Appointmentmessages> {
        const copy = this.convert(appointmentmessages);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Appointmentmessages> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Appointmentmessages.
     */
    private convertItemFromServer(json: any): Appointmentmessages {
        const entity: Appointmentmessages = Object.assign(new Appointmentmessages(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        entity.createdate = this.dateUtils
            .convertDateTimeFromServer(json.createdate);
        return entity;
    }

    /**
     * Convert a Appointmentmessages to a JSON which can be sent to the server.
     */
    private convert(appointmentmessages: Appointmentmessages): Appointmentmessages {
        const copy: Appointmentmessages = Object.assign({}, appointmentmessages);

        copy.date = this.dateUtils.toDate(appointmentmessages.date);

        copy.createdate = this.dateUtils.toDate(appointmentmessages.createdate);
        return copy;
    }
}
