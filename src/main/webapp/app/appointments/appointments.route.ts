import { Route } from '@angular/router';

import { AppointmentsComponent } from './';

export const APPOINTMENTS_ROUTE: Route = {
    path: 'appointments',
    component: AppointmentsComponent,
    data: {
        authorities: [],
        pageTitle: 'Appointments.title'
    }
};
