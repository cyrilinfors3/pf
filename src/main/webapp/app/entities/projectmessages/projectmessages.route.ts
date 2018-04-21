import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProjectmessagesComponent } from './projectmessages.component';
import { ProjectmessagesDetailComponent } from './projectmessages-detail.component';
import { ProjectmessagesPopupComponent } from './projectmessages-dialog.component';
import { ProjectmessagesDeletePopupComponent } from './projectmessages-delete-dialog.component';

@Injectable()
export class ProjectmessagesResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const projectmessagesRoute: Routes = [
    {
        path: 'projectmessages',
        component: ProjectmessagesComponent,
        resolve: {
            'pagingParams': ProjectmessagesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectmessages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'projectmessages/:id',
        component: ProjectmessagesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectmessages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectmessagesPopupRoute: Routes = [
    {
        path: 'projectmessages-new',
        component: ProjectmessagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectmessages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projectmessages/:id/edit',
        component: ProjectmessagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectmessages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projectmessages/:id/delete',
        component: ProjectmessagesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectmessages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
