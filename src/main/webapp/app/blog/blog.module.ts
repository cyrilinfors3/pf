import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfSharedModule } from '../shared';

import { BLOG_ROUTE, BlogComponent } from './';

@NgModule({
    imports: [
        PfSharedModule,
        RouterModule.forChild([ BLOG_ROUTE ])
    ],
    declarations: [
        BlogComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfBlogModule {}
