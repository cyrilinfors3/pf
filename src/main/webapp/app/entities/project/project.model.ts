import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public creationdate?: any,
        public documentaionContentType?: string,
        public documentaion?: any,
        public status?: string,
        public owner?: string,
        public sponsor?: string,
        public coach?: string,
        public votes?: BaseEntity[],
        public projectevolutions?: BaseEntity[],
    ) {
    }
}
