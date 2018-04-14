import { BaseEntity } from './../../shared';

export class Vote implements BaseEntity {
    constructor(
        public id?: number,
        public rang?: number,
        public project?: BaseEntity,
    ) {
    }
}
