import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Projectmessages } from './projectmessages.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProjectmessagesService {

    private resourceUrl = SERVER_API_URL + 'api/projectmessages';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/projectmessages';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(projectmessages: Projectmessages): Observable<Projectmessages> {
        const copy = this.convert(projectmessages);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(projectmessages: Projectmessages): Observable<Projectmessages> {
        const copy = this.convert(projectmessages);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Projectmessages> {
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
     * Convert a returned JSON object to Projectmessages.
     */
    private convertItemFromServer(json: any): Projectmessages {
        const entity: Projectmessages = Object.assign(new Projectmessages(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a Projectmessages to a JSON which can be sent to the server.
     */
    private convert(projectmessages: Projectmessages): Projectmessages {
        const copy: Projectmessages = Object.assign({}, projectmessages);

        copy.date = this.dateUtils.toDate(projectmessages.date);
        return copy;
    }
}
