import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfSharedModule } from '../../shared';
import {
    ProjectevolutionService,
    ProjectevolutionPopupService,
    ProjectevolutionComponent,
    ProjectevolutionDetailComponent,
    ProjectevolutionDialogComponent,
    ProjectevolutionPopupComponent,
    ProjectevolutionDeletePopupComponent,
    ProjectevolutionDeleteDialogComponent,
    projectevolutionRoute,
    projectevolutionPopupRoute,
    ProjectevolutionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projectevolutionRoute,
    ...projectevolutionPopupRoute,
];

@NgModule({
    imports: [
        PfSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjectevolutionComponent,
        ProjectevolutionDetailComponent,
        ProjectevolutionDialogComponent,
        ProjectevolutionDeleteDialogComponent,
        ProjectevolutionPopupComponent,
        ProjectevolutionDeletePopupComponent,
    ],
    entryComponents: [
        ProjectevolutionComponent,
        ProjectevolutionDialogComponent,
        ProjectevolutionPopupComponent,
        ProjectevolutionDeleteDialogComponent,
        ProjectevolutionDeletePopupComponent,
    ],
    providers: [
        ProjectevolutionService,
        ProjectevolutionPopupService,
        ProjectevolutionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfProjectevolutionModule {}
