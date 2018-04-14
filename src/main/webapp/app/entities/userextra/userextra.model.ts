import { BaseEntity, User } from './../../shared';

export class Userextra implements BaseEntity {
    constructor(
        public id?: number,
        public phone?: string,
        public photoContentType?: string,
        public photo?: any,
        public user?: User,
    ) {
    }
}
