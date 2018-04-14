import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfSharedModule } from '../../shared';
import { PfAdminModule } from '../../admin/admin.module';
import {
    UserextraService,
    UserextraPopupService,
    UserextraComponent,
    UserextraDetailComponent,
    UserextraDialogComponent,
    UserextraPopupComponent,
    UserextraDeletePopupComponent,
    UserextraDeleteDialogComponent,
    userextraRoute,
    userextraPopupRoute,
    UserextraResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userextraRoute,
    ...userextraPopupRoute,
];

@NgModule({
    imports: [
        PfSharedModule,
        PfAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserextraComponent,
        UserextraDetailComponent,
        UserextraDialogComponent,
        UserextraDeleteDialogComponent,
        UserextraPopupComponent,
        UserextraDeletePopupComponent,
    ],
    entryComponents: [
        UserextraComponent,
        UserextraDialogComponent,
        UserextraPopupComponent,
        UserextraDeleteDialogComponent,
        UserextraDeletePopupComponent,
    ],
    providers: [
        UserextraService,
        UserextraPopupService,
        UserextraResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfUserextraModule {}
