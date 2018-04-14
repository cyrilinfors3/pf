import { BaseEntity } from './../../shared';

export class Projectevolution implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public documentContentType?: string,
        public document?: any,
        public state?: string,
        public project?: BaseEntity,
    ) {
    }
}
