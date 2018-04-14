import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Projectevolution } from './projectevolution.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProjectevolutionService {

    private resourceUrl = SERVER_API_URL + 'api/projectevolutions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/projectevolutions';

    constructor(private http: Http) { }

    create(projectevolution: Projectevolution): Observable<Projectevolution> {
        const copy = this.convert(projectevolution);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(projectevolution: Projectevolution): Observable<Projectevolution> {
        const copy = this.convert(projectevolution);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Projectevolution> {
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
     * Convert a returned JSON object to Projectevolution.
     */
    private convertItemFromServer(json: any): Projectevolution {
        const entity: Projectevolution = Object.assign(new Projectevolution(), json);
        return entity;
    }

    /**
     * Convert a Projectevolution to a JSON which can be sent to the server.
     */
    private convert(projectevolution: Projectevolution): Projectevolution {
        const copy: Projectevolution = Object.assign({}, projectevolution);
        return copy;
    }
}
