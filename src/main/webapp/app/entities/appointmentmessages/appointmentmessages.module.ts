import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfSharedModule } from '../../shared';
import {
    AppointmentmessagesService,
    AppointmentmessagesPopupService,
    AppointmentmessagesComponent,
    AppointmentmessagesDetailComponent,
    AppointmentmessagesDialogComponent,
    AppointmentmessagesPopupComponent,
    AppointmentmessagesDeletePopupComponent,
    AppointmentmessagesDeleteDialogComponent,
    appointmentmessagesRoute,
    appointmentmessagesPopupRoute,
    AppointmentmessagesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...appointmentmessagesRoute,
    ...appointmentmessagesPopupRoute,
];

@NgModule({
    imports: [
        PfSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AppointmentmessagesComponent,
        AppointmentmessagesDetailComponent,
        AppointmentmessagesDialogComponent,
        AppointmentmessagesDeleteDialogComponent,
        AppointmentmessagesPopupComponent,
        AppointmentmessagesDeletePopupComponent,
    ],
    entryComponents: [
        AppointmentmessagesComponent,
        AppointmentmessagesDialogComponent,
        AppointmentmessagesPopupComponent,
        AppointmentmessagesDeleteDialogComponent,
        AppointmentmessagesDeletePopupComponent,
    ],
    providers: [
        AppointmentmessagesService,
        AppointmentmessagesPopupService,
        AppointmentmessagesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfAppointmentmessagesModule {}
