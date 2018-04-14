import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AppointmentComponent } from './appointment.component';
import { AppointmentDetailComponent } from './appointment-detail.component';
import { AppointmentPopupComponent } from './appointment-dialog.component';
import { AppointmentDeletePopupComponent } from './appointment-delete-dialog.component';

@Injectable()
export class AppointmentResolvePagingParams implements Resolve<any> {

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

export const appointmentRoute: Routes = [
    {
        path: 'appointment',
        component: AppointmentComponent,
        resolve: {
            'pagingParams': AppointmentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'appointment/:id',
        component: AppointmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appointmentPopupRoute: Routes = [
    {
        path: 'appointment-new',
        component: AppointmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appointment/:id/edit',
        component: AppointmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appointment/:id/delete',
        component: AppointmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.appointment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
