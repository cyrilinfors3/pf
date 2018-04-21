import { Route } from '@angular/router';

import { BlogComponent } from './';

export const BLOG_ROUTE: Route = {
    path: 'blog',
    component: BlogComponent,
    data: {
        authorities: [],
        pageTitle: 'blog.title'
    }
};
