import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AppointmentmessagesComponent } from './appointmentmessages.component';
import { AppointmentmessagesDetailComponent } from './appointmentmessages-detail.component';
import { AppointmentmessagesPopupComponent } from './appointmentmessages-dialog.component';
import { AppointmentmessagesDeletePopupComponent } from './appointmentmessages-delete-dialog.component';

@Injectable()
export class AppointmentmessagesResolvePagingParams implements Resolve<any> {

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

export const appointmentmessagesRoute: Routes = [
    {
        path: 'appointmentmessages',
        component: AppointmentmessagesComponent,
        resolve: {
            'pagingParams': AppointmentmessagesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointmentmessages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'appointmentmessages/:id',
        component: AppointmentmessagesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointmentmessages.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appointmentmessagesPopupRoute: Routes = [
    {
        path: 'appointmentmessages-new',
        component: AppointmentmessagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointmentmessages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appointmentmessages/:id/edit',
        component: AppointmentmessagesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointmentmessages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appointmentmessages/:id/delete',
        component: AppointmentmessagesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointmentmessages.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
