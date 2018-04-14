import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserextraComponent } from './userextra.component';
import { UserextraDetailComponent } from './userextra-detail.component';
import { UserextraPopupComponent } from './userextra-dialog.component';
import { UserextraDeletePopupComponent } from './userextra-delete-dialog.component';

@Injectable()
export class UserextraResolvePagingParams implements Resolve<any> {

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

export const userextraRoute: Routes = [
    {
        path: 'userextra',
        component: UserextraComponent,
        resolve: {
            'pagingParams': UserextraResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.userextra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'userextra/:id',
        component: UserextraDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.userextra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userextraPopupRoute: Routes = [
    {
        path: 'userextra-new',
        component: UserextraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.userextra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'userextra/:id/edit',
        component: UserextraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.userextra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'userextra/:id/delete',
        component: UserextraDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.userextra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
