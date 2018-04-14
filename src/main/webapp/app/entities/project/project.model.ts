import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public creationdate?: any,
        public documentaionContentType?: string,
        public documentaion?: any,
        public status?: string,
        public owner?: number,
        public sponsor?: number,
        public coach?: number,
        public votes?: BaseEntity[],
        public projectevolutions?: BaseEntity[],
    ) {
    }
}
