import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfSharedModule } from '../../shared';
import {
    ProjectmessagesService,
    ProjectmessagesPopupService,
    ProjectmessagesComponent,
    ProjectmessagesDetailComponent,
    ProjectmessagesDialogComponent,
    ProjectmessagesPopupComponent,
    ProjectmessagesDeletePopupComponent,
    ProjectmessagesDeleteDialogComponent,
    projectmessagesRoute,
    projectmessagesPopupRoute,
    ProjectmessagesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projectmessagesRoute,
    ...projectmessagesPopupRoute,
];

@NgModule({
    imports: [
        PfSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjectmessagesComponent,
        ProjectmessagesDetailComponent,
        ProjectmessagesDialogComponent,
        ProjectmessagesDeleteDialogComponent,
        ProjectmessagesPopupComponent,
        ProjectmessagesDeletePopupComponent,
    ],
    entryComponents: [
        ProjectmessagesComponent,
        ProjectmessagesDialogComponent,
        ProjectmessagesPopupComponent,
        ProjectmessagesDeleteDialogComponent,
        ProjectmessagesDeletePopupComponent,
    ],
    providers: [
        ProjectmessagesService,
        ProjectmessagesPopupService,
        ProjectmessagesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfProjectmessagesModule {}
