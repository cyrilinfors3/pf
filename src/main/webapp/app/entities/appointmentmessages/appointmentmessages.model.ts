import { BaseEntity } from './../../shared';

export class Appointmentmessages implements BaseEntity {
    constructor(
        public id?: number,
        public project?: number,
        public date?: any,
        public state?: number,
        public message?: string,
        public reply?: string,
        public createdate?: any,
        public coach?: string,
        public owner?: string,
    ) {
    }
}
