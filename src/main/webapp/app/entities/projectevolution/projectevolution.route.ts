import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProjectevolutionComponent } from './projectevolution.component';
import { ProjectevolutionDetailComponent } from './projectevolution-detail.component';
import { ProjectevolutionPopupComponent } from './projectevolution-dialog.component';
import { ProjectevolutionDeletePopupComponent } from './projectevolution-delete-dialog.component';

@Injectable()
export class ProjectevolutionResolvePagingParams implements Resolve<any> {

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

export const projectevolutionRoute: Routes = [
    {
        path: 'projectevolution',
        component: ProjectevolutionComponent,
        resolve: {
            'pagingParams': ProjectevolutionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectevolution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'projectevolution/:id',
        component: ProjectevolutionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectevolution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectevolutionPopupRoute: Routes = [
    {
        path: 'projectevolution-new',
        component: ProjectevolutionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectevolution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projectevolution/:id/edit',
        component: ProjectevolutionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectevolution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projectevolution/:id/delete',
        component: ProjectevolutionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pfApp.projectevolution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
