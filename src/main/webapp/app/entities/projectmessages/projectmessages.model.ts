import { BaseEntity } from './../../shared';

export class Projectmessages implements BaseEntity {
    constructor(
        public id?: number,
        public project?: number,
        public date?: any,
        public message?: string,
        public fileContentType?: string,
        public file?: any,
        public sender?: string,
        public receiver?: string,
    ) {
    }
}
