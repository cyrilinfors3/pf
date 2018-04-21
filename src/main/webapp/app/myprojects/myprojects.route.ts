import { Route } from '@angular/router';

import { MyprojectsComponent } from './';

export const MYPROJECTS_ROUTE: Route = {
    path: 'myprojects',
    component: MyprojectsComponent,
    data: {
        authorities: [],
        pageTitle: 'Myprojects.title'
    }
};
